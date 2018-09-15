	   	
		function checkForm(){
	   		if(document.getElementById('file').value==''){
	   	   		$.messager.alert("提示","请选择需要导入的文件!");
	   	   		return;
	   	   	}
		   	$('#form1').submit();
	   	}
	   	
		 //下载
		function downloadErrorFile(){
		    var fullFileName = $('#errorFile').text();
		    if(fullFileName != undefined && fullFileName!= ''){
		    	location.href=ctx+"/zip/downloadErrorFile.do";
		    }
		}
		
		
		 //下载
		function downloadFile(url){
		    if(url != undefined && url!= ''){
		    	
		    	location.href=ctx+"/zip/downloadFile.do?url="+encodeURIComponent(encodeURIComponent(url));
		    }
		}
		