package com.ly.vo.req;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class FBLoginReqVo {


    /**
     * userid : XXX
     * token : XXX
     */

    @NotBlank(message = "Gagal link facebook")
    @Length(min = 2, max = 32, message = "Gagal link facebook")
    private String fbuserid;

    @NotBlank(message = "Gagal link facebook")
    @Length(min = 2, max = 512, message = "Gagal link facebook")
    private String fbtoken;


    public String getFbuserid() {
        return fbuserid;
    }

    public void setFbuserid(String fbuserid) {
        this.fbuserid = fbuserid;
    }

    public String getFbtoken() {
        return fbtoken;
    }

    public void setFbtoken(String fbtoken) {
        this.fbtoken = fbtoken;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
