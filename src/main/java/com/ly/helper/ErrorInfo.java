package com.ly.helper;

public class ErrorInfo<T> implements java.io.Serializable {

    private static final long serialVersionUID = 2872348237462869L;

    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    private Integer code;
    private String message;
    private String url;
    private T data;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Integer getOK() {
        return OK;
    }

    public static Integer getERROR() {
        return ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(" url: " + url);
        result.append(" code: " + code);
        result.append(" message: " + message);
        result.append(" data: " + data);
        return result.toString();
    }
}