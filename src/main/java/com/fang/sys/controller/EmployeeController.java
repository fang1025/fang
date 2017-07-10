/**
* @version 2017-06-18 18:17:34
* @author fang
*
**/
package com.fang.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.sys.entity.EmployeeEntity;
import com.fang.sys.service.IEmployeeService;
import com.fang.sys.service.IUserService;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/sys/employee")
public class EmployeeController extends BaseController {

	private final static Logger  logger  = LoggerFactory.getLogger(EmployeeController.class);

    @Resource
    private IEmployeeService service;
    
    @Resource
    private IUserService userService;
    
    @RequestMapping(value = "/employeeList", method=RequestMethod.GET)
	public Object listPage() {
		return "employeeList";
	}
	
	@RequestMapping(value = "/employeeAdd", method=RequestMethod.GET)
	public Object addPage() {
		return "employeeAdd";
	}
    
    /**
     * 分页查询
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findEmployeeByPage", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findEmployeeByPage(@RequestBody ModelMap param, HttpServletRequest request) {
		FangResult result = new FangResult();
    	result.buildPageResult(service.findEmployeeByPage(param));
		return result;
    }

	 /**
     * 查询数据
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findEmployeeByParam",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findEmployeeByParam(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
    	result.buildListResult(service.findEmployeeByParam(param));
		return result;
    }

	/**
     * 新增
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/addEmployee",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object addEmployee(@RequestBody EmployeeEntity employee, HttpServletRequest request) {
        FangResult result = new FangResult();
		// 插入数据
		service.addEmployee(employee);
		result.buildSuccessResult();
		return result;
        
    }
    
    /**
     * 更新
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateEmployee",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateEmployee(@RequestBody EmployeeEntity employee, HttpServletRequest request) {
        FangResult result = new FangResult();
        // 修改
		service.updateEmployee(employee);
		result.buildSuccessResult();
		return result;
    }
    
    /**
     * 更新enable字段
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateEmployeeEnable",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateEmployeeEnable(@RequestBody ModelMap param, HttpServletRequest request) {
       	FangResult result = new FangResult();
       	service.updateEmployeeEnable(param);
       	result.buildSuccessResult();
       	return result;
    }

	/**
     * 删除
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/deleteEmployeeById",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object deleteEmployeeById(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
        service.deleteEmployeeById(param);
        result.buildSuccessResult();
       	return result;
    }


}
