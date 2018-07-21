package com.ly.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author zw
 * @since 2017-10-08
 */

@Entity(name = "user")
@DynamicUpdate
@DynamicInsert
public class User implements java.io.Serializable{

    private static final long serialVersionUID = 18723482374628523L;

    @Id
    @GeneratedValue
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

    private String authenticationStatus;

    private Date gmtCreate;

    private Date gmtModified;

    private Long isDeleted;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}