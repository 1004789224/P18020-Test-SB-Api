package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.User;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @AopLog
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result list(@RequestBody UserQueryVo userQueryVo,
                       BindingResult bindingResult) {
        return new Result().setData(userService.listPage(userQueryVo));
    }

    @AopLog
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        UserVo rspVo = userService.findUser(idReqVo.getIid());
        return rspVo == null ? new Result(ErrorCode.PARAMETER_ERROR) : new Result().setData(rspVo);
    }

    @AopLog
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HttpServletRequest req,MultipartFile file,
                       @RequestBody @Valid UserVo userVo, BindingResult bindingResult) {
        Long isOk;
        ImageHolder holder = null;
        if (!file.isEmpty()) {
            try {
                holder = new ImageHolder( file.getInputStream(), file.getName() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //TODO 如果用户未上传头像 从本地中选择一张图片作为默认头像
        }
        if (userVo.getId() != null && userVo.getId() > 0) {
            UserUpdateVo updateVo = new UserUpdateVo();
            BeanUtils.copyProperties( userVo,updateVo );
            isOk = userService.updateUser(updateVo, holder );

        } else {
            isOk = userService.saveUser(userVo);
        }
        return ResultHelper.saveResult(isOk);
    }

    @AopLog
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        long isOk = userService.del(idReqVo.getIid());
        return ResultHelper.delResult(isOk);
    }


    @PostMapping("updateuser")
    @AopLog
    public Result updeteUser(@RequestBody @Valid UserUpdateVo updateVo
            , BindingResult bindingResult, MultipartFile imgFile) {
        ImageHolder imageHolder;
        if (!imgFile.isEmpty()) {
            try {
                imageHolder = new ImageHolder( imgFile.getInputStream(), imgFile.getOriginalFilename() );
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException( "获取上传头像文件流失败" );
            }
        } else {
            //TODO 如果用户未上传头像 从本地中选择一张图片作为默认头像
            imageHolder = null;
        }
        Long isOk = userService.updateUser( updateVo, imageHolder );
        return ResultHelper.saveResult( isOk );
    }

    @PostMapping("modifypwd")
    @AopLog
    public Result modifyPassword(@RequestBody @Valid ModifyUserVo userVo,BindingResult bindingResult){
        if (userVo.getNewPassword().equals( userVo.getOldPassword() )) {
            return new Result( ErrorCode.SAMEPASSWORD );
        }
        final  Long isOk = userService.modifyPassword( userVo );
        return ResultHelper.saveResult( isOk );
    }
}
