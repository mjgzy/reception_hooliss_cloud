var u_name=$("#u_name").val;
var flag=false;
var page_size = 20;
var user_id = 0;
if ( $("#loginInfo").length!=0){
    user_id=$("#loginInfo").attr("name");
}
$(function(){
	toastr.options = {
        "closeButton": false, //是否显示关闭按钮

        "debug": false, //是否使用debug模式

        "positionClass": "toast-top-full-width",//弹出窗的位置

        "showDuration": "300",//显示的动画时间

        "hideDuration": "1000",//消失的动画时间

        "timeOut": "5000", //展现时间

        "extendedTimeOut": "1000",//加长展示时间

        "showEasing": "swing",//显示时的动画缓冲方式

        "hideEasing": "linear",//消失时的动画缓冲方式

        "showMethod": "fadeIn",//显示时的动画方式

        "hideMethod": "fadeOut" //消失时的动画方式

    };

	$(".pt_nav_left").hover(function () {
		if (!flag){
            flag = true;
            brandbyid(6);
		}

		$(".nav_left_menu").show();
    },function () {
		$(".nav_left_menu").hide();
    });

	$(".nav_left_menu").on("mouseover",".menu_box","",function(){
		$(this).find(".sub_menu").show();
	}).on("mouseout",".menu_box","",function () {
			$(this).find(".sub_menu").hide();
	});



	// 	.hover(function (){
	//
	// },function () {
    //
    // });
	$("#h1img").attr("src","http://127.0.0.1:81/logo/LOGO.png");


//	搜索框点击事件
	$(".tIptTxt").click(function () {
		$(this).val("").removeClass("c999");
    }).blur(function () {
    	if ($(this).val()===""){
            $(this).addClass("c999").val("搜索 品牌/系列/型号");
		}

    });
	$(".c__search").click(function () {
		var key_word = $(this).prev().val();
		$(this).attr("href","/commodity/doOs.xf/"+key_word+"/0/0/0/"+page_size+"/0/0");
    });


});

