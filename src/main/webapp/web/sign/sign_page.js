$(function () {
	
    var dHeight = "300px";
	var dWidth ="800px"; 
	$("#signature").jSignature({height:dHeight,width:dWidth, signatureLine:false});

});


// ------------------------------------------------------------
//重置签名
function resetSignature(){
	  $("#image").empty();
      $("#signature").jSignature("reset");  
}
//预览签名
function preview(){
          var $sigdiv = $("#signature");
          var datapair = $sigdiv.jSignature("getData", "image"); //设置输出的格式，具体可以参考官方文档

          var i = new Image();
          i.src = "data:" + datapair[0] + "," + datapair[1];
          $("#image").empty();
          $(i).appendTo($("#image")) // append the image (SVG) to DOM.
}
//上传签名
function saveSignature(){
	//判断是否完成签名
	var isSignature = $('#signature').jSignature('getData', 'native').length;
	if(isSignature < 1){
		alert("请完成签名");
		return false;
	}
	
	var $sigdiv = $("#signature");
    var datapair = $sigdiv.jSignature("getData", "image"); //设置输出的格式，具体可以参考官方文档
    var imagebase64 = datapair[1];//.replace(/\+/g, '%2B');
    $.ajax({
        type: "POST",
        url: ctx + "/signature/saveSignPhoto.do",
        dataType:'json',
        data: {
        	   "imageData" : imagebase64
        },
        success: function (data) {
           if(data.status == 'success'){
        	   //跳转至上一页面
        	   var createImage = new Image();
               createImage.src = ctx+"/web/common/imgView.jsp?url="+data.msg;
               $("#img_src").empty();
               $(createImage).appendTo($("#img_src"));
           }else{
	        	alert("请重新完成签名");
	       		return false;
           }
        },error:function(data){
        	console.log(data);
        }
    });
}


