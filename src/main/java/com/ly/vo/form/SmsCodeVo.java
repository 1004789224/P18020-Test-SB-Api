package com.ly.vo.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author zw
 * @since 2017-11-11
 */
public class SmsCodeVo {

    private Long id;

    private Long userId;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotNull(message = "短信验证码不能为空")
    @Range(min = 0L, max = 9999L,message = "验证码错误")
    private Long code;
    private Long isUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Long isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this, ToStringStyle.SHORT_PREFIX_STYLE );
    }
}