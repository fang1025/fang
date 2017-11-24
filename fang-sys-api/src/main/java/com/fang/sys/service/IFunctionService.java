/**
*创建时间:2016-09-19 14:46:15
* @author fang
*
**/
package com.fang.sys.service;


import com.fang.sys.entity.FunctionEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public interface IFunctionService {

    /**
    * 根据指定条件分页查询信息
    * @param param
    * @return
    */
    public PageInfo<FunctionEntity> findFunctionByPage(ModelMap param);

    /**
    * 根据指定条件查询信息
    * @param param
    * @return
    */
    public List<FunctionEntity> findFunctionByParam(Map<String, Object> param);

    /**
     * 增加信息
     * @param function
     * @return
     */
    void addFunction(FunctionEntity function);

    /**
     * 修改信息
     * @param function
     * @return
     */
    void updateFunction(FunctionEntity function);

    /**
     * 修改enable值
     * @param param
     * @return
     */
    void updateFunctionEnable(Map<String, Object> param);

	/**
     * 删除信息
     * @param param
     * @return
     */
    void deleteFunctionById(Map<String, Object> param);


    
    public Map<String, Object> queryChildrenByParent(long lft, long rgt, long hasAllChild);

    /**
     * 删除信息
     * @param param
     */
	public void deleteFunctionByParam(Map<String, Object> param);

	/**
	 * 根据登陆人id 查询对应的功能
	 * @param userId
	 * @return
	 */
	public List<FunctionEntity> findFunctionByUser(Long userId);

	/**
	 * 修改功能排序
	 * @param map
	 */
	public void moveFunction(ModelMap map);

	public List<Map<String, Object>> findFunctionByRoleForPrivilege(ModelMap param);
    
    
}
