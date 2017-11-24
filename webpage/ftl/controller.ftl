<#include "comment.ftl">
package ${packagePath?if_exists}.${packageName?if_exists}.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${packagePath?if_exists}.core.commoms.FangResult;
import ${packagePath?if_exists}.core.controller.BaseController;
import ${packagePath?if_exists}.${packageName?if_exists}.entity.${upperEntityName?if_exists }Entity;
import ${packagePath?if_exists}.${packageName?if_exists}.service.I${upperEntityName?if_exists }Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/${packageName?if_exists}/${entityName}")
public class ${upperEntityName?if_exists }Controller extends BaseController {

    @Resource
    private I${upperEntityName?if_exists }Service service;
    
    
    /**
     * 分页查询
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/find${upperEntityName?if_exists }ByPage")
    public @ResponseBody Object find${upperEntityName?if_exists }ByPage(@RequestBody ModelMap param) {
		FangResult result = new FangResult();
    	result.buildPageResult(service.find${upperEntityName?if_exists }ByPage(param));
		return result;
    }

	 /**
     * 查询数据
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/find${upperEntityName?if_exists }ByParam")
    public @ResponseBody Object find${upperEntityName?if_exists }ByParam(@RequestBody ModelMap param) {
        FangResult result = new FangResult();
    	result.buildListResult(service.find${upperEntityName?if_exists }ByParam(param));
		return result;
    }

	/**
     * 新增
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/add${upperEntityName?if_exists }")
    public @ResponseBody Object add${upperEntityName?if_exists }(@RequestBody ${upperEntityName?if_exists }Entity ${entityName?if_exists }) {
        FangResult result = new FangResult();
		// 插入数据
		service.add${upperEntityName?if_exists }(${entityName?if_exists });
		result.buildSuccessResult();
		return result;
        
    }
    
    /**
     * 更新
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/update${upperEntityName?if_exists }")
    public @ResponseBody Object update${upperEntityName?if_exists }(@RequestBody ${upperEntityName?if_exists }Entity ${entityName?if_exists }) {
        FangResult result = new FangResult();
        // 修改
		service.update${upperEntityName?if_exists }(${entityName?if_exists });
		result.buildSuccessResult();
		return result;
    }
    
    /**
     * 更新enable字段
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/update${upperEntityName?if_exists }Enable")
    public @ResponseBody Object update${upperEntityName?if_exists }Enable(@RequestBody ModelMap param, HttpServletRequest request) {
       	FangResult result = new FangResult();
       	service.update${upperEntityName?if_exists }Enable(param);
       	result.buildSuccessResult();
       	return result;
    }

	/**
     * 删除
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/delete${upperEntityName?if_exists }ById")
    public @ResponseBody Object delete${upperEntityName?if_exists }ById(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
        service.delete${upperEntityName?if_exists }ById(param);
        result.buildSuccessResult();
       	return result;
    }


}
