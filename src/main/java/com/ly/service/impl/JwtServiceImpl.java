package com.ly.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ly.service.JwtService;
import com.ly.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.service.impl
 * @date 2018/7/27 10:24
 */
@Service
public class JwtServiceImpl implements JwtService {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public String getTokenStr(Object object) {
        if (object != null) {
            try {
                return jwtUtil.getJsonStrFormObject( object );
            } catch (JsonProcessingException e) {
                throw new RuntimeException( this.getClass().getSimpleName()+" :Json解析失败!" );
            }
        }
        return null;
    }

    @Override
    public Object getOneObject(String jsonStr, Class clz) {
        if (StringUtils.hasText( jsonStr ) && clz != null) {
            try {
                return jwtUtil.getOneObjectFromJsonSrt( jsonStr, clz );
            } catch (IOException e) {
                throw new RuntimeException( this.getClass().getSimpleName()+" :Json解析失败!" );
            }
        }
        return null;
    }
}
