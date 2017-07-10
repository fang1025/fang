package com.fang.email;

import org.junit.Test;

import com.fang.core.entity.EmailEntity;
import com.fang.core.util.EmailUtil;


public class TestEmail {
	
	@Test
	public void test(){
		EmailEntity email = new EmailEntity("404437391@qq.com", "nihao", "hhhhhhhhhh");
		EmailUtil.sendEmail(email);
	}

}
