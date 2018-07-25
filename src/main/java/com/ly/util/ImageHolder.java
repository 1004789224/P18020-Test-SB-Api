package com.ly.util;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.util
 * @date 2018/7/23
 */
public class ImageHolder {

    private InputStream fileInputStream;
    private String fileName;
    private Class clz;

    public ImageHolder(InputStream fileInputStream, String fileName, Class clz) {
        this.fileInputStream = fileInputStream;
        this.fileName = fileName;
        this.clz = clz;
    }

    public Class getClz() {

        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }

    public InputStream getFileInputStream() {

        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
