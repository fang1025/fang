package com.fang.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.fang.core.util.JsonUtil;
import com.fang.sys.entity.DeptEntity;

public class TestJson {
	
	@Test
	public void test(){
		String str = "[{\"id\":1,\"createBy\":\"system\",\"createById\":1,\"createTime\":\"2017-05-04 10:25:13\",\"enable\":1,\"lastUpdateBy\":\"system\",\"lastUpdateById\":1,\"lastUpdateTime\":\"2017-05-04 10:25:23\",\"deptCode\":\"01\",\"deptName\":\"业务部\",\"deptType\":\"10\",\"fax\":null,\"notes\":null,\"officeTel\":null},{\"id\":2,\"createBy\":\"system\",\"createById\":1,\"createTime\":\"2017-05-04 10:25:13\",\"enable\":1,\"lastUpdateBy\":\"system\",\"lastUpdateById\":1,\"lastUpdateTime\":\"2017-05-04 10:25:23\",\"deptCode\":\"01\",\"deptName\":\"技术部\",\"deptType\":\"10\",\"fax\":null,\"notes\":null,\"officeTel\":null}]";
		
		 DeptEntity[] list = JsonUtil.jsonToEntity(str, DeptEntity[].class);
		
		System.out.println(list.length);
		for (DeptEntity object : list) {
//			System.out.println(object.get("deptCode") + "--" + object.get("deptName"));
			System.out.println(object.getDeptCode() + "----" + object.getDeptName());
		}
		
	}

}
