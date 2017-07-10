package com.fang.core.dao;

import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.ui.ModelMap;

import com.fang.core.config.MongoConfig;
import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * 
 * @author fang
 * @version 2017年7月6日
 * @param <T>
 */
public class BaseMongoDao<T> {

	// Morphia DAO
	private BasicDAO<T, ObjectId> basicDAO;

	// T类名
	private Class<T> entityClazz;

	@Resource
	private MongoConfig mongoConfig;

	@SuppressWarnings("unchecked")
    public BaseMongoDao() {
        this.entityClazz =
                (Class<T>) ((java.lang.reflect.ParameterizedType) super.getClass()
                        .getGenericSuperclass()).getActualTypeArguments()[0];
    }

	public void initDAO() {
		if (this.basicDAO == null) {
			this.basicDAO = new BasicDAO<T, ObjectId>(this.entityClazz, mongoConfig.getMongoClient(),
					mongoConfig.getMorphia(), mongoConfig.getDbName());
		}
	}

	public DBCollection getCollection() {
		initDAO();
		return this.basicDAO.getCollection();
	}

	public Query<T> createQuery() {
		initDAO();
		return this.basicDAO.createQuery();
	}

	public UpdateOperations<T> createUpdateOperations() {
		initDAO();
		return this.basicDAO.createUpdateOperations();
	}

	public Key<T> save(T entity) {
		initDAO();
		return this.basicDAO.save(entity);
	}

	public Key<T> save(T entity, WriteConcern wc) {
		initDAO();
		return this.basicDAO.save(entity, wc);
	}

	public UpdateResults updateFirst(Query<T> q, UpdateOperations<T> ops) {
		initDAO();
		return this.basicDAO.updateFirst(q, ops);
	}

	public UpdateResults update(Query<T> q, UpdateOperations<T> ops) {
		initDAO();
		return this.basicDAO.update(q, ops);
	}

	public WriteResult delete(T entity) {
		initDAO();
		return this.basicDAO.delete(entity);
	}

	public WriteResult delete(T entity, WriteConcern wc) {
		initDAO();
		return this.basicDAO.delete(entity, wc);
	}

	public WriteResult deleteById(ObjectId id) {
		initDAO();
		return this.basicDAO.deleteById(id);
	}

	public WriteResult deleteByQuery(Query<T> q) {
		initDAO();
		return this.basicDAO.deleteByQuery(q);
	}

	public T get(ObjectId id) {
		initDAO();
		return this.basicDAO.get(id);
	}

	public List<ObjectId> findIds() {
		initDAO();
		return this.basicDAO.findIds();
	}

	public List<ObjectId> findIds(Query<T> q) {
		initDAO();
		return this.basicDAO.findIds(q);
	}

	public List<ObjectId> findIds(String key, Object value) {
		initDAO();
		return this.basicDAO.findIds(key, value);
	}

	public Key<T> findOneId() {
		initDAO();
		return this.basicDAO.findOneId();
	}

	public Key<T> findOneId(String key, Object value) {
		initDAO();
		return this.basicDAO.findOneId(key, value);
	}

	public Key<T> findOneId(Query<T> query) {
		initDAO();
		return this.basicDAO.findOneId(query);
	}

	public boolean exists(String key, Object value) {
		initDAO();
		return this.basicDAO.exists(key, value);
	}

	public boolean exists(Query<T> q) {
		initDAO();
		return this.basicDAO.exists(q);
	}

	public long count() {
		initDAO();
		return this.basicDAO.count();
	}

	public long count(String key, Object value) {
		initDAO();
		return this.basicDAO.count(key, value);
	}

	public long count(Query<T> q) {
		initDAO();
		return this.basicDAO.count(q);
	}

	public T findOne(String key, Object value) {
		initDAO();
		return this.basicDAO.findOne(key, value);
	}

	public T findOne(Query<T> q) {
		initDAO();
		return this.basicDAO.findOne(q);
	}

	public QueryResults<T> find() {
		initDAO();
		return this.basicDAO.find();
	}

	public QueryResults<T> find(ModelMap params) {
		initDAO();
		Query<T> query = this.getDatastore().createQuery(entityClazz);
//		if(params.containsKey("start")){
//			query.offset((int) params.get("start").t);
//		}
//		if(params.containsKey("pageSize")){
//			query.limit((int) params.get("pageSize"));
//		}
		return this.basicDAO.find(query);
	}

	public Datastore getDatastore() {
		initDAO();
		return this.basicDAO.getDatastore();
	}

	public void ensureIndexes() {
		initDAO();
		this.basicDAO.ensureIndexes();
	}

}
