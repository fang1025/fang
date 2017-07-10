package com.fang.monogo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import com.fang.core.entity.EmailEntity;
import com.fang.monogo.dao.EmailDao;
import com.fang.monogo.service.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {
	
	@Resource
	private EmailDao emailDao;

	@Override
	public void addEmail(EmailEntity email) {
		emailDao.save(email);
	}

	@Override
	public List<EmailEntity> findAll() {
//		Query<EmailEntity> query = emailDao.getDatastore().createQuery(EmailEntity.class);
		return emailDao.findAll();
	}
	
	
	
}
