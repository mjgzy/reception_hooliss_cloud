toastr.options = {
    "closeButton": true, //是否显示关闭按钮

    "debug": false, //是否使用debug模式

    "positionClass": "toast-top-center",//弹出窗的位置

    "showDuration": "300",//显示的动画时间

    "hideDuration": "1000",//消失的动画时间

    "timeOut": "5000", //展现时间

    "extendedTimeOut": "1000",//加长展示时间

    "showEasing": "swing",//显示时的动画缓冲方式

    "hideEasing": "linear",//消失时的动画缓冲方式

    "showMethod": "fadeIn",//显示时的动画方式

    "hideMethod": "fadeOut" //消失时的动画方式

};
let buy_type_token;
function GetDateNow() {
		var vNow = new Date();
		var sNow = "";
		sNow += String(vNow.getFullYear());
		sNow += String(vNow.getMonth() + 1);
		sNow += String(vNow.getDate());
		sNow += String(vNow.getHours());
		sNow += String(vNow.getMinutes());
		sNow += String(vNow.getSeconds());
		sNow += String(vNow.getMilliseconds());
		return sNow;
	}
	function  randomTrdeNo() {
        var outTradeNo="";  //订单号
        for(var i=0;i<6;i++) //6位随机数，用以加在时间戳后面。
        {
            outTradeNo += Math.floor(Math.random()*10);
        }
        outTradeNo = new Date().getTime() + outTradeNo;  //时间戳，用来生成订单号。
		return outTradeNo;
    }
	function addCount(w_id,addCount) {
		$.ajax({
			url:"/shopcart/addCount.xf",
			type:"get",
			dataType:"json",
			data:"w_id="+w_id+"&addCount="+addCount+"&buy_type_token="+buy_type_token,
			success:function (data) {
				if (data.result===true){
					toastr.success("操作成功!");
				} else{
					toastr.error("操作失败!");
				}
            },
			error:function () {
				toastr.error("无法添加商品数量,请稍后再试!");
            }
		});
    }
	 function  resertForm(){
	     $(':input','#form1') 
	     .not(':button, :submit, :reset, :hidden') 
	     .val('') 
	     .removeAttr('selected');
 	}
	//显示信息ajax方法
		function InitAddress(a_val){
			$.ajax({
				"url":"/userAddress/initaddress.xf",
				"data":"address_id="+a_val,
				"type":"post",
				"dataType":"json",
				"success":function(data){
					console.log(data.race_name);
					if (data.code="200"){
                        $("[name=race_name]").val(data.race_name);		//收货人名称
                        var addStr=data.province+"-"+data.city+"-"+data.area;
                        $("[name=province]").val(addStr);		//地区
                        $("[name=street]").val(data.street);			//街道地址
                        $("[name=phone]").val(data.phone);		//手机
                        $("[name=zipCode]").val(data.zipCode);			//邮箱
                        $("[name=info_id]").val(data.info_id);			//主键id
					}else{

					}
				},
				"error":function(){toastr.warning("服务器未响应");}
			});
		}
		//获取所有信息ajax方法
		function getAll(){
			//获取用户id
			$.ajax({
					"url":"/userAddress/getAllAddress.xf",
					"type":"post",
					"dataType":"json",
					"success":function(result){
						var data=result.result;
						$("#address").children().remove();
						for(var i=0;i<data.length;i++){
							var addStr=data[i].province+"-"+data[i].city+"-"+data[i].area+"-"+data[i].street;
							var a_option="<option value="+data[i].info_id+">"+addStr+"</option>";
							$("#address").append(a_option);
						}
					},
				"error":function(){toastr.warning("加载地址信息失败!");}
			});
		}
