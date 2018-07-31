package com.ly.service;

import java.io.IOException;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.service.impl
 * @date 2018/7/27 9:56
 */
public interface JwtService {
    String  getTokenStr(Object object);

    Object getOneObject(String jsonStr,Class clz);

    Long getUserIdFromToken(String token);
}
