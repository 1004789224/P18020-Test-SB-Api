package com.ly.service.impl;

import com.ly.domain.Common;
import com.ly.domain.QInstrument;
import com.ly.domain.Instrument;
import com.ly.dto.InstrumentDto;
import com.ly.enums.CommonCode;
import com.ly.enums.InstrumentStateEnum;
import com.ly.helper.MyPage;
import com.ly.model.InstrumentM;
import com.ly.repository.InstrumentRepository;
import com.ly.service.CommonService;
import com.ly.service.InstrumentService;
import com.ly.util.ImageUtil;
import com.ly.vo.form.CommonVo;
import com.ly.vo.query.InstrumentQueryVo;
import com.ly.vo.form.InstrumentVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class InstrumentServiceImpl implements InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;
    @Autowired
    private CommonService commonService;

    @Override
    public MyPage<InstrumentDto> listPage(InstrumentQueryVo instrumentQueryVo) {
        BooleanBuilder where = new BooleanBuilder();
        if (instrumentQueryVo.getCategroyId() != null && instrumentQueryVo.getCategroyId() > 0) {
            where.and( QInstrument.instrument.categroyId.eq( instrumentQueryVo.getCategroyId().longValue() ) );
        }

        if (instrumentQueryVo.getServiceMethodId() != null && instrumentQueryVo.getServiceMethodId() > 0) {
            where.and( QInstrument.instrument.serviceMethodId.eq( instrumentQueryVo.getServiceMethodId().longValue() ) );
        }

        if (StringUtils.hasText( instrumentQueryVo.getCode() )) {
            where.and( QInstrument.instrument.code.like( Expressions.asString( "%" ).concat( instrumentQueryVo.getCode() ).concat( "%" ) ) );
        }


        if (StringUtils.hasText( instrumentQueryVo.getName() )) {
            where.and( QInstrument.instrument.name.like( Expressions.asString( "%" ).concat( instrumentQueryVo.getName() ).concat( "%" ) ) );
        }

        if (instrumentQueryVo.getGroupId() != null && instrumentQueryVo.getGroupId() > 0) {
            where.and( QInstrument.instrument.groupId.eq( instrumentQueryVo.getGroupId().longValue() ) );
        }
        if (StringUtils.hasText( instrumentQueryVo.getState() )) {
            where.and( QInstrument.instrument.state.eq( instrumentQueryVo.getState() ) );
        }
        where.and( QInstrument.instrument.isDeleted.eq( 0L ) );
        Sort sort = new Sort( Sort.Direction.DESC, InstrumentM.OREDERNUM );
        Pageable page = PageRequest.of( instrumentQueryVo.getNumber(), instrumentQueryVo.getSize(), sort );
        Page<Instrument> componentPage = instrumentRepository.findAll( where, page );
        return getPageDto( componentPage );
    }

    @Override
    public List<InstrumentDto> listInstrument() {

        return null;//getListDto(instrumentRepository.getByIsDeleted(0L));
    }

    @Override
    public Long updateInstrumentState(Long id, CommonVo state) {
        Instrument instrument = instrumentRepository.findById( id ).orElse( null );
        if (instrument != null) {
            if (state != null&&state.getValue()!=null) {
                if ("USED".equals( state.getValue() )) {
                    instrument.setOredernum( instrument.getOredernum() + 1 );
                }
                instrument.setState( state.getName() );
            } else {
                return 0L;
            }
            return instrumentRepository.save( instrument ) == null ? 0L : 1L;
        }
        return 0L;
    }

    @Override
    public InstrumentDto findInstrument(Long id) {
        Instrument instrument = instrumentRepository.findById( id ).orElse( null );
        InstrumentDto instrumentDto = new InstrumentDto();
        if (instrument != null) {
            BeanUtils.copyProperties( instrument, instrumentDto );
            getCateGoryAndServiceMethod( instrument, instrumentDto );
            return instrumentDto;
        }
        return null;
    }

    @Override
    public Long saveInstrument(InstrumentVo instrumentVo) {
        Instrument instrument = new Instrument();
        BeanUtils.copyProperties( instrumentVo, instrument );
        instrument.setIsDeleted( 0L );
        instrument.setGmtCreate( new Date() );
        instrument.setGmtModified( new Date() );
        return instrumentRepository.save( instrument ) == null ? 0L : 1L;
    }

    @Override
    public Long updateInstrument(InstrumentVo instrumentVo) {
        Instrument instrument = instrumentRepository.findById( instrumentVo.getId() ).orElse( null );
        if (instrument == null) {
            return 0L;
        }
        BeanUtils.copyProperties( instrumentVo, instrument );
        instrument.setGmtModified( new Date() );
        return instrumentRepository.save( instrument ) == null ? 0L : 1L;
    }

    /**
     * 根据id删除指定仪器 软删除 但删除磁盘中的仪器图片
     *
     * @param id
     * @return
     */
    @Override
    public Long del(Long id) {
        Instrument instrument = instrumentRepository.findById( id ).orElse( null );
        if (instrument != null) {
            if (instrument.getImgUrl() != null) {
                ImageUtil.deleteFileOrPath( instrument.getImgUrl() );
                instrument.setImgUrl( " " );
            }
            instrument.setIsDeleted( 1L );
            instrument.setGmtModified( new Date() );
        } else {
            return 0L;
        }
        return instrumentRepository.save( instrument ) == null ? 0L : 1L;
    }

    private MyPage<InstrumentDto> getPageDto(Page<Instrument> componentPage) {
        List<InstrumentDto> instrumentDtos = new LinkedList<>();
        for (Instrument instrument : componentPage.getContent()) {
            InstrumentDto instrumentDto = new InstrumentDto();
            BeanUtils.copyProperties( instrument, instrumentDto );
            getCateGoryAndServiceMethod( instrument, instrumentDto );
            instrumentDtos.add( instrumentDto );
        }
        MyPage<InstrumentDto> myPage = new MyPage();
        myPage.setContent( instrumentDtos );
        myPage.setTotalElements( componentPage.getTotalElements() );
        return myPage;
    }

    private List<InstrumentDto> getListDto(List<Instrument> instrumentList) {
        List<InstrumentDto> instrumentDtos = new LinkedList<>();
        for (Instrument instrument : instrumentList) {
            InstrumentDto instrumentDto = new InstrumentDto();
            BeanUtils.copyProperties( instrument, instrumentDto );
            getCateGoryAndServiceMethod( instrument, instrumentDto );
            instrumentDtos.add( instrumentDto );
        }
        return instrumentDtos;
    }

    private InstrumentDto getCateGoryAndServiceMethod(Instrument instrument
            ,InstrumentDto instrumentDto) {
        if (instrument.getCategroyId() != null && instrument.getCategroyId() > 0) {
            CommonVo category = commonService.findCommon( instrument.getCategroyId() );
            instrumentDto.setCategory( category.getName() );
        }
        if (instrument.getServiceMethodId() != null && instrument.getServiceMethodId() > 0) {
            CommonVo serviceMethod = commonService
                    .findCommon( instrument.getServiceMethodId() );
            instrumentDto.setServiceMethod( serviceMethod.getName() );
        }
        return instrumentDto;
    }
}