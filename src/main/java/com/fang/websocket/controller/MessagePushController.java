package com.fang.websocket.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fang.websocket.config.WebsocketConstants;


@Controller
public class MessagePushController implements WebsocketConstants {
    
	@Resource
    private SimpMessagingTemplate template;
    
	 /**
     * 接收前台推送信息，再将定制信息推送给前台
     */
    @MessageMapping("/custompush/{userName}")
    public void busialarmsinfo( @Headers Map<String, Object> param,@DestinationVariable String userName,String body) {
    	Map<String, Object> messagePushMap = new HashMap<String, Object>();
    	Map<String, String> nativeHeaders = (Map<String, String>) param.get("nativeHeaders");
    	//刷新页面时
        messagePushMap.put("name",userName);
        messagePushMap.put("brower",nativeHeaders.get("brower"));
        messagePushMap.put("message", "hellowork");
        messagePushMap.put("body", body);
        this.template.convertAndSend(CUSTOM_PUSH + "/" + userName, messagePushMap);
    }
}
