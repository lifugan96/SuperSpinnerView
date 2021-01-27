package com.glf.utilslibrary.util;

import java.lang.reflect.Field;

public class AppUtils {

    /**
     *
     * 反射 通过对象属性名称获取值
     *
     * @param o
     * @param field
     * @return
     */
    public static String getAttribute(Object o, String field) {
        String r = "";
        try {
            Field f = o.getClass().getDeclaredField(field);
            f.setAccessible(true);
            r = f.get(o).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

}
