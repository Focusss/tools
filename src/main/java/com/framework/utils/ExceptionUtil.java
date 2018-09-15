package com.framework.utils;

/**
 * 
* @ClassName: ExceptionUtil  
* @Description: TODO(异常工具类)  
* @author cjwei7  
* @date 2018年5月14日  
*
 */
public class ExceptionUtil {
	public static String getExceptionStraceInfo(Exception e){
		StringBuilder builder = new StringBuilder();
		builder.append("Cause by "+ e.toString());
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement traceElement : trace)
			builder.append("\n" + "  at "+traceElement);
		return builder.toString();
	}
}
