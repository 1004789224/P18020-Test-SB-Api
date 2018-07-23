package com.ly.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * @author lw
 * @version 1.0
 * @description cn.lw.utils
 * @date 2018/6/18
 */
public class ImageUtil {

    private static final String basePath=Thread.currentThread()
            .getContextClassLoader().getResource( "" ).getPath();
    public static final SimpleDateFormat dateFormat=new SimpleDateFormat( "yyyyMMddHHmmss" );
    public static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger( ImageUtil.class );

    /**
     * 将commonsMultipartFile转换为File
     * @param commonsMultipartFile
     * @return
     */
    public static File transferCommonsMultiPartFileToFile(CommonsMultipartFile commonsMultipartFile){
        File file = new File(commonsMultipartFile.getOriginalFilename());
        try {
            commonsMultipartFile.transferTo(file  );
        } catch (IOException e) {
            e.printStackTrace();
            logger.error( e.toString() );
        }
        return file;
    }

    /**
     * 创建目标路径所涉及的路径
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImageBasePath() + targetAddr;
        File dirPath = new File( realFileParentPath );
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring( fileName.lastIndexOf( "." ) );
    }

    /**
     * 生产随机文件名
     * @return
     */
    public static String getRandomFileName() {
        int ranNum = random.nextInt(89999)+10000;
        String nowDateStr = dateFormat.format( new Date() );
        return nowDateStr + ranNum;
    }

    /**
     * 先判断imgPath是文件还是路径
     * 文件则直接删除
     * 路径则删除改路径下的所有文件
     * @param imgPath
     */
    public static void deleteFileOrPath(String imgPath) {
        File fileOrPath = new File( PathUtil.getImageBasePath() + imgPath );
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static String  saveImage(ImageHolder imageHolder, String targetPath) {
        makeDirPath( targetPath );
        String extension = getFileExtension( imageHolder.getFileName() );
        String filePath = targetPath + getRandomFileName()+extension;
        try {
            FileOutputStream outputStream = new FileOutputStream( filePath );
            FileCopyUtils.copy( imageHolder.getFileInputStream() , outputStream  );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException( "图片上传失败" + e.getMessage() );
        }
        return filePath;

    }


}