//加商品数量
	$(function(){
		buy_type_token=$("#buy_type_token").text();
		$(".sls").click(function(){
			var count=parseFloat($(this).prev().val());
			var thisPrice=parseInt($(this).parent().prev().find("font").text());
				var price=parseFloat($("#goods_amount").text());
				var w_id=$(this).parent().parent().find(".w_id").val();
            	addCount(w_id,1);
				$(this).prev().val(count+1);
				$("#goods_amount").text(price+parseInt(thisPrice));
				$("#order_total").text(price+parseInt(thisPrice));

		});
	//减商品数量
	$(".slx").click(function(){
		var thisPrice=parseFloat($(this).parent().prev().find("font").text());
		var count=parseInt($(this).next().val());
		if(count==1){
			alert("最少购买1件!");
		}else{
			var price=parseFloat($("#goods_amount").text()); 
			var w_id=$(this).parent().parent().find(".w_id").val();
            addCount(w_id,0);
			$(this).next().val(count-1);
			$("#goods_amount").text(price-parseInt(thisPrice));
			$("#order_total").text(price-parseInt(thisPrice));
		}
		
	});
	
    $("#chaAddr").find("li").each(function(){
			$(this).find("input").attr("disabled","disabled");
		});
    //点击新地址
	$("#xAddress").click(function(){
		$("#chaAddr").find("li").each(function(){
			$(this).find("input").removeAttr("disabled");
			resertForm();
		});
		$(".save_a").find("input").val("保存");
	});
	//点击旧地址
	$("#jAddress").click(function(){
		$("#chaAddr").find("li").each(function(){
			$(this).find("input").attr("disabled","disabled");
		});
	});
	//初始化计算商品总额
	$(".w_each").each(function(){
		var count=parseInt($(this).find("[name=goods_number]").val());	//商品数量
	
		var price=parseFloat($(this).find(".w_price").text());		//金额
		var total=parseFloat($("#goods_amount").text());		//总价
		$("#goods_amount").text(total+(count*price));
		$("#order_total").text(total+(count*price));
	});
	//删除商品点击事件
	$(".delete").click(function(){
		if(confirm("确认删除?")){
			var w_id=$(this).next().next().val();
			var thisDoc=$(this);
			$.ajax({
				"url":"/shopcart/delShop.xf",
				"data":"w_id="+w_id+"&buy_type_token="+buy_type_token,
				"type":"post",
				"dataType":"json",
				"success":function(data){
					if(data.result===true){
						thisDoc.parent().parent().parent().remove();
						var count=parseInt(thisDoc.parent().parent().find("li:eq(2)").find("input").val());
						var dPrice=parseFloat(thisDoc.parent().parent().find(".f16").text())*count;	//单个价格
						var totalPrice=parseFloat($("#goods_amount").text());
						$("#goods_amount").text(totalPrice-(dPrice));
						$("#order_total").text(totalPrice-(dPrice));
						if ($(".w_infosDiv").length<=0){
                            $(".btnd00").attr("disabled","disabled").css({"background-color":"darkgrey","border":"1px solid darkgrey"});
                        }
					//设置为禁用状态
					}else{
                        toastr.error("删除失败!");
					}
				},
				"error":function(){toastr.warning("服务器未响应!")}
			});
		};
	});
	//点击图片进入首页
	$("#h1_menu").click(function(){
		location.href="/user/login.xf";
	});
	//保存地址事件
	$("#form1").submit(function(){
		var saveType="";
		if($(".save_a").find("input").val()=="保存"){
			saveType="save";
		}else {
			saveType = "modify";
		}
		$.ajax({
				"url":"/userAddress/addReceInfo.xf",
				"data":$(this).serialize()+"&saveType="+saveType,
				"type":"post",
				"dataType":"json",
			    "contentType": "application/x-www-form-urlencoded;charset=utf-8",
				"success":function(data){
					console.log(data);
					console.log(data.result);
					if(data.result===true){
						toastr.success((saveType==="save"?"保存":"修改")+"成功!");
						resertForm();
						getAll();
						$("#chaAddr").find("li").each(function(){
							$(this).find("input").attr("disabled","disabled");
						});
					}else{
						toastr.error((saveType==="save"?"保存":"修改")+"失败!");
					}
				},
				"error":function(){toastr.warning("服务器未响应!");}
			});
			
		return false;
// 		var action="Address_Info_servlet?type=save"
	});
	
	//地址选择框改变事件
	$("#address").change(function(){
		var a_id=$(this).val();
		InitAddress(a_id);
	});

	$("#addressLast").find("span").hover(function(){
		$(this).css({"text-decoration":"underline","color":"red"});
	},function(){
		$(this).css({"text-decoration":"none","color":"bleak"});
	});
	//点击修改事件
	$("#addressLast").find("span:eq(0)").click(function(){
		var a_id=$(this).prev().val();
		InitAddress(a_id);
		$("#chaAddr").find("li").each(function(){
			$(this).find("input").removeAttr("disabled");
		});
		$(".save_a").find("input").val("修改");
	});
	//地址删除事件
	$("#addressLast").find("span:eq(1)").click(function(){
		if(confirm("确认删除?")){
			var i_id=$(this).prev().prev().val();
			$.post("/userAddress/delAddress.xf?info_id="+i_id+"",
			function(data){
				if(data.result===true){
					toastr.success("删除成功!");
					getAll();
					resertForm();
				}else{
					toastr.error(data.message);
				}
			},"json");
		}
	});

		//提交订单时
		$("#order_checkout_submit").click(function(){
			const dataMap = [];			//订单集合对象
			const rece_id = $("#address").val();		//获取地址主键id
			let watch_id = 0;
			let watch_count = 0;
			const order_price = 0.00;
			alert("id"+rece_id);
			if ($(".w_each").length>0){
				for (let i=0; i<$(".w_each").length; i++) {
                    watch_id = $(".w_each .w_id:eq("+i+")").val();			//获取手表信息id
                    watch_count = $(".w_each input[name='goods_number']:eq("+i+")").val();		//订单手表数量
					var list={
						'watch_id':watch_id,
						'watch_count':watch_count
					}
					dataMap.push(list);
				}
                var subject = $(".m_10_20_10_0").attr("alt");   //商品描述
                var WIDsubject="好利时商城订单";				//订单名称
                var WIDtotal_amount=parseFloat($("#order_total").text());	//订单总金额
                var WIDbody="好利时订单支付页面";
                //发送异步请求生成订单
                $.ajax({
                    url:"/order/gengateOrder.xf",
                    data:"watch_info="+JSON.stringify(dataMap)+"&buy_type_token="+buy_type_token+"&w_receinfo_id="+rece_id,
                    dataType:"json",
                    type:"post",
                    success:function (data) {
                        if (data.code==="200"){
                            location.href="/getPagePay?outTradeNo="+randomTrdeNo()+"&totalAmount="+WIDtotal_amount+"&subject="+subject;
                        } else if (data.code==="500") {
                            toastr.error(data.message);
                        }
                    },
                    error:function () {
                        toastr.error("订单生成失败!");
                    }
                });
			}else{
				toastr.error("未选购商品!");
			}
		});
		//调用地区接口
		init_city_select($("#citySelect"));
		getAll();
	});