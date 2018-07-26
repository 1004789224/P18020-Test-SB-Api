package com.ly.service.impl;

import com.ly.domain.QSmsCode;
import com.ly.domain.SmsCode;
import com.ly.dto.SmsCodeDto;
import com.ly.helper.MyPage;
import com.ly.model.SmsCodeM;
import com.ly.repository.SmsCodeRepository;
import com.ly.service.SmsCodeService;
import com.ly.vo.query.SmsCodeQueryVo;
import com.ly.vo.form.SmsCodeVo;
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
public class SmsCodeServiceImpl implements SmsCodeService {

    @Autowired
    private SmsCodeRepository smsCodeRepository;
    
    @Override
    public MyPage<SmsCodeDto> listPage(SmsCodeQueryVo smsCodeQueryVo) {
        BooleanBuilder where = new BooleanBuilder();


        if (smsCodeQueryVo.getUserId() != null && smsCodeQueryVo.getUserId() > 0 ) {
            where.and(QSmsCode.smsCode.userId.eq(smsCodeQueryVo.getUserId().longValue()));
        }

        if (StringUtils.hasText(smsCodeQueryVo.getPhone())) {
            where.and(QSmsCode.smsCode.phone.like(Expressions.asString("%").concat(smsCodeQueryVo.getPhone()).concat("%")));
        }


        if (smsCodeQueryVo.getCode() != null && smsCodeQueryVo.getCode() > 0 ) {
            where.and(QSmsCode.smsCode.code.eq(smsCodeQueryVo.getCode().longValue()));
        }

        Sort sort = new Sort(Sort.Direction.ASC, SmsCodeM.ID);
        Pageable page = PageRequest.of(smsCodeQueryVo.getNumber(), smsCodeQueryVo.getSize(), sort);
        Page<SmsCode> componentPage = smsCodeRepository.findAll(where, page);
        return getPageDto(componentPage);
    }

    @Override
    public List<SmsCodeDto> listSmsCode() {
        Sort sort = new Sort(Sort.Direction.ASC, SmsCodeM.ID);
        Pageable page = PageRequest.of(0, 200, sort);
        Page<SmsCode> componentPage = smsCodeRepository.findAll( page);
        return getPageDto(componentPage).getContent();
    }

    @Override
    public SmsCodeVo findSmsCode(Long id) {
        SmsCode smsCode = smsCodeRepository.findById(id).orElse(null);
        SmsCodeVo smsCodeVo = new SmsCodeVo();
        if (smsCode != null){
            BeanUtils.copyProperties(smsCode, smsCodeVo);
            return smsCodeVo;
        }
        return null;
    }

    @Override
    public Long saveSmsCode(SmsCodeVo smsCodeVo) {
        SmsCode smsCode = new SmsCode();
        BeanUtils.copyProperties(smsCodeVo, smsCode);        
        smsCode.setIsDeleted(0L);
        smsCode.setGmtCreate(new Date());
        smsCode.setGmtModified(new Date());
        return smsCodeRepository.save(smsCode) == null ? 0L : 1L;
    }

    @Override
    public Long updateSmsCode(SmsCodeVo smsCodeVo) {
        SmsCode smsCode = smsCodeRepository.findById(smsCodeVo.getId()).orElse(null);
        if (smsCode == null) {
            return 0L;
        }
        BeanUtils.copyProperties(smsCodeVo, smsCode);
        return smsCodeRepository.save(smsCode) == null ? 0L : 1L;
    }

    @Override
    public Long del(Long id) {
        SmsCode smsCode = smsCodeRepository.findById(id).orElse(null);
        return smsCodeRepository.save(smsCode) == null ? 0L : 1L;
    }

    private MyPage<SmsCodeDto> getPageDto(Page<SmsCode> componentPage){
        List<SmsCodeDto> smsCodeDtos = new LinkedList<>();
        for (SmsCode smsCode : componentPage.getContent()) {
            SmsCodeDto smsCodeDto = new SmsCodeDto();
            BeanUtils.copyProperties(smsCode,smsCodeDto);
            smsCodeDtos.add(smsCodeDto);
        }
        MyPage<SmsCodeDto> myPage = new MyPage();
        myPage.setContent(smsCodeDtos);
        myPage.setTotalElements(componentPage.getTotalElements());
        return myPage;
    }

    private List<SmsCodeDto> getListDto(List<SmsCode> smsCodeList){
                  
        List<SmsCodeDto> smsCodeDtos = new LinkedList<>();
        for (SmsCode smsCode : smsCodeList) {
            SmsCodeDto smsCodeDto = new SmsCodeDto();
            BeanUtils.copyProperties(smsCode,smsCodeDto);
            smsCodeDtos.add(smsCodeDto);
        }
        return smsCodeDtos;
    }
}