	var dataJson;	//从服务器获取的数据--->page.js	
		var totalPage;	//总页数--->page.js	
	var watchData=$("#Womeninfo");

function li_click(id) {
		location.href = "FrontOfficeGeneral_servlet?type=index-JS&u_name="+u_name+"&w_id="
				+ id;
	}
//page.js---->fanPage调用
function PageTurning(data,cPage,doc){

	doc.children().remove();
	var ci=cPage*8;
	for ( var i =ci ; i < ci+8; i++) {
		if(data[i]!=null){
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
			doc.append(str);
		}
		
	}
}
	function dataLoad(data) {
		dataJson=data;		//设置数据
		totalPage=pageInit(data.length,$(".pageDiv:eq(0)"),8);	
		$("#Womeninfo").children().remove();
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
					+ "</span><i></div>" + "</div><div class=\"tInfo\">销量<b>"
					+ data[i].watch_Sellcount + "</b> </div>" + "</a></li>";
			$("#Womeninfo").append(str);
		}
	}
	function dataLoad(sours) {
		dataJson=sours;		//设置数据
		totalPage=pageInit(sours.length,$(".pageDiv:eq(0)"),8);	
		$("#Womeninfo").children().remove();
		for ( var i = 0; i < 8; i++) {
			var str = "<li onclick=\"li_click("
					+ sours[i].watch_Id
					+ ");\">"
					+ "<a title="+sours[i].brands.brand_name+"&nbsp;&nbsp;"+sours[i].watch_Name+">"
					+ "<i class='c__pTag'><span style=''></span></i>"
					+ "<div class=\"tImg\"><img src=\""+sours[i].watch_Image+"\"></div>"
					+ "<div class=\"tNm\">"
					+ (sours[i].brands.brand_name + sours[i].watch_Name) + "</div>"
					+ "<div class=\"tPrc\">￥<span>" + sours[i].watch_Price
					+ "</span><i></div>" + "</div><div class=\"tInfo\">销量<b>"
					+ sours[i].watch_Sellcount + "</b> </div>" + "</a></li>";
			$("#Womeninfo").append(str);
		}
	}
	function BrandInit(jDoc,b_id){
		$.getJSON("SexCategory","type=bInit&b_id="+b_id,
		function(data){
			for(var i=0;i<15;i++){
				if(i<data.length){
					var ddb="<dd onclick=''><a name="+data[i].brands.brand_id+"  title="+data[i].brands.dect+">"+data[i].brands.brand_name+"</a></dd>";
					jDoc.append(ddb);
				}
			}
		
		});
	}
	$(function() {
		$.ajax({
			"url" : "/gender/info.xf",
			"type" : "post",
			"data" : "gid=2",
			"dataType" : "json",
			"success" : function(data) {
				dataLoad(data);
				
			},
			"error" : function() {
				toastr.warning("服务器错误!");
			}
		});
		//点击上一页
		$(".pre").click(function(){
			 preClick(dataJson,watchData,$(".pageDiv:eq(0)"),totalPage,8);
		}).hide();
		//点击下一页
		$(".next").click(function(){
			 nextClick(dataJson,watchData,$(".pageDiv:eq(0)"),totalPage,8);
		});
		//点击页数
		$(".pageDiv").on("click",".pagenum",function(){
			pageClick($(this), dataJson,totalPage);
		});
		//品牌初始化
		BrandInit($("#level1"),5);
		BrandInit($("#level2"),6);
		BrandInit($("#level3"),7);
		BrandInit($("#level4"),8);
		//根据女表品牌查找
		function WomenBrandName(brid) {
			$.ajax({
				"url" : "SexCategory",
				"type" : "post",
				"data" : "brid=" + brid,
				"dataType" : "json",
				"success" : function(data) {
					dataLoad();
				},
				"error" : function() {
					toastr.warning("服务器错误!");
				}
			});

		}
		//搜素
		$("#button2").click(function() {
			var b = $("#brand_search").val();
		});

		//$("#jiages>dd:eq(1)").append(Womeninformation);
		//$("#jiages>dd:eq(1)").css("background", "#B01330");
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
			console.log(index);
			a.css("background", "#B01330");
			a.siblings().css("background", "#FFFFFF");
			$.ajax({
				"url":"/gender/pricecount.xf",
				"type" : "post",
				"data" : "indexType="+index,
				"dataType" : "json",
				"success" : function(sours) {
					dataLoad(sours)
				},
				"error" : function() {
					toastr.warning("加载失败!");
				}
			});
		});
		
	});