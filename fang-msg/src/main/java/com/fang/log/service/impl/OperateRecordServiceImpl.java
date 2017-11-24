package com.fang.log.service.impl;

import com.fang.core.entity.OperateRecordEntity;
import com.fang.log.mongo.OperateRecordDao;
import com.fang.log.service.IOperateRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("operateRecordService")
public class OperateRecordServiceImpl implements IOperateRecordService {
	
	@Resource
	private OperateRecordDao operateRecordDao;

	@Override
	public void addOperateRecord(OperateRecordEntity entity) {
		operateRecordDao.save(entity);
	}
	
	

}
