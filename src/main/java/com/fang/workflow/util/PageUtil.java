package com.fang.workflow.util;

import org.springframework.ui.ModelMap;

/**
* @author fang
* @version 2017年6月26日 下午9:42:10
* 
*/
public class PageUtil {
	
	public static int PAGE_SIZE = 15;

    public static int[] init(ModelMap params) {
    	
    	int page = 1, pageSize = PAGE_SIZE;
		if (params.containsKey("page")) {
			page = Integer.parseInt(params.get("page").toString());
		}
		if (params.containsKey("pageSize")) {
			pageSize = Integer.parseInt(params.get("pageSize").toString());
		}

        int firstResult = (page - 1) * pageSize;
        return new int[]{firstResult, pageSize};
    }

}
