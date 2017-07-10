package com.fang.base;

import org.junit.Test;

public class TestLong {
	
	@Test
	public void test(){
		Long a = System.currentTimeMillis();
		System.out.println(a);
		Long b = (a/1000)*1000;
		System.out.println(b);
	}

}
