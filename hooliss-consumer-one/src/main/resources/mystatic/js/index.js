
var dataJson;	//从服务器获取的数据
var id;			//性别id
var page=1;  //传入的当前页
var newTotalPage=[0,0,0,0];			//新品总页数
var trendTotalPage=[0,0,0,0];;		//时尚总页数
newTotalPage[0]=parseInt($(".hnh").val());
trendTotalPage[0]=parseInt($(".thred").val());
var newCurrentNo =[0,0,0,0];			//新品当前页
var trendCurrentNo =[0,0,0,0];		//时尚当前页
newCurrentNo[0]=1;			//初始化时男士为第一页
trendCurrentNo[0]=1;
var  topUlLiIndex;				//热销子版块下标
var newUlLiIndex=0;					//新品下子版块下标
var trendUlLiIndex=0;				//时尚下子版块下标
var ajaxReady = false;
//调用显示轮播图函数
banner();

function li_click(id) {

    window.open("/watch/doWatch.xf/"+id);
}
//三大板块
function pdataLoad(pro,parent_id) {
	dataJson=pro;		//设置数据
	if(parent_id==1){
		$("#w_info").html("");
	}else if(parent_id==2){
		$("#nanrankTit").html("");
	}else if(parent_id==3){
		$("#nvrankTit").html("");
	}
	console.log(pro);
	for ( var i = 0; i < pro.length; i++) {
		var w_id = pro[i].watch_id;
		var brand_href = pro[i].watchBrand.brand_id;
		var brand_name = pro[i].watchBrand.brand_name;
		var country_name = pro[i].watchBrand.country.c_name;
		var str="<li onclick='li_click("+w_id+")' class=\"watchLi\" goods-code=\"42471\" data-is-boutique=\"0\">\n" +
			"<a target=\"_blank\" href=\"/watch/doWatch.xf/"+pro[i].watch_id+"\" class=\"s_goods_img\">\n" +
			"<img class=\" lazy-load\" src=\""+pro[i].watch_image+"\" alt=\"\">\n" +
			"</a>\n" +
			"<div class=\"goods\">\n" +
			"<p style=\"text-align: center;\" class=\"red f14 bold\">\n" +
			"<del class=\"c666\">市场价:￥<span>"+pro[i].watch_price+"</span></del>\n" +
			"好利时价:￥<span>"+pro[i].so_price+"</span>\n" +
			"</p>\n" +
			"<a target=\"_blank\" href=\"https://www.wbiao.cn/goods/42471.html\" class=\"s_goods_name elps2\">"+pro[i].watch_name+"\n"+
			"</a>\n" +
			"<div class=\"goods_sale\">\n" +
			"<em class=\"s_sale_num\">销量<span>"+pro[i].watch_sellcount+"</span></em>\n" +
			"<a href=\"/brand/doShop.xf/"+brand_href+"\"  class=\"s_shop\">"+(country_name+brand_name)+"</a>\n" +
			"</div>\n" +
			"</div>\n" +
			"</li>"
		if(parent_id==1){
            $("#w_info").append(str).find("li").last().prepend("<div class=\"phb_01\"><i class=\"icon-d-phb-bg\"></i><em>NO."+(i+1)+"</em></div>");
		}else if(parent_id==2){
			$("#nanrankTit").append(str);
		}else if(parent_id==3){
			$("#nvrankTit").append(str);
		}
	}
}
//分页函数 分类下标，板块下标，起始页，显示页
function pageajax(index,parent_id,current_no,page_size){
	console.log(""+index);
	console.log("当前页"+current_no);
	$.ajax({
		"url":"/index/products.xf",
		"type" : "post",
		"data" : "proindex="+index+"&fotindex="+parent_id+"&current_no="+current_no+"&page_size="+page_size+"",
		"dataType" : "json",
		"success" : function(sours) {
			if (parent_id==2){
                newTotalPage[index]=sours.pages;
                $(".record:eq(0)").find(".pageTotal").text(newTotalPage[index]);		//改变总页数
                $(".record:eq(0)").find(".current_no").text(newCurrentNo[index]);
			} else if (parent_id==3){
                trendTotalPage[index]=sours.pages;
                $(".record:eq(1)").find(".pageTotal").text(trendTotalPage[index]);	//改变总页数
                $(".record:eq(1)").find(".current_no").text(trendCurrentNo[index]);
			}

			pdataLoad(sours.list,parent_id);
		},
		"error" : function() {
			toastr.warning("加载失败!");
		}
	});
}


