package com.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Blob;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * 需要提供下载文件的功能就用它吧
 *
 */
public class FileDownloadHandle {
	
	public static void downloadFile(Blob blob, String name, HttpServletResponse response) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = blob.getBinaryStream();
			outputStream = response.getOutputStream();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(name, "UTF-8") + "\"");
			IOUtils.copy(inputStream,outputStream);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
		
	}

	public static void downloadFile(byte[] content, String name, HttpServletResponse response) {
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(name, "UTF-8") + "\"");
			IOUtils.write(content, outputStream);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		
	}
	
	public static void downloadFile(File file, String fileName, HttpServletResponse response) {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			FileDownloadHandle.downloadFile(IOUtils.toByteArray(input),fileName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(input);
		}
	}

}
