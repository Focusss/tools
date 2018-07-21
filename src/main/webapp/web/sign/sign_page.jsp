<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
    <%@include file="/web/includes/head.jsp" %>
    <script type="text/javascript" src="${ctx }/js/jSignature/flashcanvas.js"></script>
     <%-- 该js文件直接从github上的源码拉下来--%>
	<script type="text/javascript" src="${ctx }/js/jSignature/jSignature.js"></script>
 <%-- 会存在下划线问题   <script type="text/javascript" src="${ctx }/js/jSignature/jSignature.min.js"></script> --%>
	<script type="text/javascript" src="${ctx }/web/sign/sign_page.js"></script>
  </head>
  
  <body >
        
			 <!-- 签名区域 -->
			 <div id="signature" style="width: 800px;height: 300px;border: 1px solid #ccc;"></div>
			 <button type="button" onclick="preview()">输出签名</button>
	         <button type="button" onclick="resetSignature()">重置</button>
	  		 <button type="button" onclick="saveSignature()">生成Image图片</button>
	  		 <div style="text-align: left"><h2>输出签名展示区</h2></div>
	  		 <div id="image" style="width: 800px;height: 300px;border: 1px solid #ccc;"></div>
	  		 <div style="text-align: left"><h2>生成图片展示区</h2></div>
	   		 <div id="img_src" style="width: 800px;height: 300px;border: 1px solid #ccc;"></div>
   		
  </body>
</html>
