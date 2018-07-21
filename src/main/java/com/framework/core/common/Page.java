package com.framework.core.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * 分页参数封装类
 */
public class Page {
	
	private Integer rows=10;//每页显示记录
	private Integer page=1;//开始查询的页，默认从第一页开始查询
	private Integer recordCount=0;//总记录数
	private Integer pageCount=0;//总页数
	private String sort;//排序的字段
	private String order;//desc,dsc
	private boolean isLoadAll = true; // 是否加载了全部分页数据
	
	public Page(){

	}
	
	public Page(HttpServletRequest request) {
		String page=request.getParameter("page");//分页开始页
		String rows=request.getParameter("rows");//每页显示条数
		if(StringUtils.isNotBlank(rows)&&StringUtils.isNotBlank(page)){
			this.rows=Integer.valueOf(rows);
			this.page=Integer.valueOf(page);
			this.sort=request.getParameter("sort");
			if(StringUtils.isBlank(sort)||"null".equals(sort)){
				this.sort=null;
			}
			if(this.sort!=null){
				this.sort=this.sort.replace("_", ".");
			}
			this.order=request.getParameter("order");
		}
	}
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * 总页数
	 * @return
	 */
	public Integer getPageCount() {
		return recordCount%rows==0?recordCount/rows:recordCount/rows+1;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	
	/**
	 * 数据库查询时，从第几条记录开始查询
	 * @return
	 */
	public int getFirstResult(){
		pageCount=getPageCount();
		if(page<=0){
			page=1;
		} else if(isLoadAll && page>pageCount){
			page=pageCount;
		}
		return (page-1)*rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isLoadAll() {
		return isLoadAll;
	}

	public void setLoadAll(boolean isLoadAll) {
		this.isLoadAll = isLoadAll;
	}
	
	
}
