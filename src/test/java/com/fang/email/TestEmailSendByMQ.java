package com.fang.email;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fang.core.entity.EmailEntity;
import com.fang.mq.sender.QueueSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class TestEmailSendByMQ {

	@Resource
	private QueueSender queueSender;

	@Test
	public void test() throws IOException {
		EmailEntity email = new EmailEntity("404437391@qq.com", "nihao", "hhhhhhhhhh");
		for (int i = 0; i < 1; i++) {
			String filename = "D:/install/logs/fang_" + i + ".log";
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			String fileContent = buffer.toString();
			email.setContent(fileContent);
			//将邮件放入 mq 队列
			queueSender.send("fang.queue.email", email);
		}

	}

}
