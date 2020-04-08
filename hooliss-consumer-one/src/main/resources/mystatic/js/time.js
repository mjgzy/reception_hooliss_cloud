

function li_click(id){
			location.href="FrontOfficeGeneral_servlet?type=index-JS&u_name=${u_name}&w_id="+id;	
		}
	$(function(){
		
		$.ajax({
			"url":"DiscountServlet?type=tejia",
			"type":"post",
			"dataType":"json",
			"success":function(data){
				console.log(data);
				for ( var i = 0; i < data.length; i++) {
					var tejia_li="<li onclick=\"li_click("+data[i].watch_Id+");\"> <span class=\"tips rec\"></span>"+
					 "<img src=\""+data[i].watch_Image+"\""+ 
					 "alt=\""+data[i].watch_Name+"&nbsp;&nbsp;"+data[i].watch_Name+"\" width=\"300\" height=\"300\"></a>"+
					"<p> <b>"+data[i].watch_Name+"&nbsp;&nbsp;"+data[i].watch_Name+"</b><br>"+
					"<u></u><ins>￥"+data[i].watch_Price+"</ins><del>原价：￥"+data[i].watch_Price+"</del><br>"+
					"<span class=\"cmt\">已被购买<i>"+data[i].watch_Sellcount+"</i>次</span> </p>"+
					"<a href=\"javascript:addToCartT(3675, 1, 1);\" class=\"btn\">立即抢购</a> </li>";
					$("#tejia_info").append(tejia_li);
				}	
			},
			"error":function(){toastr.warning("服务器错误！");}
		});
		
	});
	function countTime() {  
	              //获取当前时间  
	             var date = new Date();  
	             var now = date.getTime();  
	             //设置截止时间  
	             var str="2019/12/01 00:00:00";
	             var endDate = new Date(str); 
	             var end = endDate.getTime();
	                  //时间差  
	             var leftTime = end-now; 
	             //定义变量 d,h,m,s保存倒计时的时间  
	             var d,h,m,s;  
	             if (leftTime>=0) {  
	                d = Math.floor(leftTime/1000/60/60/24);  
	                h = Math.floor(leftTime/1000/60/60%24);  
	                m = Math.floor(leftTime/1000/60%60);  
	                s = Math.floor(leftTime/1000%60);                     
	             }  
	             //将倒计时赋值到div中  
	             $("#_d").html( d+"天");
	             $("#_h").html(h+"时");
	              $("#_m").html(m+"分");
	               $("#_s").html(s+"秒");
	             //递归每秒调用countTime方法，显示动态时间效果  
	             setTimeout(countTime,1000);   	
		
 }