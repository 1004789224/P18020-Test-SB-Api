package com.ly.service;

import com.ly.domain.Banner;
import com.ly.dto.BannerDto;
import com.ly.helper.MyPage;
import com.ly.vo.query.BannerQueryVo;
import com.ly.vo.form.BannerVo;
import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface BannerService {

    BannerVo findBanner(Long id);

    Long saveBanner(BannerVo bannerVo);

    Long updateBanner(BannerVo bannerVo);

    Long del(Long id);

    MyPage<BannerDto> listPage(BannerQueryVo bannerQueryVo);

    List<BannerDto> listBanner();

}