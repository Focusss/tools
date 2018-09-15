/**
 * FileUploadHandler.java
 * Product:BaseLayer
 * Version:1.0
 * Copyright 2009 by DNE
 * All Rights Reserved.
 */
package com.platform.document;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.framework.core.base.BaseController;
import com.framework.core.common.Constant;
import com.framework.utils.AjaxUtils;
import com.framework.utils.DateUtil;
import com.framework.utils.FileDownloadHandle;
import com.framework.utils.convert.JOD4DocToPDF;

import net.sf.json.JSONObject;

/**
 * 
* @ClassName: FileUploadController  
* @Description: TODO 文档转换
* @author cjwei7  
* @date 2018年7月31日  
*
 */
@Controller
@RequestMapping("document")
public class DocumentController extends BaseController {
	
	private final static String DOCUMENT_PATH = Constant.UPLOAD_PATH + File.separator + "document" ;
	
	
	/**
	 * 跳转到上传文档页面
	 */
	@RequestMapping("toTransformPage")
	public String toTransformPage(HttpServletRequest request){
		return ("/web/document/transform_page");
	}
	
	/**
	 * 
	* @Title: checkFileExists  
	* @Description: TODO 检查文件是否存在 
	* @param @param bean
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping(value="checkFileExists", produces="application/json;charset=UTF-8")
	@ResponseBody
	public void checkFileExists(HttpServletRequest request){
		try {
			String fileName = request.getParameter("fileName");
			File file = new File(DOCUMENT_PATH +File.separator +fileName);	
			
			if(!file.exists()){
				AjaxUtils.renderSuccess("操作成功。", response);
			}else{
				AjaxUtils.renderFailure("操作失败。", response);
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: upload  
	* @Description: TODO 上传文件及转换  
	* @param @param request
	* @param @param response    参数  
	* @return void    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/upload")
	public void upload(HttpServletRequest request,HttpServletResponse response) {

		System.out.println("开始");
		try {
			
			
			JOD4DocToPDF t = new JOD4DocToPDF();
			ExecutorService executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap(); 
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {  
			  
	            MultipartFile file = entity.getValue();
	            
	            //以文件名作为目录，方便归档
				String simpleName = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
				String saveDir = DOCUMENT_PATH + File.separator + simpleName;
				File documentPath = new File(saveDir);
				if (!documentPath.exists()) {
					documentPath.mkdirs();
				}
	            
				File targetFile = new File(saveDir,file.getOriginalFilename());
				file.transferTo(targetFile);
				
				//新开线程，转换文档
				DocumentThread dThread  = new DocumentThread(targetFile, saveDir, t);
				Thread thread = new Thread(dThread);
				thread.start();
			}   
			AjaxUtils.renderSuccess("上传成功", response);
			
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtils.renderFailure("上传失败", response);
		}
		System.out.println("===结束");
	}
	

	/**
	 * 跳转到列表查询页面
	 */
	@RequestMapping("toList")
	public String toList(HttpServletRequest request){
		List<JSONObject> list = new ArrayList<JSONObject>();
	    try {
			File mainDir = new File(DOCUMENT_PATH);
			File[] pfiles = mainDir.listFiles();
			for(File dirFile : pfiles){
				 if(dirFile.isDirectory()){
					 String dirPath = dirFile.getAbsolutePath();
					 File[] cfiles = dirFile.listFiles();
					 JSONObject json = new JSONObject();
					 json.element("name","");
					 json.element("url","");
					 json.element("directory",dirPath);
					 json.element("swfUrl","");
					 json.element("pdfUrl","");
					 json.element("jpgUrl","");
					 
					 for(File file : cfiles){
						 String fileName = file.getName();
						 String fileUrl = file.getAbsolutePath().replaceAll("\\\\", "/");
						 
						 if(fileName.indexOf(".swf") > -1){
							 String[] fileUrlArr = fileUrl.split("/");
							 json.element("swfUrl",fileUrlArr[fileUrlArr.length -2]+"/"+fileUrlArr[fileUrlArr.length-1]);
						 }else if(fileName.indexOf(".pdf") > -1){
							 json.element("pdfUrl",fileUrl);
						 }else if (fileName.indexOf(".jpg") > -1) {
							
								json.element("jpgUrl",URLEncoder.encode(URLEncoder.encode(fileUrl, "utf-8")));
						 }else {
							 json.element("name",fileName);
							 json.element("url", fileUrl);
						 }
					 }
					 list.add(json);
				 }
			}
	    } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("dmsFilelist", list);
		return ("/web/document/document_list");
	}
	/**
	 * 
	* @Title: toReadPage  
	* @Description: TODO(阅读界面)  
	* @param @param bean
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping(value="toReadPage")
	public String toReadPage(){
		try {
			String url = request.getParameter("url");
			url = URLDecoder.decode(url);
			/*if(isWindows()){
				url = url.replaceAll("/","\\\\");
			}*/
			request.setAttribute("url", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("/web/document/document_read");
	}
	
	
	/**
	 * 
	* @Title: toDownloadPage  
	* @Description: TODO(下载文件)  
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("toDownloadPdfPage")
	public void  toDownloadPdfPage(){
		try {
			String url = request.getParameter("url");
			url = URLDecoder.decode(url);
			if(isWindows()){
				url = url.replaceAll("/","\\\\");
				
			}
			String[] fileNameArr = url.split("\\\\");
			File file = new File(url);
			FileDownloadHandle.downloadFile(file, fileNameArr[fileNameArr.length-1],response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: toDownloadPage  
	* @Description: TODO(下载文件)  
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("toDownloadPage")
	public void toDownloadPage(){
		try {
			String url = request.getParameter("url");
			url = URLDecoder.decode(url);
			if(isWindows()){
				url = url.replaceAll("/","\\\\");
			}
			String[] fileNameArr = url.split("\\\\");
			File file = new File(url);
			FileDownloadHandle.downloadFile(file, fileNameArr[fileNameArr.length-1],response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isWindows() {
		return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
	}
}
