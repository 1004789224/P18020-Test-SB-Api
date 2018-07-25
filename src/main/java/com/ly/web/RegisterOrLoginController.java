package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.UserService;
import com.ly.util.VerificationUtil;
import com.ly.vo.form.UserRegisterVo;
import com.ly.vo.form.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.web
 * @date 2018/7/22
 */
@RestController
@RequestMapping("v1")
public class RegisterOrLoginController {
    @Autowired
    private UserService userService;

    /**
     * TODO 调用SMSService来获验证前台传来得短信验证码
     *
     * @return
     */
    @AopLog
    @PostMapping("register")
    public Result register(HttpServletRequest req,
                           @RequestBody @Valid UserRegisterVo registerVo,
                           BindingResult bindingResult, String vrifyCode) {
        if (!VerificationUtil.VerificationCode( req, vrifyCode )) {
            return ResultHelper.VerificationHelp();
        }
        Long isOk = userService.saveUser( registerVo );
        return ResultHelper.saveResult( isOk );
    }

    /**
     * TODO 获取前台传来的手机号码
     * TODO 判断手机号码是否已注册
     * TODO 注册则提示错误 否则发送短信验证码
     */
    @GetMapping("getphonevc")
    public Result getPhoneVC() {
        return null;
    }

    @PostMapping("login")
    @AopLog
    public Result login(@RequestBody @Valid UserVo userVo, BindingResult bindingResult) {
        UserDto userDto = userService.login( userVo );
        return userDto==null?new Result(ErrorCode. WRONGPHONEORPWD):
                new Result(  ).setData( userDto );
    }
}
