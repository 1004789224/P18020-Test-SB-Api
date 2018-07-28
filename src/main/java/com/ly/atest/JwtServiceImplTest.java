package com.ly.atest;

import com.ly.service.JwtService;
import com.ly.vo.form.UserRegisterVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.atest
 * @date 2018/7/27 10:27
 */
public class JwtServiceImplTest extends MyTest {
    @Autowired
    JwtService jwtService;
    @Test
    public void getJsonStr() throws Exception {
        UserRegisterVo userRegisterVo = new UserRegisterVo();
        userRegisterVo.setPassword( "zxcvb985632" );
        userRegisterVo.setPhone( "13203659856" );
        String jsonStr = jwtService.getTokenStr( userRegisterVo );
        UserRegisterVo oneObject = (UserRegisterVo) jwtService.getOneObject( jsonStr, UserRegisterVo.class );
        System.out.println( oneObject );
    }

    @Test
    public void getOneObject() throws IOException {
        Object oneObject = jwtService.getOneObject( "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJwaG9uZVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGx9In0.cY-oPCpwt0fzOMBro3b-gpP1rwexiVKIuyyA7jZvUILlOF7T-dIfrNYmzihmNRgI_JnCx89r9XRkPA4NMiZv_A", UserRegisterVo.class );
        System.out.println( ( oneObject) );
    }
}