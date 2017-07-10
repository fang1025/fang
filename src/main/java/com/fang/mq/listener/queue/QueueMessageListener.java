package com.fang.mq.listener.queue;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fang.sys.entity.UserEntity;
import com.fang.sys.service.IUserService;


@Component
public class QueueMessageListener implements MessageListener {
	
	private final static Logger  logger  = LoggerFactory.getLogger(QueueMessageListener.class);
	
	@Resource
    private IUserService service;
	
	@Override
	public void onMessage(Message message) {
		try {
			UserEntity user = (UserEntity) ((ActiveMQObjectMessage) message).getObject();
			System.out.println("QueueMessageListener接收到消息:" + user);
			service.addUser(user);
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
		}
		
	}

}
