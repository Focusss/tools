function _loadChildSelect(parentId,sel){
	$.getJSON(ctx+'/dataDictionary/getListByParent.do?pId='+parentId, function(data) {
		var value=sel.getAttribute("value");
		//先移除原有的option
		while(sel.length>0){
			sel.removeChild(sel.options[0]);
		}
		
		//判断是否需要设置了默认选项
		if(sel.getAttribute("blank")!=null){
			 sel.options[sel.length]=new Option(sel.getAttribute("blank"),'');
		}
		//生成OPTION
		if(data.options!=null){
			for(var i=0; i<data.options.length; i++){
				 var option=new Option(data.options[i].text,data.options[i].value);
				 option.dataDictionaryId=data.options[i].id;
				 //如果设置了默认值，就让默认option选中
				    if(value==data.options[i].value||value==data.options[i].text){
				    	option.selected="selected";
				    }
				 sel.options[sel.length]=option;
			}
		}
		
		//如果设置了级联select
		if(sel.getAttribute("child")){
			//设置ONCHANGE事件
			sel.onchange=function(){
				var childSel=document.getElementById(sel.getAttribute("child"));
				if(childSel==null){
					childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
				}
				var dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
				_loadChildSelect(dataDictionaryId,childSel);
			}
			//更新子选择框
			var childSel=document.getElementById(sel.getAttribute("child"));
			if(childSel==null){
				childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
			}
			var dataDictionaryId="";
			if(sel.selectedIndex!=-1){
				dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
			}
			_loadChildSelect(dataDictionaryId,childSel);
		}
	});
}


/**
 * 手动加载select项
 * @param name
 * @return
 */
function loadSelect(name){
	$.each($("select[name='"+name+"']"),function(index,sel){
		//只加载空的
		if(sel.length==0){
			var key=sel.getAttribute("key");
			var value=sel.getAttribute("value");
			$.getJSON(ctx+'/dataDictionary/getListByKey.do?key='+key, function(data) {
				//先移除原有的option
				while(sel.length>0){
					//sel.options.removeNode(0);
					sel.removeChild(sel.options[0]);
				}
				//判断是否需要设置了默认选项
				if(sel.getAttribute("blank")!=null){
					 sel.options[sel.length]=new Option(sel.getAttribute("blank"),'');
				}
				//生成OPTION
				if(data.options!=null){
					for(var i=0; i<data.options.length; i++){
					    var option=new Option(data.options[i].text,data.options[i].value);
					    option.dataDictionaryId=data.options[i].id;
					    //如果设置了默认值，就让默认option选中
					    if(value==data.options[i].value||value==data.options[i].text){
					    	option.selected="true";
					    }
					    sel.options[sel.length]=option;
					}
				}
				//如果设置了级联select
				if(sel.getAttribute("child")){
					//设置ONCHANGE事件
					sel.onchange=function(){
						var childSel=document.getElementById(sel.getAttribute("child"));
						if(childSel==null){
							childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
						}
						var dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
						_loadChildSelect(dataDictionaryId,childSel);
					}
					//更新子选择框
					var childSel=document.getElementById(sel.getAttribute("child"));
					if(childSel==null){
						childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
					}
					if(sel.selectedIndex!=-1){
						dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
					}
					_loadChildSelect(dataDictionaryId,childSel);
				}
			});
		}

	});
}

/*
 * 页面加载完毕后，检查是否有需要加载select内容
 * 例如:<select key='COMMON_RESOURCE_TYPE'></select>
 * 如果select标签有key属性则需要加载
 * ctx是定义在head.jsp里的一个全局变量。
 */
/*
$(function(){
	$.each($('select[key]'),function(index,sel){
		var keyName=sel.getAttribute("key");
		var valueName=sel.getAttribute("optionValue");
		$.getJSON(ctx+'/data-dictionary!getListByKey.do?key='+keyName, function(data) {
			//先移除原有的option
			while(sel.length>0){
				//sel.options.removeNode(0);
				sel.removeChild(sel.options[0]);
			}
			//判断是否需要设置了默认选项
			if(sel.getAttribute("blank")!=null){
				 sel.options[sel.length]=new Option(sel.getAttribute("blank"),'');
			}
			//生成OPTION
			if(data.options!=null){
				for(var i=0; i<data.options.length; i++){
				    var optionName=new Option(data.options[i].text,data.options[i].value);
				    optionName.dataDictionaryId=data.options[i].id;
				    //如果设置了默认值，就让默认option选中
				    if(valueName==data.options[i].value||valueName==data.options[i].text){
				    	optionName.selected="selected";
				    }
				    sel.options[sel.length]=optionName;
				}
			}
			//如果设置了级联select
			if(sel.getAttribute("child")){
				//设置ONCHANGE事件
				sel.onchange=function(){
					var childSel=document.getElementById(sel.getAttribute("child"));
					if(childSel==null){
						childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
					}
					var dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
					_loadChildSelect(dataDictionaryId,childSel);
				}
				//更新子选择框
				var childSel=document.getElementById(sel.getAttribute("child"));
				if(childSel==null){
					childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
				}
				if(sel.selectedIndex!=-1){
					dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
				}
				_loadChildSelect(dataDictionaryId,childSel);
			}
		});

	});
	
	//加载多选框
	
	$.each($("input[multiple='true']"),function(index,sel){
		var key=sel.getAttribute("key");
		$(sel).addClass("multi-input");
		$(sel).combobox({
		    url:ctx+'/data-dictionary!getMultiListByKey.do?key='+key,
		    valueField:'value',
		    textField:'text'
		});
	});
	
	$.each($('select[column]'),function(index,sel){
		var columnName=sel.getAttribute("column");
		var valueName=sel.getAttribute("value");
		var params=sel.getAttribute("params");
		if (params==null || params == undefined)params='';
		var urls=(sel.getAttribute("urls")).replace('?','');
		$.getJSON(urls+'?'+params+'&column='+columnName, function(data) {
			//先移除原有的option
			while(sel.length>0){
				//sel.options.removeNode(0);
				sel.removeChild(sel.options[0]);
			}
			//判断是否需要设置了默认选项
			if(sel.getAttribute("blank")!=null){
				 sel.options[sel.length]=new Option(sel.getAttribute("blank"),'');
			}
			//生成OPTION
			if(data.options!=null){
				for(var i=0; i<data.options.length; i++){
				    var optionName=new Option(data.options[i].text,data.options[i].value);
				    optionName.dataDictionaryId=data.options[i].id;
				    //如果设置了默认值，就让默认option选中
				    if(valueName==data.options[i].value||valueName==data.options[i].text){
				    	optionName.selected="selected";
				    }
				    sel.options[sel.length]=optionName;
				}
			}
			//如果设置了级联select
			if(sel.getAttribute("child")){
				//设置ONCHANGE事件
				sel.onchange=function(){
					var childSel=document.getElementById(sel.getAttribute("child"));
					if(childSel==null){
						childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
					}
					var dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
					_loadChildSelect(dataDictionaryId,childSel);
				}
				//更新子选择框
				var childSel=document.getElementById(sel.getAttribute("child"));
				if(childSel==null){
					childSel=$("select[name='"+sel.getAttribute("child")+"']")[0];//子选择框
				}
				if(sel.selectedIndex!=-1){
					dataDictionaryId=sel.options[sel.selectedIndex].dataDictionaryId;
				}
				_loadChildSelect(dataDictionaryId,childSel);
			}
		});
	});
});
*/