function brandbyid(id) {
	$.ajax({
		"url" : "/brand/doBrandbyid.xf",
		"type" : "post",
		"data" : "id=" + id,
		"dataType" : "json",
		"success" : function(mapdata) {

				brandload(mapdata);

			},
			"error" : function() {
				toastr.warning("敌敌畏!!");
			}
		});

}
function brandload(mapdata) {
	console.log(mapdata);
	brandLength=mapdata.typedc.length;
	listLength=mapdata.listsh.length;
	listLengthgd=mapdata.listgd.length;
	listLengthzd=mapdata.listzd.length;
	listLengthss=mapdata.listss.length;
	for ( var i = 0; i < brandLength; i++) {
		var str =
				"<div class='menu_box'>"+"<i class='menu_line fl'>"+"</i>"
					+"<dl>"
						+"<dt class='fl'>"
							+"<a rel='nofollow' target='_blank' href='/doShop.xf/"+mapdata+"'>"+mapdata.typedc[i].type_name+"<em>"+'/'+"</em>"+"</a>"
						+"</dt>"

						+"<dd class='rel fl elps1'>"
							+"<div class='clear'>"+"</div>"
						+"</dd>"
						+"<dd class='sub_menu clearfix' style='display: none;'>"
							+"<div class='col_box fl'>"
								+"<div class='col_1'>"+"<span class='t'>"+'品牌'+"</span>"
									+"<div class='brand_list clearfix'>"
										+"<ul class='clearfix'>"

											+"<div class='menu_bottom_line'>"+"</div>"
										+"</ul>"
										+"<i class='brand_bottom'>"+"</i>"
									+"</div>"
								+"</div>"
							+"</div>"
							+"<a target='_blank' href='#' class='brand_ads fl lazy-load'>"
								+"<img src='//image8.wbiao.co/mall/09f566c7795947d383f924288d945c8d.jpg?x-oss-process=image/resize,m_pad,w_205,h_412' alt=''>"
							+"</a>"
						+"</dd>"
						+"<div class='clear'>"+"</div>"
					+"</dl>"
				+"</div>"
		$(".nav_left_menu").append(str);
		if (mapdata.typedc[i].type_id==5){
			for(var j=0;j<3;j++){
				var a5 = "<a rel='nofollow' target='_blank' class='rel_a' href='/brand/doShop.xf/"+mapdata.listsh[j].brand_id+"'>"+mapdata.listsh[j].brand_name+"</a>"
				$(".elps1").eq(i).prepend(a5);
			}
			for (var k=0;k<listLength;k++){
				var listshb =
					"<li class='fl'>"
						+"<a href='/brand/doShop.xf/"+mapdata.listsh[k].brand_id+"' >"
							+"<p class='p_logo lazy-load'>"

								+"<img src=\""+mapdata.listsh[k].b_logo+"\" alt=''>"
							+"</p>"
							+"<p class='p_name'>"+mapdata.listsh[k].brand_name+"</p>"
						+"</a>"
					+"</li>"
				$(".brand_list").eq(i).children("ul").prepend(listshb);
			}
		}else if(mapdata.typedc[i].type_id==6){
			for(var j=0;j<3;j++){
				var a6 = "<a rel='nofollow' target='_blank' class='rel_a' href='/brand/doShop.xf/"+mapdata.listgd[j].brand_id+"'>"+mapdata.listgd[j].brand_name+"</a>"
				$(".elps1").eq(i).prepend(a6);
			}
			for (var k=0;k<listLengthgd;k++){
				var listgdb =
					"<li class='fl'>"
					+"<a href='/brand/doShop.xf/"+mapdata.listgd[k].brand_id+"'>"
					+"<p class='p_logo lazy-load'>"
					+"<img src=\""+mapdata.listgd[k].b_logo+"\" alt=''>"
					+"</p>"
					+"<p class='p_name'>"+mapdata.listgd[k].brand_name+"</p>"
					+"</a>"
					+"</li>"
				$(".brand_list").eq(i).children("ul").prepend(listgdb);
			}

		}else if(mapdata.typedc[i].type_id==7){
			for(var j=0;j<3;j++){
				var a7 = "<a rel='nofollow' target='_blank' class='rel_a' href='/brand/doShop.xf/"+mapdata.listzd[j].brand_id+"'>"+mapdata.listzd[j].brand_name+"</a>"
				$(".elps1").eq(i).prepend(a7);
			}
			for (var k=0;k<listLengthzd;k++){
				var listzdb =
					"<li  class='fl'>"
					+"<a href='/brand/doShop.xf/"+mapdata.listzd[k].brand_id+"'>"
					+"<p class='p_logo lazy-load'>"
					+"<img src=\""+mapdata.listzd[k].b_logo+"\" alt=''>"
					+"</p>"
					+"<p class='p_name'>"+mapdata.listzd[k].brand_name+"</p>"
					+"</a>"
					+"</li>"
				$(".brand_list").eq(i).children("ul").prepend(listzdb);
			}

		}else if(mapdata.typedc[i].type_id==8){
			for(var j=0;j<3;j++){
				var a8 = "<a rel='nofollow' target='_blank' class='rel_a' href='/brand/doShop.xf/"+mapdata.listss[j].brand_id+"'>"+mapdata.listss[j].brand_name+"</a>"
				$(".elps1").eq(i).prepend(a8);
			}
			for (var k=0;k<listLengthss;k++){
				var listssb =
					"<li  class='fl'>"
					+"<a href='/brand/doShop.xf/"+mapdata.listss[k].brand_id+"'>"
					+"<p class='p_logo lazy-load'>"
					+"<img src=\""+mapdata.listss[k].b_logo+"\" alt=''>"
					+"</p>"
					+"<p class='p_name'>"+mapdata.listss[k].brand_name+"</p>"
					+"</a>"
					+"</li>"
				$(".brand_list").eq(i).children("ul").prepend(listssb);
			}

		}



	}
}