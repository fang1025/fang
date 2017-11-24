package com.fang.cache.controller;

import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.core.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @author fang
* @version 2017年6月22日 下午10:15:31
* 
*/
@Controller
@RequestMapping(value = "/cache")
public class RedisCacheController extends BaseController {
	
	private final static Logger  logger  = LoggerFactory.getLogger(RedisCacheController.class);
	
	@RequestMapping("/findDictCachelByParam")
	public @ResponseBody Object findDeptByParam(@RequestBody ModelMap param) {
		FangResult result = new FangResult();
		Map<String, Object> dictMap = new HashMap<String, Object>();
		try {
			if(param.containsKey("dictCode")){
				String dictCode = (String) param.get("dictCode");
				Map<String, String> map = (Map<String, String>) RedisUtil.getF(dictCode);
				dictMap.put(dictCode, map);
			}
			if(param.containsKey("dictCodeList")){
				List<String> dictCode =  (List<String>) param.get("dictCodeList");
				for (String str : dictCode) {
					Map<String, String> map = (Map<String, String>) RedisUtil.getF(str);
					dictMap.put(str, map);
				}
			}
			result.put("dictMap", dictMap);
			result.buildSuccessResult();
		} catch (Exception e) {
			result.buildErrorResult(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}
