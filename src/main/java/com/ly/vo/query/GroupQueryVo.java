package com.ly.vo.query;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * @author zw
 * @since 2017-11-11
 */
public class GroupQueryVo {

    @NotNull
    @Range(min = 0, max = 99999, message = "分页数不正确")
    private Integer number;

    @NotNull
    @Range(min = 5, max = 200, message = "每页显示数范围5~200")
    private Integer size;

    
    private String name;
    
    private Long phone;
    
    private String linkName;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
    
    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
