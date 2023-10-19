package com.cskt.util;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
    /**
     * 对象 --> JSON
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * json --> 对象
     * @param jsonString
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T JsonToObject(String jsonString, Class<T> tClass) {
        return JSON.parseObject(jsonString, tClass);
    }
}
