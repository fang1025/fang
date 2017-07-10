/**
* @version 2017-02-14 14:29:27
* @author fang
*
**/
package com.fang.test.service.impl;

import com.fang.core.service.BaseService;
import com.fang.sys.entity.DeptEntity;
import com.fang.sys.service.IDeptService;
import com.fang.test.service.ITestService;
import com.fang.sys.dao.DeptMapper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


@Service
public class TestServiceImpl extends BaseService  implements ITestService {

	@Resource
    private DeptMapper mapper;




	public void addDept(DeptEntity dept) {
		dept.setCreateBy("test");
		mapper.insertDept(dept);
//		int a = 9/0;
	}


    
}
