//品牌id
var barndid = 0;
//性别
var grandsex =0;
//手表名称
var tbwatchname = "";
//最低价
var watch_priceMin =  0.00;
//最高价
var watch_priceMax =  0.00;
var doc = 0;
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
$(function () {

    var currentNo=parseInt($(".current_no").text());  //当前页
    var pageTotal=parseInt($(".pageTotal").text());  //总页数
    //跳转页数的输入框
    var jtp = document.getElementById("jtp");

    $(".left_store_floor .store_line .goods_sort_list .show_hide").click(function () {
        var o = $(this);
        o.find(".icon-a-list-show").hasClass("h") ? o.find(".icon-a-list-hide").addClass("h")
                .siblings().removeClass("h").parents("h3").siblings(".second_menu").addClass("h")
            : o.find(".icon-a-list-hide").removeClass("h").siblings().addClass("h").parents("h3")
                .siblings(".second_menu").removeClass("h")
    });
    //如果只有1页
    if (currentNo==1){
        $(".page_nav>.pre,.page_nav>.next").attr("disabled","disabled");
    }
    // //品牌id
    // var barndid = document.getElementById("abc").value;
    // //性别
    // var grandsex = document.getElementsByClassName('grandid');
    // //手表名称
    // var tbwatchname =  document.getElementById("key_word").value;
    // //最低价
    // var watch_priceMin =  document.getElementById("minprice").value;
    // //最高价
    // var watch_priceMax =  document.getElementById("maxprice").value;


    //搜索功能
    $("#srh_wbiao").click(function () {
        var key_word = $(this).prev().find("input").val();
        console.log(key_word);
        window.open("/commodity/doOs.xf/"+key_word+"/0/0/0/"+20+"/0/0");
        // $(this).attr("href","/commodity/doOs.xf/"+key_word+"/0/0/0/"+20+"/0/0");
    });
    //点击上一页
    $(".page_nav>.pre").click(function () {
        if (currentNo==1){
            toastr.error("当前为第一页!");
            return;
        }
        //品牌id
        barndid = $(".cnm").attr("name");
        //性别
       grandsex = document.getElementsByClassName('grandid');
        //手表名称
       tbwatchname =  document.getElementById("key_word").value;
        //最低价
       watch_priceMin =  document.getElementById("minprice").value;
        //最高价
        watch_priceMax =  document.getElementById("maxprice").value;
        if(tbwatchname==""){
            tbwatchname='0';
        }if(watch_priceMin==""){
            watch_priceMin=0;
        }if(watch_priceMax==""){
            watch_priceMax=0;
        }

        doc =$("#docx").val();
        if(doc==''){
            doc=0;
        }
        //价格点击
        if (doc==2){
            var iDoc = $(this).children("i");
            //如果是所有价格
            if (iDoc.attr("class")=="p_all"){
                iDoc.removeClass("p_all").addClass("p_up");
                doc = 2;
                //    如果是价格升序,则价格从低到高排序
            }else if (iDoc.attr("class")=="p_up") {
                iDoc.removeClass("p_up").addClass("p_down");
                doc = 5;
                //如果是价格降序,则价格从高到低排序
            }else if (iDoc.attr("class")=="p_down"){
                iDoc.removeClass("p_down").addClass("p_all");
                doc = 0;
            }
        }
        currentNo-=1;       //当前页加1
        $(".current_no").text(currentNo);
        //发送ajax,获取数据
        tblist(barndid,tbwatchname,watch_priceMin,watch_priceMax,currentNo,20,doc);
    });

    //点击下一页
    $(".page_nav>.next").click(function () {
        if (currentNo==pageTotal){
            toastr.error("当前为最后一页!");
            return;
        }
        //品牌id
        barndid = $(".cnm").attr("name");
        //性别
        grandsex = document.getElementsByClassName('grandid');
        //手表名称
        tbwatchname =  document.getElementById("key_word").value;
        //最低价
        watch_priceMin =  document.getElementById("minprice").value;
        //最高价
        watch_priceMax =  document.getElementById("maxprice").value;


        if(tbwatchname==""){
            tbwatchname='0';
        }if(watch_priceMin==""){
            watch_priceMin=0;
        }if(watch_priceMax==""){
            watch_priceMax=0;
        }
        doc =$("#docx").val();
        if(doc==''){
            doc=0;
        }
        //价格点击
        if (doc==2){
            var iDoc = $(this).children("i");
            //如果是所有价格
            if (iDoc.attr("class")=="p_all"){
                iDoc.removeClass("p_all").addClass("p_up");
                doc = 2;
                //    如果是价格升序,则价格从低到高排序
            }else if (iDoc.attr("class")=="p_up") {
                iDoc.removeClass("p_up").addClass("p_down");
                doc = 5;
                //如果是价格降序,则价格从高到低排序
            }else if (iDoc.attr("class")=="p_down"){
                iDoc.removeClass("p_down").addClass("p_all");
                doc = 0;
            }
        }
        currentNo+=1;       //当前页加1
        $(".current_no").text(currentNo);
        tblist(barndid,tbwatchname,watch_priceMin,watch_priceMax,currentNo,20,doc);

    });

    //点击页数输入事件
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
            currentNo=pageNum;
            $(".current_no").text(currentNo);
            tblist(barndid,tbwatchname,watch_priceMin,watch_priceMax,currentNo,20,pageNum);
        }
    });

