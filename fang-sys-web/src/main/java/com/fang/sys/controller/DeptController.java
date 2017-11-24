/**
* @author fang
* @version 2017-06-29 19:28:46
* 
**/
package com.fang.sys.controller;

import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.sys.entity.DeptEntity;
import com.fang.sys.service.IDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/sys/dept")
public class DeptController extends BaseController {

	private final static Logger  logger  = LoggerFactory.getLogger(DeptController.class);

    @Resource
    private IDeptService service;
    
    
    /**
     * 分页查询
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/findDeptByPage")
    public @ResponseBody Object findDeptByPage(@RequestBody ModelMap param) {
		FangResult result = new FangResult();
    	result.buildPageResult(service.findDeptByPage(param));
		return result;
    }

	 /**
     * 查询数据
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/findDeptByParam")
    public @ResponseBody Object findDeptByParam(@RequestBody ModelMap param) {
        FangResult result = new FangResult();
    	result.buildListResult(service.findDeptByParam(param));
		return result;
    }

	/**
     * 新增
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/addDept")
    public @ResponseBody Object addDept(@RequestBody DeptEntity dept) {
        FangResult result = new FangResult();
		// 插入数据
		service.addDept(dept);
		result.buildSuccessResult();
		return result;
        
    }
    
    /**
     * 更新
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/updateDept")
    public @ResponseBody Object updateDept(@RequestBody DeptEntity dept) {
        FangResult result = new FangResult();
        // 修改
		service.updateDept(dept);
		result.buildSuccessResult();
		return result;
    }
    
    /**
     * 更新enable字段
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/updateDeptEnable")
    public @ResponseBody Object updateDeptEnable(@RequestBody ModelMap param, HttpServletRequest request) {
       	FangResult result = new FangResult();
       	service.updateDeptEnable(param);
       	result.buildSuccessResult();
       	return result;
    }

	/**
     * 删除
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/deleteDeptById")
    public @ResponseBody Object deleteDeptById(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
        service.deleteDeptById(param);
        result.buildSuccessResult();
       	return result;
    }


}
