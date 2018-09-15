<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<%@include file="/web/includes/head.jsp" %>
		<style>
			.Btn {BORDER-RIGHT: #204998 1px solid; BORDER-TOP: #204998 1px solid; FONT-SIZE: 9pt; BORDER-LEFT: #204998 1px solid; CURSOR: pointer; COLOR: #ffffff; BORDER-BOTTOM: #204998 1px solid; HEIGHT: 25px; BACKGROUND-COLOR: #204998}
		</style>
		<script type="text/javascript" src="${ctx}/js/flexpaper_flash.js"></script>
		<script>
			$(function(){
				 var height = document.body.clientHeight;
				 $('#viewerPlaceHolder').height(height-10);
			});
		</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'center'"> 
			<div id="viewerPlaceHolder" style="width:100%;height:500px;">
		        <script type="text/javascript"> 
					var file = '${ctx}/swf/${url}';
					alert(file);
					var fp = new FlexPaperViewer(	
							 '${ctx}/js/FlexPaperViewer',
							 'viewerPlaceHolder', { config : {
							 SwfFile : file,
							// SwfFile : escape('${url}'),
							 Scale : 0.6, 
							 ZoomTransition : 'easeOut',
							 ZoomTime : 0.5,
							 ZoomInterval : 0.2,
							 FitPageOnLoad : true,
							 FitWidthOnLoad : false,
							 FullScreenAsMaxWindow : false,
							 ProgressiveLoading : true,
							 MinZoomSize : 0.2,
							 MaxZoomSize : 5,
							 SearchMatchAll : false,
							 InitViewMode : 'SinglePage',
							 ViewModeToolsVisible : true,
							 ZoomToolsVisible : true,
							 NavToolsVisible : true,
							 CursorToolsVisible : true,
							 SearchToolsVisible : true,
	  						 localeChain: 'zh_CN'
							 }});
		        </script> 
			 </div>
	    </div>
	
</body>
</html>


