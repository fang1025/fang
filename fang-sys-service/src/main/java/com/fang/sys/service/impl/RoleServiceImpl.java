/**
* @version 2017-06-17 09:05:53
* @author fang
*
**/
package com.fang.sys.service.impl;

import com.fang.core.service.BaseService;
import com.fang.sys.dao.RoleMapper;
import com.fang.sys.entity.RoleEntity;
import com.fang.sys.service.IRoleService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("roleService")
public class RoleServiceImpl extends BaseService implements IRoleService {

	private final static Logger  logger  = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Resource
    private RoleMapper mapper;
	
	@Override
    public PageInfo<RoleEntity> findRoleByPage(ModelMap param) {
    	this.startPage(param);
		PageInfo<RoleEntity> pageInfo = new PageInfo<RoleEntity>(mapper.findRoleByParam(param));
		return pageInfo;
    }

	@Override
    public List<RoleEntity> findRoleByParam(ModelMap param) {
		return mapper.findRoleByParam(param);
    }
    
    
    @Override
    public void addRole(RoleEntity role) {
		mapper.insertRole(role);
    }
    
    @Override
    public void updateRole(RoleEntity role) {
		mapper.updateRole(role);
    }
    
    @Override
    public void updateRoleEnable(ModelMap param) {
		mapper.updateRoleEnable(param);
    }

    @Override
    public void deleteRoleById(ModelMap param) {
		mapper.deleteRoleById(param);
    }

	@Override
	public void grantPrivilege(ModelMap param) {
		mapper.deletePrivilegeByRole(param);
		mapper.grantPrivilege(param);
	}

	@Override
	public List<Map<String, Object>> findForAssignRole(ModelMap param) {
		return mapper.findForAssignRole(param);
	}

	@Override
	public void assignRole(ModelMap param) {
		mapper.deleteRoleRlByUser(param);
		if (param.containsKey("ids") && !"".equals(param.get("ids"))) {
			mapper.assignRole(param);
		}
	}

}
