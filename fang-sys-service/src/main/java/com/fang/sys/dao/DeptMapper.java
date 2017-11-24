/**
* @author fang
* @version 2017-06-29 19:28:46
* 
**/
package com.fang.sys.dao;

import com.fang.core.dao.BaseMapper;
import com.fang.sys.entity.DeptEntity;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface DeptMapper extends BaseMapper {


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
    void insertDept(DeptEntity dept);
    
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
