package com.ly.web;

import com.ly.Global;
import com.ly.anon.AopLog;
import com.ly.dto.UserDto;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.JwtService;
import com.ly.service.UserService;

import com.ly.vo.form.ModifyUserVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.IdReqVo;
import com.ly.vo.rsp.UserJwtToken;
import com.ly.vo.update.UserUpdateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;
    @Autowired
    JwtService jwtService;
    private static Logger log = LoggerFactory.getLogger( UserController.class );

    @AopLog
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result list(@RequestBody UserQueryVo userQueryVo,
                       BindingResult bindingResult) {
        return new Result().setData( userService.listPage( userQueryVo ) );
    }

    @AopLog
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        long isOk = userService.del( idReqVo.getIid() );
        return ResultHelper.delResult( isOk );
    }


    @PostMapping("updateuser")
    @AopLog
    public Result updeteUser(HttpServletRequest request,
                             HttpServletResponse res,
                             @RequestBody @Valid UserUpdateVo updateVo,
                             BindingResult bindingResult
    ) {
        String token = request.getHeader( Global.TOKEN );
        UserJwtToken userJwtToken = (UserJwtToken) jwtService.getOneObject( token, UserJwtToken.class );
        if (null == userJwtToken || userJwtToken.getId() == null || userJwtToken.getId() > 0) {
            return new Result(ErrorCode.SESSION_EXPIRE);
        }
        updateVo.setId( userJwtToken.getId() );
        UserDto userDto = userService.updateUser( updateVo );
        UserJwtToken newToken = new UserJwtToken();
        BeanUtils.copyProperties( userDto, newToken );
        String tokenStr = jwtService.getTokenStr( newToken );
        res.setHeader( Global.TOKEN, tokenStr );
        return new Result().setData( userDto );
    }

    @PostMapping("modifypwd")
    @AopLog
    public Result modifyPassword(HttpServletRequest request, @RequestBody @Valid ModifyUserVo userVo, BindingResult bindingResult) {
        if (userVo.getNewPassword().equals( userVo.getOldPassword() )) {
            return new Result( ErrorCode.SAME_PASSWORD );
        }
        String token = request.getHeader( Global.TOKEN );
        UserJwtToken userJwtToken = (UserJwtToken) jwtService.getOneObject( token, UserJwtToken.class );
        if (null == userJwtToken || userJwtToken.getId() == null || userJwtToken.getId() <= 0) {
            return new Result(ErrorCode.SESSION_EXPIRE);
        }
        userVo.setId( userJwtToken.getId() );
        final Long isOk = userService.modifyPassword( userVo );
        return ResultHelper.saveResult( isOk );
    }
}

