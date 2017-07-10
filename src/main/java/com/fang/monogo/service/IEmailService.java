package com.fang.monogo.service;

import java.util.List;

import com.fang.core.entity.EmailEntity;

public interface IEmailService {
	
	void addEmail(EmailEntity email);
	
	List<EmailEntity> findAll();

}
