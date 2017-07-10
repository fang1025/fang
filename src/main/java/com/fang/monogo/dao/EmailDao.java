package com.fang.monogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fang.core.dao.BaseMongoDao;
import com.fang.core.entity.EmailEntity;

@Repository
public class EmailDao extends BaseMongoDao<EmailEntity> {

	public List<EmailEntity> findAll() {
		return find().asList();
	}
	
	

}
