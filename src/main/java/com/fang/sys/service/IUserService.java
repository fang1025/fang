/**
* @version 2017-05-23 17:06:43
* @author fang
*
**/
package com.fang.sys.service;



import com.fang.sys.entity.UserEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;

import java.util.List;


public interface IUserService {

    /**
    * 根据指定条件分页查询信息
    * @param param
    * @return
    */
    PageInfo<UserEntity> findUserByPage(ModelMap param);

    /**
    * 根据指定条件查询信息
    * @param param
    * @return
    */
    List<UserEntity> findUserByParam(ModelMap param);

    /**
     * 增加信息
     * @param user
     * @return
     */
    void addUser(UserEntity user);
    
    /**
     * 修改信息
     * @param user
     * @return
     */
    void updateUser(UserEntity user);
    
    /**
     * 修改enable值
     * @param param
     * @return
     */
    void updateUserEnable(ModelMap param);

	/**
     * 删除信息
     * @param param
     * @return
     */
    void deleteUserById(ModelMap param);


    
    
    /**
     * 通过登陆名，查询用户
     * @param string
     * @return
     */
    UserEntity findByLoginname(String string);
    
}
