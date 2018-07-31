package com.ly.atest;

import com.ly.domain.User;
import com.ly.helper.AppException;
import com.ly.service.FileService;
import com.ly.service.UserService;
import com.ly.util.ImageHolder;
import com.ly.vo.form.ModifyUserVo;
import com.ly.vo.form.UserRegisterVo;
import com.ly.vo.form.UserVo;
import com.ly.vo.update.UserUpdateVo;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.atest
 * @date 2018/7/24
 */
public class UserServiceImplTest extends MyTest {

    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Test
    public void del() {
        System.out.println( userService.del( 6L ) );
    }

    @Test
    public void modifyPassword() {
        ModifyUserVo modifyUserVo = new ModifyUserVo();
        modifyUserVo.setId( 6L );
        modifyUserVo.setOldPassword( "123456" );
        modifyUserVo.setNewPassword( "1234567" );
        userService.modifyPassword( modifyUserVo );
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void login() {
        UserRegisterVo userRegisterVo = new UserRegisterVo();
        userRegisterVo.setPhone( "13685259636" );
        userRegisterVo.setPassword( "123456" );
        userService.saveUser( userRegisterVo );
    }


    @Test
    public void updateUser() throws FileNotFoundException {
        UserVo userVo = new UserVo();
        userVo.setId( 6L );
        userVo.setName( "李华" );
        userVo.setIdnumber( "512659199601156585" );
        InputStream inputStream =
                new FileInputStream( "D:\\Is\\upload\\images\\User" +
                        "\\2018072515204267299.jpg" );
        String fileName = "2018072515204267299.jpg";
        ImageHolder imageHolder = new ImageHolder(inputStream,fileName,User.class );
        String path = fileService.uploadToDisk( imageHolder );
        System.out.println(path);
        UserUpdateVo updateVo = new UserUpdateVo();
        BeanUtils.copyProperties( userVo, updateVo );
        updateVo.setImgUrl( path );
        userService.updateUser(updateVo);
    }

    @Test
    public void saveUser1() {
    }

    @Test
    public void testLogin() throws AppException {
        UserVo userVo = new UserVo();
        userVo.setPassword( "123456" );
        userVo.setPhone(  "13685259636");
        System.out.println( userService.login( userVo ) );

    }
}