package com.ly.service.impl;

import com.ly.domain.QUser;
import com.ly.domain.User;
import com.ly.dto.UserDto;
import com.ly.helper.MyPage;
import com.ly.model.UserM;
import com.ly.repository.UserRepository;
import com.ly.service.UserService;
import com.ly.util.*;
import com.ly.vo.form.UserRegisterVo;
import com.ly.vo.query.UserQueryVo;
import com.ly.vo.form.UserVo;
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

    @Override
    public boolean modifyPassword(UserVo userVo) {
        return false;
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
     * TODO 给密码加密  md5(密码+盐)
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
    /**
     *短信注册成功,用户可编辑个人资料,输入身份证号码,名字,头像
     * @param userVo
     * @return
     */
    @Override
    public Long updateUser(UserVo userVo,ImageHolder imageHolder) {
        if (imageHolder != null && imageHolder.getFileInputStream() != null && imageHolder.getFileName() != null) {
            String filePath=PathUtil.getImageBasePath() + PathUtil.getTypeImgagePath( User.class );

        }
        User user = userRepository.findById(userVo.getId()).orElse(null);
        if (user == null) {
            return 0L;
        }
        BeanUtils.copyProperties(userVo, user);
        return userRepository.save(user) == null ? 0L : 1L;
    }

    @Override
    public Long del(Long id) {
        User user = userRepository.findById(id).orElse(null);
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