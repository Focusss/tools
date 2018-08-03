<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/web/includes/head.jsp" %>
<title></title>
<link href="${ctx}/css/demo.css" rel="stylesheet" media="all"/>
</head>
<body class="easyui-layout">
	 <div data-options="region:'center',border:false" style="padding:0px;overflow:hidden">
	 	 <form  id="form" method="post" action="${ctx}/pdf/generatePDF.do">
	 	     <input type="hidden" name="fileNames" id="fileNames"  /> 
     		 <table width="100%" class='table_info'>
				 <tr>	
				 	 <td width="20%" align="right">出卖人 ：</td>
	                 <td width="30%" align="left" colspan="3">
                 	 	<input type="text" name="saleManName" id="saleManName" style="width:150px" /> 
					 </td>
				 </tr>
				 <tr>	
					 <td width="20%" align="right">法定代表人 ：</td>
	                 <td width="30%" align="left">
                 	 	<input type="text" name="saleLegalMan" id="saleLegalMan" style="width:150px" /> 
					 </td>	
					 <td width="20%" align="right">联系人电话 ：</td>
	                 <td width="30%" align="left">
                 	 	<input type="text" name="saleLegalManTel" id="saleLegalManTel" style="width:150px"/>  
					 </td>
				 </tr>
				 <tr>	
					 <td width="20%" align="right">注册地址 ：</td>
	                 <td width="30%" align="left">
                 	 	 <input type="text" name="saleRegisterAdr" id="saleRegisterAdr" style="width:150px"/>  
					 </td>	
					 					 
					 <td width="20%" align="right">邮政编码 ：</td>
	                 <td width="30%" align="left">
                 	 	<input type="text" name="salePostalCode" id="salePostalCode" style="width:150px" /> 
					 </td>
				 </tr>
				 <tr>	
					 <td width="20%" align="right">营业执照号码 ：</td>
	                 <td width="30%" align="left" colspan="3">
                 	 	<input type="text" name="businessLicense" id="businessLicense" style="width:150px"  /> 
					 </td>	
				 </tr>
				 <tr>	
					 <td width="20%" align="right">开户行 ：</td>
	                 <td width="30%" align="left">
                 	 	<input type="text" name="bank" id="bank" style="width:150px"/>
					 </td>	
					 <td width="20%" align="right">账号 ：</td>
	                 <td width="30%" align="left">
	                 	<input type="text" id="account" name="account"  style="width:150px" />
					 </td>	
				 </tr>
				 <tr>	
					<td width="20%" align="right" valign="center">房屋图片：</td>
					<td width="70%" align="left" colspan="3">
						    <div class="page-container">
								<div id="uploader" class="wu-example">
									<div class="queueList">
										<div id="dndArea" class="placeholder">
											<div id="filePicker"></div>
											<p>或将照片拖到这里</p>
										</div>
									</div>
									<div class="statusBar" style="display: none">
										<div class="progress">
											<span class="text">0%</span> <span class="percentage"></span>
										</div>
										<div class="info"></div>
										<div class="btns">
											<div id="filePicker2"></div>
											<div class="uploadBtn">开始上传</div>
										</div>
									</div>
						         </div>
						    </div>
					</td>
				</tr>
            </table>
         </form>
	 </div><!--end center-->
	 <div data-options="region:'south',border:false" style="line-height:30px;text-align: center">
			<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="$('#form').submit();">保存</a>&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="closeWindow();">关闭</a>
	</div>
	<!--该文件只能跟上传组件放在同级目录下,不然报错me.connectRuntime is not a function  -->
	<script type="text/javascript" src="${ctx}/web/pdf/extends_upload.js"></script> 
	<script type="text/javascript" src="${ctx }/web/pdf/pdf_page.js"></script>
	<script type="text/javascript">
	window.webuploader = {
		config:{
			thumbWidth: 220, //缩略图宽度，可省略，默认为110
			thumbHeight: 220, //缩略图高度，可省略，默认为110
			wrapId: 'uploader', //必填
		},
		//处理客户端新文件上传时，需要调用后台处理的地址, 必填
		uploadUrl: ctx+'/fileUpload/upload.do',
		//处理客户端原有文件更新时的后台处理地址，必填
		updateUrl: ctx+'/fileUpload/update.do',
		//当客户端原有文件删除时的后台处理地址，必填
		removeUrl: ctx+'/fileUpload/remove.do',
		//初始化客户端上传文件，从后台获取文件的地址, 可选，当此参数为空时，默认已上传的文件为空
		//initUrl: 'fileinit.php',
	}
	</script>
</body>
</html>