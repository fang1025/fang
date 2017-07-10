package com.fang.schedule.bean;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fang.websocket.config.WebsocketConstants;


/**
 * 定时器任务（方法事物）
 */
@Component
public class TaskTimeBean {
	
	private static Log logger = LogFactory.getLog(TaskTimeBean.class);
	
	@Resource
	private SimpMessagingTemplate template;

	
//	@Scheduled(cron = "22 * * * * ?")
	@Scheduled(fixedDelay = 1000)
	public void sysdate() {
		try {
			this.template.convertAndSend(WebsocketConstants.MASSAGE_PUSH, "fff");
		} catch (MessagingException e) {
			 logger.error("TaskTimeBean.sysdate: ",e);
		}
	}
	
	

	
	

}
