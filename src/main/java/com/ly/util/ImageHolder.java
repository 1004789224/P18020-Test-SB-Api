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

    public ImageHolder(InputStream fileInputStream, String fileName) {
        this.fileInputStream = fileInputStream;
        this.fileName = fileName;
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
