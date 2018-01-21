package com.fang.core.define;

/**
 * 
 * @author Administrator
 *
 */
public interface Constants {
	
	String APP_ID = "fang";
     
    // web层返回结果
    int RESULT_SUCCESS = 1;
    int RESULT_ERROR = 0;
    
    //后台返回数据 key定义
    String RESULT_CODE = "code";
    String RESULT_MSG = "message";
    String TOTAL = "total";
    String ROWS = "rows";
    
    //redis
    String CACHE_NAMESPACE = "fang";
    
    //session定义
    String USERSESSION = "USERSESSION";
    String FUNCTIONSESSION = "FUNCTIONSESSION";
    String FUNCMAPSESSION = "FUNCMAPSESSION";

    //定义http status
    
    /**
     * http status: 用户未登录
     */
    Integer USERLOGOUT=999;
    
    /**
     * http status: 没有访问权限
     */
    Integer NOPRIVILEGE=998;


    String[] NOTALLOWATT = new String [] {".js",".exe",".ssh",".php",".bat",".asp",".vbs"};

    /**
     * 配置的基本文件路径
     */
    String BASEPATH = "fang.baseFilePath";
    
    
    //mq destination
    String QUEQUE = "fang.queue";
    
    /**
     * MQ queue 邮件处理队列
     */
    String QUEQUE_EMAIL = "fang.queue.email";
    
    /**
     * MQ queue 操作记录处理队列
     */
    String QUEQUE_OPERATE = "fang.queue.operate";
    
    /**
     * MQ topic 主题
     */
    String TOPIC = "fang.topic";
    
}
