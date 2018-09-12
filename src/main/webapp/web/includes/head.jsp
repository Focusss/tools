<%@ page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
response.addHeader("Cache-control", "No-cache"); 
response.addDateHeader("Expires", 0); 
request.setAttribute("ctx",request.getContextPath());
%>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style >
	/*.datagrid-btable tr{height:27px;}*/
</style>
<script type="text/javascript">
var ctx="${ctx}";//定义全局变量
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/public.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader/css/webuploader.css" />
<script type="text/javascript" src="${ctx }/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/js/webuploader/js/webuploader.js"></script>
<script type="text/javascript" src="${ctx }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx }/js/easyui/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.mask.js"></script>
<script type="text/javascript" src="${ctx }/js/util.js"></script>
<!-- datagrid扩展 -->
<script type="text/javascript" src="${ctx }/js/jquery.datagrid.ext.js"></script>

<script>
	/**
	 * close window by id
	 * @param id
	 * @return
	 */
	function closeWindowById(id){
		$("#"+id).window("close");
	}
	
	/**
	 * close this div window.
	 */
	function closeWindow(){
		window.parent.closeWindowById("${param.randomwindowid}");
	}
	
	//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外   
	function banBackSpace(e){      
	    var ev = e || window.event;//获取event对象      
	    var obj = ev.target || ev.srcElement;//获取事件源      
	       
	    var t = obj.type || obj.getAttribute('type');//获取事件源类型     
	       
	    //获取作为判断条件的事件类型   
	    var vReadOnly = obj.getAttribute('readonly');   
	    var vEnabled = obj.getAttribute('enabled');   
	    //处理null值情况   
	    vReadOnly = (vReadOnly == null) ? false : vReadOnly;   
	    vEnabled = (vEnabled == null) ? true : vEnabled;   
	       
	    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，   
	    //并且readonly属性为true或enabled属性为false的，则退格键失效   
	    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")    
	                && (vReadOnly==true || vEnabled!=true))?true:false;   
	      
	    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效   
	    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")   
	                ?true:false;           
	       
	    //判断   
	    if(flag2){   
	        return false;   
	    }   
	    if(flag1){      
	        return false;      
	    }      
	}   
	   
	//禁止后退键 作用于Firefox、Opera   
	document.onkeypress=banBackSpace;   
	//禁止后退键  作用于IE、Chrome   
	document.onkeydown=banBackSpace; 
	
	/**easyUI时间控件扩展清空按钮*/
	var buttons = $.extend([],$.fn.datebox.defaults.buttons);
	buttons.splice(1,0,{
		text : "清空",
		handler : function(target){
			$(target).datebox('setValue','');
			$(target).datebox('hidePanel');
		}
	});
	$.fn.datebox.defaults.buttons = buttons; 
	
	/*点击菜单时，在主窗体显示新页面*/
	function addTabs(title,url,name){
		var tabspanel=$('.easyui-tabs');
		if(tabspanel.tabs('exists',title)){
			tabspanel.tabs('close',title);
		}
		var name = title;
		tabspanel.tabs('add',{
			title:title,
			content:"<iframe name='"+name+"' src='"+url+"'  style='width:100%;height:100%;overflow:auto;' frameborder='0'></iframe>",
			closable:true
		});
	}
	
	var ctx = '${ctx}';
</script>

<base target="_self" />