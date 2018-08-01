package com.ly.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zw
 * @since 2017-10-08
 */

@Entity(name = "instrument")
@DynamicUpdate
@DynamicInsert
public class Instrument implements java.io.Serializable{

    private static final long serialVersionUID = 18723482374628616L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 分类标签
     */
    private Long categroyId;
    /**
     *服务方式
     */
    private Long serviceMethodId;
    /**
     *编码
     */
    private String code;

    private String name;
    /**
     *型号
     */
    private String modelNum;
    /**
     *所属单位
     */
    private Long groupId;
    /**
     * 放置地点
     */
    private String place;
    /**
     * 产地
     */
    private String origin;
    /**
     * 厂家
     */
    private String vender;
    /**
     * 生产日期
     */
    private Date productionDate;
    /**
     * 购置日期
     */
    private Date acquisitionDate;
    /**
     * 技术指标
     */
    private String technicalIndex;
    /**
     * 技术团队
     */
    private String technicalTeam;
    /**
     * 功能特点
     */
    private String features;
    /**
     *仪器照片
     */
    private String imgUrl;
    /**
     *排序
     */
    private Long oredernum;

    private Date gmtCreate;

    private Date gmtModified;

    private Long isDeleted;

    private Long stateId;

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategroyId() {
        return categroyId;
    }

    public void setCategroyId(Long categroyId) {
        this.categroyId = categroyId;
    }

    public Long getServiceMethodId() {
        return serviceMethodId;
    }

    public void setServiceMethodId(Long serviceMethodId) {
        this.serviceMethodId = serviceMethodId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelNum() {
        return modelNum;
    }

    public void setModelNum(String modelNum) {
        this.modelNum = modelNum;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getTechnicalIndex() {
        return technicalIndex;
    }

    public void setTechnicalIndex(String technicalIndex) {
        this.technicalIndex = technicalIndex;
    }

    public String getTechnicalTeam() {
        return technicalTeam;
    }

    public void setTechnicalTeam(String technicalTeam) {
        this.technicalTeam = technicalTeam;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getOredernum() {
        return oredernum;
    }

    public void setOredernum(Long oredernum) {
        this.oredernum = oredernum;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}