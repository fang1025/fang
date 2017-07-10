<#include "comment.ftl">
package ${packagePath?if_exists}.${packageName?if_exists}.dao;

import ${packagePath?if_exists}.core.dao.BaseMapper;
import ${packagePath?if_exists}.${packageName?if_exists}.entity.${upperEntityName?if_exists }Entity;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ${upperEntityName?if_exists }Mapper extends BaseMapper {


    /**
    * 根据指定条件查询信息
    * @param param
    * @return
    */
    List<${upperEntityName?if_exists }Entity> find${upperEntityName?if_exists }ByParam(ModelMap param);

    /**
     * 增加信息
     * @param ${entityName?if_exists }
     * @return
     */
    void insert${upperEntityName?if_exists }(${upperEntityName?if_exists }Entity ${entityName?if_exists });
    
    /**
     * 修改信息
     * @param ${entityName?if_exists }
     * @return
     */
    void update${upperEntityName?if_exists }(${upperEntityName?if_exists }Entity ${entityName?if_exists });
    
    /**
     * 修改enable值
     * @param param
     * @return
     */
    void update${upperEntityName?if_exists }Enable(ModelMap param);

	/**
     * 删除信息
     * @param param
     * @return
     */
    void delete${upperEntityName?if_exists }ById(ModelMap param);

}
