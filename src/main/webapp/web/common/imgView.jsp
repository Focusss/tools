<%@page import="java.net.URLDecoder"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String url = request.getParameter("url");
    String isEncode = request.getParameter("isEncode");
    if(!"".equals(isEncode)){
    	url = URLDecoder.decode(url);
    }
	java.io.InputStream fis = new java.io.FileInputStream(url);
	java.io.OutputStream os = response.getOutputStream();
	try {
		response.setContentType("image/jpeg; charset=UTF-8");
		byte[] b = new byte[1024];
		int i = 0;
		while ((i = fis.read(b)) > 0) {
			os.write(b, 0, i);
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (os != null)
			os.close();
		if (fis != null)
			fis.close();
	}
	out.clear();
	out = pageContext.pushBody();
%>

