package com.ly.service;

import com.ly.domain.Common;
import com.ly.dto.CommonDto;
import com.ly.helper.MyPage;
import com.ly.model.InstrumentM;
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

    /**
     * 根据名称 获取通用台账里的仪器分类、仪器状态和仪器服务方式
     * @param code 即InstrumentM中的InstrumentM.GROUP_ID SERVICE_METHOD_ID CATEGROY_ID
     * @return
     */

    List<CommonDto> getCommonList(String code);

    CommonVo findByName(String name);
}