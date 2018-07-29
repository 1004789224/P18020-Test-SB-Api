package com.ly.web;

import com.ly.anon.AopLog;
import com.ly.domain.Instrument;
import com.ly.domain.User;
import com.ly.helper.AppException;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import com.ly.service.FileService;
import com.ly.util.ImageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.web
 * @date 2018/7/25
 */
@RestController
@RequestMapping("v1")
public class ImgUploadController {
    @Autowired
    FileService fileService;
    @AopLog
    @PostMapping("userupload")
    public Result uploadUserImgFile(@RequestParam("userImgFile") MultipartFile imgFile) throws AppException {
        ImageHolder imageHolder = null;
        if (null != imgFile && !imgFile.isEmpty()) {
            try {
                imageHolder = new ImageHolder( imgFile.getInputStream(), imgFile.getOriginalFilename(), User.class );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String relativePath = fileService.uploadToDisk( imageHolder );
        return new Result().setData( relativePath );
    }
    @AopLog
    @PostMapping("instrumentupload")
    public Result uploadInstrumentImgFile(@RequestParam("instrumentImgFile") MultipartFile imgFile) throws AppException {
        ImageHolder imageHolder = null;
        if (null != imgFile && !imgFile.isEmpty()) {
            try {
                imageHolder = new ImageHolder( imgFile.getInputStream(), imgFile.getOriginalFilename(), Instrument.class );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String relativePath = fileService.uploadToDisk( imageHolder );
        return new Result().setData( relativePath );
    }
}
