/**
* @version 2017-06-18 18:17:34
* @author fang
*
**/
package com.fang.sys.service.impl;

import com.fang.core.service.BaseService;
import com.fang.core.util.SessionUtil;
import com.fang.sys.dao.EmployeeMapper;
import com.fang.sys.entity.EmployeeEntity;
import com.fang.sys.entity.UserEntity;
import com.fang.sys.service.IEmployeeService;
import com.fang.sys.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;


@Service
public class EmployeeServiceImpl extends BaseService implements IEmployeeService {

	private final static Logger  logger  = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Resource
    private EmployeeMapper mapper;
	
	@Resource
    private IUserService userService;
	
	@Override
    public PageInfo<EmployeeEntity> findEmployeeByPage(ModelMap param) {
    	this.startPage(param);
		PageInfo<EmployeeEntity> pageInfo = new PageInfo<EmployeeEntity>(mapper.findEmployeeByParam(param));
		return pageInfo;
    }

	@Override
    public List<EmployeeEntity> findEmployeeByParam(ModelMap param) {
		return mapper.findEmployeeByParam(param);
    }
    
    
    @Override
    public void addEmployee(EmployeeEntity employee) {
    	UserEntity user = (UserEntity) SessionUtil.getCurrentUser();
    	employee.setCreateById(user.getId());
    	employee.setCreateBy(user.getName());
    	employee.setLastUpdateById(user.getId());
    	employee.setLastUpdateBy(user.getName());
    	employee.setEnable(0); 
    	employee.setType(1);
    	userService.addUser(employee);
		mapper.insertEmployee(employee); 
    }
    
    @Override
    public void updateEmployee(EmployeeEntity employee) {
		mapper.updateEmployee(employee);
    }
    
    @Override
    public void updateEmployeeEnable(ModelMap param) {
		mapper.updateEmployeeEnable(param);
    }

    @Override
    public void deleteEmployeeById(ModelMap param) {
		mapper.deleteEmployeeById(param);
    }

}
