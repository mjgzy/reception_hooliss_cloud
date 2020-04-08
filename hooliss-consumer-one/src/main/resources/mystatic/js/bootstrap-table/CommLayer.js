var CommLayer={
		msg:function(_msg,callback){
			layer.msg(_msg, { shift: -1, time: 1000 }, function(){
				if(callback!=undefined&&callback!=null){
					callback();
				}
			});
		},
		alertOk:function(msg,callback){
			layer.alert(msg,{icon: 1,title:'系统提示'}, function (index) { 
				 layer.close(index);
				if(callback!=undefined&&callback!=null){
					callback();
				}
			});
		},
		alertNo:function(msg,callback){
			layer.alert(msg,{icon: 2,title:'系统提示'}, function (index) { 
				 layer.close(index);
				if(callback!=undefined&&callback!=null){
					callback();
				}
			});
		},
		alert:function(msg,callback){
			layer.alert(msg,{title:'系统提示'}, function (index) { 
				 layer.close(index);
				if(callback!=undefined&&callback!=null){
					callback();
				}
			});
		},
		comfirmOk:function(msg,callback){
			if(msg==null||msg==undefined||msg==""){
				msg="您确定要执行吗？";
			}
			layer.confirm(msg, {icon: 3, title:'系统提示'}, function(index){
				  callback();
				  layer.close(index);
				});
		},
		comfirm:function(msg,yescallback,nocallback){
			if(msg==null||msg==undefined||msg==""){
				msg="您确定要执行吗？";
			}
			layer.confirm(msg, {icon: 3, title:'系统提示'}, function(index){
				 layer.close(index);
				 if(yescallback!=undefined&&yescallback!=null){
					 yescallback();
					} 
				},function(){
					if(nocallback!=undefined&&nocallback!=null){
						nocallback();
					}
				});
		}
}