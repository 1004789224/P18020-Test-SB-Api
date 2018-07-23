package com.ly.util;

import java.util.Random;

/**
 * @author lw
 * @version 1.0
 * @description cn.lw.utils
 * @date 2018/6/28
 */
//48-57:0-19
//65-90:A-Z
//97-122:a-z
public enum Random4CharUtil {
    /**
     * 单例
     */
    istance;
    private static Random random = new Random();
    private static StringBuilder saltCode = new StringBuilder();

    public static String getSaltCode() {
        for (int i = 0; i < 4; i++) {
            int type = random.nextInt( 3 );
            switch (type) {
                case 0:
                    saltCode.append( (char) (random.nextInt( 10 ) + 48) );
                    break;
                case 1:
                    saltCode.append( (char) (random.nextInt( 26 ) + 65) );
                    break;
                case 2:
                    saltCode.append( (char) (random.nextInt( 26 ) + 97) );
                    break;
                default:
            }
        }
        return saltCode.toString();
    }
}

