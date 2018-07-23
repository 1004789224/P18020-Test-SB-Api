package com.ly.util;

/**
 * @author lw
 * @version 1.0
 * @description cn.lw.utils
 * @date 2018/6/18
 */
public class PathUtil {
    public static final String seperator = System.getProperty( "file.separator" );

    /**
     * 生成相对地址的根路径
     * @return
     */
    public static String getImageBasePath(){
        String os = System.getProperty( "os.name" );
        String basePath;
        if (os.toLowerCase().startsWith( "win" )){
            basePath = "D:/Is/upload";
        }else {
            basePath = "/home/upload";
        }
        basePath.replace( "/", seperator );
        return basePath;
    }

    /**
     * 根据类型和id获取图片的相对路径
     * @param clz
     * @return
     */
    public static String getTypeImgagePath(Class  clz){
        String imagePath = "/images/"+clz.getSimpleName()+"/";
        return imagePath.replace( "/", seperator );
    }

    public static String getTargetDir(Class clz) {
        return PathUtil.getImageBasePath()+PathUtil.getTypeImgagePath( clz );
    }
}
