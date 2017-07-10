package com.fang.monogo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fang.core.entity.OperateRecordEntity;
import com.fang.monogo.dao.OperateRecordDao;
import com.fang.monogo.service.IOperateRecordService;

@Service
public class OperateRecordServiceImpl implements IOperateRecordService {
	
	@Resource
	private OperateRecordDao operateRecordDao;

	@Override
	public void addOperateRecord(OperateRecordEntity entity) {
		operateRecordDao.save(entity);
	}
	
	

}
