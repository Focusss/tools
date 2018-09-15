<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  	<%@include file="/web/includes/head.jsp" %>
    <title>批量导入</title>
    <script type="text/javascript" src="${ctx}/web/zip/import_zip_page.js"></script>
</head>
<body>
         <div>
			<form id="form1" action="${ctx }/zip/importZip.do" method="post" enctype="multipart/form-data">
					<table cellpadding="0" cellspacing="0" width="100%"  style="margin: 0px auto;">
						<tr height="40px">
							<td align="center">
								压缩包文件路径：<input type="file" id="file" name="file" style="width:220px" />
							</td>
						</tr>
						<tr height="40px">
							<td align="center">
								<a class="easyui-linkbutton" icon="icon-import" onclick="checkForm()">确定导入</a>
							</td>
						</tr>
						<tr height="40px">
							<td align="center">
								&nbsp;&nbsp;&nbsp;错误日志：<a id="errorFile" onclick="downloadErrorFile();">${fileName}</a>
							</td>
						</tr>
					</table>
				</form>
				<c:forEach items="${list }" var="file" varStatus="status">
					<div style="position: relative;float: left;width:20%;height:200px;border:1px solid;text-align:center;margin: 0 auto;vertical-align:middle;">
						<div onclick="downloadFile('${file.url}')">
							<img src="${ctx }/images/ext/${file.ext}" />
						    ${file.name}
						</div>
					</div>	     		    	 
     			</c:forEach>
		</div> 
  </body>
</html>
