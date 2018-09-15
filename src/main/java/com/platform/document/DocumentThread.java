/**
 * FileUploadHandler.java
 * Product:BaseLayer
 * Version:1.0
 * Copyright 2009 by DNE
 * All Rights Reserved.
 */
package com.platform.document;

import java.io.File;

import com.framework.utils.convert.JOD4DocToPDF;

/**
 * 
* @ClassName: DocumentThread  
* @Description: TODO 用于文档转换
* @author cjwei7  
* @date 2018年9月3日  
*
 */
public class DocumentThread implements Runnable {

	private String documentPath; 
	
	private File targetFile;
	
	private JOD4DocToPDF t;
	
	public DocumentThread(File targetFile,String documentPath,JOD4DocToPDF t){
		this.targetFile = targetFile;
		this.documentPath = documentPath;
		this.t = t;
	}
	
	public void run() {
		 //转换为PDF，SWF
		System.out.println("+++++++++++执行文件转换+++++++++++++++");
		
		//文件名
		String simpleName = targetFile.getName().substring(0, targetFile.getName().lastIndexOf("."));
		//后缀名
		String extName = targetFile.getName().substring(targetFile.getName().indexOf("."));
		
		//pdf文件路径
		String pdfDocumentPath = documentPath + File.separator + simpleName + ".pdf";
		//flash文件路径
		String swfDocumentPath = documentPath + File.separator + "1.swf";
		//缩略图路径
		String jpgDocumentPath = documentPath + File.separator +  simpleName + ".jpg";
		
		t.docToPdfByOpenOffice(targetFile.getName(),targetFile.getAbsolutePath(), pdfDocumentPath,swfDocumentPath,jpgDocumentPath,extName);
		
		System.out.println("+++++++++++执行文件结束+++++++++++++++");	
		
	}
	
	
	
}
