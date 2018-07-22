package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.Banner;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.BannerService;

import com.ly.vo.form.BannerVo;
import com.ly.vo.query.BannerQueryVo;
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
@RequestMapping(value = "/v1/banner")
public class BannerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BannerController.class);

    @Autowired
    private BannerService bannerService;
    
    @AopLog
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result list(@RequestBody BannerQueryVo bannerQueryVo,
                       BindingResult bindingResult) {
        return new Result().setData(bannerService.listPage(bannerQueryVo));
    }

    @AopLog
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        BannerVo rspVo = bannerService.findBanner(idReqVo.getIid());
        return rspVo == null ? new Result(ErrorCode.PARAMETER_ERROR) : new Result().setData(rspVo);
    }

    @AopLog
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HttpServletRequest req,
                       @RequestBody @Valid BannerVo bannerVo, BindingResult bindingResult) {
        Long isOk;
        if (bannerVo.getId() != null && bannerVo.getId() > 0) {
            isOk = bannerService.updateBanner(bannerVo);
        } else {
            isOk = bannerService.saveBanner(bannerVo);
        }
        return ResultHelper.saveResult(isOk);
    }

    @AopLog
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        long isOk = bannerService.del(idReqVo.getIid());
        return ResultHelper.delResult(isOk);
    }
}
