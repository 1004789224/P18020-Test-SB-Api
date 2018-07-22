package com.ly.service;

import com.ly.domain.Instrument;
import com.ly.dto.InstrumentDto;
import com.ly.helper.MyPage;
import com.ly.vo.query.InstrumentQueryVo;
import com.ly.vo.form.InstrumentVo;
import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface InstrumentService {

    InstrumentVo findInstrument(Long id);

    Long saveInstrument(InstrumentVo instrumentVo);

    Long updateInstrument(InstrumentVo instrumentVo);

    Long del(Long id);

    MyPage<InstrumentDto> listPage(InstrumentQueryVo instrumentQueryVo);

    List<InstrumentDto> listInstrument();

}