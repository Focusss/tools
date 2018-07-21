package com.framework.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Response帮助工具类，在controller中,使用此工具返回内容。
 */
public class ResponseUtil {

	private static Log log=LogFactory.getLog(AjaxUtils.class);
	
	/**
	 * 生成分页JSON数据
	 * @param objectList JSON格式的对象集合
	 * @param recordCount 记录数
	 */
	public static String renderPagination(List<JSONObject> objectList,Integer recordCount) {
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
		return jsonRoot.toString();
	}

	/**
	 * 向页面返回一个错误JSON信息。
	 * @param msg
	 */
	public static String renderFailure(String msg) {
		log.debug(msg);
		if(StringUtils.isEmpty(msg)){
			log.debug("AjaxRender.java>>method:renderText>>content is null!");
		}
		return ("{\"status\":\"failure\",\"msg\":\""+msg+"\"}");
	}

	/**
	 * 向页面返回一个成功JSON信息。
	 * @param msg
	 */
	public static String renderSuccess(String msg) {
		log.debug(msg);
		if(StringUtils.isEmpty(msg)){
			log.debug("AjaxRender.java>>method:renderText>>content is null!");
		}
		return ("{\"status\":\"success\",\"msg\":\""+msg+"\"}");
	}
	
	/**
	 * 生成分页JSON数据+底部
	 * @param objectList JSON格式的对象集合
	 * @param root 底部信息
	 * @param recordCount 记录数
	 */
	public static String renderRootPagination(List<JSONObject> objectList,String root,Integer recordCount) {
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
		return jsonRoot.toString();
	}
	
	/**
	 * 生成JSON数组
	 * @param objectList JSON格式的对象集合
	 * @param root 底部信息
	 */
	public static String renderList(List<JSONObject> objectList,String root) {
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
		return jsonRoot.toString();
	}
	
	/**
	 * 生成JSON数组
	 * @param objectList JSON格式的对象集合
	 * @param root1/2 底部信息
	 */
	public static String renderLists(
			List<JSONObject> objectList1,String root1,
			List<JSONObject> objectList2,String root2) {
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
			objectList2 = new ArrayList<JSONObject>();
		}
		JSONArray jsonArray2 = new JSONArray();
		for (JSONObject jsonObj : objectList2) {
			jsonArray2.add(jsonObj);
		}
		jsonRoot.put(root2, jsonArray2);
		
		return jsonRoot.toString();
	}
	
	/**
	 * 向页面返回一个成功JSON。
	 * */
	public static String renderList(List<JSONObject> objectList) {
		if(objectList==null||objectList.size()==0){
			log.debug("目前对象集合为空！");
			objectList=new ArrayList<JSONObject>();
		}
		JSONArray jsonArray = new JSONArray();
		for (JSONObject jsonObj : objectList) {
			jsonArray.add(jsonObj);
		}
		return jsonArray.toString();
	}
	
	/**
	 * 向页面返回一个成功JSON。
	 * */
	public static String renderJson(JSONObject objectList) {
		if(objectList==null||objectList.size()==0){
			log.debug("目前对象集合为空！");
			objectList=new JSONObject();
		}
		return objectList.toString();
	}

}
