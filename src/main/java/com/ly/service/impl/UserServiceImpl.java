package com.ly.service.impl;

import com.ly.domain.QUser;
import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.MyPage;
import com.ly.model.UserM;
import com.ly.repository.UserRepository;
import com.ly.service.UserService;
import com.ly.util.*;
import com.ly.vo.form.ModifyUserVo;
import com.ly.vo.form.UserRegisterVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.rsp.UserInfoRspVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public MyPage<UserDto> listPage(UserQueryVo userQueryVo) {
        BooleanBuilder where = new BooleanBuilder();


        if (StringUtils.hasText(userQueryVo.getPhone())) {
            where.and(QUser.user.phone.like(Expressions.asString("%").concat(userQueryVo.getPhone()).concat("%")));
        }


        if (StringUtils.hasText(userQueryVo.getIdnumber())) {
            where.and(QUser.user.idnumber.like(Expressions.asString("%").concat(userQueryVo.getIdnumber()).concat("%")));
        }


        if (StringUtils.hasText(userQueryVo.getName())) {
            where.and(QUser.user.name.like(Expressions.asString("%").concat(userQueryVo.getName()).concat("%")));
        }


        Sort sort = new Sort(Sort.Direction.ASC, UserM.ID);
        Pageable page = PageRequest.of(userQueryVo.getNumber(), userQueryVo.getSize(), sort);
        Page<User> componentPage = userRepository.findAll(where, page);
        return getPageDto(componentPage);
    }

    @Override
    public List<UserDto> listUser() {
        return null;//getListDto(userRepository.getByIsDeleted(0L));
    }

    /**
     * 删除用户,软删除,但是删除时将本地中得图片删除掉 节约磁盘空间
     * @param id
     * @return
     */
    @Override
    public Long del(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setIsDeleted( 1L );
            if (user.getImgUrl() != null) {
                ImageUtil.deleteFileOrPath( user.getImgUrl() );
                user.setImgUrl( null );
            }
        }
        return userRepository.save(user) == null ? 0L : 1L;
    }
    /**
     *
     * @param userVo 包含老密码,新密码 以及用户id
     *               用户Id在controller中调用JWTService获取
     * @return
     */
    @Override
    public boolean modifyPassword(ModifyUserVo userVo) {
        int i = userRepository.updatePassword(
                userVo.getId(),
                userVo.getOldPassword(),
                userVo.getNewPassword(),
                new Date() );
        return i==1?true:false;
    }

    /**
     * 初始注册时,user只有phone和密码
     *
     * @param registerVo
     * @return
     */
    @Override
    public Long saveUser(UserRegisterVo registerVo) {
        User user = new User();
        BeanUtils.copyProperties( registerVo, user );
        user.setGmtCreate( new Date(  ) );
        user.setGmtModified( new Date() );
        String saltCode = Random4CharUtil.getSaltCode();
        user.setSalt( saltCode );
        user.setPassword( MD5Util.getMD5String( user.getPassword()+saltCode ) );
        return userRepository.save( user )==null?0L:1L;
    }

    /**
     * TODO 引入JWT 给 客户端发送token
     * @param userVo
     * @return
     */
    @Override
    public UserDto login(UserVo userVo) {
        User user = userRepository.findUserByPasswordAndPhone( userVo.getPhone(),
                userVo.getPassword() );
        if (user != null) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties( user, userDto );
            return userDto;
        }
        return null;
    }

    /**
     *短信注册成功,用户可编辑个人资料,输入身份证号码,名字,头像
     * @param userVo
     * @return
     */
    @Override
    public Long updateUser(UserVo userVo,ImageHolder imageHolder) {
        if (userVo.getImgUrl() != null) {
            ImageUtil.deleteFileOrPath( userVo.getImgUrl() );
        }
        if (imageHolder != null && imageHolder.getFileInputStream() != null
                && imageHolder.getFileName() != null) {
            String targetDir = PathUtil.getTargetDir( User.class );
            String image = ImageUtil.saveImage( imageHolder, targetDir );
            userVo.setImgUrl( image );
        }
        User user = userRepository.findById(userVo.getId()).orElse(null);
        if (user == null) {
            return 0L;
        }
        BeanUtils.copyProperties(userVo, user);
        return userRepository.save(user) == null ? 0L : 1L;
    }
    @Override
    public UserVo findUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        UserVo userVo = new UserVo();
        if (user != null){
            BeanUtils.copyProperties(user, userVo);
            return userVo;
        }
        return null;
    }

    /**
     * @param userVo
     * @return
     */
    @Override
    public Long saveUser(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);        
        user.setIsDeleted(0L);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        String saltCode = Random4CharUtil.getSaltCode();
        user.setSalt( saltCode );
        user.setPassword( MD5Util.getMD5String( user.getPassword()+saltCode) );
        return userRepository.save(user) == null ? 0L : 1L;
    }




    private MyPage<UserDto> getPageDto(Page<User> componentPage){
        List<UserDto> userDtos = new LinkedList<>();
        for (User user : componentPage.getContent()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtos.add(userDto);
        }
        MyPage<UserDto> myPage = new MyPage();
        myPage.setContent(userDtos);
        myPage.setTotalElements(componentPage.getTotalElements());
        return myPage;
    }

    private List<UserDto> getListDto(List<User> userList){
        List<UserDto> userDtos = new LinkedList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }
}