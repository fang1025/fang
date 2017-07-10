package com.fang.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {
	
	private final static Logger  logger  = LoggerFactory.getLogger(TestLog.class);
	
	@Test
	public void test(){
		logger.info("info..............");
		logger.debug("debug............");
		logger.error("error............");
	}

}
