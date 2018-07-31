package com.ly.vo.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author zw
 * @since 2017-11-11
 */
public class BannerVo {

    private Long id;
    @NotBlank(message = "名称不能为空")
    private String name;
    @Pattern(regexp = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?"
            , message = "输入的必须是跳转路径")
    @NotBlank(message = "跳转路径不能为空")
    private String gotoUrl;
    @NotBlank(message = "图片不能为空")
    private String imgUrl;
    @Length(min = 10,max = 64,message = "图片描述应该在10-64个字之间")
    private String description;
    @NotNull
    @Range(min = 0L,max = 100L,message = "权重应该在0-100之间")
    private Long ordernum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGotoUrl() {
        return gotoUrl;
    }

    public void setGotoUrl(String gotoUrl) {
        this.gotoUrl = gotoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this, ToStringStyle.SHORT_PREFIX_STYLE );
    }
}