package com.fang.core.config;

import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

/**
 * 
 * @author fang
 * @version 2017年7月6日
 */
public class MongoConfig {

    // MongoClient是线程安全的，只创建一个实例
    private MongoClient mongoClient;
    
    // Morphia是线程安全的，只创建一个实例
    private Morphia morphia;
    
    // 数据库名
    private String dbName;

    /**
     * @return the mongoClient
     */
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    /**
     * @param mongoClient the mongoClient to set
     */
    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    /**
     * @return the morphia
     */
    public Morphia getMorphia() {
        return morphia;
    }

    /**
     * @param morphia the morphia to set
     */
    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
   
}
