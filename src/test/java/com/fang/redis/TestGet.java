package com.fang.redis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fang.core.util.RedisUtil;
import com.fang.sys.entity.DictEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class TestGet {
	
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	
	@Test
	public void test() {
		RedisUtil.setRedisTemplate(redisTemplate);
		Map<String, String> map = (Map<String, String>) RedisUtil.getF("gender");
        System.out.println(map);
        
	}

}
