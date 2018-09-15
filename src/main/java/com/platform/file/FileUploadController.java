/**
 * FileUploadHandler.java
 * Product:BaseLayer
 * Version:1.0
 * Copyright 2009 by DNE
 * All Rights Reserved.
 */
package com.platform.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.framework.core.base.BaseController;
import com.framework.core.common.Constant;
import com.framework.utils.AjaxUtils;
import com.framework.utils.DateUtil;
import com.framework.utils.FileDownloadHandle;

/**
 * 
* @ClassName: FileUploadController  
* @Description: TODO 上传文件 
* @author cjwei7  
* @date 2018年7月31日  
*
 */
@Controller
@RequestMapping("fileUpload")
public class FileUploadController extends BaseController {
	

	@RequestMapping(value = "/upload")
	public void upload(@RequestParam(value="file",required=true) MultipartFile[] files, HttpServletRequest request,HttpServletResponse response) {

		System.out.println("开始");
		try {
			List<String> list = new ArrayList<String>();
			String newFileName =  "";
			if(files != null && files.length > 0){
				for(MultipartFile fileChild : files){
					newFileName = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + String.format("%02d", new Random().nextInt(999)) + "."+
							fileChild.getOriginalFilename().substring(fileChild.getOriginalFilename().lastIndexOf(".")+1,fileChild.getOriginalFilename().length());
					File targetFile = new File(Constant.UPLOAD_PATH ,newFileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					fileChild.transferTo(targetFile);
					
					list.add(newFileName);
				}
			}
			AjaxUtils.renderSuccess(newFileName, response);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtils.renderFailure("操作失败", response);
		}
		System.out.println("===结束");
	}
	
	
	@RequestMapping(value = "/remove")
	public void upload(@RequestParam(value="fileName",required=true) String fileName, HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("开始");
		try {
			File file = new File(Constant.UPLOAD_PATH ,fileName);
			if (file.exists()) {
				file.delete();
			}
			AjaxUtils.renderSuccess("操作成功", response);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtils.renderFailure("操作失败", response);
		}
		System.out.println("===结束");
	}
	
	@RequestMapping(value = "/download")
	public void download(@RequestParam(value="fileName",required=true) String fileName, HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("开始");
		try {
			File file = new File(Constant.UPLOAD_PATH ,fileName);
			FileDownloadHandle.downloadFile(file, fileName, response);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		System.out.println("===结束");
	}
}
