package com.fang.sys.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.fang.core.annotation.AttributeInfo;
import com.fang.core.annotation.ClassInfo;
import com.fang.core.entity.BaseEntity;


@Entity
@Table(name="sys_role_tbl")
@Comment("角色")
@ClassInfo(packageName = "sys",moduleName = "角色")
public class RoleEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6427162537497136865L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Comment("角色编号")
	@Column(nullable = false,length=20,unique=true)
	@AttributeInfo(formType = "input")
	private String roleCode;
	

	@Comment("角色名称")
	@Column(nullable = false,length=48)
	@AttributeInfo(formType = "input")
	private String roleName;
	
	
	@Comment("备注 ")
	@Column(length=128)
	@AttributeInfo(formType = "input")
	private String notes;
	
	@Comment("是否为不可更改 0：为可更改  	1: 为不可更改 ")
	@Column(length=1)
	private Integer isfinal;
	
	@ManyToMany
	@JoinTable(name="sys_user_role_rl",
				joinColumns={@JoinColumn(name="roleId")},
				inverseJoinColumns={@JoinColumn(name="userId")})
	private List<UserEntity> userList;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<UserEntity> getUserList() {
		return userList;
	}

	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleCode == null) ? 0 : roleCode.hashCode());
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
		RoleEntity other = (RoleEntity) obj;
		if (roleCode == null) {
			if (other.roleCode != null)
				return false;
		} else if (!roleCode.equals(other.roleCode))
			return false;
		return true;
	}

	public Integer getIsfinal() {
		return isfinal;
	}

	public void setIsfinal(Integer isfinal) {
		this.isfinal = isfinal;
	}

}
