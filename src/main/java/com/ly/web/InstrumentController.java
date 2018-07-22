package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.Instrument;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.InstrumentService;

import com.ly.vo.form.InstrumentVo;
import com.ly.vo.query.InstrumentQueryVo;
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
@RequestMapping(value = "/v1/instrument")
public class InstrumentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentController.class);

    @Autowired
    private InstrumentService instrumentService;
    
    @AopLog
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result list(@RequestBody InstrumentQueryVo instrumentQueryVo,
                       BindingResult bindingResult) {
        return new Result().setData(instrumentService.listPage(instrumentQueryVo));
    }

    @AopLog
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        InstrumentVo rspVo = instrumentService.findInstrument(idReqVo.getIid());
        return rspVo == null ? new Result(ErrorCode.PARAMETER_ERROR) : new Result().setData(rspVo);
    }

    @AopLog
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HttpServletRequest req,
                       @RequestBody @Valid InstrumentVo instrumentVo, BindingResult bindingResult) {
        Long isOk;
        if (instrumentVo.getId() != null && instrumentVo.getId() > 0) {
            isOk = instrumentService.updateInstrument(instrumentVo);
        } else {
            isOk = instrumentService.saveInstrument(instrumentVo);
        }
        return ResultHelper.saveResult(isOk);
    }

    @AopLog
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        long isOk = instrumentService.del(idReqVo.getIid());
        return ResultHelper.delResult(isOk);
    }
}
