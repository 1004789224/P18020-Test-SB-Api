package com.ly.web;

import com.ly.Global;
import com.ly.anon.AopLog;
import com.ly.dto.UserDto;
import com.ly.helper.AppException;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.JwtService;
import com.ly.service.UserService;
import com.ly.util.VerificationUtil;
import com.ly.vo.form.UserRegisterVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.rsp.UserJwtToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private JwtService jwtService;

    /**
     * TODO 调用SMSService来获验证前台传来得短信验证码
     *
     * @return
     */
    @AopLog
    @PostMapping("register")
    public Result register(HttpServletRequest req,
                           HttpServletResponse res,
                           @RequestBody @Valid UserRegisterVo registerVo,
                           BindingResult bindingResult,
                           String vrifyCode) {
        if (!VerificationUtil.VerificationCode( req, vrifyCode )) {
            return new Result( ErrorCode.CODE_IS_ERROR );
        }
        UserDto userDto = userService.saveUser( registerVo );
        if (userDto != null) {
            res.setHeader( Global.TOKEN,  jwtService.getTokenStr( userDto ) );
            return new Result().setData( userDto );
        }
        return new Result(ErrorCode.INNER_WRONG);
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
    public Result login(HttpServletResponse res,
                        @RequestBody @Valid UserVo userVo,
                        BindingResult bindingResult) throws AppException {
        UserDto userDto = userService.login( userVo );
        if (userDto != null) {
            res.setHeader( Global.TOKEN, jwtService.getTokenStr( userDto ) );
            return new Result().setData( userDto );
        }
        return new Result(ErrorCode.WRONG_PHONE_OR_PWD );
    }
}
