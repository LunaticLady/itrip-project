package com.cskt.util;

/**
 * @Author pet.team
 * @Description 数字相关工具类
 **/
public class MathUtils {

    /**
     * 返回一个4位随机数
     * @return
     */
    public static String random(){
        int random = (int) ((Math.random() * 9 + 1) * 1000);
        return random + "";
    }


    /**
     * 返回一个指定长度的位随机数
     * @return
     */
    public static String random(int size){
        double pow = Math.pow(10, size) / 10;
        int random = (int) ((Math.random() * 9 + 1) * pow);
        return random + "";
    }

}
