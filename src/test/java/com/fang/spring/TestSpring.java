package com.fang.spring;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fang.sys.entity.DeptEntity;
import com.fang.sys.service.IDeptService;
import com.fang.test.service.ITestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class TestSpring {
	
	@Resource
	ITestService testService;
	
	@Resource
	IDeptService deptService;
	
//	@Test
//	public void testSpringExtend(){
//		testService.printABC();
//	}
	
	@Test
	public void testAspect(){
		DeptEntity entity = new DeptEntity();
		entity.setDeptCode("071906");
		entity.setDeptName("test");
		deptService.addDept(entity);
		List<DeptEntity> list = deptService.findDeptByParam(null);
		for (DeptEntity deptEntity : list) {
			System.out.println(deptEntity);
		}
		entity.setDeptCode("071907");
		entity.setDeptName("after-2");
		deptService.addDept(entity);
		
	}

}
