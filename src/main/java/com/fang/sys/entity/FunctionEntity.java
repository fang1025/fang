package com.fang.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;

import com.fang.core.annotation.AttributeInfo;
import com.fang.core.annotation.ClassInfo;
import com.fang.core.entity.BaseEntity;

@Entity
@Table(name="sys_function_tbl")
@Comment("功能权限")
@ClassInfo(packageName = "sys",moduleName = "功能权限")
public class FunctionEntity extends BaseEntity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5857590381741988260L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Comment("功能名称")
	@Column(nullable=false,length=30)
	@AttributeInfo(formType = "input")
	private String functionName;
	
	/**
	 *  功能名称对应的Url，可以为空，空时链接到blank，Url的地址存相对地址或绝对地址，如果是http 开始的则是绝对地址，否则就是相对地址。
	 */
	@Comment("功能地址")
	@Column(length=128)
	@AttributeInfo(formType = "input")
	private String functionUrl;
	

	@Comment("功能按钮对应的 节点的id或方法名称")
	@Column(length=128)
	@AttributeInfo(formType = "input",name="节点Id")
	private String functionId;
	
	@Column
	@Comment("后台地址")
	@Type(type="text")
	@AttributeInfo(formType = "input")
	private String extraUrl;
	
	@Column
	@Comment("功能元素")
	@Type(type="text")
	private String htmlStr;	
	

	@Comment("功能菜单时对应的Icon图片地址")
	@Column(length=48)
	@AttributeInfo(formType = "input",name = "功能图标")
	private String functionIcon;
	
	@Comment("功能菜单对应的class样式名称")
	@Column(length=48)
	@AttributeInfo(formType = "input",name = "功能样式")
	private String functionClass;
	
	/**
	 * 功能类型  1：左侧父功能  2：中间主功能  3：右侧次功能 4：上方页签功能
	 */
	@Comment("功能类型  1：左侧父功能   2：二级子功能（按钮）")
	@Column(nullable=false)
	@AttributeInfo(formType = "select",name = "功能类型")
	private Integer type;
	
	@Comment("起始位置")
	@Column(nullable=false)
	private Long lft;
	

	@Comment("结束位置")
	@Column(nullable=false)
	private Long rgt;
	
	
//	@Comment("序号")
//	@Column(nullable=false)
//	private Integer sortNo;
	
	@Comment("备注")
	@Column(length=255)
	private String notes;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFunctionName() {
		return functionName;
	}


	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}


	public String getFunctionUrl() {
		return functionUrl;
	}


	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}


	public String getFunctionId() {
		return functionId;
	}


	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}


	public String getExtraUrl() {
		return extraUrl;
	}


	public void setExtraUrl(String extraUrl) {
		this.extraUrl = extraUrl;
	}


	public String getHtmlStr() {
		return htmlStr;
	}


	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}


	public String getFunctionIcon() {
		return functionIcon;
	}


	public void setFunctionIcon(String functionIcon) {
		this.functionIcon = functionIcon;
	}


	public String getFunctionClass() {
		return functionClass;
	}


	public void setFunctionClass(String functionClass) {
		this.functionClass = functionClass;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Long getLft() {
		return lft;
	}


	public void setLft(Long lft) {
		this.lft = lft;
	}


	public Long getRgt() {
		return rgt;
	}


	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}

}
