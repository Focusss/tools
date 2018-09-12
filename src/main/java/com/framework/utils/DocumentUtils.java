package com.framework.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentUtils {
	
	private static Logger log = LoggerFactory.getLogger(DocumentUtils.class);
	
	public static void transform(File file){
		/*log.info("+++++++++++执行文件转换+++++++++++++++");
		
		if(file.exists()){
			//源文件
			String documentPath = tempFile.getFileUrl()+"/"+tempFile.getFileName();
			//pdf文件路径
			String pdfDocumentPath = tempFile.getFileUrl()+"/"+tempFile.getFileName().substring(0, tempFile.getFileName().lastIndexOf("."))+".pdf";
			//flash文件路径
			String swfDocumentPath = tempFile.getAppUrl()+"/swf/"+tempFile.getId()+".swf";
			//缩略图路径
			String jpgDocumentPath = tempFile.getAppUrl()+"/jpg/"+tempFile.getId()+".jpg";
					
			JOD4DocToPDF t = new JOD4DocToPDF();
			t.docToPdfByOpenOffice(file.getFileName(),documentPath, pdfDocumentPath,swfDocumentPath,jpgDocumentPath,file.getFileExt());
		}
		log.info("------------------执行文件转换结束---------------");*/
	}
	
}
