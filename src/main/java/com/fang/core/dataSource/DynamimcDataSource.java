package com.fang.core.dataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

public class DynamimcDataSource extends AbstractRoutingDataSource {
	
	private static final ThreadLocal<String> holder = new ThreadLocal<String>();
	
	private static List<String> READMETHODLIST = new ArrayList<String>();
	
	public static final String WRITE = "write";
	public static final String READONLY = "read";
	
	/**
	 * 设置数据源Key  
	 * @param dataSourceKey  
	 */
	public static void setDataSourceName(String dataSourceName) {  
	    Assert.notNull(dataSourceName, "DataSourceName cannot be null");  
	    holder.set(dataSourceName);
	}  
	  
	/**
	 * 获取数据源Key    
	 * @return 数据源Key  
	 */
	public static String getDataSourceName() {  
	    return holder.get();  
	} 
	
	/**
	 * 清除数据源类型  
	 */
	public static void clearDataSourceName() {  
		holder.remove();  
	}
	
	/**
	 * 加载数据：使用write数据源的方法名
	 * @param map
	 */
	public void setMethodName(String str) {
		String[] methods = str.split(",");
		for (String method : methods) {
			if (StringUtils.isNotBlank(method)) {
				READMETHODLIST.add(method);
			}
		}
		
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return holder.get();
	}

	public static List<String> getReadMethodList() {
		return READMETHODLIST;
	}


}
