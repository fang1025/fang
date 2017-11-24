package com.fang.core.util;

public class CommonEnum {
	
	/**
	 * 状态：正常、禁用、删除
	 * @author Administrator
	 *
	 */
	public enum enable {
		NORMAL(0), //正常
		DISABLE(1), //禁用
		DELETE(2); //删除
		
		private int value;
		
		private enable(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
    }

}
