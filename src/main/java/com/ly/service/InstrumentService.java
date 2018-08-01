package com.ly.service;

import com.ly.domain.Common;
import com.ly.domain.Instrument;
import com.ly.dto.InstrumentDto;
import com.ly.enums.InstrumentStateEnum;
import com.ly.helper.MyPage;
import com.ly.vo.form.CommonVo;
import com.ly.vo.query.InstrumentQueryVo;
import com.ly.vo.form.InstrumentVo;

import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */

public interface InstrumentService {
    //TODO is.1 预约处理 仪器租借一次  就把仪器的orderNum+1 以便统计使用热度 同时仪器当前状态变为使用中
    //TODO is.2 每一个组织都可以查看自己的仪器列表
    //TODO is.3 仪器添加  删除  修改
    InstrumentDto findInstrument(Long id);

    Long saveInstrument(InstrumentVo instrumentVo);

    Long updateInstrument(InstrumentVo instrumentVo);

    Long del(Long id);

    MyPage<InstrumentDto> listPage(InstrumentQueryVo instrumentQueryVo);

    List<InstrumentDto> listInstrument();

    Long updateInstrumentState(Long id,String  stateName);


}