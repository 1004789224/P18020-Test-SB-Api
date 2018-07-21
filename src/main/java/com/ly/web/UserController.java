package com.ly.web;

import com.ly.Global;
import com.ly.anon.AopLog;
import com.ly.domain.Product;
import com.ly.domain.User;
import com.ly.helper.AppException;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.LendOrderService;
import com.ly.service.ProductService;
import com.ly.service.UserService;

import com.ly.vo.form.LendOrderVo;
import com.ly.vo.form.ProductVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.IdReqVo;
import com.ly.vo.req.IndexVo;
import com.ly.vo.req.KtpInfoReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.rmi.dgc.Lease;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     *  保存ktp信息
     * @param ktpInfoReqVo
     * @return
     */
    @AopLog
    @RequestMapping(value = "/saveKtpInfo", method = RequestMethod.POST)
    public Result saveKtpInfo(@RequestAttribute(Global.USER_ID) Long userId,
                              @RequestBody @Valid KtpInfoReqVo ktpInfoReqVo, BindingResult bindingResult) throws AppException {
        ktpInfoReqVo.setUserId(userId);
        return new Result().setData(userService.saveKtpInfo(ktpInfoReqVo));
    }

    /**
     *  查询ktp信息
     * @param userId
     * @return
     */
    @AopLog
    @RequestMapping(value = "/getKtpInfo", method = RequestMethod.POST)
    public Result getKtpInfo(@RequestAttribute(Global.USER_ID) Long userId) {
        return new Result().setData(userService.getKtpInfo(userId));
    }

    /**
     *  查询用户基本信息
     * @param userId
     * @return
     */
    @AopLog
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public Result getUserInfo(@RequestAttribute(Global.USER_ID) Long userId) {
        return new Result().setData(userService.getUserInfo(userId));
    }

    /**
     *  我的页面
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserAllInfo", method = RequestMethod.POST)
    public Result getUserAllInfo(@RequestAttribute(value = Global.USER_ID) Long userId) {
        return userService.getUserAllInfo(userId);
    }

}
