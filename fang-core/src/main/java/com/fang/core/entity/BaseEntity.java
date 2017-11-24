
package com.fang.core.entity;

import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {

	protected final static Logger logger = LoggerFactory.getLogger(BaseEntity.class);

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer(super.toString() + ",field info is: [");
		Method[] ms = getClass().getDeclaredMethods();
		for (Method m : ms) {
			try {
				String name = m.getName();
				if (name.startsWith("get")) {
					Object o = m.invoke(this);
					buf.append(name.substring(3)).append("=").append(o).append(",");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		buf.append("createBy=" + createBy + ", createById=" + createById + ", createTime=" + createTime
				+ ", lastUpdateBy=" + lastUpdateBy + ", lastUpdateById=" + lastUpdateById + ", lastUpdateTime="
				+ lastUpdateTime + ", enable=" + enable + "]");
		return buf.toString();

	}

	@Comment("创建人 ")
	@Column(nullable = false, updatable = false)
	private String createBy;

	@Comment("创建人用户ID ")
	@Column(nullable = false, updatable = false)
	private Long createById;

	@Comment("创建时间 ")
	@Column(nullable = false, updatable = false)
	private Date createTime;

	@Comment("最后更新人员 ")
	@Column(nullable = false)
	private String lastUpdateBy;

	@Comment("最后更新人员ID ")
	@Column(nullable = false)
	private Long lastUpdateById;

	@Comment("最后更新时间 ")
	@Column(nullable = false)
	private Date lastUpdateTime;

	@Comment("0:正常 1：删除 ")
	@Column(nullable = false, length = 1)
	private Integer enable;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Long getCreateById() {
		return createById;
	}

	public void setCreateById(Long createById) {
		this.createById = createById;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Long getLastUpdateById() {
		return lastUpdateById;
	}

	public void setLastUpdateById(Long lastUpdateById) {
		this.lastUpdateById = lastUpdateById;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
}
