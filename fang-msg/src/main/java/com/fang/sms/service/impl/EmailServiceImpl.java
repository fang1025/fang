package com.fang.sms.service.impl;

import com.fang.core.entity.EmailEntity;
import com.fang.core.service.BaseService;
import com.fang.sms.mongo.EmailDao;
import com.fang.sms.service.IEmailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("emailService")
public class EmailServiceImpl extends BaseService implements IEmailService {
	
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
