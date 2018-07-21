package com.ly.web;

import com.ly.Global;
import com.ly.anon.AopLog;
import com.ly.dto.BorrowerInfoDto;
import com.ly.helper.*;
import com.ly.service.*;
import com.ly.vo.query.BorrowerQueryVo;
import com.ly.vo.query.LendCallbackQueryVo;
import com.ly.vo.query.WithdrawCallbackQueryVo;
import com.ly.vo.req.FBKitLoginReqVo;
import com.ly.vo.req.FBLoginReqVo;
import com.ly.vo.req.RepayReqVo;
import com.ly.vo.rsp.BorrowerRspVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/login")
public class UserLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private LendOrderService lendOrderService;

    @Autowired
    private CallbackService callbackService;
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BorrowerInfoService borrowerInfoService;

    @Autowired
    JwtCheckService jwtCheckService;
    /**
     * facebook登录
     * @param
     * @return
     */
    @AopLog
    @RequestMapping(value = "/facebook", method = RequestMethod.POST)
    public Result facebook(@RequestBody @Valid FBLoginReqVo fbLoginReqVo,
                           BindingResult bindingResult) throws AppException {
        Result result = new Result().setData(userService.fbLogin(fbLoginReqVo));
        return result;
    }

    /**
     * facebookKit登录
     * @param
     * @return
     */
    @AopLog
    @RequestMapping(value = "/fbkit", method = RequestMethod.POST)
    public Result facebookKit(@RequestBody @Valid FBKitLoginReqVo fbKitLoginReqVo,
                           BindingResult bindingResult) throws AppException {
        Result result = new Result().setData(userService.fbKitLogin(fbKitLoginReqVo));
        return result;
    }

    /**
     * 获取首页
     * @param token
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public Result index(@RequestHeader(value = Global.TOKEN) String token)
    {
        Long userId =0L;
        if(StringUtils.hasText(token)){
            userId = jwtCheckService.getUserId(token);
            if(0==userId){
                return new Result(ErrorCode.SESSION_EXPIRE);
            }
        }
        return  productService.findProductByUserId(userId);
    }

    @AopLog
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HttpServletRequest req,
                       @RequestBody @Valid BorrowerInfoDto borrowerInfoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(ErrorCode.PARAMETER_ERROR.getCode(), bindingResult.getAllErrors().toString());
        }
        Long isOk = borrowerInfoService.saveBorrowerInfo(borrowerInfoDto);
        return ResultHelper.saveResult(isOk);
    }

    @AopLog
    @RequestMapping(value = "/lendCallback", method = RequestMethod.POST)
    public Result lendCallback(@RequestBody LendCallbackQueryVo lendCallbackQueryVo) throws AppException {
        LOGGER.info("进入投资回调接口");
        Result result = callbackService.lendCallback(lendCallbackQueryVo);
        return result;
    }

    @AopLog
    @RequestMapping(value = "/withdrawCallback", method = RequestMethod.POST)
    public Result withdrawCallback(@RequestBody @Valid WithdrawCallbackQueryVo withdrawCallbackQueryVo){
        LOGGER.info("进入还款回调接口");
        Result result = callbackService.withdrawCallback(withdrawCallbackQueryVo);
        return result;
    }

    /**
     * 还款更新还款信息
     * @return
     */
    @AopLog
    @RequestMapping(value = "/saveRepay", method = RequestMethod.POST)
    public Result saveRepay(@RequestBody @Valid RepayReqVo repayReqVo, BindingResult bindingResult) throws AppException {
        Long isOk = lendOrderService.saveRepay(repayReqVo);
        return ResultHelper.saveResult(isOk);
    }

    /**
     * 审批控制台查询借款人信息
     * @param borrowerQueryVo
     * @param bindingResult
     * @return
     */
    @AopLog
    @RequestMapping(value = "/findBorrower", method = RequestMethod.POST)
    public Result findBorrower(@RequestBody @Valid BorrowerQueryVo borrowerQueryVo, BindingResult bindingResult) {
        MyPage<BorrowerRspVo> page = borrowerInfoService.findBorrower(borrowerQueryVo);
        return new Result().setData(page);
    }

}
