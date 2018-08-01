package com.ly.service.impl;

import com.ly.domain.QCommon;
import com.ly.domain.Common;
import com.ly.dto.CommonDto;
import com.ly.helper.MyPage;
import com.ly.model.CommonM;
import com.ly.repository.CommonRepository;
import com.ly.service.CommonService;
import com.ly.vo.query.CommonQueryVo;
import com.ly.vo.form.CommonVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonRepository commonRepository;
    
    @Override
    @Transactional(readOnly = true)
    public MyPage<CommonDto> listPage(CommonQueryVo commonQueryVo) {
        BooleanBuilder where = new BooleanBuilder();


        if (StringUtils.hasText(commonQueryVo.getName())) {
            where.and(QCommon.common.name.like(Expressions.asString("%").concat(commonQueryVo.getName()).concat("%")));
        }


        if (StringUtils.hasText(commonQueryVo.getCode())) {
            where.and(QCommon.common.code.like(Expressions.asString("%").concat(commonQueryVo.getCode()).concat("%")));
        }


        if (StringUtils.hasText(commonQueryVo.getLanguage())) {
            where.and(QCommon.common.language.like(Expressions.asString("%").concat(commonQueryVo.getLanguage()).concat("%")));
        }

        if (StringUtils.hasText(commonQueryVo.getValue())) {
            where.and(QCommon.common.value.like(Expressions.asString("%").concat(commonQueryVo.getValue()).concat("%")));
        }


        Sort sort = new Sort(Sort.Direction.DESC, CommonM.ORDERNUM);
        Pageable page = PageRequest.of(commonQueryVo.getNumber(), commonQueryVo.getSize(), sort);
        Page<Common> componentPage = commonRepository.findAll(where, page);
        return getPageDto(componentPage);
    }

    @Override
    public List<CommonDto> listCommon() {
        Pageable page = PageRequest.of(0, 50);
        return getListDto(commonRepository.findAll(page).getContent());
    }

    /**
     * 根据名称 获取通用台账里的仪器分类、仪器状态和仪器服务方式
     *
     * @param code 即InstrumentM中的InstrumentM.GROUP_ID SERVICE_METHOD_ID CATEGROY_ID
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommonDto> getCommonList(String code) {
        List<Common> commonsByName = commonRepository
                .findCommonsByCodeAndIsDeleted( code,0L );
        List<CommonDto> listDto = getListDto( commonsByName );
        return listDto;
    }

    @Override
    public CommonVo findByName(String name) {
        Common common = commonRepository.findCommonByName( name );
        if (common != null) {
            CommonVo commonVo = new CommonVo();
            BeanUtils.copyProperties( common, commonVo );
            return commonVo;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CommonVo findCommon(Long id) {
        Common common = commonRepository.findById(id).orElse(null);
        CommonVo commonVo = new CommonVo();
        if (common != null){
            BeanUtils.copyProperties(common, commonVo);
            return commonVo;
        }
        return null;
    }

    @Override
    @Transactional
    public Long saveCommon(CommonVo commonVo) {
        Common common = new Common();
        BeanUtils.copyProperties(commonVo, common);        
        common.setIsDeleted(0L);
        common.setGmtCreate(new Date());
        common.setGmtModified(new Date());
        return commonRepository.save(common) == null ? 0L : 1L;
    }

    @Override
    @Transactional
    public Long updateCommon(CommonVo commonVo) {
        Common common = commonRepository.findById(commonVo.getId()).orElse(null);
        if (common == null) {
            return 0L;
        }
        BeanUtils.copyProperties(commonVo, common);
        common.setGmtModified( new Date() );
        return commonRepository.save(common) == null ? 0L : 1L;
    }

    @Override
    @Transactional
    public Long del(Long id) {
        Common common = commonRepository.findById(id).orElse(null);
        if (common != null) {
            common.setIsDeleted( 1L );
            common.setGmtModified( new Date() );
        }
        return commonRepository.save(common) == null ? 0L : 1L;
    }

    private MyPage<CommonDto> getPageDto(Page<Common> componentPage){
        List<CommonDto> commonDtos = new LinkedList<>();
        for (Common common : componentPage.getContent()) {
            CommonDto commonDto = new CommonDto();
            BeanUtils.copyProperties(common,commonDto);
            commonDtos.add(commonDto);
        }
        MyPage<CommonDto> myPage = new MyPage();
        myPage.setContent(commonDtos);
        myPage.setTotalElements(componentPage.getTotalElements());
        return myPage;
    }

    private List<CommonDto> getListDto(List<Common> commonList){
        List<CommonDto> commonDtos = new LinkedList<>();
        for (Common common : commonList) {
            CommonDto commonDto = new CommonDto();
            BeanUtils.copyProperties(common,commonDto);
            commonDtos.add(commonDto);
        }
        return commonDtos;
    }
}