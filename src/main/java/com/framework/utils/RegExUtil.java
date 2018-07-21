package com.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 *
 * Copyright (c) 2017 GRG Banking Co.,Ltd.
 * @createDate 2017年6月15日上午10:56:01
 * @author 陈健伟
 * @description 正则表达式过滤器
 *
 */
public class RegExUtil {
	
	
	/**
	 * 过滤参数存在的sql注入字符
	 * @param name
	 * @return
	 */
	public static String matchSql(String str){
		   String regEx= "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
		            + "(\\b(select|script|exist|from|union|update|and|or|delete|insert|trancate|char|in|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
		   Pattern p = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE); 
		   Matcher m = p.matcher(str);
		   return m.replaceAll("").replaceAll("\\s*", "");
	}
}
