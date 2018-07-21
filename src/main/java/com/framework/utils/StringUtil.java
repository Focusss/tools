package com.framework.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
/**
 * 字符串工具类，提供字符串处理相关的常用方法
 */
public class StringUtil {
	
	/**
	 * 截取字符串中间部分
	 * @param str 需截取的字符串
	 * @param separator1 开始分隔符
	 * @param separator2 结束分隔符
	 * @return 字符串
	 */
	public static String substringBetween(String str,String separator1,String separator2){
		if(StringUtil.isEmpty(str)){
			return str;
		}
		str=StringUtils.substringAfter(str, separator1);
		return StringUtils.substringBefore(str, separator2);
	}
	
	/**
	 * 截取字符串指定分隔符后面的内容
	 * @param str 指定字符串
	 * @param separator 指定分隔符
	 * @return 字符串
	 */
	public static String substringAfter(String str,String separator){
		if(StringUtil.isEmpty(str)){
			return str;
		}
		return StringUtils.substringAfter(str, separator);
	}
	
	/**
	 * 取得最后一个指定字符串之后的字符串
	 * @param str 指定字符串
	 * @param separator 指定分隔符
	 * @return 字符串
	 */
	public static String substringAfterLast(String str,String separator){
		if(StringUtil.isEmpty(str)){
			return str;
		}
		return StringUtils.substringAfterLast(str, separator);
	}
	
	/**
	 * 取得指定字符串之前的字符串
	 * @param str 指定字符串
	 * @param separator 指定分隔符
	 * @return 字符串
	 */
	public static String substringBefore(String str,String separator){
		if(StringUtil.isEmpty(str)){
			return str;
		}
		return StringUtils.substringBefore(str, separator);
	}
	
	/**
	 * 取得最后一个指定字符串之前的字符串
	 * @param str 指定字符串
	 * @param separator 指定分隔符
	 * @return 字符串
	 */
	public static String substringBeforeLast(String str,String separator){
		if(StringUtil.isEmpty(str)){
			return str;
		}
		return StringUtils.substringBeforeLast(str, separator);
	}
	
	/**
	 * 将字符串首字母转换成大写字母
	 * @param str 指定的字符串
	 * @return 字符串
	 */
	public static String upperFirstCharacter(String str){
		if(StringUtil.isEmpty(str)){
			return str;
		}
		char[] chars = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return String.valueOf(chars);
	}
	
	/**
	 * 将字符串首字母转换成小写字母
	 * @param str 指定的字符串
	 * @return 字符串
	 */
	public static String lowerFirstCharacter(String str){
		if(StringUtil.isEmpty(str)){
			return str;
		}
		char[] chars = str.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return String.valueOf(chars);
	}
	
	/**
	 * 判断指定的字符串是否为null或者空值""，空格也算空值
	 * @param str 指定字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str){
		return str==null||str.trim().equals("");
	}

	/**
	 * 判断指定的字符串是否非空或null
	 * @param str 指定字符串
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 把数组中的元素按指定分隔符连接成一个字符串返回
	 * @param array 字符换数组
	 * @param separator 指定分隔符
	 * @return 字符串
	 */
	public static String join(String[] array,String separator){
		if(array==null){
			return null;
		}
		return StringUtils.join(array, separator);
	}
	
	/**
	 * 把集合List中的元素按指定分隔符连接成一个字符串返回
	 * @param list 集合
	 * @param separator 指定分隔符
	 * @return 字符串
	 */
	public static String join(List list,String separator){
		if(list==null){
			return null;
		}
		return StringUtils.join(list, separator);
	}

	/**
	 * 返回重复多次的字符串数组
	 * @param str 指定字符串
	 * @param times 重复次数
	 * @return 字符串数组
	 */
	public static String[] repeatString(String str, int times) {
		String[] result=new String[times];
		for (int i = 0; i < times; i++) {
			result[i]=str;
		}
		return result;
	}
	
	/**
	 * 判断指定的字符串是否非空或null
	 * @param str 指定字符串
	 * @return boolean
	 */
	public static boolean isNotEmpty(Object str) {
		return !(str==null);
	}
}
