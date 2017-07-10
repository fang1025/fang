package com.fang.core.entity;

import java.io.Serializable;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="core_operaterecord_tbl", noClassnameStored=true)
public class OperateRecordEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8682907866053959332L;
	
	
	@Id
    private ObjectId id;
	    
    /**
     * 请求的URL
     */
    private String url;
    
    
    /**
     * 方法名称
     */
    private String methodName;
    
    /**
     * ip
     */
    private String ip;
    
    /** 
	 * 备注
	 */
	private String rmks;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRmks() {
		return rmks;
	}

	public void setRmks(String rmks) {
		this.rmks = rmks;
	}

}
