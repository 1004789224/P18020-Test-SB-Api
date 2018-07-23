package com.ly.util;

import com.google.code.kaptcha.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.util
 * @date 2018/7/22
 */
public class VerificationUtil {

    private static Logger log = LoggerFactory.getLogger( VerificationUtil.class );
    public static boolean VerificationCode(HttpServletRequest request,String vrifyCode) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(
                "vrifyCode"
        );

        log.debug( "session的验证码:" + verifyCodeExpected );
        log.debug( "用户的验证码:" + vrifyCode );
        if (vrifyCode == null || !vrifyCode.equalsIgnoreCase( verifyCodeExpected )) {
            return false;
        }
        return true;
    }
}
