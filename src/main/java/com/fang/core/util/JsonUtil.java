package com.fang.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * json字符串转换成map.
	 * 
	 * @param params
	 *            json字符串
	 * @return map map
	 */
	public static Map<?, ?> jsonToMap(String params) {
		try {
			return new ObjectMapper().readValue(params, Map.class);
		} catch (JsonParseException e) {
			logger.error("JsonUtil", e);
		} catch (JsonMappingException e) {
			logger.error("JsonUtil", e);
		} catch (IOException e) {
			logger.error("JsonUtil", e);
		}
		return null;
	}

	/**
	 * json字符串转换成entity.
	 * 
	 * @param params
	 *            json字符串
	 * @param entityClass
	 *            entity类型
	 * @return entity entity
	 */
	public static <T> T jsonToEntity(String params, Class<T> entityClass) {
		try {
			return new ObjectMapper().readValue(params, entityClass);
		} catch (JsonParseException e) {
			logger.error("JsonUtil", e);
		} catch (JsonMappingException e) {
			logger.error("JsonUtil", e);
		} catch (IOException e) {
			logger.error("JsonUtil", e);
		}
		return null;
	}

	/**
	 * 对象转换成json字符串.
	 * 
	 * @param obj
	 *            需要转换的对象
	 * @return json字符串
	 */
	public static String objectTojson(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.error("JsonUtil", e);
		} catch (JsonMappingException e) {
			logger.error("JsonUtil", e);
		} catch (IOException e) {
			logger.error("JsonUtil", e);
		}
		return null;
	}

	/**
	 * map转化成Entity，深度转化
	 * 
	 * @param map
	 *            map
	 * @param entityClass
	 *            entity类型
	 * @return entity
	 */
	public static <T> T mapToEntity(Map<?, ?> map, Class<T> entityClass) {
		return jsonToEntity(objectTojson(map), entityClass);
	}

	/**
	 * Entity转化成map，深度转化
	 * 
	 * @param map
	 * @param entityClass
	 * @return map
	 */
	public static Map<?, ?> entityToMap(Object obj) {
		return jsonToMap(objectTojson(obj));
	}

	/**
	 * Map合并方法
	 * 
	 * @param maps
	 * @return
	 */
	public static Map<?, ?> mergeMap(Map<?, ?>... maps) {
		if (maps == null || maps.length == 0)
			return null;
		Map<Object, Object> result = new HashMap<Object, Object>();
		for (Map<?, ?> map : maps) {
			if (map == null)
				continue;
			Set<Object> set = (Set<Object>) map.keySet();
			for (Object key : set) {
				result.put(key, map.get(key));
			}
		}
		return result;
	}
}
