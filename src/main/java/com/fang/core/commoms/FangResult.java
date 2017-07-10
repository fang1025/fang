package com.fang.core.commoms;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.fang.core.define.Constants;
import com.fang.sys.entity.DeptEntity;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author fang
 * @version 2017年6月25日 
 */
public class FangResult extends ModelMap implements Constants {
	
	    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8025860745218218L;

	/**
	 * 返回list数据
	 * @param data
	 */
	public void buildListResult(List<?> data) {
		buildSuccessResult();
		this.setRows(data);
    }
	
	/**
	 * 返回分页数据
	 * @param pageInfo
	 */
	public void buildPageResult(PageInfo<?> pageInfo) {
		buildSuccessResult();
		this.setTotal(pageInfo.getTotal());
		this.setRows(pageInfo.getList());
	}

    /**
     * 返回成功结果
     * @return void
     */
    public void buildSuccessResult() {
    	this.put(RESULT_CODE, RESULT_SUCCESS);
    }

    /**
     * 返回错误信息
     * @return void
     */
    public void buildErrorResult(String message) {
        this.put(RESULT_CODE, RESULT_ERROR);
        this.put(RESULT_MSG, message);
    }
    

	public long getTotal() {
		return this.containsKey(TOTAL)?Long.valueOf(this.get(TOTAL)+""):0;
	}

	public void setTotal(long total) {
		this.put(TOTAL, total);
	}

	public List<?> getRows() {
		return (List<?>) this.get(ROWS);
	}

	public void setRows(List<?> rows) {
		this.put(ROWS, rows);
	}

	public String getMessage() {
		return (String) this.get(RESULT_MSG);
	}

	public void setMessage(String message) {
		this.put(RESULT_MSG, message);
	}

	public int getCode() {
		return (Integer) this.get(RESULT_CODE);
	}

	public void setCode(int code) {
		this.put(RESULT_CODE, code);
	}

	

}
