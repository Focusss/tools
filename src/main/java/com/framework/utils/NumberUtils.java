/**
 * 数字类型数据处理工具
 * Product:Grgbanking Service Of Customer System.
 * Version:2.0
 * Copyright 2010 by Grgbanking
 * All Rights Reserved.
 * Author: zhangzhi
 * Date: 2010-4-6 下午02:01:03
 */
package com.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * Copyright (c) 2016 GRG Banking Co.,Ltd.
 * @createDate 2016年12月13日上午9:39:03
 * @author 
 * @description 数字工具类
 * 
 */
public class NumberUtils {
	/**
	 * 将指点的数字对象按照指点的规则格式化, 例如:###.00,
	 * @param format String 格式
	 * @param obj Double 需转换的Double类型数据
	 * @return 格式化后的字符串
	 */
	public static String format(String format, double obj) {
		DecimalFormat formater = new DecimalFormat(format);
		return formater.format(obj);
	}
	
	/**
	 * 将指点的数字对象按照指点的规则格式化, 例如:###.00,
	 * @param format String
	 * @param obj Float 需转换的Float类型数据
	 * @return 格式化后的字符串
	 */
	public static String format(String format, float obj) {
		DecimalFormat formater = new DecimalFormat(format);
		return formater.format(obj);
	}

	/**
	 * 获取指定位数的随机数
	 * @param size int 位数
	 * @return 指定位数的随机数 long
	 */
	public static long getRandom(int size) {
		Double value = (Math.random() * Math.pow(10, size));
		return value.longValue();
	}
	
	/**
	 * 判断是否为整数
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value){
		try {
			Integer.valueOf(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * 判断是否为数字
	 * @param value
	 * @return
	 */
	public static boolean isNumeric(String value){
		try {
			Double.valueOf(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * 把字符串转换成数字类型  
	 * @param longValue
	 * @return
	 */
	public static Long createLong(String longValue){
		Long result;
		try {
			result = Long.parseLong(longValue);
		}
		catch (NumberFormatException e){
			return null;
		}
		return result;
	}
	/**
	 * 把字符串转换成数字类型  
	 * @param longValue
	 * @return
	 */
	public static Double createDouble(String doubleValue){
		Double result;
		try {
			result = Double.parseDouble(doubleValue);
		}
		catch (NumberFormatException e){
			return 0D;
		}
		return result;
	}
	
	/**
	 * 把字符串转换成数字类型  
	 * @param longValue
	 * @return
	 */
	public static Integer createInteger(String intValue){
		Integer result;
		try {
			result = Integer.parseInt(intValue);
		}
		catch (NumberFormatException e){
			return null;
		}
		return result;
	}
	
	public static Short createShort(String shortValue){
		Short result;
		try {
			result = Short.parseShort(shortValue);
		}
		catch (NumberFormatException e){
			return null;
		}
		return result;
	}
	/**
	 * 把数字类型转车字符串，如果传入的数字为null，则返回默认数字
	 * @param integerValue
	 * @param defaultValue
	 * @return
	 */
	public static String formatInteger(Integer integerValue, String defaultValue){
		if (integerValue == null){
			return defaultValue;
		}
		else{
			return String.valueOf(integerValue);
		}
	}
	/**
	 * 把数字类型转车字符串，如果传入的数字为null，则返回默认数字
	 * @param integerValue
	 * @param defaultValue
	 * @return
	 */
	public static String formatLong(Long longValue, String defaultValue){
		if (longValue == null){
			return defaultValue;
		}
		else{
			return String.valueOf(longValue);
		}
	}
	/**
	 * 把数字类型转车字符串，如果传入的数字为null，则返回默认数字
	 * @param integerValue
	 * @param defaultValue
	 * @return
	 */
	public static String formatShort(Short shortValue, String defaultValue){
		if (shortValue == null){
			return defaultValue;
		}
		else{
			return String.valueOf(shortValue);
		}
		
	}
	/**
	 * 
	 * @createDate 2011-1-24
	 * @author GuaBin
	 * @param numerator 分子
	 * @param denominator 分母
	 * @param scale 数字标量；
	 * @param roundingMode 舍入类型，四舍五入如：BigDecimal.ROUND_HALF_UP
	 * @return BigDecimal 返回值已经乘上了100
	 * @description
	 *
	 */
	public static BigDecimal calculatePercent(double numerator,double denominator,int scale ,int roundingMode){
		BigDecimal decimalNumerator = new BigDecimal(numerator);
		BigDecimal decimalDenominator = new BigDecimal(denominator);
		return decimalNumerator.divide(decimalDenominator, scale, roundingMode).movePointRight(2);
	}
	
}

