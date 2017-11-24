/**
*创建时间:2016-09-19 14:46:15
* @author fang
*
**/
package com.fang.sys.dao;

import com.fang.core.dao.BaseMapper;
import com.fang.sys.entity.FunctionEntity;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public interface FunctionMapper extends BaseMapper {


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
    void insertFunction(FunctionEntity function);

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


    public void deleteFunctionByParam(Map<String, Object> param);

	public void updateFunctionLft(Map<String, Object> param);

	public void updateFunctionRgt(Map<String, Object> param);

	public List<FunctionEntity> findFunctionByUser(Long userId);

	public List<FunctionEntity> findFunctionByLftAndRgt(Map<String, Object> param);

	/**
	 * 根据角色查找功能数据
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findFunctionByRoleForPrivilege(ModelMap param);
}
