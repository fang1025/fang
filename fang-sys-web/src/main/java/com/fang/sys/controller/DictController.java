/**
* @version 2017-06-12 16:19:34
* @author fang
*
**/
package com.fang.sys.controller;

import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.sys.entity.DictEntity;
import com.fang.sys.service.IDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/dict")
public class DictController extends BaseController {

	private final static Logger  logger  = LoggerFactory.getLogger(DictController.class);

    @Resource
    private IDictService service;
    
    @RequestMapping(value = "/dictList", method=RequestMethod.GET)
	public Object listPage() {
		return "dictList";
	}
	
	@RequestMapping(value = "/dictAdd", method=RequestMethod.GET)
	public Object addPage() {
		return "dictAdd";
	}
    
    /**
     * 分页查询
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findDictByPage", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findDictByPage(@RequestBody ModelMap param, HttpServletRequest request) {
		FangResult result = new FangResult();
    	result.buildPageResult(service.findDictByPage(param));
		return result;
    }

	 /**
     * 查询数据
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findDictByParam",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findDictByParam(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
    	result.buildListResult(service.findDictByParam(param));
		return result;
    }

	/**
     * 新增
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/addDict",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object addDict(@RequestBody DictEntity dict, HttpServletRequest request) {
        FangResult result = new FangResult();
		// 插入数据
		service.addDict(dict);
		result.buildSuccessResult();
		return result;
        
    }
    
    /**
     * 更新
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateDict",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateDict(@RequestBody DictEntity dict, HttpServletRequest request) {
        FangResult result = new FangResult();
        // 修改
		service.updateDict(dict);
		result.buildSuccessResult();
		return result;
    }
    
    /**
     * 更新enable字段
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateDictEnable",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateDictEnable(@RequestBody ModelMap param, HttpServletRequest request) {
       	FangResult result = new FangResult();
       	service.updateDictEnable(param);
       	result.buildSuccessResult();
       	return result;
    }

	/**
     * 删除
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/deleteDictById",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object deleteDictById(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
        service.deleteDictById(param);
        result.buildSuccessResult();
       	return result;
    }
    
    
    @RequestMapping(value="/findDictionaryByCode", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findDictByCode(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	if(!param.containsKey("dictCode") || null == param.get("dictCode")){
    		List<Map<String, Object>> codeList = service.findDictcode();
    		result.put("codeList", codeList);
    		param.put("dictCode", codeList.get(0).get("dictCode"));
    	}
    	result.buildPageResult(service.findDictByPage(param));
    	return result;
    }
    
    @RequestMapping(value="/isDictvalueUnique", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object isDictCodeUnique(@RequestBody ModelMap param, HttpServletRequest request) {
    	FangResult result = new FangResult();
    	List<DictEntity> list = service.findDictByParam(param);
    	if(list != null && list.size() > 0){
    		DictEntity dict = list.get(0);
    		String msg = "字典" + dict.getDictCode() + "[" + dict.getDictName() + "]已存在值为 <b>" + dict.getDictValue() + "</b>的数据!"; 
    		result.buildErrorResult(msg);
    		return result;
    	}
    	result.buildSuccessResult();
    	return result;
    }


}
