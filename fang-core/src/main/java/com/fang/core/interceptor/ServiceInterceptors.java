package com.fang.core.interceptor;

import com.fang.core.util.SessionUtil;
import com.fang.sys.entity.UserEntity;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;


public class ServiceInterceptors implements MethodBeforeAdvice, AfterReturningAdvice {

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
	}

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		String methodName = arg0.getName(); 
		Object entity = arg1.length == 1?arg1[0]:null;
		String userName="system";
		Long userId=0L;
		try {
			UserEntity user = (UserEntity) SessionUtil.getCurrentUser();
			userId = user.getId();
			userName = user.getName();
		} catch (Exception e) {
			
		}
		
		if(entity != null && entity.getClass().getName().endsWith("Entity") && (methodName.startsWith("add") || methodName.startsWith("update") || methodName.startsWith("insert"))){

			// 设置用户信息为有效状态
			Class entityClass = entity.getClass();
			Class<?> baseClass = entityClass.getSuperclass();

			Method method = null;
			try {
				method = baseClass.getDeclaredMethod("setCreateBy", String.class);
				if(method != null) method.invoke(entity, userName);
				method = baseClass.getDeclaredMethod("setCreateById", Long.class);
				if(method != null) method.invoke(entity, userId);
//				method = baseClass.getDeclaredMethod("setCreateTime", Date.class);
//				if(method != null) method.invoke(entity, new Date());
			} catch (Exception e1) {
			}
			

			//update
			try {
				method = baseClass.getDeclaredMethod("setLastUpdateBy", String.class);
				if(method != null) method.invoke(entity, userName);
				method = baseClass.getDeclaredMethod("setLastUpdateById", Long.class);
				if(method != null) method.invoke(entity, userId);
//				method = baseClass.getDeclaredMethod("setLastUpdateTime", Date.class);
//				if(method != null) method.invoke(entity, new Date());
			} catch (Exception e) {
				
			}
			
			try {
				method = baseClass.getDeclaredMethod("setEnable", Integer.class);
				if(method != null) method.invoke(entity, 0);
			} catch (Exception e) {
				
			}

		} 
		
		
		if(entity != null && entity.getClass().getName().endsWith("Map") &&  methodName.startsWith("update")){
			Class entityClass = entity.getClass();
			Class<?> baseClass = entityClass;
			while(baseClass.getName() != "java.util.HashMap" && baseClass.getName() != "java.util.Map" ){
				baseClass = baseClass.getSuperclass();
			}
			Method method = baseClass.getDeclaredMethod("put", Object.class,Object.class);
			method.invoke(entity, "lastUpdateBy",userName);
			method.invoke(entity, "lastUpdateById",userId);
		} 
		
	}



}
