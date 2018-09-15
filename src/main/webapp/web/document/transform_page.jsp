 <%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>上传文档</title>
<style>
	.cpurl-wrap {
		BORDER-BOTTOM: #f8e7bd 1px solid; BORDER-LEFT: #f8e7bd 1px solid; PADDING-BOTTOM: 5px; MARGIN: 10px 0px; PADDING-LEFT: 5px; PADDING-RIGHT: 5px; BACKGROUND: #F0FFF0; COLOR: #e67201; CLEAR: both; BORDER-TOP: #f8e7bd 1px solid; BORDER-RIGHT: #f8e7bd 1px solid; PADDING-TOP: 5px
	}
	.cpmsg-wrap {
		BORDER-BOTTOM: #f8e7bd 1px solid; BORDER-LEFT: #f8e7bd 1px solid; PADDING-BOTTOM: 5px; MARGIN: 10px 0px; PADDING-LEFT: 5px; PADDING-RIGHT: 5px; BACKGROUND: #fefae2; COLOR: #e67201; CLEAR: both; BORDER-TOP: #f8e7bd 1px solid; BORDER-RIGHT: #f8e7bd 1px solid; PADDING-TOP: 5px
	}
	.cpmsg-wrap .cpmsg-icon {
		MARGIN: 0px 5px -3px 0px
	}
	.uplaod-wrap {
		text-align: center;HEIGHT:80px; BORDER-BOTTOM: #B4CDCD 1px solid; BORDER-LEFT: #B4CDCD 1px solid; BACKGROUND: #F0F8FF; COLOR: #e67201; CLEAR: both; BORDER-TOP: #B4CDCD 1px solid; BORDER-RIGHT: #B4CDCD 1px solid; PADDING-TOP: 25px
	}
	.info-wrap {
		width: 700px; text-align: center; margin-left: auto;margin-right: auto;HEIGHT:165px; BORDER-BOTTOM: #B4CDCD 1px solid; BORDER-LEFT: #B4CDCD 1px solid; BACKGROUND: #F0F8FF; COLOR: #e67201; CLEAR: both; BORDER-TOP: #B4CDCD 1px solid; BORDER-RIGHT: #B4CDCD 1px solid; PADDING-TOP: 10px
	}
	.hui {color:#ccc;}
	.hei {color:#000;}
</style>
<%@include file="/web/includes/head.jsp"%> 
<script type="text/javascript" src="${ctx }/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<script type="text/javascript" src="${ctx }/js/uploadify/swfobject.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/js/uploadify/uploadify.css"/>
<script type="text/javascript" src="${ctx }/web/document/transform_page.js"></script>
</head>

<body>
<div style="width: 700px; margin-left: auto;margin-right: auto;">
	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr id="tr0">
			<%-- <td>
				<div class="cpurl-wrap">
					<p>当前路径：${location}</p>
					<p>批量选择多个文件上传至【${currentDirectory}】</p>
				</div>
				
			</td> --%>
		</tr>
		<tr id="tr3">
			<td>
				<div class="cpmsg-wrap">
					<h3><img class="cpmsg-icon" alt="" src="${ctx}/images/cpmsg-alert.gif">必须按照以下几点上传文档</h3>
					<div>	
						<p style="font-size: 12pt;font-weight: bold;">
						1、上传Excel文档前请先确保每个sheet表单在打印预览时可以全部显示在一页中，否则在线预览时会分页。
						   具体方法是点击菜单栏视图-分页预览，把蓝色的虚线拖到Excel数据外，在一页里显示出来</p>
						<p>2、加密的PDF文档无法在线阅读。</p>
						<p>3、文件名已经存在的文档不能上传，修改文档内容后，请在文件名后加上版本号再上传，如xxxxx-v1.1.doc</p>
						<p>4、每次最多选择10份文档，每份文档不超过100M</p>
					</div>
				</div>
			</td>
		</tr>
		<tr id="tr2">
			<td>
				<div class="uplaod-wrap">
					<div><span style="margin:5px auto;color:red;font-size:18px;">请不要上传加密的或者带有水印的文档！</span></div>
				    <div>
						<input type="file" name="uploadify" id="uploadify" />
					</div> 
				</div>
			</td>
		</tr>
		<tr id="tr4">
			<td>
				<div id="fileQueue"></div>
			</td>
		</tr>
		<tr id="tr5" style="display: none;">
			<td>
				<div id="result" class="cpmsg-wrap">	
					
				</div>
			</td>
		</tr>
	</table>
	<div id ="formdiv" style="display: none;">
		<div style="line-height:30px;text-align: center">
			<img src="${ctx}/images/submit_document.gif" style="cursor: pointer;" onclick="checkForm();">
		</div>
	</div>
</body>
</html>