package com.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileReader {
	
	public static Properties getProperties(String name) throws IOException{
		InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream(name);
		Properties p = new  Properties();
		p.load(inputStream);
		return p;
	}
	
	public static void main(String[] args) {
		try {
			Properties p = FileReader.getProperties("path.properties");
			String upload = p.getProperty("upload");
			System.out.println(upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
