package com.ly.web;

import com.ly.Global;
import com.ly.anon.AopLog;
import com.ly.domain.SmsCode;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.SmsCodeService;

import com.ly.vo.form.SmsCodeVo;
import com.ly.vo.query.SmsCodeQueryVo;
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
@RequestMapping(value = "/v1/smsCode")
public class SmsCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsCodeController.class);

    @Autowired
    private SmsCodeService smsCodeService;
    
    @AopLog
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result list(@RequestHeader(Global.USER_ID) Long userId,
                       @RequestBody SmsCodeQueryVo smsCodeQueryVo,
                       BindingResult bindingResult) {
        smsCodeQueryVo.setUserId(userId);
        return new Result().setData(smsCodeService.listPage(smsCodeQueryVo));
    }

    @AopLog
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(@RequestHeader(Global.USER_ID) Long userId,
                      @RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        SmsCodeVo rspVo = smsCodeService.findSmsCode(idReqVo.getIid());
        return rspVo == null ? new Result(ErrorCode.PARAMETER_ERROR) : new Result().setData(rspVo);
    }

    @AopLog
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(Global.USER_ID) Long userId,
                       @RequestBody @Valid SmsCodeVo smsCodeVo, BindingResult bindingResult) {
        smsCodeVo.setUserId(userId);
        Long isOk;
        if (smsCodeVo.getId() != null && smsCodeVo.getId() > 0) {
            isOk = smsCodeService.updateSmsCode(smsCodeVo);
        } else {
            isOk = smsCodeService.saveSmsCode(smsCodeVo);
        }
        return ResultHelper.saveResult(isOk);
    }

    @AopLog
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestHeader(Global.USER_ID) Long userId,
                    @RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        long isOk = smsCodeService.del(idReqVo.getIid());
        return ResultHelper.delResult(isOk);
    }
}
