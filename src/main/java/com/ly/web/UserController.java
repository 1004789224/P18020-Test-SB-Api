package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.UserService;

import com.ly.util.ImageHolder;
import com.ly.util.ImageUtil;
import com.ly.util.PathUtil;
import com.ly.vo.form.ModifyUserVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.IdReqVo;
import com.ly.vo.update.UserUpdateVo;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;
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
    public Result updeteUser(
                             @RequestBody @Valid UserUpdateVo updateVo,
                             BindingResult bindingResult
                          ) {
        UserDto userDto = userService.updateUser( updateVo );
        log.debug( "返回的Dto:" + userDto );
        return new Result().setData( userDto );
    }

    @PostMapping("modifypwd")
    @AopLog
    public Result modifyPassword(@RequestBody @Valid ModifyUserVo userVo, BindingResult bindingResult) {
        //todo 从token中拿到user信息 将user的id取出来付给userVo
        if (userVo.getNewPassword().equals( userVo.getOldPassword() )) {
            return new Result( ErrorCode.SAMEPASSWORD );
        }
        final Long isOk = userService.modifyPassword( userVo );
        return ResultHelper.saveResult( isOk );
    }
}

