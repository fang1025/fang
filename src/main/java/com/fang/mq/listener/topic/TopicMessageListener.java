package com.fang.mq.listener.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author fang
 * @version 2017年6月1日
 */
@Component
public class TopicMessageListener implements MessageListener  {
	
	private final static Logger  logger  = LoggerFactory.getLogger(TopicMessageListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("TopicMessageListener接收到消息:" + ((TextMessage) message).getText());
		} catch (JMSException e) {
			logger.error(e.getMessage(),e);
		}
		
	}

}
