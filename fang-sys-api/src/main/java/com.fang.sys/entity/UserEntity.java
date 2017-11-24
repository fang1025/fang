package com.fang.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Index;

import com.fang.core.annotation.AttributeInfo;
import com.fang.core.annotation.ClassInfo;
import com.fang.core.entity.BaseEntity;

@Entity
@Table(name="sys_user_tbl")
@Comment("系统用户")
@ClassInfo(packageName = "sys",moduleName = "系统用户")
@Inheritance(strategy=InheritanceType.JOINED)
public class UserEntity extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5528539106657280946L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Comment("姓名")
	@Column(length = 128)
	@AttributeInfo(formType = "input")
	private String name;
	
	@Comment("登陆名")
	@Column(length = 56,unique = true)
	@AttributeInfo(formType = "input")
	private String loginName;
	
	@Comment("邮箱")
	@Column(length = 56)
	@Index(name = "index_email")
	@AttributeInfo(formType = "input")
	private String email;
	
	@Comment("手机")
	@Column(length = 11)
	@Index(name = "index_mobile")
	@AttributeInfo(formType = "input")
	private String mobile;
	
	@Comment("密码")
	@Column(nullable = false, length = 56)
	@AttributeInfo(formType = "input")
	private String password;
	
	@Comment("用户类型,0:默认，1：员工，2：客户")
	@Column(length=2)
	@AttributeInfo(name="用户类型",formType = "select",options="0:系统用户,1:员工,2:客户")
	private Integer type;
	
	@Comment("备注")
	@Column(length=255)
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		UserEntity other = (UserEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
