package com.ly.vo.form;

import javax.validation.constraints.NotNull;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.vo.form
 * @date 2018/7/23
 */
public class ModifyUserVo {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "原密码不得为空")
    private String oldPassword;
    @NotNull(message = "新密码不得为空")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
