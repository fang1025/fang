/**
* @version 2017-06-17 09:05:53
* @author fang
*
**/
package com.fang.sys.service;



import com.fang.sys.entity.RoleEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;


public interface IRoleService {

    /**
    * 根据指定条件分页查询信息
    * @param param
    * @return
    */
    PageInfo<RoleEntity> findRoleByPage(ModelMap param);

    /**
    * 根据指定条件查询信息
    * @param param
    * @return
    */
    List<RoleEntity> findRoleByParam(ModelMap param);

    /**
     * 增加信息
     * @param role
     * @return
     */
    void addRole(RoleEntity role);
    
    /**
     * 修改信息
     * @param role
     * @return
     */
    void updateRole(RoleEntity role);
    
    /**
     * 修改enable值
     * @param param
     * @return
     */
    void updateRoleEnable(ModelMap param);

	/**
     * 删除信息
     * @param param
     * @return
     */
    void deleteRoleById(ModelMap param);

    /**
     * 给角色分配权限
     * @param param
     */
	void grantPrivilege(ModelMap param);

	/**
	 * 查询角色数据，用于给用户分配角色
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findForAssignRole(ModelMap param);

	/**
	 * 分配角色
	 * @param param
	 */
	void assignRole(ModelMap param);

}
