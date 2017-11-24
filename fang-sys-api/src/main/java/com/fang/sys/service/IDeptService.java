/**
* @author fang
* @version 2017-06-29 19:28:46
* 
**/
package com.fang.sys.service;



import com.fang.sys.entity.DeptEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;

import java.util.List;


public interface IDeptService {

    /**
    * 根据指定条件分页查询信息
    * @param param
    * @return
    */
    PageInfo<DeptEntity> findDeptByPage(ModelMap param);

    /**
    * 根据指定条件查询信息
    * @param param
    * @return
    */
    List<DeptEntity> findDeptByParam(ModelMap param);

    /**
     * 增加信息
     * @param dept
     * @return
     */
    void addDept(DeptEntity dept);
    
    /**
     * 修改信息
     * @param dept
     * @return
     */
    void updateDept(DeptEntity dept);
    
    /**
     * 修改enable值
     * @param param
     * @return
     */
    void updateDeptEnable(ModelMap param);

	/**
     * 删除信息
     * @param param
     * @return
     */
    void deleteDeptById(ModelMap param);

}
