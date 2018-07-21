<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
    <%@include file="/web/includes/head.jsp" %>
    <script>
	  $(function() {
		  $.fn.zTree.init($("#tree"), setting,zNodes);
      });
	  
	  var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
	  };

	  var zNodes =[
				{ id:1, pId:0, name:"常用功能", open:true},
				{ id:101, pId:1, name:"在线签名",click:"addTabs('在线签名','${ctx}/signature/toSignPage.do','在线签名')" },
				{ id:102, pId:1, name:"生成数据到PDF",click:"addTabs('生成数据到PDF','${ctx}/pdf/toPDFPage.do','生成数据到PDF')" },
				{ id:103, pId:1, name:"批量下载文件",click:"addTabs('批量下载文件','${ctx}/download/toBatchPage.do','批量下载文件')" },
				{ id:104, pId:1, name:"war包导入", click:"addTabs('批量下载文件','${ctx}/import/toWarImportPage.do','批量下载文件')" },
				{ id:105, pId:1, name:"邮件提醒", click:"addTabs('邮件提醒','${ctx}/email/toEmailPage.do','邮件提醒')" },
	  ];
	  
    </script>
  </head>
  
  <body class="easyui-layout">
        <div data-options="region:'west',split:true,collapsible:false" title="导航栏" style="width:250px;">
        	<ul id="tree" class="ztree" style="margin-top:0;margin-left:0; width:180px;"></ul>
        </div>
		<div region="center" style="overflow: hidden;"data-options="region:'center',border:false">
			<div class="easyui-tabs" id="menutabs" fit="true" border="false"  style="padding: 0px;width: 100%;height: 100%;" />
		</div>
  </body>
</html>
