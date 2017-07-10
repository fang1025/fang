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
import com.fang.sys.service.IDictService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class TestRedis {
	
	@Resource
	private IDictService service;
	
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	@Test
	public void test() {
		RedisUtil.setRedisTemplate(redisTemplate);
		List<DictEntity> dictList = service.findDictByParam(null);
        
        // 分组
        Map<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
        for (DictEntity dict : dictList) {
            if (map.get(dict.getDictCode()) == null) {
            	HashMap<String, String> dictMap = new HashMap<String, String>();
                dictMap.put(dict.getDictValue(), dict.getDictText());
                map.put(dict.getDictCode(), dictMap);
            } else {
                Map<String, String> dictMap = map.get(dict.getDictCode());
                dictMap.put(dict.getDictValue(), dict.getDictText());
            }
        }
        
        // 放入缓存 
        for (Entry<String, HashMap<String, String>> entry : map.entrySet()) {
//            cache.put(entry.getKey(), entry.getValue());
            RedisUtil.setF(entry.getKey(), entry.getValue());
        }
        
        
	}

}
