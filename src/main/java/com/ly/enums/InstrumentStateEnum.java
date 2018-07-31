package com.ly.enums;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.enums
 * @date 2018/7/29 14:04
 */

import com.alibaba.fastjson.JSONObject;
import com.ly.helper.AppException;
import com.ly.helper.ErrorCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 仪器状态枚举
 */
public enum InstrumentStateEnum {
    //可使用
    UNUSED( 0, "空闲" ),
    //使用中
    USED( 1, "非空闲" ),
    //无法使用
    UNSELESS( 2, "不可使用" );
    private int code;
    private String name;

    InstrumentStateEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String valueof(int code) throws AppException {
        switch (code) {
            case 0:
                return "空闲";
            case 1:
                return "非空闲";
            case 2:
                return "不可使用";
            default:
                throw new AppException( ErrorCode.PARAMETER_ERROR );
        }
    }

    @Override
    public String toString() {
        return "InstrumentStateEnum{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "stateId", code );
        jsonObject.put( "stateName", name );
        return jsonObject.toJSONString();
    }
}