//    排序点击事件
    $("#jiages").find("dd a").click(function(){
        //品牌id
        barndid = $(".cnm").attr("name");
        //性别
       grandsex = document.getElementsByClassName('grandid');
        //手表名称
       tbwatchname =  document.getElementById("key_word").value;
        //最低价
         watch_priceMin =  document.getElementById("minprice").value;
        //最高价
         watch_priceMax =  document.getElementById("maxprice").value;

        if(tbwatchname==""){
            tbwatchname='0';
        }if(watch_priceMin==""){
            watch_priceMin=0;
        }if(watch_priceMax==""){
            watch_priceMax=0;
        }
         doc = $(this).parent().index();
        //价格点击
        if ($(this).parent().index()==2){
            var iDoc = $(this).children("i");
            //如果是所有价格
            if (iDoc.attr("class")=="p_all"){
                iDoc.removeClass("p_all").addClass("p_up");
                doc = 2;
                //    如果是价格升序,则价格从低到高排序
            }else if (iDoc.attr("class")=="p_up") {
                iDoc.removeClass("p_up").addClass("p_down");
                doc = 5;
                //如果是价格降序,则价格从高到低排序
            }else if (iDoc.attr("class")=="p_down"){
                iDoc.removeClass("p_down").addClass("p_all");
                doc = 0;
            }
        }
        $("#docx").val(doc);
        $(this).css("border-bottom","3px solid #cc5252").parent("dd").siblings().children().css("border-bottom"," 3px solid #ffffff");
        currentNo=1;
        $(".current_no").text(currentNo);   //设置当前页

        tblist(barndid,tbwatchname,watch_priceMin,watch_priceMax,currentNo,20,doc);

    }).first().css("border-bottom","3px solid #cc5252");

    //搜索按钮
    $("#search_btn").click(function(){
        //品牌id
         barndid = $(".cnm").attr("name");
        //性别
         grandsex = document.getElementsByClassName('grandid');
        //手表名称
         tbwatchname =  document.getElementById("key_word").value;
        //最低价
         watch_priceMin =  document.getElementById("minprice").value;
        //最高价
         watch_priceMax =  document.getElementById("maxprice").value;

        if(tbwatchname==""){
            tbwatchname='0';
        }if(watch_priceMin==""){
            watch_priceMin=0;
        }if(watch_priceMax==""){
            watch_priceMax=0;
        }
         doc = $(this).parent().index();
        //价格点击事件
        if ($(this).parent().index()==2){
            var iDoc = $(this).children("i");
            //如果是所有价格
            if (iDoc.attr("class")=="p_all"){
                iDoc.removeClass("p_all").addClass("p_up");
                doc = 2;
                //    如果是价格升序,则价格从低到高排序
            }else if (iDoc.attr("class")=="p_up") {
                iDoc.removeClass("p_up").addClass("p_down");
                doc =5;
                //如果是价格降序,则价格从高到低排序
            }else if (iDoc.attr("class")=="p_down"){
                iDoc.removeClass("p_down").addClass("p_all");
                doc = 0;
            }
        }
        currentNo=0;
        tblist(barndid,tbwatchname,watch_priceMin,watch_priceMax,currentNo,20,doc)
    }).first().css("border-bottom","3px solid #cc5252");


    function tblist(barndid,tbwatchname,watch_priceMin,watch_priceMax,current_no,page_size,doc){
        $.ajax({
            url:"/brand/click.xf/"+barndid +"/0"+"/"+ tbwatchname +"/"+ watch_priceMin +"/"+ watch_priceMax +"/"+current_no+"/"+page_size+"/"+doc,
            type:"POST",
            data:{},
            dataType:"json",
            success:function(pageInfo){
                var data = pageInfo.list;
                if(data!=null){
                    $("#goods_list").html('');
                }
                for(var i =0;i<data.length;i++){
                    var html="<li><a target=\"_blank\" href=\"/watch/doWatch.xf/"+data[i].watch_id+"\" class=\"s_goods_img\">\n" +
                        "                    <img class=\" lazy-load\" src=\""+data[i].watch_image+"\" alt=\"\">\n" +
                        "                </a><input type=\"hidden\"  id=\"abc\" value=\""+data[i].brand_id+"\">\n" +
                        "                    <div class=\"goods_txt\">\n" +
                        "                        <p style=\"text-align: center;\" class=\"red f14 bold\">\n" +
                        "                            <del class=\"c666\" >原价:"+data[i].watch_price+"</del>\n" +
                        "                            <span >好利时价:"+data[i].so_price+"</span></p>\n" +
                        "                        <a target=\"_blank\" href=\"\" class=\"s_goods_name elps2\" >\n" +
                        "                            <p >"+data[i].watch_name+"</p>\n" +
                        "                        </a>\n" +
                        "                        <div class=\"goods_sale\">\n" +
                        "                            <span  class=\"s_sale_num\" >销量:"+data[i].watch_sellcount+"</span>\n" +
                        "                            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>\n" +
                        "                            <a  href=\"/shopcart/doShop.xf/"+data[i].brand_id+"\"\n" +
                        "                                class=\"s_shop\" >"+data[i].watchBrand.country.c_name+data[i].watchBrand.brand_name+"官方旗舰店</a>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </li>";
                    // console.log(html);
                    $("#goods_list").append(html);
                    html ="";
                };
                $(".pageTotal").text(pageInfo.pages);         //设置总页数

            },
            error:function(){
                toastr.error("错误");
            }
        });
    }   



});


