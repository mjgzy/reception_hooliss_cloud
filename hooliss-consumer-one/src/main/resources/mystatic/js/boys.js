		var dataJson;	// 从服务器获取的数据--->page.js
		var totalPage;	// 总页数--->page.js
		var watchData=$("#mensin");

        function li_click(id) {
		location.href = "/watch/doWatch.xf/"+id;
	}
	// page.js---->fanPage调用
	function PageTurning(data,cPage,doc){
		doc.children().remove();
		var ci=cPage*8;
		for ( var i =ci ; i < ci+8; i++) {
			if(data[i]!=null){
				var str = "<li onclick=\"li_click("
						+ data[i].w_id
						+ ");\">"
						+ "<a title="+data[i].brands.brand_name+"&nbsp;&nbsp;"+data[i].watch_Name+">"
						+ "<i class='c__pTag'><span style=''></span></i>"
						+ "<div class=\"tImg\"><img src=\""+data[i].watch_Image+"\"></div>"
						+ "<div class=\"tNm\">"
						+ (data[i].brands.brand_name + data[i].watch_Name) + "</div>"
						+ "<div class=\"tPrc\">￥<span>" + data[i].watch_Price
						+ "</span><i></i></div>"
						+ "</div><div class=\"tInfo\">销量<b>" + data[i].watch_Sellcount
						+ "</b> </div>" + "</a></li>";
				doc.append(str);
			}
			
		}
	}
	function dataLoad(data) {
		dataJson=data;		// 设置数据
		totalPage=pageInit(data.length,$(".pageDiv:eq(0)"),8);
		$("#mensin").children().remove();
		for ( var i = 0; i < 8; i++) {
			var str = "<li onclick=\"li_click("
					+ data[i].watch_Id
					+ ");\">"
					+ "<a title="+data[i].brands.brand_name+"&nbsp;&nbsp;"+data[i].watch_Name+">"
					+ "<i class='c__pTag'><span style=''></span></i>"
					+ "<div class=\"tImg\"><img src=\""+data[i].watch_Image+"\"></div>"
					+ "<div class=\"tNm\">"
					+ (data[i].brands.brand_name + data[i].watch_Name) + "</div>"
					+ "<div class=\"tPrc\">￥<span>" + data[i].watch_Price
					+ "</span><i></i></div>"
					+ "</div><div class=\"tInfo\">销量<b>" + data[i].watch_Sellcount
					+ "</b> </div>" + "</a></li>";
			$("#mensin").append(str);
		}
	}
	function datasoure(soure) {
		dataJson=soure;		// 设置数据
		$("#mensin").children().remove();
		for ( var i = 0; i < 8; i++) {
			var str = "<li onclick=\"li_click("
					+ soure[i].watch_Id
					+ ");\">"
					+ "<a title="+soure[i].brands.brand_name+"&nbsp;&nbsp;"+soure[i].watch_Name+">"
					+ "<i class='c__pTag'><span style=''></span></i>"
					+ "<div class=\"tImg\"><img src=\""+soure[i].watch_Image+"\"></div>"
					+ "<div class=\"tNm\">"
					+ (soure[i].brands.brand_name + soure[i].watch_Name) + "</div>"
					+ "<div class=\"tPrc\">￥<span>" + soure[i].watch_Price
					+ "</span><i></i></div>"
					+ "</div><div class=\"tInfo\">销量<b>" + soure[i].watch_Sellcount
					+ "</b> </div>" + "</a></li>";
			$("#mensin").append(str);
		}
	}

		// 品牌点击事件
	function ddClick(brand_id){
		$.getJSON("SexCategory","type=bSerach&g_id=1&b_id="+brands.brand_id+"",
		function(data){
			dataLoad(data);
		});
	};
	function BrandInit(jDoc,b_id){
		$.getJSON("SexCategory","type=bInit&b_id="+b_id,
		function(data){
			for(var i=0;i<15;i++){
				if(i<data.length){
					var ddb="<dd name="+data[i].brand_id+" onclick='ddClick($(this).attr('name'))'><a   title="+data[i].dect+">"+data[i].brand_name+"</a></dd>";
					jDoc.append(ddb);
				}
			}
		
		});
	}
	
	// 入口
	$(function() {
		$.ajax({
			"url":"/gender/info.xf",
			"type" : "post",
			"data" : "gid=1",
			"dataType" : "json",
			"success" : function(data) {
				dataLoad(data);
			},
			"error" : function() {
				toastr.warning("服务器错误!");
			}
		});
	});

	
	
		// 点击上一页
		$(".pre").click(function(){
			 preClick(dataJson,watchData,$(".pageDiv:eq(0)"),totalPage,8);
		}).hide();
		// 点击下一页
		$(".next").click(function(){
			 nextClick(dataJson,watchData,$(".pageDiv:eq(0)"),totalPage,8);
		});
		// 品牌初始化
		BrandInit($("#level1"),5);
		BrandInit($("#level2"),6);
		BrandInit($("#level3"),7);
		BrandInit($("#level4"),8);
		// 搜索框
		$("#button2").click(function() {
			var b = $("#brand_search").val();

		});
		// 点击页数
		$(".pageDiv").on("click",".pagenum",function(){
			pageClick($(this), dataJson,totalPage);
		});
		var flag=false;
		$("#jiages>dd").click(function() {
			var index = $(this).index()-1;
			var a = $("#jiages>dd:eq("+index+")");
			if (index==0&&flag==false) {
				index=0;
				flag=true;
			}else if(index==0&&flag==true){
				index=3;
				$("#jiages>dd:eq(0)").css("background","blue");
				flag=false;
			}
			a.css("background", "#B01330");
			a.siblings().css("background", "#FFFFFF");
			$.ajax({
				"url":"/gender/pricecount.xf",
				"type" : "post",
				"data" : "indexType="+index,
				"dataType" : "json",
				"success" : function(soure) {
					datasoure(soure)
				},
				"error" : function() {
					toastr.warning("加载失败!");
				}
			});
            function initboys() {
                $.ajax({
                    "url" : "boysajax",
                    "type" : "GET",
                    "data" : "list",
                    "dataType" : "json",
                    "success" : initbb
                });
            }
            function initbb(data) {
                var newdata = $("$show_area").empty();

            }

		});
