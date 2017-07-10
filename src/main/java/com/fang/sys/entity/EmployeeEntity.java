package com.fang.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;

import com.fang.core.annotation.AttributeInfo;
import com.fang.core.annotation.ClassInfo;

@Entity
@Table(name = "sys_employee_tbl")
@Comment("员工")
@ClassInfo(packageName = "sys",moduleName = "员工")
public class EmployeeEntity extends UserEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1637396400766458712L;
	
	
	@Comment("工号")
	@Column(length = 15,nullable=false)
	@AttributeInfo(formType = "input")
	private String employeeNo;
	
	@Comment("身份证号")
	@Column(length = 18)
	@AttributeInfo(formType = "input")
	private String identityCard;
	
	@Comment("性别")
	@Column(length = 2)
	@AttributeInfo(formType = "select")
	private String sexType;
	
	@Comment("出生日期")
	@Column
	@Type(type="date")
	@AttributeInfo(formType = "date")
	private Date birthDate;
	
	
	@Comment("入职日期")
	@Column
	@Type(type="date")
	@AttributeInfo(formType = "date")
	private Date entryDate;
	
	@Comment("离职日期")
	@Column
	@Type(type="date")
	private Date leaveDate;
	
	@Comment("员工状态，0：正常，1：离职，2：休假，3：其他")
	@Column(length=1)
	private Integer workStatus;


	public String getIdentityCard() {
		return identityCard;
	}


	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}


	public String getSexType() {
		return sexType;
	}


	public void setSexType(String sexType) {
		this.sexType = sexType;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	

}
