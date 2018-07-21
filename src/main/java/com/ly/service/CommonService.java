package com.ly.service;

import com.ly.domain.Common;
import com.ly.dto.CommonDto;
import com.ly.helper.MyPage;
import com.ly.vo.query.CommonQueryVo;
import com.ly.vo.form.CommonVo;
import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface CommonService {

    CommonVo findCommon(Long id);

    Long saveCommon(CommonVo commonVo);

    Long updateCommon(CommonVo commonVo);

    Long del(Long id);

    MyPage<CommonDto> listPage(CommonQueryVo commonQueryVo);

    List<CommonDto> listCommon();

}