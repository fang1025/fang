/**
* @version 2017-06-12 16:19:34
* @author fang
*
**/
package com.fang.sys.service;



import com.fang.sys.entity.DictEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;


public interface IDictService {

    /**
    * 根据指定条件分页查询信息
    * @param param
    * @return
    */
    PageInfo<DictEntity> findDictByPage(ModelMap param);

    /**
    * 根据指定条件查询信息
    * @param param
    * @return
    */
    List<DictEntity> findDictByParam(ModelMap param);

    /**
     * 增加信息
     * @param dict
     * @return
     */
    void addDict(DictEntity dict);
    
    /**
     * 修改信息
     * @param dict
     * @return
     */
    void updateDict(DictEntity dict);
    
    /**
     * 修改enable值
     * @param param
     * @return
     */
    void updateDictEnable(ModelMap param);

	/**
     * 删除信息
     * @param param
     * @return
     */
    void deleteDictById(ModelMap param);

    /**
     * 查找所有的字典类型
     * @return
     */
	List<Map<String, Object>> findDictcode();

}
