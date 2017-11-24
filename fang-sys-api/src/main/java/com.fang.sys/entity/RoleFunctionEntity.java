package com.fang.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.ForeignKey;

import com.fang.core.entity.BaseEntity;


/**
* @author fang
* @version 2017年6月27日 下午8:11:38
* 
*/
@Entity
@Table(name="sys_role_function_rl")
public class RoleFunctionEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8595825143373618927L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Comment("角色ID")
	@ManyToOne
	@JoinColumn(name = "roleId")
	@ForeignKey(name="FK_roleId")
	private RoleEntity role;
	
	@Comment("功能ID")
	@ManyToOne
	@JoinColumn(name = "functionId")
	@ForeignKey(name="FK_functionId")
	private FunctionEntity function;
	
	@Comment("是否为不可更改 0：为可更改  	1: 为不可更改")
	@Column
	private Integer isfinal;
	

}
