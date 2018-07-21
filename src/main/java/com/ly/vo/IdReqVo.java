package com.ly.vo;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class IdReqVo implements java.io.Serializable {

    @NotNull
    @Range(min = 1, message = "id参数错误")
    private Long iid;

    public Long getIid() {
        return iid;
    }

    public void setIid(Long iid) {
        this.iid = iid;
    }
}