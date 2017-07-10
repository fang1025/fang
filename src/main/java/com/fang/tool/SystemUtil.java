package com.fang.tool;

import java.lang.reflect.Field;

/**
 * 
 * @author fang
 * @version 2017年6月25日 下午2:11:17
 */
public class SystemUtil {
    public static String toStr(Object obj) {
        return obj == null?"":obj.toString();
    }
    public static String entityToString(Object obj) {
        StringBuffer buf = new StringBuffer(",field info is:");
        Field[] fs = obj.getClass().getDeclaredFields();
        for (Field f : fs) {
            if (f.isAccessible()) {
                try {
                    buf.append(f.getName()).append("=").append(f.get(obj)).append(",");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return buf.toString();
    }
    public static String firstLetterUpper(String word) {
        char[] chars = new char[1];
        chars[0] = word.charAt(0);
        String temp = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z') {//当为字母时，则转换为小写
            return (word.replaceFirst(temp, temp.toLowerCase()));
        }
        return word;
    }
}
