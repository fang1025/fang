package com.fang.sms.mongo;

import com.fang.core.dao.BaseMongoDao;
import com.fang.core.entity.EmailEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailDao extends BaseMongoDao<EmailEntity> {

	public List<EmailEntity> findAll() {
		return find().asList();
	}
	
	

}
