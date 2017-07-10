package com.fang.mq;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fang.mq.sender.QueueSender;
import com.fang.mq.sender.TopicSender;
import com.fang.sys.dao.UserMapper;
import com.fang.sys.entity.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class TestMQ {
	
	@Autowired
	private QueueSender queueSender;
	@Autowired
	private TopicSender topicSender;
	
	@Resource
	private UserMapper mapper;

	
	
	@Test
	public void test() {
		UserEntity user = mapper.findByLoginname("admin");
		for (int i = 0; i < 5; i++) {
			user.setLoginName("TEST" + i);
			queueSender.send("fang.queue", user);
		}
//		queueSender.send("fang.queue", "test");
//		topicSender.send("fang.topic", "test");
	}

}
