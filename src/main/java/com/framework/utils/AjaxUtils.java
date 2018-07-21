package com.framework.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * AJAX帮助工具类，在controller中,使用此工具返回内容。
 */
public class AjaxUtils {
	
	private static Log log=LogFactory.getLog(AjaxUtils.class);
	/**
	 * 将字符串内容写到一个页面
	 * @param content
	 */
	public static void renderText(final String content,HttpServletResponse response){
		try{
			log.debug(content);
			if(StringUtils.isEmpty(content)){
				log.debug("AjaxRender.java>>method:renderText>>content is null!");
				return;
			}
			initHeader(response);
			response.getWriter().write(content);
			response.getWriter().close();
		}catch(Exception e){
			log.error(e);
		}
	}
	
	/**
	 * 初始化页面header
	 * @param response
	 */
	private static void initHeader(HttpServletResponse response){
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	}
	
	
	
	/**
	 * 生成分页JSON数据
	 * @param objectList JSON格式的对象集合
	 * @param recordCount 记录数
	 */
	public static void renderPagination(List<JSONObject> objectList,Integer recordCount,HttpServletResponse response) {
		if(objectList==null||objectList.size()==0){
			log.debug("目前对象集合为空！");
			objectList=new ArrayList<JSONObject>();
		}
		JSONObject jsonRoot = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (JSONObject jsonObj : objectList) {
			jsonArray.add(jsonObj);
		}
		jsonRoot.put("rows", jsonArray);
		jsonRoot.put("total", recordCount);
		renderText(jsonRoot.toString(),response);
	}
	
	/**
	 * 生成分页JSON数据+底部
	 * @param objectList JSON格式的对象集合
	 * @param root 底部信息
	 * @param recordCount 记录数
	 */
	public static void renderRootPagination(List<JSONObject> objectList,String root,Integer recordCount,HttpServletResponse response) {
		if(objectList==null||objectList.size()==0){
			log.debug("目前对象集合为空！");
			objectList=new ArrayList<JSONObject>();
		}
		JSONObject jsonRoot = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (JSONObject jsonObj : objectList) {
			jsonArray.add(jsonObj);
		}
		jsonRoot.put("rows", jsonArray);
		jsonRoot.put("footer", root);
		jsonRoot.put("total", recordCount);
		renderText(jsonRoot.toString(),response);
	}
	
	/**
	 * 生成JSON数组
	 * @param objectList JSON格式的对象集合
	 * @param root 底部信息
	 */
	public static void renderList(List<JSONObject> objectList,String root,HttpServletResponse response) {
		if(objectList==null||objectList.size()==0){
			log.debug("目前对象集合为空！");
			objectList=new ArrayList<JSONObject>();
		}
		JSONObject jsonRoot = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (JSONObject jsonObj : objectList) {
			jsonArray.add(jsonObj);
		}
		jsonRoot.put(root, jsonArray);
		renderText(jsonRoot.toString(),response);
	}
	
	/**
	 * 生成JSON数组
	 * @param objectList JSON格式的对象集合
	 * @param root1/2 底部信息
	 */
	public static void renderLists(
			List<JSONObject> objectList1,String root1,
			List<JSONObject> objectList2,String root2,
			HttpServletResponse response) {
		if(objectList1==null||objectList1.size()==0){
			log.debug("目前对象集合为空！");
			objectList1=new ArrayList<JSONObject>();
		}
		JSONObject jsonRoot = new JSONObject();
		JSONArray jsonArray1 = new JSONArray();
		for (JSONObject jsonObj : objectList1) {
			jsonArray1.add(jsonObj);
		}
		jsonRoot.put(root1, jsonArray1);
		
		if(objectList2==null||objectList2.size()==0){
			log.debug("目前对象集合为空！");
			objectList2=new ArrayList<JSONObject>();
		}
		JSONArray jsonArray2 = new JSONArray();
		for (JSONObject jsonObj : objectList2) {
			jsonArray2.add(jsonObj);
		}
		jsonRoot.put(root2, jsonArray2);
		
		renderText(jsonRoot.toString(),response);
	}

	/**
	 * 向页面返回一个错误JSON信息。
	 * @param msg
	 */
	public static void renderFailure(String msg,HttpServletResponse response) {
		AjaxUtils.renderText("{\"status\":\"failure\",\"msg\":\""+msg+"\"}",response);
	}

	/**
	 * 向页面返回一个成功JSON信息。
	 * @param msg
	 */
	public static void renderSuccess(String msg,HttpServletResponse response) {
		AjaxUtils.renderText("{\"status\":\"success\",\"msg\":\""+msg+"\"}",response);
	}
	
	/**
	 * 向页面返回一个成功JSON。
	 * */
	public static void renderList(List<JSONObject> objectList,HttpServletResponse response) {
		if(objectList==null||objectList.size()==0){
			log.debug("目前对象集合为空！");
			objectList=new ArrayList<JSONObject>();
		}
		JSONArray jsonArray = new JSONArray();
		for (JSONObject jsonObj : objectList) {
			jsonArray.add(jsonObj);
		}
		renderText(jsonArray.toString(),response);
	}
	
	/**
	 * 向页面返回一个成功JSON。
	 * */
	public static void renderJson(JSONObject objectList,HttpServletResponse response) {
		if(objectList==null||objectList.size()==0){
			log.debug("目前对象集合为空！");
			objectList=new JSONObject();
		}
		renderText(objectList.toString(),response);
	}
}
