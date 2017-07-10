/**
* @version 2017-05-23 17:06:43
* @author fang
*
**/
package com.fang.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.core.util.MD5Util;
import com.fang.sys.entity.FunctionEntity;
import com.fang.sys.entity.UserEntity;
import com.fang.sys.service.IFunctionService;
import com.fang.sys.service.IUserService;

@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {


    @Resource
    private IUserService service;
    
    @Resource
    private IFunctionService functionService;
    
    @RequestMapping(value = "/userList", method=RequestMethod.GET)
	public Object listPage() {
		return "userList";
	}
	
	@RequestMapping(value = "/userAdd", method=RequestMethod.GET)
	public Object addPage() {
		return "userAdd";
	}
    
    /**
     * 分页查询
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findUserByPage", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findUserByPage(@RequestBody ModelMap param, HttpServletRequest request) {
		FangResult result = new FangResult();
    	result.buildPageResult(service.findUserByPage(param));
		return result;
    }

	 /**
     * 查询数据
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/findUserByParam",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object findUserByParam(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
    	result.buildListResult(service.findUserByParam(param));
		return result;
    }

	/**
     * 新增
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/addUser",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object addUser(@RequestBody UserEntity user, HttpServletRequest request) {
        FangResult result = new FangResult();
		// 插入数据
		service.addUser(user);
		result.buildSuccessResult();
		return result;
        
    }
    
    /**
     * 更新
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateUser",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateUser(@RequestBody UserEntity user, HttpServletRequest request) {
        FangResult result = new FangResult();
        // 修改
		service.updateUser(user);
		result.buildSuccessResult();
		return result;
    }
    
    /**
     * 更新enable字段
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/updateUserEnable",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object updateUserEnable(@RequestBody ModelMap param, HttpServletRequest request) {
       	FangResult result = new FangResult();
       	service.updateUserEnable(param);
       	result.buildSuccessResult();
       	return result;
    }

	/**
     * 删除
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value="/deleteUserById",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    public @ResponseBody Object deleteUserById(@RequestBody ModelMap param, HttpServletRequest request) {
        FangResult result = new FangResult();
        service.deleteUserById(param);
        result.buildSuccessResult();
       	return result;
    }


    
    /**
	 * 用户登录方法
	 * @param map 手机号码和密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/userLogin")
	public @ResponseBody Object userLogin(@RequestBody ModelMap map, HttpServletRequest request){
		FangResult result = new FangResult();
		UserEntity user = service.findByLoginname(map.get("loginName").toString());
		if(user == null){
			result.buildErrorResult("用户不存在！");
			return result;
		}
		if(!user.getPassword().equals(MD5Util.getMD5(map.get("password").toString() + user.getCreateTime().getTime()))){
			result.buildErrorResult("密码错误！");
			return result;
			
		}
		if(user.getEnable() != 0){
			result.buildErrorResult("账户被禁用！");
			return result;
		}
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3600 * 3);
		session.setAttribute(USERSESSION, user);
		
		if(2 != user.getType() ){
			List<FunctionEntity> functionList = functionService.findFunctionByUser(user.getLoginName().equals("admin")?null:user.getId());
			result.put("functionList", functionList);
			session.setAttribute(FUNCTIONSESSION, functionList);
			HashMap<String, Object> funcMap = new HashMap<String, Object>();
			for (FunctionEntity entity : functionList) {
				if(entity.getExtraUrl() != null && entity.getExtraUrl().trim().length() > 0){
					String[] urlArr = entity.getExtraUrl().split("/");
					dealFunc(urlArr,funcMap,0);				
				}
			}
			session.setAttribute(FUNCMAPSESSION, funcMap);
		}
		
		result.put("user", user);
		result.buildSuccessResult();
		return result;
	}
	
	private void dealFunc(String[] urlArr, Map<String, Object> map2, int i) {
		if(i >= urlArr.length){
			return;
		}
		String url = urlArr[i++];
		if(url == null ||  url.trim().length() == 0){
			dealFunc(urlArr,map2,i);
		}else if(map2.containsKey(url)){
			Map<String, Object> map = (Map<String, Object>) map2.get(url);
			dealFunc(urlArr, map, i);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map2.put(url, map);
			dealFunc(urlArr, map, i);
		}
	}

	
	/**
	 * 登出
	 * @param request
	 * @return
	 */
	@RequestMapping("/userLogout")
	public @ResponseBody Map<String, Object> userLogout(HttpServletRequest request){
		FangResult result = new FangResult();
		request.getSession().removeAttribute(USERSESSION);
		//如果Session中还有其他元素，也要一起删除
		result.buildSuccessResult();
		return result;
	}
	
}
