/**
* @version 2017-05-23 17:06:43
* @author fang
*
**/
package com.fang.sys.service.impl;

import com.fang.core.service.BaseService;
import com.fang.core.util.MD5Util;
import com.fang.sys.dao.UserMapper;
import com.fang.sys.entity.UserEntity;
import com.fang.sys.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseService implements IUserService {

	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private UserMapper mapper;

	@Override
	public PageInfo<UserEntity> findUserByPage(ModelMap param) {
		this.startPage(param);
		PageInfo<UserEntity> pageInfo = new PageInfo<UserEntity>(mapper.findUserByParam(param));
		return pageInfo;
	}

	@Override
	public List<UserEntity> findUserByParam(ModelMap param) {
		return mapper.findUserByParam(param);
	}

	@Override
	public void addUser(UserEntity user) {
		user.setCreateTime(new Date((System.currentTimeMillis()/1000) * 1000) );
		user.setPassword(MD5Util.getMD5(user.getPassword() + user.getCreateTime().getTime()));
		mapper.insertUser(user);

	}

	@Override
	public void updateUser(UserEntity user) {
		mapper.updateUser(user);
	}

	@Override
	public void updateUserEnable(ModelMap param) {
		mapper.updateUserEnable(param);
	}

	@Override
	public void deleteUserById(ModelMap param) {
		mapper.deleteUserById(param);
	}

	@Override
	public UserEntity findByLoginname(String loginName) {
		return mapper.findByLoginname(loginName);
	}

}
