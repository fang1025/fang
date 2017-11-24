package com.fang.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.fang.core.annotation.AttributeInfo;
import com.fang.core.annotation.ClassInfo;
import com.fang.core.entity.BaseEntity;



/**
 * 
 * @author fang
 *
 */
@Entity
@Table(name="sys_dept_tbl")
@Comment("部门")
@ClassInfo(packageName = "sys",moduleName = "部门")
public class DeptEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3125661745031879187L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	/** 
	 * 
	 */
	@Comment("部门编号")
	@Column(nullable = false,length=20,unique=true)
	@AttributeInfo(formType = "input")
	private String deptCode;
	
	/** 
	 * 部门名称 
	 */
	@Comment("部门名称")
	@Column(nullable = false,length=48)
	@AttributeInfo(formType = "input")
	private String deptName;


	@Comment("部门类型")
	@Column(length=2)
	@AttributeInfo(formType = "select")
	private String deptType;
	
	
	/**
	 * 电话 
	 */
	@Column(length=11)
	@AttributeInfo(formType = "input",name="电话")
	private String officeTel;
	
	
	@Comment("传真 ")
	@Column(length=20)
	private String fax;
	

	@Comment("备注 ")
	@Column(length=128)
	@AttributeInfo(formType = "input",name="备注")
	private String notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptCode == null) ? 0 : deptCode.hashCode());
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
		DeptEntity other = (DeptEntity) obj;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		return true;
	}
	



	





}