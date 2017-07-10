<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packagePath?if_exists}.${packageName?if_exists}.dao.${upperEntityName?if_exists }Mapper">
	
	<!-- 实体类 - 数据库映射 -->
	<resultMap id="BaseResultMap" type="${packagePath?if_exists}.${packageName?if_exists}.entity.${upperEntityName?if_exists }Entity" >
		<id column="${idName}" property="${idName}"  />
		<#list fieldInfos as info>
		<result column="${info.fieldName}" property="${info.fieldName}"/>
		</#list>
		<result column="enable" property="enable"/>
		<result column="createBy" property="createBy"/>
		<result column="createById" property="createById"/>
		<result column="createTime" property="createTime"/>
		<result column="lastUpdateBy" property="lastUpdateBy"/>
		<result column="lastUpdateById" property="lastUpdateById"/>
		<result column="lastUpdateTime" property="lastUpdateTime"/>
	</resultMap>
    
    <select id="find${upperEntityName?if_exists }ByParam" resultMap="BaseResultMap" parameterType="java.util.Map">
        select
            <#list fieldInfos as info>
            ${info.fieldName},
            </#list>
            ${idName},enable,createBy,createById,createTime,lastUpdateBy,lastUpdateById,lastUpdateTime
        from  ${tableName?if_exists } where  1=1  and enable <![CDATA[<>]]> '1'
        <if test="${idName} != null">
            and ${idName}=${r"#{"}${idName }${r"}"}
        </if>
        order by createTime desc
    </select>

    <insert id="insert${upperEntityName?if_exists }" parameterType="${packagePath?if_exists}.${packageName?if_exists}.entity.${upperEntityName?if_exists }Entity" >
        <selectKey resultType="java.lang.Long" keyProperty="${idName}" order="AFTER" >
			SELECT LAST_INSERT_ID()
		</selectKey>
        insert into ${tableName?if_exists }(
        <#list fieldInfos as info> ${info.fieldName},</#list>
        enable,createBy,createById,createTime,lastUpdateBy,lastUpdateById,lastUpdateTime)
        values(
        <#list fieldInfos as info> ${r"#{"}${info.fieldName}${r"}"},</#list>
        ${r'#{enable}'},${r'#{createBy}'},${r'#{createById}'},now(),${r'#{lastUpdateBy}'},${r'#{lastUpdateById}'},now())
    </insert>

    <update id="update${upperEntityName?if_exists }" parameterType="${packagePath?if_exists}.${packageName?if_exists}.entity.${upperEntityName?if_exists }Entity" >
        update ${tableName?if_exists } set
        <#list fieldInfos as info>
        <#if info.fieldName != 'id' && info.fieldName != 'stationCode'>
        ${info.fieldName} = ${r"#{"}${info.fieldName}${r"}"},
        </#if>
        </#list>
        lastUpdateBy=${r'#{lastUpdateBy}'},lastUpdateById=${r'#{lastUpdateById}'},lastUpdateTime=now()
        where ${idName} = ${r"#{"}${idName }${r"}"}
    </update>
    
    <update id="update${upperEntityName?if_exists }Enable" parameterType="java.util.Map" >
        update ${tableName?if_exists } set
        enable = ${r'#{enable}'},
        lastUpdateBy=${r'#{lastUpdateBy}'},lastUpdateById=${r'#{lastUpdateById}'},lastUpdateTime=now()
        where ${idName} = ${r'#{id}'}
    </update>

    <delete id="delete${upperEntityName?if_exists }ById">
        delete from ${tableName?if_exists } 
        where ${idName} = ${r'#{id}'}
    </delete>



</mapper>
