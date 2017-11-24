package com.fang.core.aspect;

import com.fang.core.dataSource.DynamimcDataSource;
import com.fang.core.util.SessionUtil;
import com.fang.sys.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 *
 */
public class MapperAspect implements MethodBeforeAdvice,AfterReturningAdvice {
	
	private final static Logger  logger  = LoggerFactory.getLogger(MapperAspect.class);
	
	private static String[] updateMethodNames = new String[]{"add","update","insert"};
	
//	@Pointcut("execution(* com.fang..dao..*Mapper.*(..))")
//	public void aspect() {
//	}
	
	
	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		DynamimcDataSource.clearDataSourceName();
	}


//	@Before("aspect()")
	@Override
	public void before(Method method, Object[] arg1, Object arg2) throws Throwable {
		String methodName = method.getName(); 
		Object entity = arg1.length == 1?arg1[0]:null;
		logger.debug(method.getDeclaringClass().getName() + "." +  methodName + "(" + StringUtils.join(arg1, ",") + ")");
		try {
			//选择数据库
			for (String item : DynamimcDataSource.getReadMethodList()) {
				if (methodName.startsWith(item)) {
					DynamimcDataSource.setDataSourceName(DynamimcDataSource.READONLY);
					break;
				}
			}
			
			//设置基础信息
			initBaseInfo(methodName,entity);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


	private void initBaseInfo(String methodName, Object entity) {
		if(entity == null) return;
		String userName="system";
		Long userId=0L;
		try {
			UserEntity user = (UserEntity) SessionUtil.getCurrentUser();
			userId = user.getId();
			userName = user.getName();
		} catch (Exception e) {
			
		}
		if(entity.getClass().getName().endsWith("Entity") && testMethod(methodName)){

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
		
		
		if(entity.getClass().getName().endsWith("Map") &&  methodName.startsWith("update")){
			Class entityClass = entity.getClass();
			Class<?> baseClass = entityClass;
			while(baseClass.getName() != "java.util.HashMap" && baseClass.getName() != "java.util.Map" ){
				baseClass = baseClass.getSuperclass();
			}
			try {
				Method method = baseClass.getDeclaredMethod("put", Object.class,Object.class);
				method.invoke(entity, "lastUpdateBy",userName);
				method.invoke(entity, "lastUpdateById",userId);
			} catch (Exception e) {
			}
		} 
	}


	private boolean testMethod(String methodName) {
		for (String item : updateMethodNames) {
			if(methodName.startsWith(item)){
				return true;
			}
		}
		return false;
	}




}
