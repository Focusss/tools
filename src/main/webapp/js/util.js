(function($){
	var defaults={
		np:/^[0-9]*$/,//number pattern
		fp:'^[0-9]+(\\.[0-9]{1,10})?$',//float pattern
		ep:/^[0-9a-zA-Z]{1,20}@[a-zA-z]{1,20}(\.[a-zA-z]{1,3}){1,2}$///email pattern
	};
	var isStr=function(s){if(s==='null'||typeof s!=='string'){return 0;}else{return 1;}};
	var isNum=function(s){if(s==='null'||typeof s!=='number'){return 0;}else{return 1;}};
	$.util={
		isDigit:function(s){
			if(typeof s==='number'){return 1;}
			if(!isStr(s)){return 0;}
			if(!defaults.np.exec(s)){return 0;}else{return 1;} 
		},
		isFloat:function(s,l){
			if(s==='null'||s===''||typeof s==='object'){return 0;}
			try{if(isNaN(parseInt(l))){l=0;}}catch(e){}
			if(l>0){reg='^[0-9]+(\\.[0-9]{'+l+'})?$';}else{reg=defaults.fp;}
			if(typeof s==='number'){s=s+'';}
			if(!s.match(new RegExp(reg,'g'))){return 0;}else{return 1;}
		},
		isEmail:function(s){
			if(!isStr(s)){return 0;}
			if(!defaults.ep.exec(s)){return 0;}else{return 1;}
		},
		trimStr:function(s){
			if(!isStr(s)){return s;}
			return s.replace(/(^(\s)*)|((\s)*$)/g,"");
		},
		isEmpty:function(s){
			s=this.trimStr(s);
			if(isNum(s)){return 1;}
			if(!isStr(s)){return 0;}
			if(s.length<=0){return 1;}return 0;
		},
		isDate:function(s,separator){
			if(!isStr(s)){return 0;}
			var regExpStr=0;
			if(!separator){regExpStr="^\\d{4}-\\d{1,2}-\\d{1,2}$";}else{regExpStr="^\\d{4}"+separator+"\\d{1,2}"+separator+"\\d{1,2}$";}
			var patrn=new RegExp(regExpStr);
			if(!patrn.exec(s)){return 0;}
			var dateArray=s.split(!separator?'-':separator);
			var d=new Date(dateArray[0],dateArray[1]-1,dateArray[2]);
			if(d&&(d.getFullYear()==dateArray[0])&&(d.getMonth()+1==dateArray[1])&&(d.getDate()==dateArray[2])){return d;}else{return 0;}
		},
		compareDate:function(dateOne,dateTwo,separator){
			var d1=this.isDate(dateOne,separator);
			var d2=this.isDate(dateTwo,separator);
			if(d1&&d2){
				var t1=d1.getTime();var t2=d2.getTime();
				if(t1>t2){return 1;}else if(t1<t2){return -1;}else{return 0;}
			}
		},
		isDateTime:function(s){//格式仅限yyyy-mm-dd H24:MI:SS
			if(!isStr(s)){return 0;}
			var regExpStr=regExpStr="^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{1,2}:\\d{1,2}$";
			var patrn=new RegExp(regExpStr);
			if(!patrn.exec(s)){return 0;}
			var dateTime=s.split(' ');
			if(!dateTime||dateTime.length!==2){return 0;}
			var dateArray=dateTime[0].split('-');
			var timeArray=dateTime[1].split(':');
			var d=new Date(dateArray[0],dateArray[1]-1,dateArray[2],timeArray[0],timeArray[1],timeArray[2]);
			if(d&&(d.getFullYear()==dateArray[0])&&(d.getMonth()+1==dateArray[1])&&(d.getDate()==dateArray[2])&&(d.getHours()==timeArray[0])&&(d.getMinutes()==timeArray[1])&&(d.getSeconds()==timeArray[2])){return d;}else{return 0;}
		},
		compareDateTime:function(dtOne,dtTwo){
			var dt1=this.isDateTime(dtOne);
			var dt2=this.isDateTime(dtTwo);
			if(dt1&&dt2){
				var t1=dt1.getTime();var t2=dt2.getTime();
				if(t1>t2){return 1;}else if(t1<t2){return -1;}else{return 0;}
			}
		},
		tagOutput:function(s){//页面不翻译标签，直接显示标签及其内容
			return s.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		},
		isBlank:function(s){//派单字符串是否为空，包括null/undefined/空串/只有空格
			if(s == null || s == undefined){
				return true;
			}else if($.util.trimStr(s).length < 1){
				return true;
			}
			return false;
		},
		closeAndReturn:function(model,window,returnValue,datagridId){
			if(window != undefined && window != null){
				try{
					window.opener.doSearch(datagridId);
					window.close();
				}catch(e){
					if(model == true){
						window.returnValue=returnValue;
						window.close();
					}else{
						window.parent.doSearch(datagridId);
						window.close();
					}
				}
			}
			return;
		}
	};
})(jQuery);