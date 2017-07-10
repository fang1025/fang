package com.fang.workflow.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import com.fang.workflow.entity.ProcessDefinitionEntity;
import com.fang.workflow.entity.TaskEntity;


public class EntityTransUtil {
	
	
	public static List<TaskEntity> transTask(List<Task> tasks){
		List<TaskEntity> list = new ArrayList<TaskEntity>();
		for (Task task : tasks) {
			list.add(EntityTransUtil.transTask(task));
		}
		return list;
	}
	
	public static TaskEntity transTask(Task task){
		TaskEntity entity = new TaskEntity();
		entity.setId(task.getId());
		entity.setAssignee(task.getAssignee());
		entity.setCategory(task.getCategory());
		entity.setCreateTime(task.getCreateTime());
//		entity.setDeleted(task.getDe);
		entity.setDescription(task.getDescription());
		entity.setDueDate(task.getDueDate());
//		entity.setEventName(task.get);
		entity.setExecutionId(task.getExecutionId());
		entity.setFormKey(task.getFormKey());
//		entity.setIdentityLinksInitialized(task.geti);
//		entity.setInitialAssignee(task.getIn);
		entity.setName(task.getName());
		entity.setOwner(task.getOwner());
		entity.setParentTaskId(task.getParentTaskId());
		entity.setProcessDefinitionId(task.getProcessDefinitionId());
		entity.setProcessInstanceId(task.getProcessInstanceId());
//		entity.setRevision(task.g);
//		entity.setSuspensionState(task.getS);
		entity.setTaskDefinitionKey(task.getTaskDefinitionKey());
		entity.setVariables(task.getProcessVariables());
		return entity;
	};
	
	
	public static List<HistoricTaskInstanceEntity> transHisTask(List<HistoricTaskInstance> historyTasks){
		List<HistoricTaskInstanceEntity> list = new ArrayList<HistoricTaskInstanceEntity>();
		for (HistoricTaskInstance historyTask : historyTasks) {
			list.add(EntityTransUtil.transHisTask(historyTask));
		}
		return list;
	}

	private static HistoricTaskInstanceEntity transHisTask(
			HistoricTaskInstance historyTask) {
		// TODO Auto-generated method stub
		HistoricTaskInstanceEntity entity = new HistoricTaskInstanceEntity();
		entity.setAssignee(historyTask.getAssignee());
		entity.setClaimTime(historyTask.getClaimTime());
		entity.setStartTime(historyTask.getCreateTime());
		entity.setId(historyTask.getId());
		entity.setName(historyTask.getName());
		entity.setEndTime(historyTask.getEndTime());
		entity.setParentTaskId(historyTask.getParentTaskId());
		entity.setProcessInstanceId(historyTask.getProcessInstanceId());
		entity.setExecutionId(historyTask.getExecutionId());
		entity.setParentTaskId(historyTask.getParentTaskId());
		entity.setTaskDefinitionKey(historyTask.getTaskDefinitionKey());
		return entity;
	}
	
	public static List<HistoricActivityInstanceEntity> transHisAct(List<HistoricActivityInstance> hisActs){
		List<HistoricActivityInstanceEntity> list = new ArrayList<HistoricActivityInstanceEntity>();
		for (HistoricActivityInstance hisAct : hisActs) {
			list.add(EntityTransUtil.transHisAct(hisAct));
		}
		return list;
	}
	
	private static HistoricActivityInstanceEntity transHisAct(HistoricActivityInstance hisAct){
		HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
		entity.setActivityId(hisAct.getActivityId());
		entity.setActivityName(hisAct.getActivityName());
		entity.setExecutionId(hisAct.getExecutionId());
		entity.setProcessDefinitionId(hisAct.getProcessDefinitionId());
		entity.setId(hisAct.getId());
		entity.setStartTime(hisAct.getStartTime());
		entity.setActivityType(hisAct.getActivityType());
		entity.setEndTime(hisAct.getEndTime());
		return entity;
	}
	
	public static ProcessDefinitionEntity transProcessDefinition(ProcessDefinition processDefinition){
		ProcessDefinitionEntity entity = new ProcessDefinitionEntity();
		entity.setDeploymentId(processDefinition.getDeploymentId());
		entity.setDiagramResourceName(processDefinition.getDiagramResourceName());
		entity.setId(processDefinition.getId());
		entity.setKey(processDefinition.getKey());
		entity.setName(processDefinition.getName());
		entity.setResourceName(processDefinition.getResourceName());
		entity.setSuspended(processDefinition.isSuspended());
		entity.setVersion(processDefinition.getVersion());
		return entity;
	}

}
