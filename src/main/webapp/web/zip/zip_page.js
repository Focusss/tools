    var testFileTimer;
	
    /**
     * 定时器
     */
    function getStatus(){
		clearInterval(testFileTimer);
		loadProcess();
		testFileTimer = setInterval(function(){
			loadProcess();
		},100);
	}
	
	/**
	 * 获取进度
	 */
	function loadProcess(){
		$.ajax({
			type: 'post',
			url: ctx+"/zip/getStatus.do",
			success: function(data){
				switch(data.stage){
					case '1' : 
						$("#processline").css('width',30+"%");
						$(".processList li").eq(data.stage-1).addClass("processActive").prevAll().addClass("processActive");
						break;
					case '2' : 
						$("#processline").css('width',47+(data.number/data.allNumber)*50+"%");
						$(".processList li").eq(data.stage-1).addClass("processActive").prevAll().addClass("processActive");
						$("#stage-info").html("已压缩个数："+data.number+"/"+data.allNumber+" 个");
						break;
					case '3' : 
						clearInterval(testFileTimer);
						$("#stage-info").html("已压缩个数："+data.allNumber+"/"+data.allNumber+" 个");
						$("#processline").css('width',100+"%");
						$(".processList li").eq(data.stage-1).addClass("processActive").prevAll().addClass("processActive");
						break;
				}
			}
		});
	}



function downImage(){
	//实时获取进度条
	getStatus();
	window.location.href = ctx + "/zip/download.do";
	
}




