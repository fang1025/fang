/**
* @version 2017-06-12 16:19:34
* @author fang
*
**/
package com.fang.sys.service.impl;

import com.fang.core.service.BaseService;
import com.fang.sys.dao.DictMapper;
import com.fang.sys.entity.DictEntity;
import com.fang.sys.service.IDictService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("dictService")
public class DictServiceImpl extends BaseService implements IDictService {

	private final static Logger  logger  = LoggerFactory.getLogger(DictServiceImpl.class);
	
	@Resource
    private DictMapper mapper;
	
	@Override
    public PageInfo<DictEntity> findDictByPage(ModelMap param) {
    	this.startPage(param);
		PageInfo<DictEntity> pageInfo = new PageInfo<DictEntity>(mapper.findDictByParam(param));
		return pageInfo;
    }

	@Override
    public List<DictEntity> findDictByParam(ModelMap param) {
		return mapper.findDictByParam(param);
    }
    
    
    @Override
    public void addDict(DictEntity dict) {
		try {
			mapper.insertDict(dict);
		} catch (DuplicateKeyException e) {
			mapper.updateDict(dict);
		}
    }
    
    @Override
    public void updateDict(DictEntity dict) {
		mapper.updateDict(dict);
    }
    
    @Override
    public void updateDictEnable(ModelMap param) {
		mapper.updateDictEnable(param);
    }

    @Override
    public void deleteDictById(ModelMap param) {
		mapper.deleteDictById(param);
    }

	@Override
	public List<Map<String, Object>> findDictcode() {
		return mapper.findDictcode();
	}

}
