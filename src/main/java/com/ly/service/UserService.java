package com.ly.service;

import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.MyPage;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.form.UserVo;
import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface UserService {

    UserVo findUser(Long id);

    Long saveUser(UserVo userVo);

    Long updateUser(UserVo userVo);

    Long del(Long id);

    MyPage<UserDto> listPage(UserQueryVo userQueryVo);

    List<UserDto> listUser();

}