$(function(){
	$(".page_naw>.pre").attr("disabled","disabled");		//初始化禁用点击上一页
    //子版块点击事件
	$(".tGud li").click(function () {
		var parent_id = parseInt($(this).parent().prev("span").attr("about"));		//获取父级id
		if (parent_id==1){
            topUlLiIndex = $(this).index();
            pageajax(topUlLiIndex, parent_id,trendCurrentNo[trendUlLiIndex],10);
		}else if(parent_id==2){
            newUlLiIndex = $(this).index();
            pageajax(newUlLiIndex,parent_id, newCurrentNo[newUlLiIndex],10);

		} else if (parent_id==3){
            trendUlLiIndex = $(this).index();
            pageajax(trendUlLiIndex, parent_id,trendCurrentNo[trendUlLiIndex],10);

		}
	});
	$(".s_shop>span").click(function (event) {
        event.stopPropagation();    //  阻止事件冒泡
		$(this).parent().trigger();
    });

//点击上一页
//分页点击方法,上一页
$(".page_nav").find(".pre").click(function(){
	var nav_index = 0;
    var  thisName= $(this).attr("name");
    if (thisName=="new") {
        newCurrentNo[newUlLiIndex]-=1;
        pageajax(newUlLiIndex, 2, newCurrentNo[newUlLiIndex], 10);
        if (newCurrentNo[newUlLiIndex]==1){
            $(this).hide();		//隐藏当前按钮
        }
        $(".page_nav:eq("+nav_index+")").find(".next").show(); //下一页显示
    }else if (thisName=="trend"){
        nav_index=1;
        trendCurrentNo[trendUlLiIndex]-=1;
		console.log("当前页::::"+trendCurrentNo[trendUlLiIndex]);
        //传入页数下标,父级id
        pageajax(trendUlLiIndex,3, trendCurrentNo[trendUlLiIndex],10);
        //如果当前页是第一页
        if (trendCurrentNo[trendUlLiIndex]==1){
            $(this).hide();		//隐藏当前按钮
        }
        $(".page_nav:eq("+nav_index+")").find(".next").show(); //下一页显示
	} 
}).hide();
	//点击下一页
	$(".page_nav").find(".next").click(function(){
		var  thisName= $(this).attr("name");
		if (thisName=="new"){


			newCurrentNo[newUlLiIndex]+=1;
			pageajax(newUlLiIndex,2, newCurrentNo[newUlLiIndex],10);
			//如果当前页是最后一页
			if (newCurrentNo[newUlLiIndex]==newTotalPage[newUlLiIndex]){
				$(this).hide();		//隐藏当前按钮
			}
            $(".page_nav:eq(0)").find(".pre").show(); //上一页显示
		} else if (thisName=="trend") {
			trendCurrentNo[trendUlLiIndex]+=1;

			//传入页数下标,父级id
			pageajax(trendUlLiIndex,3, trendCurrentNo[trendUlLiIndex],10);
			//如果当前页是最后一页
			if (trendCurrentNo[trendUlLiIndex]==trendTotalPage[trendUlLiIndex]){
				$(this).hide();		//隐藏当前按钮
			}
            $(".page_nav:eq(1)").find(".pre").show(); //上一页显示
		}
	});
	$(".btnJtp").click(function () {
		console.log( $(this).prev().val());
		var pageNum =parseInt( $(this).prev().val());
        var  thisName= $(this).attr("name");
        var  tPage = parseInt($(this).prev().prev().prev().text());
		if (isNaN(pageNum)){
			toastr.error("请输入数字!");
		}else if (pageNum>tPage) {
            toastr.error("没有这么多页数!");
		}else{
			var nav_index = 0;
			if (thisName=="new"){
                newCurrentNo[newUlLiIndex]=pageNum;		//记录当前页数
                pageajax(newUlLiIndex,2, pageNum,10);
            } else if (thisName=="trend"){
                nav_index=1;
				trendCurrentNo[trendUlLiIndex]=pageNum;	//记录当前页数
                pageajax(trendUlLiIndex,3, pageNum,10);
			}
            if (pageNum!=1){
                $(".page_nav:eq("+nav_index+")").find(".pre").show(); //上一页显示
            }else{
                $(".page_nav:eq("+nav_index+")").find(".pre").hide(); //上一页显示
            }
		}
    });

});