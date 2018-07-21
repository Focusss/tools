package com.framework.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ArrayTransitionUtils {
	/**
	 * 
	 * @createDate 2011-5-28
	 * @author GuaBin
	 * @param content    需要解析的字符串
	 * @param separator  分割符
	 * @return Long[]
	 * @description
	 * 将形如"1,2,3"的字符串 转换成 Long数组
	 * 兼容     "1,2,3,"这种尾部存在分割符的情况
	 */
	public static Long[] stringToLongArray(String content,String separator){
		String[] StringArray = StringUtils.split(content, separator);
		
		Long[] result = new Long[StringArray.length];
		for(int i=0;i<StringArray.length;i++){
			result[i]=Long.parseLong(StringArray[i]);
		}
		return result;
	}
	
	/**
	 * 
	 * @createDate 2011-5-28
	 * @author GuaBin
	 * @param content    需要解析的字符串
	 * @param separator  分割符
	 * @return Integer[]
	 * @description
	 * 将形如"1,2,3"的字符串 转换成 Long数组
	 * 兼容     "1,2,3,"这种尾部存在分割符的情况
	 */
	public static Integer[] stringToIntegerArray(String content,String separator){
		String[] StringArray = StringUtils.split(content, separator);
		
		Integer[] result = new Integer[StringArray.length];
		for(int i=0;i<StringArray.length;i++){
			result[i]=Integer.parseInt(StringArray[i]);
		}
		return result;
	}
	/**
	 * 
	 * @createDate 2011-5-28
	 * @author GuaBin
	 * @param paramArray   待转换的字符串数组
	 * @return Long[]
	 * @description
	 * 将形如{"1","2"} 的字符串数组 转换成 Long数组
	 */
	public static Long[] stringArrayToLongArray(String[] paramArray){
		Long[] result = new Long[paramArray.length];
		for(int i=0;i<paramArray.length;i++){
			result[i]=Long.parseLong(paramArray[i]);
		}
		return result;
	}
	/**
	 * 
	 * @createDate 2011-5-30
	 * @author GuaBin
	 * @param contennt
	 * @return String
	 * @description
	 * 去掉字符转分割符的最后一个符号，如：
	 * "10,20,30,"  ->  "10,20,30"
	 * 
	 */
	public static String sliceOffSeparator(String contennt){
		if(contennt.endsWith(","))
			return contennt.substring(0, contennt.length() - 1);
		else 
			return contennt;
	}
	/**
	 * 
	 * @createDate 2011-5-30
	 * @author GuaBin
	 * @param contennt
     * @param separator 自定义分割符
	 * @return String
	 * @description
	 * 去掉字符转分割符的最后一个符号，如：
	 * "10:20:30:"  ->  "10:20:30"
	 * 
	 */
	public static String sliceOffSeparator(String contennt,String separator){
		if(contennt.endsWith(separator))
			return contennt.substring(0, contennt.length() - 1);
		else 
			return contennt;
	}
	
	/**
	 * 
	 * @author:guabin 
	 * @createDate 2012-5-3
	 * @return String
	 * @description
	 * 生成SQL语句中in条件的字符串
	 * 如：{"ddd","222","ccc"} --> 'ddd','222','ccc'
	 * 再配上in(),是不是很快活？
	 */
	public static String stringArrayToInString(String[] array){
		return "'" + StringUtils.join(array, "','") + "'";
	}
	
	/**
	 * 
	 * @author:guabin 
	 * @createDate 2012-5-3
	 * @return String
	 * @description
	 * 生成SQL语句中in条件的字符串
	 */
	@SuppressWarnings("unchecked")
	public static String stringListToInString(List list){
		return "'" + StringUtils.join(list, "','") + "'";
	}
	
	/**
	 * 
	 * @author:guabin 
	 * @createDate 2012-5-3
	 * @return String
	 * @description
	 * 生成SQL语句中in条件的字符串
	 * 如："111,222,333,444,555" --> '111','222','333','444','555'
	 * 再配上in(),是不是很快活？
	 */
	public static String StringToInString(String string,String separator){
		string = string.replaceAll(" ","");
		String[] array = StringUtils.split(string,separator);
		return stringArrayToInString(array);
	}
	
	/**
	 * 
	 * @author:guabin 
	 * @createDate 2012-6-21
	 * @return String
	 * @description
	 * 生成SQL语句中数字in条件的字符串
	 * 如：1,2,3,4，即不使用单引号分隔
	 */
	public static String listToInNumString(List list){
		String result = "";
		for(Object temp : list){
			result += temp + ",";
		}
		
		return sliceOffSeparator(result);
	}

}
