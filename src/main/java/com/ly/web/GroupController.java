package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.Group;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.helper.ResultHelper;
import com.ly.service.GroupService;

import com.ly.vo.form.GroupVo;
import com.ly.vo.query.GroupQueryVo;
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
@RequestMapping(value = "/v1/group")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;
    
    @AopLog
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result list(@RequestBody GroupQueryVo groupQueryVo,
                       BindingResult bindingResult) {
        return new Result().setData(groupService.listPage(groupQueryVo));
    }

    @AopLog
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        GroupVo rspVo = groupService.findGroup(idReqVo.getIid());
        return rspVo == null ? new Result(ErrorCode.PARAMETER_ERROR) : new Result().setData(rspVo);
    }

    @AopLog
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HttpServletRequest req,
                       @RequestBody @Valid GroupVo groupVo, BindingResult bindingResult) {
        Long isOk;
        if (groupVo.getId() != null && groupVo.getId() > 0) {
            isOk = groupService.updateGroup(groupVo);
        } else {
            isOk = groupService.saveGroup(groupVo);
        }
        return ResultHelper.saveResult(isOk);
    }

    @AopLog
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result del(@RequestBody @Valid IdReqVo idReqVo, BindingResult bindingResult) {
        long isOk = groupService.del(idReqVo.getIid());
        return ResultHelper.delResult(isOk);
    }
}
