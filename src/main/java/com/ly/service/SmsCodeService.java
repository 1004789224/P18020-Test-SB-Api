package com.ly.service;

import com.ly.domain.SmsCode;
import com.ly.dto.SmsCodeDto;
import com.ly.helper.MyPage;
import com.ly.vo.query.SmsCodeQueryVo;
import com.ly.vo.form.SmsCodeVo;
import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface SmsCodeService {

    SmsCodeVo findSmsCode(Long id);

    Long saveSmsCode(SmsCodeVo smsCodeVo);

    Long updateSmsCode(SmsCodeVo smsCodeVo);

    Long del(Long id);

    MyPage<SmsCodeDto> listPage(SmsCodeQueryVo smsCodeQueryVo);

    List<SmsCodeDto> listSmsCode();

}