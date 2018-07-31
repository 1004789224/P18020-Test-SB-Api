package com.ly.vo.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zw
 * @since 2017-11-11
 */
public class InstrumentVo {

    private Long id;
    @NotNull(message = "分类不可为空")
    private Long categroyId;
    @NotNull(message = "服务方式不可为空")
    private Long serviceMethodId;
    @NotBlank(message = "编码不能为空")
    private String code;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotBlank(message = "型号不能为空")
    private String modelNum;
    @NotNull(message = "")
    private Long groupId;
    @NotBlank(message = "放置地址不能为空")
    private String place;
    @NotBlank(message = "产地不能为空")
    private String origin;
    @NotBlank(message = "生产厂家不能为空")
    private String vender;
    @NotNull(message = "出厂时间不能为空")
    private Date productionDate;
    private Date acquisitionDate;

    private String technicalIndex;

    private String technicalTeam;
    @NotBlank(message = "功能特点不能为空")
    private String features;
    @NotBlank(message = "仪器图片不能为空")
    private String imgUrl;

    private Long oredernum;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this, ToStringStyle.SHORT_PREFIX_STYLE );
    }
}