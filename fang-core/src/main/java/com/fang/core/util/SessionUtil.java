package com.fang.core.util;

import com.fang.core.define.Constants;
import com.fang.sys.entity.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	
	/**
	 * 线程本地环境  
	 */
	private static ThreadLocal<HttpSession> sessionLocal = new ThreadLocal<HttpSession>();
	
	/**
	 * 获取当前HttpSession
	 * @return 当前HttpSession
	 */
	public static HttpSession getSession() {
		return sessionLocal.get();
	}
	
	/**
	 * 设置当前HttpSession
	 * @param session
	 */
	public static void setSession(HttpSession session) {
		sessionLocal.set(session);
	}
	
	/**
	 * 清除线程内容  
	 */
	public static void removeSession(){
		sessionLocal.remove();
	}
	
	public static HttpServletRequest  getHttpServletRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	public static ApplicationContext getSpringContext(){
		HttpServletRequest request = getHttpServletRequest();
		if (request == null || request.getSession() == null) return null;
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return ac;
	}
	
	/**
	 * 获取当前用户信息
	 * @return 当前用户信息
	 */
	public static UserEntity getCurrentUser(){
		HttpServletRequest request = getHttpServletRequest();    
		if (request != null && request.getSession() != null)
		return (UserEntity) request.getSession().getAttribute(Constants.USERSESSION);
		return null;
	}
	
	
	

}
