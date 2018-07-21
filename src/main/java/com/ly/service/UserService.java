package com.ly.service;

import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.AppException;
import com.ly.helper.MyPage;
import com.ly.vo.UserIdStatusVo;
import com.ly.vo.req.FBKitLoginReqVo;
import com.ly.vo.req.FBLoginReqVo;
import com.ly.helper.Result;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.req.KtpInfoReqVo;
import com.ly.vo.rsp.FBLoginRspVo;
import com.ly.vo.rsp.KtpInfoRspVo;
import com.ly.vo.rsp.UserInfoRspVo;

import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface UserService {

    FBLoginRspVo fbLogin(FBLoginReqVo fbLoginReqVo) throws AppException;

    FBLoginRspVo fbKitLogin(FBKitLoginReqVo fbKitLoginReqVo) throws AppException;

    UserVo findUser(Long id);

    Long saveUser(User user);

    Long updateUser(UserVo userVo);

    Long del(Long id);

    MyPage<UserDto> listPage(UserQueryVo userQueryVo);

    List<UserDto> listUser();

    /**
     *  我的账户信息
     * @param id
     * @return
     */
    Result getUserAllInfo(Long id);

    /**
     *  保存ktp信息
     * @param ktpInfoReqVo
     * @return
     */
    Long saveKtpInfo(KtpInfoReqVo ktpInfoReqVo) throws AppException;

    /**
     *  查询ktp信息
     * @param userId
     * @return
     */
    KtpInfoRspVo getKtpInfo(Long userId);

    /**
     *  查询用户基本信息
     * @param userId
     * @return
     */
    UserInfoRspVo getUserInfo(Long userId);

    /**
     *  修改用户认证状态
     * @param status
     * @return
     */
    Long updateStatus(UserIdStatusVo status);

    /**
     * 获取用户非敏感信息
     * @return
     */
    UserVo getUser(Long userId);
}