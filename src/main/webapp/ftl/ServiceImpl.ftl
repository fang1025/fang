<#include "comment.ftl">
package ${packagePath?if_exists}.${packageName?if_exists}.service.impl;

import com.fang.core.service.BaseService;
import ${packagePath?if_exists}.${packageName?if_exists}.entity.${upperEntityName?if_exists }Entity;
import ${packagePath?if_exists}.${packageName?if_exists}.service.I${upperEntityName?if_exists }Service;
import ${packagePath?if_exists}.${packageName?if_exists}.dao.${upperEntityName?if_exists }Mapper;

import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


@Service
public class ${upperEntityName?if_exists }ServiceImpl extends BaseService implements I${upperEntityName?if_exists }Service {

	
	@Resource
    private ${upperEntityName?if_exists }Mapper mapper;
	
	@Override
    public PageInfo<${upperEntityName?if_exists }Entity> find${upperEntityName?if_exists }ByPage(ModelMap param) {
    	this.startPage(param);
		PageInfo<${upperEntityName?if_exists }Entity> pageInfo = new PageInfo<${upperEntityName?if_exists }Entity>(mapper.find${upperEntityName}ByParam(param));
		return pageInfo;
    }

	@Override
    public List<${upperEntityName?if_exists }Entity> find${upperEntityName?if_exists }ByParam(ModelMap param) {
		return mapper.find${upperEntityName?if_exists }ByParam(param);
    }
    
    
    @Override
    public void add${upperEntityName?if_exists }(${upperEntityName?if_exists }Entity ${entityName?if_exists }) {
		mapper.insert${upperEntityName?if_exists }(${entityName?if_exists });
    }
    
    @Override
    public void update${upperEntityName?if_exists }(${upperEntityName?if_exists }Entity ${entityName?if_exists }) {
		mapper.update${upperEntityName?if_exists }(${entityName?if_exists });
    }
    
    @Override
    public void update${upperEntityName?if_exists }Enable(ModelMap param) {
		mapper.update${upperEntityName?if_exists }Enable(param);
    }

    @Override
    public void delete${upperEntityName?if_exists }ById(ModelMap param) {
		mapper.delete${upperEntityName?if_exists }ById(param);
    }

}
