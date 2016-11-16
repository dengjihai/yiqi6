package com.yiqi6.util;

import java.util.Random;

/**
 * 验证码生成工具类
 *
 * @author dengjh
 * @create 2016-11-16 17:20
 **/
public class RandomCodeUtils {

    public static  String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
