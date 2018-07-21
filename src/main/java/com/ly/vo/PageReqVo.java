package com.ly.vo;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class PageReqVo implements java.io.Serializable {

    @NotNull
    @Range(min = 0, max = 99999, message = "分页数不正确")
    private Integer number;

    @NotNull
    @Range(min = 5, max = 200, message = "每页显示数范围5~200")
    private Integer size;

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
}