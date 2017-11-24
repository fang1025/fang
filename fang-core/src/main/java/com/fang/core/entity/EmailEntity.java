package com.fang.core.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

@Entity(value="core_email_tbl", noClassnameStored=true)
public class EmailEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1214645230706019173L;

	@Id
    private ObjectId id;
	
	private String host;
	private String from;
	private String name;
	private String password;
	private String keyword;
	private String sendTo;
	private String copyTo;
	private String subject;
	private String content;
	private String[] attachments;
	private Integer status;//0:未发送 1:发送成功 2：发送失败

	public EmailEntity() {
	}

	/**
	 * @param sendTo
	 *            接收人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public EmailEntity(String sendTo, String subject, String content) {
		this(null, sendTo, null, subject, content, null);
	}

	/**
	 * @param sendTo
	 *            接收人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param attachments
	 *            附件
	 */
	public EmailEntity(String sendTo, String subject, String content, String[] attachments) {
		this(sendTo, null, subject, content, attachments);
	}

	/**
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public EmailEntity(String sendTo, String copyTo, String subject, String content) {
		this(null, sendTo, copyTo, subject, content, null);
	}

	/**
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param attachments
	 *            附件
	 */
	public EmailEntity(String sendTo, String copyTo, String subject, String content, String[] attachments) {
		this(null, sendTo, copyTo, subject, content, attachments);
	}

	/**
	 * @param from
	 *            发送人
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public EmailEntity(String from, String sendTo, String copyTo, String subject, String content) {
		this(from, sendTo, copyTo, subject, content, null);
	}

	/**
	 * @param from
	 *            发送人
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param attachments
	 *            附件
	 */
	public EmailEntity(String from, String sendTo, String copyTo, String subject, String content,
			String[] attachments) {
		this(from, null, null, null, sendTo, copyTo, subject, content, attachments);
	}

	/**
	 * @param from
	 *            发送人
	 * @param name
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public EmailEntity(String from, String name, String password, String sendTo, String copyTo, String subject,
			String content) {
		this(null, from, name, password, sendTo, copyTo, subject, content, null);
	}

	/**
	 * @param from
	 *            发送人
	 * @param name
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param attachments
	 *            附件
	 */
	public EmailEntity(String from, String name, String password, String sendTo, String copyTo, String subject,
			String content, String[] attachments) {
		this(null, from, name, password, sendTo, copyTo, subject, content, attachments);
	}

	/**
	 * @param host
	 *            服务器地址
	 * @param from
	 *            发送人
	 * @param name
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public EmailEntity(String host, String from, String name, String password, String sendTo, String copyTo,
			String subject, String content) {
		this(host, from, name, password, sendTo, copyTo, subject, content, null);
	}

	/**
	 * @param host
	 *            服务器地址
	 * @param from
	 *            发送人
	 * @param name
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param sendTo
	 *            接收人
	 * @param copyTo
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param attachments
	 *            附件
	 */
	public EmailEntity(String host, String from, String name, String password, String sendTo, String copyTo,
			String subject, String content, String[] attachments) {
		this.host = host;
		this.from = from;
		this.name = name;
		this.password = password;
		this.sendTo = sendTo;
		this.copyTo = copyTo;
		this.subject = subject;
		this.content = content;
		this.attachments = attachments;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getAttachments() {
		return attachments;
	}

	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailEntity other = (EmailEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}





}
