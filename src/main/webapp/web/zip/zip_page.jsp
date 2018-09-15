<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/web/includes/head.jsp" %>
<style type="text/css">
/* 进度条 */
.processBox{
	position:relative;
}
.processList{
	position:absolute;
	top:-8px;
	width:100%;
}
.processList li{
	position:absolute;
	border-radius:50%;
	height:30px;
	width:30px;
	/*line-height:28px;*/
	font-size:16px;
	background:#fff;
	color:#dedede;
	border:5px solid #f5f5f5;
	text-align:center;
	padding:0;
}
.processList .processActive{
	background:#73c9e3;
	border:5px solid #ebebeb;
	color:white;
}
#stage-info{
	margin-bottom:5px;
}
</style>
<script type="text/javascript" src="${ctx}/web/zip/zip_page.js"></script>
<title></title>
</head>
<body class="easyui-layout">
	 <div data-options="region:'center',border:false" style="padding:0px;overflow-y:auto; ">
     	 <c:forEach items="${list }" var="fileName" varStatus="status">
				<div style="position: relative;float: left;width:20%;height:200px;border:1px solid;text-align:center;margin: 0 auto;vertical-align:middle;">
					<img src="${ctx }/images/${fileName}" ></img>
				</div>	     		    	 
     	</c:forEach>
	 </div><!--end center-->
	 <div data-options="region:'south',border:false" style="line-height:30px;text-align: center">
	 		<div style="height:80px;">
		     	<div class="clearBoth" style="position:relative;height:30px;font-weight: 600;color:#32b0d5;">
						<span style='position:absolute;left:14%;'>校验文件</span>
						<span id="stage-info" style='position:absolute;left:42%;'>压缩文件</span>
						<span style='position:absolute;left:91%;'>成功下载文件</span>
				</div>
				<div class="processBox">
						<ul class="processList clearBoth">
							<li style="left:15%;">1</li>
							<li style="left:45%">2</li>
							<li style="left:92%">3</li>
						</ul>
						<!-- 进度条 -->
						<div id="processline" class="progress" style="height:18px;background-color:#73c9e3;width:0%"></div>
				</div>
     	</div>
		<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="downImage();">批量下载</a>&nbsp;&nbsp;
	</div>
</body>
</html>