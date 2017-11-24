/**
* @version 2017-06-17 09:05:53
* @author fang
*
**/
package com.fang.sys.dao;

import com.fang.core.dao.BaseMapper;
import com.fang.sys.entity.RoleEntity;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper {


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
    void insertRole(RoleEntity role);
    
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
	 * 删除给角色分配的权限
	 * @param param
	 */
	void deletePrivilegeByRole(ModelMap param);

	/**
	 * 查询角色数据，用于给用户分配角色
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findForAssignRole(ModelMap param);

	/**
	 * 删除用户已有的角色
	 * @param param
	 */
	void deleteRoleRlByUser(ModelMap param);

	/**
	 * 给用户分配角色
	 * @param param
	 */
	void assignRole(ModelMap param);

}
