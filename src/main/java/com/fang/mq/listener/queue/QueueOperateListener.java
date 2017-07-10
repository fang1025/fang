package com.fang.mq.listener.queue;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fang.core.entity.OperateRecordEntity;
import com.fang.monogo.service.IOperateRecordService;
import com.fang.sys.entity.UserEntity;

/**
 * 将操作记录存入mongodb
 * @author fang
 * @version 2017年7月7日
 */
@Component
public class QueueOperateListener implements MessageListener  {
	
	private final static Logger  logger  = LoggerFactory.getLogger(QueueOperateListener.class);
	
	@Resource
	private IOperateRecordService operateRecordService;

	@Override
	public void onMessage(Message message) {
		try {
			OperateRecordEntity operate = (OperateRecordEntity) ((ActiveMQObjectMessage) message).getObject();
			logger.debug("QueueMessageListener接收到消息:" + operate);
			operateRecordService.addOperateRecord(operate);
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
		}
		
	}

}
