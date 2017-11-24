package com.fang.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Comment;

import com.fang.core.annotation.AttributeInfo;
import com.fang.core.annotation.ClassInfo;
import com.fang.core.entity.BaseEntity;

@Entity
@Table(name="sys_dict_tbl",uniqueConstraints={@UniqueConstraint(columnNames={"dictCode","dictValue"})})
@Comment("字典")
@ClassInfo(packageName = "sys",moduleName = "字典")
public class DictEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5424122350987761658L;
	
	
	/** 
	 * 字典ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Comment("字典编码")
	@Column(length=32,nullable=false)
	@AttributeInfo(formType = "input")
	private String dictCode;


	@Comment("字典名称")
	@Column(length=64,nullable=false)
	@AttributeInfo(formType = "input")
	private String dictName;
	
	@Comment("字典值")
	@Column(length=16,nullable=false)
	@AttributeInfo(formType = "input")
	private String dictValue;
	
	@Comment("字典内容")
	@Column(length=32,nullable=false)
	@AttributeInfo(formType = "input")
	private String dictText;
	
	@Comment("排序值")
	@Column(length=2)
	@AttributeInfo(formType = "input")
	private Integer sortNo;

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictText() {
		return dictText;
	}

	public void setDictText(String dictText) {
		this.dictText = dictText;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dictCode == null) ? 0 : dictCode.hashCode());
		result = prime * result + ((dictValue == null) ? 0 : dictValue.hashCode());
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
		DictEntity other = (DictEntity) obj;
		if (dictCode == null) {
			if (other.dictCode != null)
				return false;
		} else if (!dictCode.equals(other.dictCode))
			return false;
		if (dictValue == null) {
			if (other.dictValue != null)
				return false;
		} else if (!dictValue.equals(other.dictValue))
			return false;
		return true;
	}

}
