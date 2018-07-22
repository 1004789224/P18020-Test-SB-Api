package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.User;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.UserService;

import com.ly.vo.form.UserVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.IdReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public Result save(HttpServletRequest req,
                       @RequestBody @Valid UserVo userVo, BindingResult bindingResult) {
        Long isOk;
        if (userVo.getId() != null && userVo.getId() > 0) {
            isOk = userService.updateUser(userVo);
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
}
