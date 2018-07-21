package com.framework.utils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class HqlQueryUtils{

	/**
	 * 判断是否是无用的方法
	 * 
	 * @param name
	 * @return
	 */
	public static boolean judgedIsUselessField(String name) {
		return "class".equals(name) || "ids".equals(name)
				|| "page".equals(name) || "rows".equals(name)
				|| "sort".equals(name) || "order".equals(name);
	}
	
	/**
	 * 判断属性的类型是否支持
	 * @param type
	 * @return
	 */
	public static boolean ifRightType(String type){
		return type.contains("class java.lang")
				|| type.contains("class java.math")
				||type.contains("class java.util.Date");
	}
	
	/**
	 * 判断这个类是不是所以属性都为空
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotAllEmpty(Object param) {
		boolean isNotEmpty = false;
		try {
			PropertyDescriptor origDescriptors[] = PropertyUtils
					.getPropertyDescriptors(param);
			String name;
			for (int i = 0; i < origDescriptors.length; i++) {
				name = origDescriptors[i].getName();
				if ("class".equals(name)
						|| !PropertyUtils.isReadable(param, name)) {
					continue;
				}
				if (Map.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Map<?, ?> map = (Map<?, ?>) PropertyUtils.getSimpleProperty(param, name);
					if (map != null && map.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (Collection.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Collection<?> c = (Collection<?>) PropertyUtils.getSimpleProperty(param, name);
					if (c != null && c.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (StringUtil.isNotEmpty(PropertyUtils.getSimpleProperty(param, name))) {
					isNotEmpty = true;
					break;
				}
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
		return isNotEmpty;
	}
}
