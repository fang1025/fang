package com.fang.mq.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/**
 * 队列消息发送类
 * @author fang
 * @version 2017年6月1日
 */
@Component
public class QueueSender {
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	/**
	 * 发送一条消息到指定的队列（点对点）
	 * 
	 * @param queueName 队列名称
	 * @param message 消息内容
	 */
	public void send(String queueName, final Serializable message) {
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});
	}
}
