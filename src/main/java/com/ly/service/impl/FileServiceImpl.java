package com.ly.service.impl;

import com.ly.domain.User;
import com.ly.service.FileService;
import com.ly.util.ImageHolder;
import com.ly.util.ImageUtil;
import com.ly.util.PathUtil;
import org.springframework.stereotype.Service;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.service.impl
 * @date 2018/7/25
 */
@Service
public class FileServiceImpl<T> implements FileService {

    @Override
    public String uploadToDisk(ImageHolder imageHolder) {
        String image=null;
        if (imageHolder != null && imageHolder.getFileInputStream() != null
                && imageHolder.getFileName() != null) {
            String targetDir = PathUtil.getTypeImgagePath( imageHolder.getClz() );
            image = ImageUtil.saveImage( imageHolder, targetDir );
        }
        return image;
    }
}
