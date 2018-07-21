package com.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.framework.core.common.Constant;

import sun.misc.BASE64Decoder;

public class FileWriter {

	public static void inputstreamToFile(FileInputStream ins, File file) {
		try {
			FileOutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[32768];
			while ((bytesRead = ins.read(buffer, 0, 32768)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void inputstreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[32768];
			while ((bytesRead = ins.read(buffer, 0, 32768)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
	public static void deleteAll(File path) {
        try {
			if (!path.exists()) // 路径不存在
				return;
			if (path.isFile()) { // 是文件
				path.delete();
				return;
			}
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAll(files[i]);
			}
			path.delete();  //删除目录
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *将BASE64的图片解码上传至服务器 
	 */
	public static String uploadBase64Img(String imgData,String newFileName){
		String realFilePath = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		String dateStr = sdf.format(date);
		String directoryPath = Constant.UPLOAD_PATH + File.separator + dateStr;	
		File directoryFile = new File(directoryPath);
		if(!directoryFile.exists()){
			directoryFile.mkdirs();
		}
		if (StringUtils.isBlank(imgData)){
			return realFilePath;
		}
		//imgData 的BASE64格式为data:image/jpeg;base64,4AAQSkZJRgABAQA...
		String imgRealData = "";
		if(imgData.indexOf(",") > -1){
			String[] imgDataArr = imgData.split(",");
			imgRealData = imgDataArr[1];
		}else{
			imgRealData = imgData;
		}
		
		realFilePath = directoryPath +  File.separator + newFileName; //带后缀
		
		//jdk包自带，若找不到参考https://blog.csdn.net/u011514810/article/details/72725398
		BASE64Decoder decoder = new BASE64Decoder();  
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgRealData);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(realFilePath);
			out.write(b);
			out.flush();
			out.close();
			return realFilePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return realFilePath;
	}

	
}
