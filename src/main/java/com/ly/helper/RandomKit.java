package com.ly.helper;

import java.util.Random;

/**
 * Created by zhongwei on 18/02/2017.
 */
public class RandomKit {

    public static int getRandom() {
        int max = 9999;
        int min = 1000;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}
