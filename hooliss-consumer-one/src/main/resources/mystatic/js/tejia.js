
function li_click(id){
			location.href="FrontOfficeGeneral_servlet?type=index-JS&u_name=${u_name}&w_id="+id;	
		}
	$(function(){
		$.ajax({
			"url":"/discount/DiscountServlet?type=time",
			"type":"post",
			"dataType":"json",
			"success":function(data){
				console.log(data);
				for ( var i = 0; i < data.length; i++) {
					var tejia_li="<li onclick=\"li_click("+data[i].watch_Id+");\"> <span class=\"\"></span> "+
					 "<img src=\""+data[i].watch_Image+"\""+ 
					 "alt=\""+ data[i].brands.brand_name +"&nbsp;&nbsp;"+data[i].watch_Name+"\" width=\"300\" height=\"300\"></a>"+
					"<p> <b>"+ data[i].brands.brand_name +"&nbsp;&nbsp;"+data[i].watch_Name+"</b><br>"+
					"<u></u><ins>￥"+data[i].watch_Price+"</ins><del>原价：￥"+data[i].watch_Price+"</del><br>"+
					"<span class=\"cmt\">已被购买<i>"+data[i].watch_Sellcount+"</i>次</span> </p>"+ 
					"<a href=\"javascript:addToCartT(3675, 1, 1);\" class=\"btn\">立即抢购</a> </li>";
					$("#tejia_info").append(tejia_li);
				}	
			},
			"error":function(){toastr.warning("服务器错误！");}
		});
	});