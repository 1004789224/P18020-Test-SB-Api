package com.ly.vo.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * @author zw
 * @since 2017-11-11
 */
public class UserVo {

    private Long id;

    private String phone;

    private String password;

    private String salt;

    private String fbuserid;

    private String fbtoken;

    private String kitid;

    private String kittoken;

    private String ktpNo;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

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

    public String getKitid() {
        return kitid;
    }

    public void setKitid(String kitid) {
        this.kitid = kitid;
    }

    public String getKittoken() {
        return kittoken;
    }

    public void setKittoken(String kittoken) {
        this.kittoken = kittoken;
    }

    public String getKtpNo() {
        return ktpNo;
    }

    public void setKtpNo(String ktpNo) {
        this.ktpNo = ktpNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}