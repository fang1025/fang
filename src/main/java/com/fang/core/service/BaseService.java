package com.fang.core.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


public class BaseService {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	/**
	 * 获取request
	 * @return
	 */
	protected HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/** 
	 * 启动分页查询 
	 */
    protected Page<?> startPage(Map<String, Object> params) {
    	int page = 0, pageSize = 0;
    	if(params.containsKey("page")){
        	page = Integer.parseInt(params.get("page").toString());
        }
    	if(params.containsKey("pageSize")){
    		pageSize = Integer.parseInt(params.get("pageSize").toString());
        }
        return PageHelper.startPage(page,pageSize);
    }

}
