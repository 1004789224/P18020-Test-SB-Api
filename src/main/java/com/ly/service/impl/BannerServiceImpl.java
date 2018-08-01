package com.ly.service.impl;

import com.ly.domain.QBanner;
import com.ly.domain.Banner;
import com.ly.dto.BannerDto;
import com.ly.helper.MyPage;
import com.ly.model.BannerM;
import com.ly.repository.BannerRepository;
import com.ly.service.BannerService;
import com.ly.vo.query.BannerQueryVo;
import com.ly.vo.form.BannerVo;
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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;
    
    @Override
    @Transactional(readOnly = true)
    public MyPage<BannerDto> listPage(BannerQueryVo bannerQueryVo) {
        BooleanBuilder where = new BooleanBuilder();


        if (StringUtils.hasText(bannerQueryVo.getName())) {
            where.and(QBanner.banner.name.like(Expressions.asString("%").concat(bannerQueryVo.getName()).concat("%")));
        }


        Sort sort = new Sort(Sort.Direction.ASC, BannerM.ORDERNUM);
        Pageable page = PageRequest.of(bannerQueryVo.getNumber(), bannerQueryVo.getSize(), sort);
        Page<Banner> componentPage = bannerRepository.findAll(where, page);
        return getPageDto(componentPage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerDto> listBanner() {
        return getListDto(bannerRepository.getByIsDeleted(0L));
    }

    @Override
    public BannerVo findBanner(Long id) {
        Banner banner = bannerRepository.findById(id).orElse(null);
        BannerVo bannerVo = new BannerVo();
        if (banner != null){
            BeanUtils.copyProperties(banner, bannerVo);
            return bannerVo;
        }
        return null;
    }

    @Override
    @Transactional
    public Long saveBanner(BannerVo bannerVo) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(bannerVo, banner);        
        banner.setIsDeleted(0L);
        banner.setGmtCreate(new Date());
        banner.setGmtModified(new Date());
        return bannerRepository.save(banner) == null ? 0L : 1L;
    }

    @Override
    @Transactional
    public Long updateBanner(BannerVo bannerVo) {
        Banner banner = bannerRepository.findById(bannerVo.getId()).orElse(null);
        if (banner == null) {
            return 0L;
        }
        BeanUtils.copyProperties(bannerVo, banner);
        return bannerRepository.save(banner) == null ? 0L : 1L;
    }

    @Override
    @Transactional
    public Long del(Long id) {
        Banner banner = bannerRepository.findById(id).orElse(null);
        return bannerRepository.save(banner) == null ? 0L : 1L;
    }

    private MyPage<BannerDto> getPageDto(Page<Banner> componentPage){
        List<BannerDto> bannerDtos = new LinkedList<>();
        for (Banner banner : componentPage.getContent()) {
            BannerDto bannerDto = new BannerDto();
            BeanUtils.copyProperties(banner,bannerDto);
            bannerDtos.add(bannerDto);
        }
        MyPage<BannerDto> myPage = new MyPage();
        myPage.setContent(bannerDtos);
        myPage.setTotalElements(componentPage.getTotalElements());
        return myPage;
    }

    private List<BannerDto> getListDto(List<Banner> bannerList){
        List<BannerDto> bannerDtos = new LinkedList<>();
        for (Banner banner : bannerList) {
            BannerDto bannerDto = new BannerDto();
            BeanUtils.copyProperties(banner,bannerDto);
            bannerDtos.add(bannerDto);
        }
        return bannerDtos;
    }
}