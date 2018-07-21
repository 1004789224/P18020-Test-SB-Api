package com.ly.vo.req;

import com.ly.vo.form.*;

import java.util.List;

/**
 * description 个人信息 账户管理 资金流水等
 *
 * @author lianzeng
 * @date 2018/5/23
 */
public class UserInfo {
    private UserVo userVo;
    private AccountVo accountVo;
    private List<UserBankVo> userBankVos;
    private List<LendOrderVo> lendOrderVos;
    private List<CapitalFlowVo> capitalFlowVos;

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }

    public AccountVo getAccountVo() {
        return accountVo;
    }

    public void setAccountVo(AccountVo accountVo) {
        this.accountVo = accountVo;
    }

    public List<UserBankVo> getUserBankVos() {
        return userBankVos;
    }

    public void setUserBankVos(List<UserBankVo> userBankVos) {
        this.userBankVos = userBankVos;
    }

    public List<LendOrderVo> getLendOrderVos() {
        return lendOrderVos;
    }

    public void setLendOrderVos(List<LendOrderVo> lendOrderVos) {
        this.lendOrderVos = lendOrderVos;
    }

    public List<CapitalFlowVo> getCapitalFlowVos() {
        return capitalFlowVos;
    }

    public void setCapitalFlowVos(List<CapitalFlowVo> capitalFlowVos) {
        this.capitalFlowVos = capitalFlowVos;
    }
}
