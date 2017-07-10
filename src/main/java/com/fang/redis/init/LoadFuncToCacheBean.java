package com.fang.redis.init;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fang.core.util.RedisUtil;
import com.fang.sys.entity.FunctionEntity;
import com.fang.sys.service.IFunctionService;

@Component
public class LoadFuncToCacheBean {
	
	@Resource
	private IFunctionService service;
	
	public void init() {
		loadFunc();
	}

	private void loadFunc() {
		List<FunctionEntity> list = service.findFunctionByParam(null);
		HashSet<Object> set1 = new HashSet<Object>();
		HashSet<Object> set2 = new HashSet<Object>();
		for (FunctionEntity entity : list) {
			if(entity.getType() == 9){
				set2.add(entity.getExtraUrl());
			}else{
				set1.add(entity.getExtraUrl());
			}
		}
		RedisUtil.setF("privilegeFunc",set1);
		RedisUtil.setF("loginFunc",set2);
	}

	


}
