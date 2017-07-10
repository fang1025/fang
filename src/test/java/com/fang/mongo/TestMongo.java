package com.fang.mongo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.CodeWScope;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.CriteriaContainerImpl;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.MorphiaKeyIterator;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

import com.fang.core.entity.EmailEntity;
import com.fang.monogo.dao.EmailDao;
import com.fang.monogo.service.IEmailService;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.ReadPreference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class TestMongo {

	@Resource
	private IEmailService emailService;
	
	@Resource
	private EmailDao emailDAO;

	@Test
	public void testFind() {
		List<EmailEntity> list = emailService.findAll();
		for (EmailEntity entity : list) {
			System.out.println("EmailEntity-----" + entity);
		}
	}
	
	
	@Test
	public void testPage(){
		ModelMap params = new ModelMap();
		params.put("start", 1);
		params.put("pageSize", 4);
		QueryResults<EmailEntity> result = emailDAO.find(params);
		System.out.println("total------" + result.countAll());
		System.out.println("getSize------" + result.asList().size());
		List<EmailEntity> list = result.asList();
		for (EmailEntity emailEntity : list) {
			System.out.println("emailEntity-----" + emailEntity);
		}
	}

}
