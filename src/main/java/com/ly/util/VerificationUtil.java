package com.ly.util;

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
    public static boolean VerificationCode(HttpServletRequest request) {
        String vrifyCodeInSession = (String) request.getSession().getAttribute( "vrifyCode" );
        String vrifyCodeFromUser = (String) request.getAttribute( "vrifyCode" );
        if (StringUtils.hasText( vrifyCodeFromUser ) && StringUtils.hasText( vrifyCodeInSession )) {
            return vrifyCodeFromUser.equals( vrifyCodeInSession );
        } else {
            return false;
        }
    }
}
