package com.fang.core.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fang.core.define.Constants;
import com.fang.core.entity.OperateRecordEntity;
import com.fang.core.util.RedisUtil;
import com.fang.mq.sender.QueueSender;
import com.fang.sys.entity.UserEntity;

public class ControllerInterceptors implements HandlerInterceptor {
	
	private final static Logger  logger  = LoggerFactory.getLogger(ControllerInterceptors.class);
	
	@Resource
	private QueueSender queueSender;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String url = request.getRequestURI();
		url = url.replace("/" + Constants.APP_ID + "/", "");
		url = cleanUrl(url);
		HttpSession session = request.getSession();
		HashSet<Object> set1 = (HashSet<Object>) RedisUtil.getF("privilegeFunc");
		HashSet<Object> set2 = (HashSet<Object>) RedisUtil.getF("loginFunc");

		UserEntity user = (UserEntity) session.getAttribute(Constants.USERSESSION);
		if (set1.contains(url)) {
			HashMap<String, Object> funcMap = (HashMap<String, Object>) session.getAttribute(Constants.FUNCMAPSESSION);
			if (user == null) {
				response.setStatus(Constants.USERLOGOUT);
				return false;
			} else if (!verifyPrivilege(url, funcMap)) {
				response.setStatus(Constants.NOPRIVILEGE);
				return false;
			}
		} else if (set2.contains(url)) {
			if (user != null)
				return true;
			response.setStatus(Constants.USERLOGOUT);
			return false;
		}
		return true;

	}

	private String cleanUrl(String url) {
		url = url.replaceAll("//", "/").replaceAll("//", "/");
		int index;
		if ((index = url.indexOf("?")) != -1)
			url = url.substring(0, index);
		if (url.startsWith("/")) {
			url = url.substring(1);
			return cleanUrl(url);
		}
		return url;
	}

	private boolean verifyPrivilege(String url, HashMap<String, Object> funcMap) {
		String[] arr = url.split("/");
		for (String item : arr) {
			if (item == null || item.trim().length() == 0) {
				continue;
			} else {
				if (!funcMap.containsKey(item)) {
					return false;
				}
				funcMap = (HashMap<String, Object>) funcMap.get(item);
			}
		}

		return true;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse arg1, Object object, Exception arg3)
			throws Exception {

		// 纯查询操作不做记录
		String url = request.getRequestURI();
		if (url.indexOf("find") != -1 || url.indexOf("get") != -1 || url.indexOf("query") != -1
				|| url.indexOf(".woff2") != -1)
			return;
		UserEntity user = (UserEntity) request.getSession().getAttribute(Constants.USERSESSION);
		
		Class<?> c = object.getClass();
		String methodName = null;
		try {
			Method m = c.getDeclaredMethod("getMethod", null);
			if (m != null) {
				Method method = (Method) m.invoke(object);
				methodName = method.getName();
			}
		} catch (Exception e) {
			logger.equals(e);
		}

		OperateRecordEntity entity = new OperateRecordEntity();
		entity.setUrl(url);
		entity.setMethodName(methodName);
		entity.setIp(request.getRemoteHost());

		queueSender.send(Constants.QUEQUE_OPERATE, entity);

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

}
