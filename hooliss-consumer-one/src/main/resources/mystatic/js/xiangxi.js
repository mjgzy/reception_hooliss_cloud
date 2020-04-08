var dataJson;	//从服务器获取的数据
var watchStyle;     //手表款式信息
$(function () {
    //正品保障
    $(".fl>li").mouseover(function () {
        $(this).find("span").attr("class", "on");
        $(this).find("div").show();
    });
    $(".fl>li").mouseout(function () {
        $(this).find("span").attr("class", "");
        $(this).find("div").hide();
    });
    //加入购物车
    $("#addGwc").click(
            function () {
                var count = $("#slx").next().val(); //获取商品数量
                var watch_id = $("#buy>span").attr("name"); //获取手表主键id
                //不等于0则表示用户已登录
                if (user_id!=0){
                    $.ajax({
                        "url": "/shopcart/addCart.xf/"+user_id+"/"+watch_id+"/"+count+"",
                        "type": "post",
                        "dataType": "json",
                        "success": function (data) {
                            if (data.code=="200") {
                                toastr.success("加入购物车成功!");
                            } else if (data.code=="500") {
                                toastr.error(data.message);
                            }
                        },
                        "error": function () {
                            toastr.error("服务器出错!");
                        }
                    });
                } else{
                    //用户未登录
                    var watch_name = $(".watch_name").text();       //获取手表名称
                    var brand_name = $(".shop_tit>i").text();       //获取品牌名称
                    var brand_id =$(".shop_tit>i").attr("name");            //获取品牌id
                    var watch_price = $(".js_price").text();        //获取手表价格
                    var watch_image = $("#zoom1").attr("name");       //获取手表图片
                    console.log(watch_name);
                    console.log(brand_name);
                    console.log(brand_id);
                    console.log(watch_price);
                    console.log(watch_image);
                    console.log(count);
                    console.log(watch_id);
                    console.log(user_id);
                    $.ajax({
                        "url": "/shopcart/doCookieCart.xf",
                        data:"watch_name="+watch_name+"&brand_name="+brand_name+"&brand_id="+brand_id+"" +
                            "&watch_id="+watch_id+"&watch_price="+watch_price+"&add_count="+count+"&watch_image="+watch_image+"",
                        "type": "post",
                        "dataType": "json",
                        "success": function (data) {
                            if (data.code=="200") {
                                toastr.success("加入购物车成功!");
                            } else if (data.code=="500") {
                                toastr.error(data.message);
                            }
                        },
                        "error": function () {
                            toastr.error("服务器出错!");
                        }
                    });
                }
            });

    //详情评价等点击事件
    $(".subnav li").click(function () {
        // console.log($(".commodity_details:eq("+$(this).index()+")"));
        $(".commodity_details").hide();
        $(".commodity_details").eq($(this).index()).show();


        $(this).addClass("detailsOn").find("#d").addClass("detailsOna");
        $(this).siblings().removeClass("detailsOn").find("#d").removeClass("detailsOna");
    }).first().addClass("detailsOn").find("#d").addClass("detailsOna");
    //显示评价,隐藏商品详情
    // $(".comment").click(function () {
    //
    // });
    //加商品数量
    $("#sls").click(function () {
        var count = parseInt($(this).prev().val());
        if (count == 10) {
            alert("最多购买十件!");

        } else {
            $(this).prev().val(count + 1);
        }

    });
    //减商品数量
    $("#slx").click(function () {

        var count = parseInt($(this).next().val());
        if (count == 1) {
            alert("最少购买1件!");
        } else {
            $(this).next().val(count - 1);
        }

    });

//用户评价信息 点击才加载
    $(".comment").click(function () {
        var wid = $("#buy").find("span").attr("name"); //获取表ID
        commentajax(wid,1,0,10);
    });

    $("#watchid").hide();

//评价函数
    function commentajax(w_id,whether_image,current_no,page_size) {
        $.ajax({
            "url": "/user/comment.xf",
            "type": "post",
            "data": "w_id=" + w_id +"&whether_image="+whether_image+ "&current_no="+current_no+"&page_size="+page_size,
            "dataType": "json",
            "success": function (sours) {
                if (sours.code==="200"){

                    //渲染标签
                    comment(sours, w_id);
                } else{
                    toastr.error("错误码:"+sours.code+"错误消息:"+sours.message);
                }

            },
            "error": function () {
                toastr.warning("加载失败!");
            }
        });
    }
    //渲染用户评价标签
    function comment(comm, w_id) {
        dataJson = comm.result;
        console.log(dataJson);
        $(".wholeTwo_img").children().remove();
        for (var i = 0; i < dataJson.length; i++) {
            var comment_str="<li class=\"clearfix js_evaluate\">" +
                "<div class=\"left fl\">" +
                "<div class=\"left_logo \">" +
                "<img src=\""+dataJson[i].wuser.head_image+"\" alt=\"\"></div>" +
                "<div class=\"left_name js_name\">"+dataJson[i].wuser.u_name+"</div></div>" +
                "<div class=\"right fl js_star\">" +
                "<div class=\"clearfix\">" +
                "<div class=\"right_star fl\" data-index=\"4\">";
                for (var j=0;j<dataJson[i].star;j++){
                    comment_str+="<span class=\"icon-a-goods-wuxing fl\"></span>";
                }
                comment_str+="</div>" +
                "<div class=\"right_grade fl\" style=\"background-color:#FFBF80\">"+dataJson[i].watch_vip.vip_name+"</div></div>" +
                "<div class=\"right_pinyu\">"+dataJson[i].content+"</div>" +
                "<div class=\"video_img_box clearfix\"><!--视频div标签-->";
                if (dataJson[i].video!==null){
                    comment_str+=  "<div class=\"video_box fl\" video-id=\"e51280d355614460afa097646bc8ce58\">" +
                        "<img class=\"comment_video fl\" src=\"视频路径图片封面\" alt=\"\"></div>" +
                        "<div class=\"right_img fl clearfix loadImg-evaluate\">";
                }
                //遍历评价图片
                for (var k=0;k<dataJson[i].comment_Images.length;k++) {
                    comment_str+="<a class=\"fl\"><img  src=\""+dataJson[i].comment_Images[k].image_url+"\"></a>" ;
                }
            comment_str+="</div></div><div class=\"right_time\">" +
                "<span>"+dataJson[i].publish_date+"</span><span>手表款式信息</span></div>";
            //如果客服回复了
                if (dataJson[i].reply_status===1){
                    comment_str+="<div class=\"right_kefu\">" +
                        "<span class=\"right_kefu_a\">［客服回复］:</span>" +
                        "<span class=\"right_kefu_b\">"+dataJson[i].reply_content+"</span></div>" +
                        "<div class=\"right_kefu_time\">"+dataJson[i].replyDate+"</div></div></li>";
                }else{
                    comment_str+="</li>";
                }
            $(".wholeTwo_img").append(comment_str);
        }
    }
    //款式悬浮事件
    $(".upper_style_right").on("mouseover",".watchStyle",function () {
        $(this).css("border","2px #cc5252 solid");
    }).on("mouseout",".watchStyle",function () {
        $(this).css("border","2px #ddd solid");
    });
    //立即购买点击事件
    $("#buy>span").click(function () {
        var u_id = $("#loginInfo").attr("name");
        var w_id = $(this).attr("name");
        var number = $("#number").val();
        if (typeof(u_id) == "undefined"||typeof(w_id) == "undefined"||typeof(number) == "undefined")
        {
            $(this).parent().attr("href","/user/login.xf");
            $(this).parent().attr("target","_self");
        }else{
            $(this).parent().attr("href","/shopcart/doTijiao.xf?watch_id="+w_id+"&add_count="+number+"");
        }
        $(this).trigger();
    });
});

