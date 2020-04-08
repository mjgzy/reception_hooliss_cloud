var brand = $(".conditions:eq(0) b").find("i");       //品牌条件对象
var sex = $(".conditions:eq(2) b").find("i");         //性别条件对象
var key_word = $(".conditions:eq(3) b").find("i");    //关键字条件对象
var type_price = $(".conditions:eq(1) b").find("i");  //价格区间条件对象
var grade_id = 0;           //性别id
var  brand_id=0;
var  sex_id=0;
var key_word_id=0;
var type_price_id=0;
var ul_brand_id =  0;
var currentNo=0;  //当前页
var pageTotal=0;  //总页数
var condition=0;
//初始化条件变量
function initCondition(){
    ul_brand_id=parseInt( $("#brand_span").attr("name"));
    brand_id =isNaN(ul_brand_id)?0:ul_brand_id;
    sex_id = sex.text()!==""?sex.text():0;
    if (sex_id==="男士"){
        grade_id=1;
    } else if (sex_id==="女士"){
        grade_id=2;
    } else if (sex_id==="情侣"){
        grade_id=3;
    }
    key_word_id = key_word.text()!==""?key_word.text():0;
    type_price_id = type_price.text()!==""?type_price.text():0;
}
//排序请求
function paixu_ajax(){
    console.log("当前页数:"+currentNo);
    $.ajax({
        url:"/commodity/doPaixu.xf/"+key_word_id+"/"+grade_id+"/"+type_price_id+"/"+currentNo+"/"+page_size+"/"+brand_id+"/"+condition+"",
        dataType:"json",
        type:"post",
        success:function (data) {
            var each_list = data.result.content;       //遍历对象
            var doc1 = $("#goods_list");
            doc1.html("");      //清空元素
            console.log(data);
            console.log(each_list.length);
            for (var i=0;i<each_list.length;i++){
                var html = "<li ><a target=\"_blank\" href=\"/watch/doWatch.xf/"+each_list[i].watch_id+"\" class=\"s_goods_img\">\n" +
                    "         <img class=\" lazy-load\" src=\""+each_list[i].watch_image+"\"\n" +
                    "           alt=\""+each_list[i].watch_name+"\"> </a>\n" +
                    "           <div class=\"goods_txt\">\n" +
                    "           <p style=\"text-align: center;\" class=\"red f14 bold\">\n" +
                    "           <del class=\"c666\">市场价:￥"+each_list[i].watch_price+"</del>\n" +
                    "            好利时价:￥"+each_list[i].so_price+"\n" +
                    "           </p>\n" +
                    "            <a target=\"_blank\" href=\"\" class=\"s_goods_name elps2\">"+each_list[i].watch_name+"</a>\n" +
                    "           <div class=\"goods_sale\">\n" +
                    "            <em class=\"s_sale_num\">销量"+each_list[i].watch_sellcount+"</em>\n" +
                    "            <a target='_blank' th:href=\"/brand/doShop.xf/"+each_list[i].brand_id+"\" class=\"s_shop\">\n" +
                    "            <span> "+each_list[i].watchBrand.country.c_name+""+each_list[i].watchBrand.brand_name+"官方旗舰店</span></a>\n" +
                    "           </div>\n" +
                    "            </div></li>";
                doc1.append(html);          //添加
            }
            //回显总页数和当前页
            $(".current_no").text(currentNo);
            pageTotal=data.result.totalPages; //获取当前页
            $(".pageTotal").text(pageTotal);
        },
        error:function () {
            toastr.error("服务器错误，加载数据失败！");
        }
    });
}
$(function () {
    currentNo=parseInt($(".current_no").text());  //当前页
    pageTotal=parseInt($(".pageTotal").text());  //总页数
    initCondition();        //初始化条件值
    $(".srh_more_block").click(function(){
        if ($(".hide").css("display")==="none") {
            $(".hide").slideDown("slow");
            $(this).find(".btn_txt").text("收起");
        }else{
            $(".hide").slideUp("slow");
            $(this).find(".btn_txt").text("更多选项（表盘、表带、功能、防水等）");;
        }

    });
    $(".intended_for>li>span").click(function () {
        var grade_id = $(this).attr("name");
        var key_word = $(".key_word").val();    //获取搜索关键字
        $(this).parent().attr("href","/commodity/doOs.xf/").trigger();

    });
    //品牌点击事件
    $(".s_shop>span").click(function (event) {
        event.stopPropagation();    //  阻止事件冒泡
        $(this).parent().trigger();
    });
    //回显搜索词
    if (key_word.parent().parent().attr("style")==="display:inline-block"){
        $(".tIptTxt").removeClass("c999").val(key_word.text());
    }
    //筛选点击事件
    $(".conditions>b").click(function () {
        if ($(this).prev().text()==="品牌:"){
            brand_id=0;
        } else if ($(this).prev().text()==="适用人群:") {
           grade_id=0;
        }else if ($(this).prev().text()==="价格区间:"){
            type_price_id=0;
        } else if ($(this).prev().text()==="搜索词:"){
            key_word_id=0;
        }
        currentNo=0;
        $(this).parent().attr("href","/commodity/doOs.xf/"+key_word_id+"/"+grade_id+"/"+type_price_id+"/"+currentNo+"/"+page_size+"/"+brand_id+"/"+0+"").trigger();
    });
//    价格区间点击事件
    $(".price_between>.fl span").click(function () {
        type_price_id = $(this).text();
        currentNo=0;
        $(this).parent().attr("href","/commodity/doOs.xf/"+key_word_id+"/"+grade_id+"/"+type_price_id+"/"+currentNo+"/"+page_size+"/"+brand_id+"/"+0+"").trigger();
    });
    //适用人群点击事件
    $(".intended_for>.fl span").click(function () {
        var grade_name = $(this).attr("name");
        if (grade_name==="man"){
            grade_id=1;
        } else if (grade_name==="woman") {
            grade_id=2;
        }else if (grade_name==="lovers"){
            grade_id = 3;
        }
        currentNo=0;
        $(this).parent().attr("href","/commodity/doOs.xf/"+key_word_id+"/"+grade_id+"/"+type_price_id+"/"+currentNo+"/"+page_size+"/"+brand_id+"/"+0+"").trigger();
    });
//    品牌点击事件
    $(".brand_ul>li>a").click(function () {
        brand_id=parseInt($(this).attr("name"));    //品牌id
        currentNo=0;
        $(this).attr("href","/commodity/doOs.xf/"+key_word_id+"/"+grade_id+"/"+type_price_id+"/"+currentNo+"/"+page_size+"/"+brand_id+"/"+0+"").trigger();
    });
    //价格输入事件
    $(".price_btn").click(function () {
        var minprice = parseFloat($("input[name='minprice']").val());    //最小值
        var maxprice =parseFloat($("input[name='maxprice']").val());    //最大值
        if (minprice>maxprice||minprice<0.00||maxprice<0.00){
            toastr.warning("请输入合理的价格!");
            return false;
        }else{
            type_price_id=minprice+"-"+maxprice;
            currentNo=0;
            $(this).attr("href","/commodity/doOs.xf/"+key_word_id+"/"+grade_id+"/"+type_price_id+"/"+currentNo+"/"+page_size+"/"+brand_id+"/"+0+"").trigger();
        }
    });
    //排序点击事件
    $("#jiages").find("dd a").click(function(){
        $(this).parent().siblings().find("a").removeClass("paixu_style");
        $(this).addClass("paixu_style");
        //价格点击
        console.log($(this).parent().index());
        if ($(this).parent().index()===1){
            var iDoc = $(this).children("i");
            //如果是所有价格
            if (iDoc.attr("class")==="p_all"){
                iDoc.removeClass("p_all").addClass("p_up");
                condition=2;
                paixu_ajax();       //刷新数据
                //    如果是价格升序,则价格从低到高排序
            }else if (iDoc.attr("class")==="p_up") {
                iDoc.removeClass("p_up").addClass("p_down");
                condition=1;
                paixu_ajax();       //刷新数据
                //如果是价格降序,则价格从高到低排序
            }else if (iDoc.attr("class")==="p_down"){
                iDoc.removeClass("p_down").addClass("p_all");
                condition=0;
                paixu_ajax();       //刷新数据
            }
        }else{
            var avc=parseInt($(this).parent().index())+1;
            condition=avc;
            paixu_ajax();       //刷新数据
        }

    });
    //搜索框点击事件
    $(".c__search").click(function () {
        key_word_id = $(this).prev().val();
        currentNo=0;
        $(this).attr("href","/commodity/doOs.xf/"+key_word_id+"/"+grade_id+"/"+type_price_id+"/"+currentNo+"/"+page_size+"/"+brand_id+"/"+0+"");
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
            if (pageNum!==1){
                $(".page_nav .pre").show(); //上一页显示
            }else{
                $(".page_nav .next").hide(); //上一页显示
            }
            currentNo=pageNum;
            $(".current_no").text(currentNo);
            paixu_ajax();
        }
    });
    //如果只有1页
    if (currentNo===1){
        $(".page_nav>.pre,.page_nav>.next").attr("disabled","disabled");
    }
    //点击下一页
    $(".page_nav>.next").click(function () {
        if (currentNo === pageTotal||pageTotal===0) {
            toastr.error("当前为最后一页!");
            return;
        }
        currentNo+=1;       //当前页加1
        $(".current_no").text(currentNo);
        console.log(currentNo);
        paixu_ajax();
    });
    //点击上一页
    $(".page_nav>.pre").click(function () {
        if (currentNo === 1) {
            toastr.error("当前为第一页!");
            return;
        }
        currentNo-=1;       //当前页加1
        $(".current_no").text(currentNo);
        console.log(currentNo);
        paixu_ajax();
    });
});