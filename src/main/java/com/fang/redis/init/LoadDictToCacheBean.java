package com.fang.redis.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fang.core.util.RedisUtil;
import com.fang.sys.entity.DictEntity;
import com.fang.sys.service.IDictService;

/**
 * 
 * @author fang
 * @version 2017年6月22日 下午10:09:40
 */
@Component
public class LoadDictToCacheBean {
	
	@Resource
	private IDictService service;

	public void init() {
		loadDict();
	}

	private void loadDict() {
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
			RedisUtil.setF(entry.getKey(), entry.getValue());
		}
	}
}
