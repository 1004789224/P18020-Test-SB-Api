package com.ly.service.impl;

import com.ly.domain.QUser;
import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.AppException;
import com.ly.helper.ErrorCode;
import com.ly.helper.MyPage;
import com.ly.model.UserM;
import com.ly.repository.UserRepository;
import com.ly.service.JwtService;
import com.ly.service.UserService;
import com.ly.util.ImageUtil;
import com.ly.util.MD5Util;
import com.ly.util.Random4CharUtil;
import com.ly.vo.form.ModifyUserVo;
import com.ly.vo.form.UserRegisterVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.update.UserUpdateVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger( UserServiceImpl.class );
    @Autowired
    private UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Override
    @Transactional(readOnly = true)
    public MyPage<UserDto> listPage(UserQueryVo userQueryVo) {
        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.hasText( userQueryVo.getPhone() )) {
            where.and( QUser.user.phone.like( Expressions.asString( "%" ).concat( userQueryVo.getPhone() ).concat( "%" ) ) );
        }


        if (StringUtils.hasText( userQueryVo.getIdnumber() )) {
            where.and( QUser.user.idnumber.like( Expressions.asString( "%" ).concat( userQueryVo.getIdnumber() ).concat( "%" ) ) );
        }
        if (StringUtils.hasText( userQueryVo.getName() )) {
            where.and( QUser.user.name.like( Expressions.asString( "%" ).concat( userQueryVo.getName() ).concat( "%" ) ) );
        }
        Sort sort = new Sort( Sort.Direction.ASC, UserM.ID );
        Pageable page = PageRequest.of( userQueryVo.getNumber(), userQueryVo.getSize(), sort );
        Page<User> componentPage = userRepository.findAll( where, page );
        return getPageDto( componentPage );
    }

    /**
     * 删除用户,软删除,但是删除时将本地中得图片删除掉 节约磁盘空间
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Long del(Long id) {
        User user = userRepository.findById( id ).orElse( null );
        if (user != null) {
            if (user.getImgUrl() != null) {
                ImageUtil.deleteFileOrPath( user.getImgUrl() );
                //TODO 设置为本地默认图片
                user.setImgUrl( " " );
            }
            user.setIsDeleted( 1L );
            user.setGmtModified( new Date() );
        }
        return userRepository.save( user ) == null ? 0L : 1L;
    }

    /**
     * @param userVo 包含老密码,新密码 以及用户id
     *               用户Id在controller中调用JWTService获取
     * @return
     */
    @Override
    @Transactional
    public Long modifyPassword(ModifyUserVo userVo)  {
        User user = userRepository.findById( userVo.getId() ).orElse( null );
        String salt;
        if (user != null) {
            salt = user.getSalt();
            StringBuilder saltPwd = new StringBuilder();
            String oldPassword = (MD5Util.getMD5String( userVo.getOldPassword()+salt));
            if (!oldPassword.equals( user.getPassword() )) {
                return 0L;
            }
            int i = userRepository.updatePassword(
                    userVo.getId(),
                    MD5Util.getMD5String( new StringBuilder()
                            .append( userVo.getNewPassword() )
                            .append( salt )
                            .toString() ),

                    new Date() );
            if (i < 1) {
                return 0L;
            }
            return 1L;
        } else {
            return 0L;
        }
    }

    /**
     * 初始注册时,user只有phone和密码
     *
     * @param registerVo
     * @return
     */
    @Override
    @Transactional
    public UserDto saveUser(UserRegisterVo registerVo) {
        if (null == registerVo) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties( registerVo, user );
        StringBuilder saltPwd = new StringBuilder();
        String saltCode = Random4CharUtil.getSaltCode();
        user.setIsDeleted( 0L );
        user.setSalt( saltCode );
        user.setPassword( MD5Util
                .getMD5String( saltPwd.append( user.getPassword() )
                        .append( saltCode )
                        .toString() ) );
        user.setGmtCreate( new Date() );
        user.setGmtModified( new Date() );
        User save = userRepository.save( user );
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties( save, userDto );
        return userDto;
    }

    /**
     * @param userVo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto login(UserVo userVo) throws AppException {
        User byPhone = userRepository.getUserByPhone( userVo.getPhone() );
        if (byPhone == null) {
            throw new AppException(ErrorCode.WRONG_USER);
        }
        BooleanBuilder where = new BooleanBuilder();
        where.and( QUser.user.phone.eq( userVo.getPhone() ) );
        where.and( QUser.user.password.eq(
                MD5Util.getMD5String( userVo.getPassword()
                        + byPhone.getSalt() ) )
        );
        where.and( QUser.user.isDeleted.ne( 1L ) );
        User user = userRepository.findOne( where ).orElse( null );
        if (user != null) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties( user, userDto );
            return userDto;
        } else {
            return null;
        }
    }

    /**
     * 短信注册成功,用户可编辑个人资料,输入身份证号码,名字,头像
     *
     * @param updateVo
     * @return
     */
    @Override
    @Transactional
    public UserDto updateUser(UserUpdateVo updateVo) {

        User user = userRepository.findById( updateVo.getId() ).orElse( null );
        if (user == null||user.getIsDeleted()==1L) {
            return null;
        }
        //当前用户修改图片  即数据库中本来有图片,用户仍然上传了图片,则删除本地原来的图片
        if (user.getImgUrl() != null && updateVo.getImgUrl() != null) {
            ImageUtil.deleteFileOrPath( user.getImgUrl() );
        }
        if (StringUtils.hasText( updateVo.getName() )) {
            user.setName( updateVo.getName() );
        }
        //TODO 引入token后在token中判断是否有idnumber 有则不允许删改,前台在用户修改界面应该没有idnumber的输入框 idnumber只允许输入一次
        if (StringUtils.hasText( updateVo.getIdnumber() )) {
            user.setIdnumber( updateVo.getIdnumber() );
        }
        if (StringUtils.hasText( updateVo.getImgUrl() )) {
            user.setImgUrl( updateVo.getImgUrl() );
        }
        user.setGmtModified( new Date() );
        if (userRepository.save( user ) == null) {
            return null;
        } else {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties( user, userDto );
            return userDto;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public UserVo findUser(Long id) {
        User user = userRepository.findById( id ).orElse( null );
        UserVo userVo = new UserVo();
        if (user != null&&user.getIsDeleted()!=1L) {
            BeanUtils.copyProperties( user, userVo );
            return userVo;
        }
        return null;
    }

    /**
     * @param userVo
     * @return
     */
    @Override
    @Transactional
    public Long saveUser(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties( userVo, user );
        user.setIsDeleted( 0L );
        user.setGmtCreate( new Date() );
        user.setGmtModified( new Date() );
        String saltCode = Random4CharUtil.getSaltCode();
        user.setSalt( saltCode );
        user.setPassword( MD5Util.getMD5String( user.getPassword() + saltCode ) );
        return userRepository.save( user ) == null ? 0L : 1L;
    }


    private MyPage<UserDto> getPageDto(Page<User> componentPage) {
        List<UserDto> userDtos = new LinkedList<>();
        for (User user : componentPage.getContent()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties( user, userDto );
            userDtos.add( userDto );
        }
        MyPage<UserDto> myPage = new MyPage();
        myPage.setContent( userDtos );
        myPage.setTotalElements( componentPage.getTotalElements() );
        return myPage;
    }

    private List<UserDto> getListDto(List<User> userList) {
        List<UserDto> userDtos = new LinkedList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties( user, userDto );
            userDtos.add( userDto );
        }
        return userDtos;
    }

    @Override
    public List<UserDto> listUser() {
        return null;//getListDto(userRepository.getByIsDeleted(0L));
    }

}