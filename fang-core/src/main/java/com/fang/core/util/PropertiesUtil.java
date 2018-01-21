package com.fang.core.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class PropertiesUtil {
	private static Properties properties = new Properties();
	private static InputStreamReader is = null;

	public static String getParam(String key) {
		if (is == null) {
			try {
				is = new InputStreamReader(
						PropertiesUtil.class.getClassLoader().getResourceAsStream("config/common.properties"), "UTF-8");
				properties.load(is);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		if (key == null)
			return key;
		Object value = properties.get(key);
		if (value == null || "".equals(value))
			return "";
		else
			return value.toString();
	}

	public static boolean getBoolean(String key) {
		return getParam(key).equals("true") ? true : false;
	}

	public static long getLong(String key) {
		return Long.valueOf(getParam(key));
	}

	public static Integer getInt(String key) {
		return Integer.valueOf(getParam(key));
	}

}
