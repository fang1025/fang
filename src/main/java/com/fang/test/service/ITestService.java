/**
* @version 2017-02-14 14:29:27
* @author fang
*
**/
package com.fang.test.service;



import com.fang.sys.entity.DeptEntity;
import org.springframework.ui.ModelMap;

import java.util.Map;
import java.util.List;

public interface ITestService {

 

    /**
     * 增加信息
     * @param dept
     * @return
     */
    void addDept(DeptEntity dept);
    
  

    
}
