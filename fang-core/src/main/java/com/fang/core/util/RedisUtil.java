package com.fang.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author fang
 * @version 2017年6月12日
 */
@PropertySource("classpath:configCore/redis.properties")
public final class RedisUtil {
	private RedisUtil() {
	}

	private static RedisTemplate<Serializable, Serializable> redisTemplate = null;
	private static Integer EXPIRE = Integer
			.valueOf(ResourceBundle.getBundle("configCore/redis").getString("redis.expiration"));
	
	public static void setRedisTemplate(RedisTemplate<Serializable, Serializable> arg0){
		redisTemplate = arg0;
	}

	// 获取连接
	@SuppressWarnings("unchecked")
	private static RedisTemplate<Serializable, Serializable> getRedis() {
		if (redisTemplate == null) {
			synchronized (RedisUtil.class) {
				if (redisTemplate == null) {
					ApplicationContext ac = SpringContextHolder.getApplicationContext();
					redisTemplate = (RedisTemplate<Serializable, Serializable>) ac.getBean("redisTemplate");
				}
			}
		}
		return redisTemplate;
	}

	/**
	 * 获取缓存
	 * @param key
	 * @param second
	 * @return
	 */
	public static final Serializable get(final String key,Integer second) {
		expire(key, second);
		return getRedis().opsForValue().get(key);
	}
	public static final Serializable get(final String key) {
		return get(key,EXPIRE);
	}
	public static final Serializable getF(final String key) {
		return getRedis().opsForValue().get(key);
	}

	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 * @param second
	 */
	public static final void set(final String key, final Serializable value,Integer second) {
		getRedis().boundValueOps(key).set(value);
		expire(key, second);
	}
	public static final void set(final String key, final Serializable value) {
		set(key, value, EXPIRE);
	}
	public static final void setF(final String key, final Serializable value) {
		getRedis().boundValueOps(key).set(value);
	}

	/**
	 * 是否存在
	 * @param key
	 * @return
	 */
	public static final Boolean exists(final String key) {
		expire(key, EXPIRE);
		return getRedis().hasKey(key);
	}

	public static final void del(final String key) {
		getRedis().delete(key);
	}

	public static final void delAll(final String pattern) {
		getRedis().delete(getRedis().keys(pattern));
	}

	public static final String type(final String key) {
		expire(key, EXPIRE);
		return getRedis().type(key).getClass().getName();
	}

	/**
	 * 在某段时间后失效
	 * 
	 * @return
	 */
	public static final Boolean expire(final String key, final int seconds) {
		return getRedis().expire(key, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 在某个时间点失效
	 * 
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static final Boolean expireAt(final String key, final long unixTime) {
		return getRedis().expireAt(key, new Date(unixTime));
	}

	public static final Long ttl(final String key) {
		return getRedis().getExpire(key, TimeUnit.SECONDS);
	}

	public static final void setrange(final String key, final long offset, final String value) {
		expire(key, EXPIRE);
		getRedis().boundValueOps(key).set(value, offset);
	}

	public static final String getrange(final String key, final long startOffset, final long endOffset) {
		expire(key, EXPIRE);
		return getRedis().boundValueOps(key).get(startOffset, endOffset);
	}

	public static final Serializable getSet(final String key, final String value) {
		expire(key, EXPIRE);
		return getRedis().boundValueOps(key).getAndSet(value);
	}


	/**
	 * HASH 操作
	 */
	public static final void hset(final String key, final Serializable hkey,final Serializable hvalue){
		getRedis().boundHashOps(key).put(hkey,hvalue);
	}
	public static final void hset(final String key , Map<String,Object> map){
		getRedis().boundHashOps(key).putAll(map);
	}

	public static final Object hget(final String key, final Serializable hkey){
		return getRedis().boundHashOps(key).get(hkey);
	}
	public static final Map<Object,Object> hget(final String key ){
		return getRedis().boundHashOps(key).entries();
	}

	public static final void hdel(final String key, final Serializable hkey){
		getRedis().boundHashOps(key).delete(hkey);
	}

}
