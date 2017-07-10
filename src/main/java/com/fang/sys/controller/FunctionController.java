/**
*创建时间:2016-09-19 14:46:15
* @author fang
*
**/
package com.fang.sys.controller;


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
import com.fang.sys.entity.FunctionEntity;
import com.fang.sys.service.IFunctionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/function")
public class FunctionController extends BaseController {

	private final static Logger  logger  = LoggerFactory.getLogger(FunctionController.class);

    @Resource
    private IFunctionService service;
    
    
    @RequestMapping(value="/findFunctionByPage", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findFunctionByPage(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	result.buildPageResult(service.findFunctionByPage(param));
		return result;
    }

    @RequestMapping(value="/findFunctionByParam",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findFunctionByParam(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	result.buildListResult(service.findFunctionByParam(param));
		return result;
    }


    @RequestMapping(value="/addFunction",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object addFunction(@RequestBody FunctionEntity function, HttpServletRequest request) {
		FangResult result = new FangResult();
		// 插入数据
		service.addFunction(function);
		result.buildSuccessResult();
		return result;
        
    }
    
    
    @RequestMapping(value="/updateFunction",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateFunction(@RequestBody FunctionEntity function, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	service.updateFunction(function);
    	result.buildSuccessResult();
		return result;
    }
    
    @RequestMapping(value="/updateFunctionEnable",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateFunctionEnable(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	service.updateFunctionEnable(param);
    	result.buildSuccessResult();
		return result;
    }

    @RequestMapping(value="/deleteFunctionById",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object deleteFunctionById(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	service.deleteFunctionById(param);
    	result.buildSuccessResult();
		return result;
    }
    
    @RequestMapping(value="/deleteFunctionByParam",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object deleteFunctionByParam(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	service.deleteFunctionByParam(param);
    	result.buildSuccessResult();
		return result;
    }

    
    
    @RequestMapping(value="/findChildrenByParentId",method=RequestMethod.POST,produces="application/json;charset=UTF-8" )
	public @ResponseBody Map<String, Object> findChildrenByParentId(@RequestBody ModelMap map)
	{
		long lft =0,rgt=0,hasAllChild=0;
		if(map.get("hasAllChild") != null)
		{
			hasAllChild = Long.valueOf(map.get("hasAllChild").toString());
		}
		if(map.get("lft") != null)
		{
			lft = Long.valueOf(map.get("lft").toString());
		}
		if(map.get("rgt") != null)
		{
			rgt = Long.valueOf(map.get("rgt").toString());
		}
		return service.queryChildrenByParent(lft,rgt,hasAllChild);
	}
    
    @RequestMapping(value="/moveFunction",method=RequestMethod.POST,produces="application/json;charset=UTF-8" )
	public @ResponseBody Object moveFunction(@RequestBody ModelMap map)
	{
    	FangResult result = new FangResult();
    	service.moveFunction(map);
    	result.buildSuccessResult();
		return result;
    	
	}
    
    @RequestMapping("/findFunctionByRoleForPrivilege")
    public @ResponseBody Object findFunctionByRoleForPrivilege(@RequestBody ModelMap param) {
    	FangResult result = new FangResult();
    	result.buildListResult(service.findFunctionByRoleForPrivilege(param));
		return result;
    }
    
    
}
