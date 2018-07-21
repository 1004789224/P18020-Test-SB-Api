package com.ly.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author lzh
 * @since 2018/5/24
 */
public class BankNoReqVo {

    @NotBlank
    private String bankNo;

    @NotBlank
    private String bankCode;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
