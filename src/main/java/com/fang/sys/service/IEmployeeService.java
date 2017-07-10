/**
* @version 2017-06-18 18:17:34
* @author fang
*
**/
package com.fang.sys.service;



import com.fang.sys.entity.EmployeeEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;

import java.util.List;


public interface IEmployeeService {

    /**
    * 根据指定条件分页查询信息
    * @param param
    * @return
    */
    PageInfo<EmployeeEntity> findEmployeeByPage(ModelMap param);

    /**
    * 根据指定条件查询信息
    * @param param
    * @return
    */
    List<EmployeeEntity> findEmployeeByParam(ModelMap param);

    /**
     * 增加信息
     * @param employee
     * @return
     */
    void addEmployee(EmployeeEntity employee);
    
    /**
     * 修改信息
     * @param employee
     * @return
     */
    void updateEmployee(EmployeeEntity employee);
    
    /**
     * 修改enable值
     * @param param
     * @return
     */
    void updateEmployeeEnable(ModelMap param);

	/**
     * 删除信息
     * @param param
     * @return
     */
    void deleteEmployeeById(ModelMap param);

}
