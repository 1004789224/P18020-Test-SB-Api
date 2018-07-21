package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.Common;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.CommonService;

import com.ly.vo.form.CommonVo;
import com.ly.vo.query.CommonQueryVo;
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
@RequestMapping(value = "/v1/common")
public class CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService commonService;
    @AopLog
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result list(@RequestBody CommonQueryVo commonQueryVo,
                       BindingResult bindingResult) {
        return new Result().setData(commonService.listPage(commonQueryVo));
    }

    @AopLog
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        CommonVo rspVo = commonService.findCommon(idReqVo.getIid());
        return rspVo == null ? new Result(ErrorCode.PARAMETER_ERROR) : new Result().setData(rspVo);
    }

    @AopLog
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HttpServletRequest req,
                       @RequestBody @Valid CommonVo commonVo, BindingResult bindingResult) {
        Long isOk;
        if (commonVo.getId() != null && commonVo.getId() > 0) {
            isOk = commonService.updateCommon(commonVo);
        } else {
            isOk = commonService.saveCommon(commonVo);
        }
        return ResultHelper.saveResult(isOk);
    }

    @AopLog
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        long isOk = commonService.del(idReqVo.getIid());
        return ResultHelper.delResult(isOk);
    }
}
