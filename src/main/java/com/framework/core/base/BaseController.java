package com.framework.core.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.core.common.Page;
import com.framework.utils.AjaxUtils;

@Controller
@RequestMapping("base")
public class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Page queryPage;

	public final static String LOGOUT = "logout";
	public final static String LOGIN = "login";
	public final static String INPUT = "input";
	public final static String ERROR = "error";
	
	/**
	 * 在其它方法之前执行 date 2016年1月4日 author luowei
	 * 
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, HttpServletResponse response) {
		logger.info("BaseController:"+request.getRequestURI());
		this.request = request;
		this.response = response;
	
	}

	/**
	 * 用于处理异常的
	 * 
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public void exception(Exception e, HttpServletResponse response) {
		e.printStackTrace();
		AjaxUtils.renderFailure(e.getMessage(), response);
	}

	/**
	 * 前端到后端的日期转换拦截 date 2016年1月4日 
	 * @author luowei
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
	}
	

}
