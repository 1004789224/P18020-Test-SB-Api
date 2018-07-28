package com.ly.atest;

import com.ly.util.JwtUtil;
import com.ly.vo.rsp.UserJwtToken;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * @author lw
 * @version 1.0
 * @description com.ly.atest
 * @date 2018/7/27 14:11
 */
public class JwtUtilTest extends MyTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void testJwtUil() throws IOException {
        UserJwtToken userJwtToken = new UserJwtToken();
        userJwtToken.setId( 1L );
        userJwtToken.setName( "王华" );
        userJwtToken.setPhone( "13569859632" );
        String formObject = jwtUtil.getJsonStrFormObject( userJwtToken );
        try {
            Thread.sleep( 3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserJwtToken userJwtToken1 = (UserJwtToken) jwtUtil.getOneObjectFromJsonSrt( formObject, UserJwtToken.class );
        System.out.println( userJwtToken1 );
    }
}