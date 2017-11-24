package com.fang.sms.service;

import com.fang.core.entity.EmailEntity;

import java.util.List;

public interface IEmailService {
	
	void addEmail(EmailEntity email);
	
	List<EmailEntity> findAll();

}
