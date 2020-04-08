var CommAjax={
	//执行Ajax提交(支持回调)，支持异步	
	SubmitAjaxCallBack:function(url,data,callback,async){
		var _async=true;//默认是true，即为异步方式,false：同步方式
		if(async==false){
			_async=false;
		}
		/*var index = layer.load(1, {
			  shade: [0.3,'#000'],
			  title:'正在提交中...'
			});*/
		var index = layer.msg('正在加载中...', {icon: 16,time: false,shade:[0.3,'#393D49']});
		console.log("传递的参数："+JSON.stringify(data));
		setTimeout(function(){
			$.ajax({
	            type: "post",
	            url: url,
	            data: data,
	            async: _async,
	            dataType: "json",
	            //contentType: 'application/json;charset=utf-8',
	            success: function (res) {
	            	layer.close(index);
	                callback(res);
	            },
	            error: function (res) {
	            	//console.log(res);
	            	layer.close(index);
	            	var data={"result":false,msg:"接口请求失败,请稍后再试"};
	            	callback(data);
	            }
	        });
		},300);
		
	},
	LoadAjaxCallBack:function(url,data,callback,async){
		var _async=true;//默认是true，即为异步方式,false：同步方式
		if(async==false){
			_async=false;
		}
		$.ajax({
            type: "post",
            url: url,
            data: data,
            async: _async,
            dataType: "json",
            //contentType: 'application/json;charset=utf-8',
            success: function (ret) {
                callback(ret);
            },
            error: function (res) {
            	var data={"result":false,msg:"操作失败,请稍后再试"};
            	callback(data);
            }
        });
		
	},
}