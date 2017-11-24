package com.fang.mq.listener.queue;

import com.fang.core.entity.EmailEntity;
import com.fang.core.util.EmailUtil;
import com.fang.sms.service.IEmailService;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 监听邮件信息
 * 
 * 1.发送邮件
 * 2.将邮件信息存入mongodb
 * 
 * @author fang
 * @version 2017年7月7日
 */
@Component
public class EmailDealListener implements MessageListener {
	
	private final static Logger  logger  = LoggerFactory.getLogger(EmailDealListener.class);
	
	@Resource
	private IEmailService emailService;

	@Override
	public void onMessage(Message arg0) {
		try {
			EmailEntity email = (EmailEntity) ((ActiveMQObjectMessage) arg0).getObject();
			logger.debug("QueueMessageListener接收到消息:" + email);
			//发送邮件
			boolean flag = EmailUtil.sendEmail(email);
			email.setStatus(flag?1:2);
			
			//保存邮件
			emailService.addEmail(email);
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
