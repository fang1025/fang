/**
*创建时间:2016-09-19 14:46:15
* @author fang
*
**/
package com.fang.sys.service.impl;

import com.fang.core.service.BaseService;
import com.fang.sys.dao.FunctionMapper;
import com.fang.sys.entity.FunctionEntity;
import com.fang.sys.service.IFunctionService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FunctionServiceImpl extends BaseService implements IFunctionService {

	@Resource
    private FunctionMapper mapper;
	
    public PageInfo<FunctionEntity> findFunctionByPage(ModelMap param) {
    	this.startPage(param);
		PageInfo<FunctionEntity> pageInfo = new PageInfo<FunctionEntity>(mapper.findFunctionByParam(param));
		return pageInfo;
    	
    }

    public List<FunctionEntity> findFunctionByParam(Map<String,Object> param) {
    	return mapper.findFunctionByParam(param);
    }
    
    @Override
    public void addFunction(FunctionEntity function) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("lft", function.getLft());
    	param.put("rgt", function.getLft());
    	param.put("number", 2);
    	param.put("op", ">=");
    	param.put("lastUpdateBy",function.getCreateBy());
    	param.put("lastUpdateById",function.getCreateById());
    	mapper.updateFunctionLft(param);
    	mapper.updateFunctionRgt(param);
    	mapper.insertFunction(function);
    }
    
    @Override
    public void updateFunction(FunctionEntity function) {
    	// 设置用户信息为有效状态
    	mapper.updateFunction(function);

    }
    
    @Override
    public void updateFunctionEnable(Map<String,Object> param) {
    	mapper.updateFunctionEnable(param);
    }

    @Override
    public void deleteFunctionById(Map<String,Object> param) {
    	mapper.deleteFunctionById(param);
    }


    
    @Override
    public Map<String, Object> queryChildrenByParent(long lft, long rgt, long hasAllChild) {
    	Map<String, Object> result = new HashMap<String, Object>();
      
        Map<String, Object> param = new HashMap<String, Object>();
        if (lft > 0) {
            param.put("lft", lft);
        }
        if (rgt > 0) {
            param.put("rgt", rgt);
        }
        param.put("sort", "lft");
        param.put("order", "asc");
        List<FunctionEntity> dataList = mapper.findFunctionByParam(param);

        if (dataList == null || dataList.size() == 0) {
            result.put("total", 0);
            return result;
        }

        if (hasAllChild == 0) {
            List<FunctionEntity> sub = new ArrayList<FunctionEntity>();
            FunctionEntity ago = dataList.get(0);
            FunctionEntity currentEntity = null;
            sub.add(ago);
            for (int i = 1, len = dataList.size(); i < len; i++) {
                currentEntity = dataList.get(i);
                if (currentEntity.getLft() - ago.getRgt() >= 1) {
                    sub.add(currentEntity);
                    ago = currentEntity;
                }
            }
            result.put("rows", sub);
            result.put("total", sub.size());
        } else {
            result.put("rows", dataList);
            result.put("total", dataList.size());
        }
        return result;
    }

	@Override
	public void deleteFunctionByParam(Map<String, Object> param) {
		// 设置用户信息为有效状态
//		param.put("lastUpdateTime",DateUtil.currentDate());
//		param.put("lastUpdateBy",user.getDisplayname());
//		param.put("lastUpdateById",user.getEmpId());
		
		Long lft = Long.valueOf(param.get("lft").toString());
		Long rgt = Long.valueOf(param.get("rgt").toString());
		Long number = rgt - lft + 1;
		param.put("number", 0-number);
		param.put("op", ">");
		mapper.deleteFunctionByParam(param);
		mapper.updateFunctionLft(param);
		mapper.updateFunctionRgt(param);
	}

	@Override
	public List<FunctionEntity> findFunctionByUser(Long userId) {
		List<FunctionEntity> dataList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		if (userId != null) {
			dataList = mapper.findFunctionByUser(userId);
//				sql = "select DISTINCT func.* from u_function_tbl func,"
//						+ "u_rolefunction_tbl rf,u_operatorrole_tbl orole  "
//						+ "where func.id = rf.functionId and rf.roleId = orole.roleId " + "and orole.operatorId =  " + id
//						+ " order by lft ";
		} else {
			param.put("sort", "lft");
			param.put("order", "asc");
			dataList = mapper.findFunctionByParam(param);
		}
		
		return dataList;
	}

	@Override
	public void moveFunction(ModelMap map) {
        Long id = Long.parseLong(map.get("id").toString());
        FunctionEntity lftEntity = null;
        FunctionEntity rgtEntity = null;
        Map<String, Object> param = new HashMap<String, Object>();
        if (map.containsKey("lft")) {
            param.put("rgt_", Integer.parseInt(map.get("lft").toString()) - 1);
            lftEntity = mapper.findFunctionByLftAndRgt(param).get(0);
            param.clear();
            param.put("id", id);
            rgtEntity = mapper.findFunctionByParam(param).get(0);
        }
        if (map.containsKey("rgt")) {
           
        	param.clear();
            param.put("lft_", Integer.parseInt(map.get("rgt").toString()) + 1);
            rgtEntity = mapper.findFunctionByLftAndRgt(param).get(0);
            param.clear();
            param.put("id", id);
            lftEntity = mapper.findFunctionByParam(param).get(0);
        }
        Long rgtNum = rgtEntity.getRgt() - lftEntity.getRgt();
        Long lftNum = rgtEntity.getLft() - lftEntity.getLft();
        param.clear();
        param.put("lft", lftEntity.getLft());
        param.put("rgt", lftEntity.getRgt());
        List<FunctionEntity> lftList = mapper.findFunctionByLftAndRgt(param);
       
        param.clear();
        param.put("lft", rgtEntity.getLft());
        param.put("rgt", rgtEntity.getRgt());
        List<FunctionEntity> rgtList = mapper.findFunctionByLftAndRgt(param);
        for (FunctionEntity entity : lftList) {
            entity.setRgt(entity.getRgt() + rgtNum);
            entity.setLft(entity.getLft() + rgtNum);
            mapper.updateFunction(entity);
        }

        for (FunctionEntity entity : rgtList) {
            entity.setRgt(entity.getRgt() - lftNum);
            entity.setLft(entity.getLft() - lftNum);
            mapper.updateFunction(entity);
        }
		
	}

	@Override
	public List<Map<String, Object>> findFunctionByRoleForPrivilege(ModelMap param) {
		return mapper.findFunctionByRoleForPrivilege(param);
	}
}
