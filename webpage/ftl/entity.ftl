<#include "comment.ftl">
package com.vast.${packageName?if_exists}.entity;

import com.vast.dict.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "${tableName?if_exists }")
public class ${upperEntityName?if_exists }Entity extends BaseEntity implements Serializable {

    private Integer enable;//是否可用  1 正常  0 删除 2禁用
    private String notes;//备注

<#list fieldInfos as info>
    private ${info.fieldType} ${info.fieldName};//${info.fieldTitle}
</#list>

<#list fieldInfos as info>
    public ${info.fieldType} get${info.fieldName?cap_first}(){
        return ${info.fieldName};
    }

    public void set${info.fieldName?cap_first}(${info.fieldType} ${info.fieldName}){
        this.${info.fieldName} = ${info.fieldName};
    }
</#list>

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
