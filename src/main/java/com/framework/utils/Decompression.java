package com.framework.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;




/**
 * 
 *
 * Copyright (c) 2017 GRG Banking Co.,Ltd.
 * @createDate 2017年7月4日下午7:01:42
 * @author 陈健伟
 * @description 
 *
 */
public class Decompression {
    
	   /**
	    * 
	    *
	    * @createDate 2017年7月6日上午11:42:04 
	    * @author 陈健伟 
	    * @param sourceFile 压缩文件带绝对路径
	    * @param desZipPath 解压目录
	    * @description 解压zip
	    */
	  public synchronized static void unzip(File sourceFile , String desZipPath){
		  try {
				Project p = new Project();     
		        Expand e = new Expand();     
		        e.setProject(p);     
		        e.setSrc(sourceFile);     
		        e.setOverwrite(false);     
		        e.setDest(new File(desZipPath));     
		        /*   
			      ant下的zip工具默认压缩编码为UTF-8编码，   
			           而winRAR软件压缩是用的windows默认的GBK或者GB2312编码   
			           所以解压缩时要制定编码格式   
		        */    
		        e.setEncoding("UTF-8");     
		        e.execute();     
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	  
	 /**
	  * 
	  *
	  * @createDate 2017年7月6日下午12:31:17 
	  * @author 陈健伟 
	  * @param sourceFile 压缩文件带绝对路径
	  * @param desRarPath 解压目录
	  * @throws Exception 
	  * @description 解压rar文件 
	  */
	 public synchronized static void unrar(File sourceFile , String destDir) throws Exception{  
		Archive a = null;
		FileOutputStream fos = null;
		try {
			a = new Archive(sourceFile);
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				if (!fh.isDirectory()) {
					// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
					String compressFileName = fh.getFileNameString().trim();
					String destFileName = "";
					String destDirName = "";
					// 非windows系统
					if (File.separator.equals("/")) {
						destFileName = destDir + File.separator + compressFileName.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
						// windows系统
					} else {
						destFileName = destDir + File.separator +compressFileName.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
					}
					// 2创建文件夹
					File dir = new File(destDirName);
					if (!dir.exists() || !dir.isDirectory()) {
						dir.mkdirs();
					}
					// 3解压缩文件
					fos = new FileOutputStream(new File(destFileName));
					a.extractFile(fh, fos);
					fos.close();
					fos = null;
				}
				fh = a.nextFileHeader();
			}
			a.close();
			a = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (a != null) {
				try {
					a.close();
					a = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	 }
}
