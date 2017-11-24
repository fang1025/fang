/**
* @author fang
* @version 2017-06-29 19:28:46
* 
**/
package com.fang.sys.service.impl;

import com.fang.core.service.BaseService;
import com.fang.sys.dao.DeptMapper;
import com.fang.sys.entity.DeptEntity;
import com.fang.sys.service.IDeptService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;


@Service("deptService")
public class DeptServiceImpl extends BaseService implements IDeptService {

	private final static Logger  logger  = LoggerFactory.getLogger(DeptServiceImpl.class);
	
	@Resource
    private DeptMapper mapper;
	
	@Override
    public PageInfo<DeptEntity> findDeptByPage(ModelMap param) {
    	this.startPage(param);
		PageInfo<DeptEntity> pageInfo = new PageInfo<DeptEntity>(mapper.findDeptByParam(param));
		return pageInfo;
    }

	@Override
    public List<DeptEntity> findDeptByParam(ModelMap param) {
		return mapper.findDeptByParam(param);
    }
    
    
    @Override
    public void addDept(DeptEntity dept) {
		mapper.insertDept(dept);
    }
    
    @Override
    public void updateDept(DeptEntity dept) {
		mapper.updateDept(dept);
    }
    
    @Override
    public void updateDeptEnable(ModelMap param) {
		mapper.updateDeptEnable(param);
    }

    @Override
    public void deleteDeptById(ModelMap param) {
		mapper.deleteDeptById(param);
    }

}
