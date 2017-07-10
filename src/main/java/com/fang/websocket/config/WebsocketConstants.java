package com.fang.websocket.config;

public interface WebsocketConstants {

	/**
	 * 后台定时推送信息
	 */
    String MASSAGE_PUSH = "/topic/messagepush";
    
    
    /**
     * 根据前台传入用户信息，推送定制信息
     */
    String CUSTOM_PUSH = "/topic/custompush";

}
