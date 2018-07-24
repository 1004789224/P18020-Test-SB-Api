package com.ly.service;

import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.MyPage;
import com.ly.util.ImageHolder;
import com.ly.vo.form.ModifyUserVo;
import com.ly.vo.form.UserRegisterVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.rsp.UserInfoRspVo;
import com.ly.vo.update.UserUpdateVo;

import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface UserService {

    UserVo findUser(Long id);

    Long saveUser(UserVo userVo);

    /**
     *短信注册成功,用户可编辑个人资料,输入身份证号码,名字,头像
     * @param updateVo
     * @return
     */
    Long updateUser(UserUpdateVo updateVo, ImageHolder imageHolder);

    Long del(Long id);

    MyPage<UserDto> listPage(UserQueryVo userQueryVo);

    List<UserDto> listUser();

    Long modifyPassword(ModifyUserVo userVo);
    /**
     * 初始注册时,user只有phone和密码
     * @param registerVo
     * @return
     */
    Long saveUser(UserRegisterVo registerVo);

    /**
     * 根据phone和密码进行登陆
     * @param userVo
     * @return
     */
    UserDto login(UserVo userVo);
}