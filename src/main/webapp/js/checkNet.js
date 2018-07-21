(function() {
    //检测入口
    checkNet = function() {
        var def = $.Deferred();
        xiuNet(null).then(function(res) {
            netWork(null).then(function(res) {
                if(res) {
                    def.resolve(true);
                } else {
                    def.resolve(false);
                }
            })
        })
        return def.promise();
    }
    
    //嗅探网络，解决ios弹框接入网络问题
    var checkTime = 2; //嗅探次数
    xiuNet = function(def) {
        if(def == null) {def = $.Deferred();}
        var iframe = document.createElement("iframe");
        iframe.style.cssText = "display:none;width:0px;height:0px;";
        document.body.appendChild(iframe);
        var ua = navigator.userAgent;
        if(ua.indexOf("iPhone") != -1 || ua.indexOf("iPod") != -1 ||
            ua.indexOf("iPad") != -1) {
            iframe.src = "http://www.baidu.com" + "?timestap=" + new Date().getTime();
            console.info("嗅探网络----");
            if(checkTime > 1) {
                checkTime--;
                setTimeout(function() {
                    xiuNet(def)
                }, 1000);
            } else {
                def.resolve(true);
            }
        } else {
            def.resolve(false);
        }
        return def.promise();
    }

    //检测网络，是否可以连接公网
    var cnetTime = 3; //检测网络次数
    netWork = function(def) {
        if(def == null) {def = $.Deferred();}
        $.ajax({
            type: "get",
            timeout: 500,
            dataType: "jsonp",
            jsonp: "callback",
            cache: false,
            url: "https://jktv.tv/shphone/ver.txt"+ "?timestap=" + new Date().getTime(),
            success: function(data) {},
            error: function(xhr) {
                $("#ddd").html("status:"+xhr.status)
                if(xhr.readyState == 4 && xhr.status == 200) {
                    def.resolve(true);
                } else {
                    if(cnetTime > 1) {
                        cnetTime--;
                        setTimeout(function() {
                            netWork(def)
                        }, 200);
                    } else {
                        def.resolve(false);
                    }
                }
            }
        });
        return def.promise();
    }
})();