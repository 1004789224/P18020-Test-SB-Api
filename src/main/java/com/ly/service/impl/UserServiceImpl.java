package com.ly.service.impl;

import com.alibaba.fastjson.JSON;
import com.ly.Global;
import com.ly.domain.Account;
import com.ly.domain.QUser;
import com.ly.domain.User;
import com.ly.dto.*;
import com.ly.enums.AccountEnum;
import com.ly.enums.AuthenticationStatusEnum;
import com.ly.helper.*;
import com.ly.model.UserM;
import com.ly.repository.UserRepository;
import com.ly.service.*;
import com.ly.vo.UserIdStatusVo;
import com.ly.vo.form.*;
import com.ly.service.AccountService;
import com.ly.service.HttpService;
import com.ly.service.UserService;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.req.KtpInfoReqVo;
import com.ly.vo.req.UserInfo;
import com.ly.vo.form.UserVo;
import com.ly.vo.req.FBKitLoginReqVo;
import com.ly.vo.req.FBLoginReqVo;
import com.ly.vo.rsp.FBLoginRspVo;
import com.ly.vo.rsp.KtpInfoRspVo;
import com.ly.vo.rsp.UserInfoRspVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import io.jsonwebtoken.SignatureAlgorithm;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import io.jsonwebtoken.Jwts;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpService httpService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${jwt.key}")
    private String jwtKey = "onecard666";

    @Value("${fb.access.key}")
    private String fb_access_key = "350a57c0f53d3d35f5c6b2247b91b9e4";

    @Value("${check.token}")
    private int isCheckToken = 1;

    @Value("${session.time}")
    private Long sessionTime = 1209600000L;


    private final int LOGIN_OK = 1;

    private final int REGISTER_OK = 10;

    private final int NEED_REGISTER = 2;


    @Autowired
    private AccountService accountService;

    @Autowired
    private UserBankService userBankService;
    @Autowired
    private LendOrderService lendOrderService;

    @Autowired
    private CapitalFlowService capitalFlowService;

    @Override
    public MyPage<UserDto> listPage(UserQueryVo userQueryVo) {
        BooleanBuilder where = new BooleanBuilder();

        if (StringUtils.hasText(userQueryVo.getPhone())) {
            where.and(QUser.user.phone.like(Expressions.asString("%").concat(userQueryVo.getPhone()).concat("%")));
        }

        Sort sort = new Sort(Sort.Direction.DESC, UserM.ID);
        Pageable page = new PageRequest(userQueryVo.getNumber(), userQueryVo.getSize(), sort);
        Page<User> componentPage = userRepository.findAll(where, page);
        return getPageDto(componentPage);
    }

    @Override
    public List<UserDto> listUser() {
        return getListDto(userRepository.getByIsDeleted(0L));
    }

    @Override
    public FBLoginRspVo fbLogin(FBLoginReqVo fbLoginReqVo) throws AppException {
        //TODO 验证Facebook Token
        LOGGER.info("isCheckToken:"+isCheckToken);
        try {
            String fbtoken = fbLoginReqVo.getFbtoken();
            LOGGER.info("fbtoken:"+fbtoken);
            StringBuilder sbStr = new StringBuilder(fbtoken);
            sbStr.append("&access_token=");
            //TODO access_token：应用访问口令，或者应用开发者的访问口令。
            sbStr.append(fb_access_key);
            LOGGER.info("sbStr:"+sbStr.toString());
            Response response = httpService.doGetFB(sbStr.toString());
            LOGGER.info("facebookResponse:"+response);
            String res = response.body().string();
            LOGGER.info("faceBookReturn:"+res);
//            String res = "{\"id\":\"548053555544328\",\"phone\":{\"number\":\"+8615910649102\",\"country_prefix\":\"86\",\"national_number\":\"15910649102\"},\"application\":{\"id\":\"164316504172322\"}}";
            FaceBookLoginRspDto faceBookLoginRspDto = JSON.parseObject(res, FaceBookLoginRspDto.class);
            if (!faceBookLoginRspDto.getData().getUser_id().equals(fbLoginReqVo.getFbuserid())){
                LOGGER.info("faceBookLoginRspDto.getUser_id():"+faceBookLoginRspDto.getData().getUser_id()+"|"+"fbLoginReqVo.getFbuserid():"+fbLoginReqVo.getFbuserid());
                throw new AppException(ErrorCode.PARAMETER_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new AppException(ErrorCode.PARAMETER_ERROR);
        }

        //根据fbuserid 查询User
        User user = userRepository.findOne(QUser.user.fbuserid.eq(fbLoginReqVo.getFbuserid()));
        FBLoginRspVo rspVo = new FBLoginRspVo();
        if (user != null){
            if (StringUtils.hasText(user.getPhone())){
                rspVo.setResult(LOGIN_OK);
                String token = getJwtToken(user);
                rspVo.setToken(token);
                // 存入Redis
                saveRedis(user.getId(), token);
                rspVo.setPhone(PhoneUtil.getLikePhone(user.getPhone()));
                return rspVo;
            }
        }
        rspVo.setResult(NEED_REGISTER);
        rspVo.setToken("");
        return rspVo;
    }

    @Override
    public FBLoginRspVo fbKitLogin(FBKitLoginReqVo fbKitLoginReqVo) throws AppException {
        //获取token
        LOGGER.info("isCheckToken:"+isCheckToken);
        try {
            String authorizationCode = fbKitLoginReqVo.getAuthorizationCode();
            LOGGER.info(authorizationCode);
            // 通过authorizationCode获取kittoken和kitid
            Response response = httpService.doGetNoAuth(authorizationCode);
            String res = response.body().string();
            LOGGER.info(res);
            FBKitLoginDto faceBookRspDto = JSON.parseObject(res, FBKitLoginDto.class);
            if (null == faceBookRspDto.getAccessToken() || null == faceBookRspDto.getId()){
                throw new AppException(ErrorCode.PARAMETER_ERROR);
            }
            fbKitLoginReqVo.setKitid(faceBookRspDto.getId());
            fbKitLoginReqVo.setKittoken(faceBookRspDto.getAccessToken());
            // 获取用户Phone
            Response response1 = httpService.doGetFBKitPhone(faceBookRspDto.getAccessToken());
            String res1 = response1.body().string();
            FBKitLoginDataDto fbKitLoginDataDto = JSON.parseObject(res1, FBKitLoginDataDto.class);
            if (null == fbKitLoginDataDto.getPhone()){
                throw new AppException(ErrorCode.PARAMETER_ERROR);
            }
            fbKitLoginReqVo.setPhone(fbKitLoginDataDto.getPhone().getNationalNumber());
        } catch (Exception e) {
            throw new AppException(ErrorCode.PARAMETER_ERROR);
        }

        // 电话号码检查用户 
        User user = userRepository.findOne(QUser.user.phone.eq(fbKitLoginReqVo.getPhone()));
        FBLoginRspVo rspVo = new FBLoginRspVo();
        if (null == user) {
            //检查KitId
            User kitUser = userRepository.findOne(QUser.user.kitid.eq(fbKitLoginReqVo.getKitid()));
            if (kitUser != null){
                throw new AppException(ErrorCode.DATA_REPEAT_ERROR);
            }

            //检查fbuserid
            if (StringUtils.hasText(fbKitLoginReqVo.getFbuserid())) {
                User fbUser = userRepository.findOne(QUser.user.fbuserid.eq(fbKitLoginReqVo.getFbuserid()));
                if (fbUser != null){
                    throw new AppException(ErrorCode.DATA_REPEAT_ERROR);
                }
            }

            //新建
            User newUser = new User();
            newUser.setPhone(fbKitLoginReqVo.getPhone());
            newUser.setKitid(fbKitLoginReqVo.getKitid());
            newUser.setKittoken(fbKitLoginReqVo.getKittoken());
            if (StringUtils.hasText(fbKitLoginReqVo.getFbtoken())) {
                newUser.setFbtoken(fbKitLoginReqVo.getFbtoken());
            }
            if (StringUtils.hasText(fbKitLoginReqVo.getFbuserid())) {
                newUser.setFbuserid(fbKitLoginReqVo.getFbuserid());
            } else {
                newUser.setFbuserid(newUser.getPhone());
            }

            newUser.setAuthenticationStatus(AuthenticationStatusEnum.KTP_NOT_CERTIFIED.name());
            newUser.setPassword("111");
            newUser.setSalt("111");
            newUser.setGmtCreate(new Date());
            newUser.setIsDeleted(0L);
            newUser.setKtpNo(newUser.getPhone());
            User userRtn = userRepository.save(newUser);

            // 卡片初始化
            Account account = new Account();
            account.setUserId(userRtn.getId());
            account.setStatus(AccountEnum.NO_ACTION.name());
            accountService.saveAccount(account);
            rspVo.setResult(REGISTER_OK);
            String token = getJwtToken(newUser);
            rspVo.setToken(token);
            // 登录存入Redis
            saveRedis(userRtn.getId(), token);
            rspVo.setPhone(PhoneUtil.getLikePhone(newUser.getPhone()));
            return rspVo;
        } else {
            //判断
            if (StringUtils.hasText(fbKitLoginReqVo.getFbuserid()) && StringUtils.hasText(user.getFbuserid()) && !user.getPhone().equals(user.getFbuserid())) {
                if (!user.getFbuserid().equals(fbKitLoginReqVo.getFbuserid())) {
                    throw new AppException(ErrorCode.FB_ALREADY_BIND);
                }
            }

            if (StringUtils.hasText(fbKitLoginReqVo.getFbuserid())) {
                user.setFbuserid(fbKitLoginReqVo.getFbuserid());
            }

            //修改
            if (StringUtils.hasText(fbKitLoginReqVo.getFbtoken())) {
                user.setFbtoken(fbKitLoginReqVo.getFbtoken());
            }

            user.setKitid(fbKitLoginReqVo.getKitid());
            user.setKittoken(fbKitLoginReqVo.getKittoken());
            userRepository.save(user);
            rspVo.setResult(LOGIN_OK);
            String token = getJwtToken(user);
            rspVo.setToken(token);
            // 登录存入Redis
            saveRedis(user.getId(), token);
            rspVo.setPhone(PhoneUtil.getLikePhone(user.getPhone()));
            return rspVo;
        }
    }

    private String getJwtToken(User user) {
        //返回TOKEN
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Global.PHONE, user.getPhone());
        claims.put(Global.USER_ID, user.getId());
        String jwttoken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .setExpiration(new Date(System.currentTimeMillis() + sessionTime))
                .compact();
        return jwttoken;
    }

    private void saveRedis(Long userId, String token) throws AppException
    {
        StringBuilder sb = new StringBuilder();
        String key = sb.append(Global.P2P_USER_ID).append(userId).toString();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if (operations == null){
            throw new AppException(ErrorCode.REDIS_NOT_CONN);
        }
        operations.set(key, token, sessionTime, TimeUnit.SECONDS);

    }



    @Override
    public UserVo findUser(Long id) {
        User user = userRepository.findOne(QUser.user.id.eq(id.longValue()));
        UserVo userVo = new UserVo();
        if (user != null){
            BeanUtils.copyProperties(user, userVo);
            return userVo;
        }
        return null;
    }

    @Override
    public Long saveUser(User user) {
        user.setIsDeleted(0L);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        return userRepository.save(user) == null ? 0L : 1L;
    }

    @Override
    public Long updateUser(UserVo userVo) {
        User user = userRepository.findOne(userVo.getId());
        if (user == null) {
            return 0L;
        }
        BeanUtils.copyProperties(userVo, user);
        return userRepository.save(user) == null ? 0L : 1L;
    }

    @Override
    public Long del(Long id) {
        User user = userRepository.findOne(id);
        return userRepository.save(user) == null ? 0L : 1L;
    }

    private MyPage<UserDto> getPageDto(Page<User> componentPage){
        List<UserDto> userDtos = new LinkedList<>();
        for (User user : componentPage.getContent()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtos.add(userDto);
        }
        MyPage<UserDto> myPage = new MyPage();
        myPage.setContent(userDtos);
        myPage.setTotalElements(componentPage.getTotalElements());
        return myPage;
    }

    private List<UserDto> getListDto(List<User> userList){
        List<UserDto> userDtos = new LinkedList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public Result getUserAllInfo(Long id) {
        if(id<1){
            return new Result(ErrorCode.PARAMETER_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        UserVo user = this.getUser(id);
        //账户信息
        AccountVo account = accountService.findAccount(id);
        //个人银行卡信息
        List<UserBankVo> userBankVos = userBankService.findUserBank(id);
        //出借记录
        List<LendOrderVo> lendOrderVos = lendOrderService.findLendOrder(id);
        //资金流水
        List<CapitalFlowVo> capitalFlowVos = capitalFlowService.findCapitalFlow(id);
        userInfo.setUserVo(user);
        userInfo.setAccountVo(account);
        userInfo.setUserBankVos(userBankVos);
        userInfo.setLendOrderVos(lendOrderVos);
        userInfo.setCapitalFlowVos(capitalFlowVos);
        return new Result().setData(userInfo);
    }

    @Override
    public Long saveKtpInfo(KtpInfoReqVo ktpInfoReqVo) throws AppException {
        // 判断ktp账号是否重复
        BooleanBuilder where = new BooleanBuilder();
        where.andNot(QUser.user.id.eq(ktpInfoReqVo.getUserId().longValue()));
        where.and(QUser.user.ktpNo.eq(ktpInfoReqVo.getKtpNo()));
        User one = userRepository.findOne(where);
        if (one != null){
            throw new AppException(ErrorCode.KTPNO_IS_REGISTER);
        }
        User user = userRepository.findOne(QUser.user.id.eq(ktpInfoReqVo.getUserId().longValue()));
        if (null != user){
            if (user.getAuthenticationStatus().equals(AuthenticationStatusEnum.PASSED_THE_CERTIFICATION.name())){
                throw new AppException(ErrorCode.ACCOUNT_ACTION);
            }
            user.setKtpNo(ktpInfoReqVo.getKtpNo());
            user.setName(ktpInfoReqVo.getName());
            user.setAuthenticationStatus(AuthenticationStatusEnum.BANK_NOT_CERTIFIED.name());
            user.setGmtModified(new Date());
            return userRepository.save(user) == null ? 0L : 1L;
        }
        return 0L;
    }

    @Override
    public KtpInfoRspVo getKtpInfo(Long userId) {
        KtpInfoRspVo ktpInfoRspVo = new KtpInfoRspVo();
        User user = userRepository.findOne(QUser.user.id.eq(userId.longValue()));
        if (null != user){
            ktpInfoRspVo.setKtpNo(user.getKtpNo());
            ktpInfoRspVo.setName(user.getName());
        }
        return ktpInfoRspVo;
    }

    @Override
    public UserInfoRspVo getUserInfo(Long userId) {
        UserInfoRspVo userInfoRspVo = new UserInfoRspVo();
        User user = userRepository.findOne(QUser.user.id.eq(userId.longValue()));
        if (null != user){
            userInfoRspVo.setAuthenticationStatus(user.getAuthenticationStatus());
            userInfoRspVo.setName(user.getName());
            userInfoRspVo.setPhone(PhoneUtil.getLikePhone(user.getPhone()));
        }
        return userInfoRspVo;
    }

    @Override
    public Long updateStatus(UserIdStatusVo status) {
        User user = userRepository.findOne(QUser.user.id.eq(status.getUserId().longValue()));
        if (null != user){
            user.setAuthenticationStatus(status.getStatus());
            return userRepository.save(user) == null ? 0L : 1L;
        }
        return 0L;
    }

    @Override
    public UserVo getUser(Long userId) {
        User user = userRepository.findOne(userId);
        if(null!=user){
            UserVo userVo = new UserVo();
            userVo.setPhone(PhoneUtil.getLikePhone(user.getPhone()));
            userVo.setName(user.getName());
            userVo.setId(userId);
            return userVo;
        }
        return null;
    }
}