<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/web/includes/head.jsp" %>
<title></title>
	<script type="text/javascript" src="${ctx }/web/pdf/pdf_page.js"></script>
</head>
<body class="easyui-layout">
	 <div data-options="region:'center',border:false" style="padding:0px;overflow:hidden">
	 	 <form  id="form" method="post" action="${ctx}/pdf/generatePDF.do">
     		<table width="100%" class='table_info'>
     		     <tr>
				 	<td align="right" style="width:40%">用户姓名：</td>
				 	<td>
                 	 	<input type="text" name="name" id="name" style="width:150px"/> 
					 </td>
				 </tr>
				 <tr>
				 	<td align="right" style="width:40%">手机号码：</td>
				 	<td>
				 		<input name="mobileNum" id="mobileNum" style="width:150px" />
					 </td>
				 </tr>
				 <tr>
				 	<td align="right" style="width:40%">备注：</td>
				 	<td>
				 		<input id="remarks" name="remarks" style="width:150px" editable="false"/>
				 	</td>
				 </tr>
            </table>
         </form>
	 </div><!--end center-->
	 <div data-options="region:'south',border:false" style="line-height:30px;text-align: center">
			<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="$('#form').submit();">保存</a>&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="closeWindow();">关闭</a>
	</div>
</body>

</html>