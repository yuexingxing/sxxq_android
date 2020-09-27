package com.exam.commonbiz.util;

import com.google.gson.Gson;

public class BeanUtil {

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */

    public static String beanToJson(Object bean) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(bean);
        System.out.println(jsonStr);
        return jsonStr;
    }
}
