package com.ly.helper;

import java.util.LinkedHashMap;
import java.util.Map;


public class ResponseKit {
    /**
     * @param state
     * @param data  1:成功 0:失败 -1:未登录
     * @return
     */
    public static Map rtn(int state, Object data) {
        Map map = new LinkedHashMap();
        map.put("state", state);
        map.put("data", data);
        return map;
    }

}
