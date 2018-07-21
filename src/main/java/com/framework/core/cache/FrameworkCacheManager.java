package com.framework.core.cache;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

@Component
public class FrameworkCacheManager {


	/**
	 * 初始化缓存
	 * 
	 * @CreateDate 2016-7-11
	 * @author clmin
	 * @description
	 */
	public static synchronized void init(ServletContext servletContext) {
		
	}

	
	/**
	 * 清空缓存
	 */
	public void close() {
		// 清空数据缓存
		
	}

	/**
	 * 重新加载机构信息
	 */
	public synchronized void reLoadOrganization() {
		
	}

	

}
