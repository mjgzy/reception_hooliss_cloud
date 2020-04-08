var errMessage;
var i = 60;
var flag = true;
var user_id =0;
if ($("#loginInfo").length!=0){
    user_id=$("#loginInfo").attr("name");
}
var overTime;
function KFHover(ipt){
    $(ipt).hover(function(){
        var cls     = $(this).attr('class');
        var css  = cls.substr(cls.lastIndexOf("kf_btn"), 8) ;
        $(this).removeClass(css).addClass(css + 'Hover');
    },function(){
        var cls     = $(this).attr('class');
        var css  = cls.substr(cls.lastIndexOf("kf_btn"), 8);
        $(this).removeClass(css + 'Hover').addClass(css);
    });
}
//添加商品数量方法
function addCount(user_id,w_id,addCount) {
    var goods_number_value=parseInt($("#goods_number").val());
    $.ajax({
        url:"/shopcart/addCount.xf",
        type:"get",
        dataType:"text",
        data:"user_id="+user_id+"&w_id="+w_id+"&addCount="+addCount,
        success:function (data) {
            if (data=="true"){
                toastr.success("操作成功!");
            } else{
                toastr.error("操作失败!");
                // $("#goods_number").val(goods_number_value-1);
            }
        },
        error:function () {
            toastr.error("无法添加商品数量,请稍后再试!");
            // $("#goods_number").val(goods_number_value-1);
        }
    });
}

function flagFunc(){
    if(i==1){
        console.log( $("#get_code>span").text());
        clearTimeout(overTime);
        $("#get_code>span").text("获取验证码");
        flag=true;
        i=60;
        return ;

    }
    i--;
    $("#get_code>span").text(i+"秒后重试");
}
function commback(ppq){
    if(ppq.result.length==6){
        flag=false;
        overTime= setInterval(flagFunc,1000);
    }
}
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
$(function(){

        //收藏点击事件
        $("#collection").click(function(){
            var goods_id = $(this).attr('oprid');
            $.post(www_domain + "/user/collection", { is_ajax : 1, ids : goods_id, collection : 1 },
                function(result){
                    alert(result);
                }, "json");
        });
//     fcbox("#login_now", true); //登录弹窗

    var refresh_gift_card_list = function(){};

    function pop_login(){
        $("#login_now").trigger("click");
    }

    KFHover('.context span');

    var thisPrice="${shop.w_price}";
    //加商品数量
    $(".sls").click(function(){
        var count=parseFloat($(this).prev().val());
        var thisPrice=parseInt($(this).parent().prev().find("span").text());
            var w_id=$(this).parent().parent().find(".w_id").val();
            addCount(user_id,w_id,1);
            var price=parseFloat($("#goods_amount").text());
            $(this).prev().val(count+1);
            $("#goods_amount").text(price+parseInt(thisPrice));
    });
    //减商品数量
    $(".slx").click(function(){
        var thisPrice=parseFloat($(this).parent().prev().find("span").text());
        var count=parseInt($(this).next().val());
        if(count==1){
            alert("最少购买1件!");
        }else{
            var w_id=$(this).parent().parent().find(".w_id").val();
            $(this).next().val(count-1);
            addCount(user_id,w_id,0);
            var price=parseFloat($("#goods_amount").text());
            $("#goods_amount").text(price-parseInt(thisPrice));
        }

    });

    //删除购物车操作
    $(".delete").click(function(){
        if(confirm("确认删除?")){
            var w_id=$(this).parent().find(".w_id").val();
            console.log(w_id);
            var thisDoc=$(this);
            $.ajax({
                "url":"/shopcart/delShop.xf",
                "data":"u_id="+user_id+"&w_id="+w_id,
                "type":"post",
                "dataType":"text",
                "success":function(data){
                    if(data=="true"){
                        thisDoc.parent().parent().remove();
                        var count=parseInt(thisDoc.parent().parent().find("li:eq(2)").find("input").val());
                        var dPrice=parseFloat(thisDoc.parent().parent().find(".f16").text())*count;	//单个价格
                        var totalPrice=parseFloat($("#goods_amount").text());
                        $("#goods_amount").text(totalPrice-(dPrice));
                        $("#order_total").text(totalPrice-(dPrice));
                        if ($(".shopEach").length==0){
                            //设置结算按钮为禁用状态
                            $(".btnd00").attr("disabled","disabled").css({"background-color":"darkgrey","border":"1px solid darkgrey"});
                        }


                    }else{
                        toastr.error("删除失败!");
                    }
                },
                "error":function(){toastr.warning("服务器未响应!")}
            });
        };
    });
    $(".shopEach").each(function(){
        var total=parseFloat($("#goods_amount").text());		//商品总额
        var count=parseInt($(this).find("li:eq(2)").find("input").val());
        var dPrice=parseFloat($(this).find(".f16").text())*count;	//单个价格
        $("#goods_amount").text(total+dPrice);
    });
});
//点击我的订单事件
$("#MyShop").click(function(){
    $("iframe",parent.document).attr("src","Order_servlet?type=Init&u_name=${u_name}");
});
    errMessage=$("#errMessage").val();
    $("#form1").submit(function() {
        // var name = $("#textname").val();
        //         // var zuozhe = $("#textpwd").val();
        //         // if (name.length == 0 || zuozhe.length == 0) {
        //         //     alert("用户名和密码不能为空");
        //         //     return false;
        //         // }
        //         // return true;

    });
    if(errMessage!='' && errMessage!=null){
        toastr.error(errMessage);
    }
    $(".tLnk2").hide();
    $("body").css("", "");

    //初始化隐藏账户密码表单
    $("#form1").hide();
    $("#form2").show();

    //点击现在登录事件
    $(".login_now").click(function () {
        //显示登录框
        $("#layui-layer-shade1,.section").fadeIn();
    });
    //点击去结算事件
    $(".btnd00,.s__arrow_red_down").click(function () {
        if (user_id==0){
            //显示登录框
            $("#layui-layer-shade1,.section").fadeIn();
            return;
        }
        $(this).parent().attr("href","/shopcart/cartTijiao.xf").trigger();

    });
    //关闭登录框事件
    $(".login_close").click(function () {
        //隐藏登录框
        $("#layui-layer-shade1,.section").fadeOut();
    });
    $(".phone_dynamic").click(function () {
        $("#form1").show();
        $("#form2").hide();
    });
    $(".phone_dynamic1").click(function () {
        $("#form2").show();
        $("#form1").hide();
    });

    $("#get_code>span").click(function(){
        if(!flag){
            return false;
        }
        var url ="/user/getCode/"+ $("#phone").val();
        $.ajax({
            url:url,
            type:"post",
            dataType:"json",
            success:function (ppq) {
                console.log(ppq);
                console.log(ppq.result);
                console.log(ppq.error);
                console.log(ppq.data);
                var message=$.parseJSON(ppq.data);
                if(message.Message!="OK"){
                    toastr.error("发送失败:"+message.Message);
                    ppq.result="error";
                }else{
                    toastr.success("发送成功!");
                }
                commback(ppq);
            }
        });
        // $(this).trigger('click');
    });

