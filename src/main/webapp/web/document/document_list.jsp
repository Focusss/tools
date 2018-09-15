<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/web/includes/head.jsp" %>
<style type="text/css">
	.myli{width:75px;height:75px;list-style-type:none;float:left;padding-top:10px;padding-bottom:10px;  padding-right: 10px;}
	.myli img{padding:1px;border:1px solid #ccc;}
	.myli p{text-align:center;}
	.hui {color:#ccc;}
	.hei {color:#000;}
</style>
<script type="text/javascript">

		//阅读
		function readFile(url){
			window.open("${ctx}/document/toReadPage.do?url="+encodeURIComponent(encodeURIComponent(url)));
		}

		//下载
		function downloadFile(url){
			window.location.href="${ctx}/document/toDownloadPage.do?url="+encodeURIComponent(encodeURIComponent(url));
		}

		//下载pdf格式
		function downloadPDFFile(url){
			window.location.href="${ctx}/document/toDownloadPdfPage.do?url="+encodeURIComponent(encodeURIComponent(url));
		}
		
</script>
</head>
<body style="overflow-y: scroll;">  
							<div style="overflow: auto;">
								<table border="1" style="width:100%;height:100%; border-collapse:collapse;border-color:#E0ECFF;border-width: 2px; " cellpadding="5" cellspacing="0" >
									<thead>
										<tr style="background:#E0ECFF;">
											<th width="34%" align="center">名称</th>
											<th width="34%" align="center">目录</th>
											<th width="32%" align="center">操作</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${dmsFilelist}" var="dmsFile" >   
									    <tr>		
											<td align="center" >
												${dmsFile.name}								
											</td>
											<td align="center">${dmsFile.directory }</td>
											<td align="center" valign="middle">
											    <div class="box" style="text-align:center;width:180px;">
											        <img src="${ctx}/web/common/imgView.jsp?url=${dmsFile.jpgUrl}&isEncode=true" align="absmiddle" width="100%"/> 
													<a href='#' style='color:#0072BC;text-decoration:underline' onclick='readFile("${dmsFile.swfUrl}")'>阅读</a>
													<a href='#' style='color:#0072BC;text-decoration:underline' onclick='downloadFile("${dmsFile.url}")'>下载(源文件)</a>	
													<a href='#' style='color:#0072BC;text-decoration:underline' onclick='downloadPDFFile("${dmsFile.pdfUrl}")'>下载(pdf文件)</a>
												</div>
											</td>
										</tr>
									</c:forEach>
							    	</tbody>
						    	</table> 
						    </div>
	</body>
</html>


