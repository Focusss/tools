	$(function(){
		$("#uploadify").uploadify({
			'uploader' : ctx+'/js/uploadify/uploadify.swf',
			'script' : ctx+'/document/upload.do',//后台处理的请求
			'method' : 'get',
			'cancelImg' : ctx+'/js/uploadify/cancel.png',
			'queueID' : 'fileQueue',//与下面的id对应
			'queueSizeLimit' : 10,
			'fileExt' : '*.*', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
			'sizeLimit': 100*1024*1024, 
			'auto' : false,
			'multi' : true,
			'removeCompleted' : true,
			'simUploadLimit' : 1,
			'buttonImg' : ctx+'/images/select_document.gif',
			'width' : 157,
			'height' : 45,
			'onCancel' : function(event,ID,fileObj,data){
				$('#'+ID).remove();
				$.messager.alert('提示','【'+fileObj.name+'】已取消上传。');
			},
			/*
			'onError' : function(event,ID,fileObj,errorObj){
				$('#'+ID).remove();
				return false;
			},*/
            'onSelect' : function(event,ID,fileObj) {
				$('#tr3').hide();
				var fileSize = fileObj.size;
				if(fileSize > 100 * 1024 * 1024){
					alert('文件【'+fileObj.name+'】大小超过100M，无法上传。');
					$('#'+ID).remove();
					return false;
				}
				$.ajax({
					type : 'post',
					dataType: 'json',
					url : ctx+"/document/checkFileExists.do",
					data:{
						fileName : fileObj.name
					},
					success : function(data) {
							if(data.status == 'success'){
				            	$('#formdiv').show();
							}else{
								alert('文件【'+fileObj.name+'】在上传路径中已经存在，请从上传队列删除文件，修改文件名后重新上传！');
							}
					 }
				});

				
            },
            'onComplete' : function(event, ID, fileObj, response, data){
				
            },
            'onAllComplete' : function(event,data){
            	/*
				$('#result').html('所有文档上传完毕，请填写下方的文档信息<br/>');
				var ids = $('#ids').val();
				var idsArr = ids.split(',');
				for(var i=0;i<idsArr.length;i++){
					$('#fileType'+idsArr[i]).combotree({   
						url:'${ctx}/system/document-type!getCombotreeList.do' 
					});
					initSelect("fileLevel"+idsArr[i]);		
				}
				$('#tr5').show();
            	$('#formdiv').show();
            	*/
            	$.messager.alert('提示','文档提交成功。','info',function(){
					window.location.reload();
		       	});
            }
		});

	});
	

	
	function checkForm(){
		window.scroll(0,50);
		$("#uploadify").uploadifyUpload();
	}