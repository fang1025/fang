package com.fang.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fang.core.define.Constants;
import com.fang.core.util.JsonUtil;

public class BaseController implements Constants  {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/** 异常处理 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		logger.error(request.getRequestURI(), ex);
		ModelMap result = new ModelMap();
		result.put(RESULT_CODE, RESULT_ERROR);
		result.put(RESULT_MSG, ex.getMessage());
		response.setContentType("application/json;charset=UTF-8");
		byte[] bytes = JsonUtil.objectTojson(result).getBytes("utf-8");
		response.getOutputStream().write(bytes);

	}
	

}
