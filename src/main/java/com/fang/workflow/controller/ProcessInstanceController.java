package com.fang.workflow.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fang.core.commoms.FangResult;

@Controller
@RequestMapping(value = "/workflow/processinstance")
public class ProcessInstanceController {

    @Autowired
    private RuntimeService runtimeService;

    @RequestMapping("running")
    public ModelMap running(ModelMap params, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/workflow/running-manage");
        FangResult result = new FangResult();
        
        int page = 1, pageSize = 0;
    	if(params.containsKey("page")){
        	page = Integer.parseInt(params.get("page").toString());
        }
    	if(params.containsKey("pageSize")){
    		pageSize = Integer.parseInt(params.get("pageSize").toString());
        }

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> list = processInstanceQuery.listPage((page-1)*pageSize, pageSize);
        result.setRows(list);
        result.setTotal(processInstanceQuery.count());
        return result;
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "update/{state}/{processInstanceId}")
    public String updateState(@PathVariable("state") String state, @PathVariable("processInstanceId") String processInstanceId,
                              RedirectAttributes redirectAttributes) {
        if (state.equals("active")) {
            redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processInstanceId + "]的流程实例。");
            runtimeService.activateProcessInstanceById(processInstanceId);
        } else if (state.equals("suspend")) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processInstanceId + "]的流程实例。");
        }
        return "redirect:/workflow/processinstance/running";
    }
}
