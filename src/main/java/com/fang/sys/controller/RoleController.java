/**
* @version 2017-06-17 09:05:53
* @author fang
*
**/
package com.fang.sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.sys.entity.RoleEntity;
import com.fang.sys.service.IRoleService;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

	private final static Logger  logger  = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private IRoleService service;
    
    @RequestMapping(value = "/roleList", method=RequestMethod.GET)
	public Object listPage() {
		return "roleList";
	}
	
	@RequestMapping(value = "/roleAdd", method=RequestMethod.GET)
	public Object addPage() {
		return "roleAdd";
	}
    
    /**
     * 分页查询
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findRoleByPage", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findRoleByPage(@RequestBody ModelMap param, HttpServletRequest request) {
		FangResult result = new FangResult();
    	result.buildPageResult(service.findRoleByPage(param));
		return result;
    }

	 /**
     * 查询数据
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findRoleByParam",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findRoleByParam(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
    	result.buildListResult(service.findRoleByParam(param));
		return result;
    }

	/**
     * 新增
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/addRole",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object addRole(@RequestBody RoleEntity role, HttpServletRequest request) {
        FangResult result = new FangResult();
		// 插入数据
		service.addRole(role);
		result.buildSuccessResult();
		return result;
        
    }
    
    /**
     * 更新
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateRole",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateRole(@RequestBody RoleEntity role, HttpServletRequest request) {
        FangResult result = new FangResult();
        // 修改
		service.updateRole(role);
		result.buildSuccessResult();
		return result;
    }
    
    /**
     * 更新enable字段
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateRoleEnable",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateRoleEnable(@RequestBody ModelMap param, HttpServletRequest request) {
       	FangResult result = new FangResult();
       	service.updateRoleEnable(param);
       	result.buildSuccessResult();
       	return result;
    }

	/**
     * 删除
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/deleteRoleById",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object deleteRoleById(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
        service.deleteRoleById(param);
        result.buildSuccessResult();
       	return result;
    }
    
    
    
    @RequestMapping("/grantPrivilege")
    public @ResponseBody Object grantPrivilege(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	service.grantPrivilege(param);
    	result.buildSuccessResult();
		return result;
    }
    
    /**
     * 查询角色数据，用于给用户分配角色
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/findForAssignRole")
    public @ResponseBody Object findForAssignRole(@RequestBody ModelMap param) {
    	FangResult result = new FangResult();
    	result.buildListResult(service.findForAssignRole(param));
		return result;
    }
    
    /**
     * 分配角色
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/assignRole")
    public @ResponseBody Object assignRole(@RequestBody ModelMap param) {
    	FangResult result = new FangResult();
    	service.assignRole(param);
    	result.buildSuccessResult();
		return result;
    }


}
