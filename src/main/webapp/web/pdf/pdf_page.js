var check = true;
var flag = true;
	$(function(){
            var options = { 
				dataType:'json',
				beforeSubmit:function(formData,jqForm,options){
					//Common.showLoading('正在检查数据中...');
	            },
	          	beforeSend:function(a,b,c){
	        	  	//Common.showLoading('正在保存数据中...');
	          	},
	          	complete:function(){
	        	  	//Common.hideLoading();
	          	},
			    success:callbackmethod
			}; 
			
			$.validator.setDefaults({
				submitHandler: function(form) {
					if(flag){
						if(!check){
							$.messager.alert('提示','存在字段验证不通过','error');
							return;
						}
						flag = false;
						
						var fileNames = '';
						$('ul>li').each(function(){
							
							if(fileNames == ''){
								fileNames = $(this).attr("name");
							}else{
								fileNames = fileNames + "," + $(this).attr("name");
							}
						});
						$('#fileNames').val(fileNames);
						$(form).ajaxSubmit(options);
					}
				}
			});
			$("#form").validate({});
	});	
		
	
	
	//回调方法
	function callbackmethod(data){ 
			if(data.status=='success'){
			     window.location.href = ctx + '/fileUpload/download.do?fileName='+data.msg;
		 	}else{
		 		$.messager.alert('提示',data.msg,'error');
		 		flag = true;
		 	}
	}	
		
	