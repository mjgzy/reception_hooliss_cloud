
$(function(){

    $(".floor .c").hide();
    $(".floor").click(function(){
        $(this).find(".c").slideToggle("slow");
        $(this).siblings().find(".c").slideUp("slow");
    }).hover(function () {
        $(this).find(".f_fixed").css("color","#BD2D30")
    },function () {
        $(this).find(".f_fixed").css("color","#333")
    }).find(".c ul li").hover(function () {
        $(this).addClass("menuLiHover").find("i").addClass("u__point").find("a").addClass("ccf0");
    },function () {
        $(this).removeClass("menuLiHover").find("i").removeClass("u__point").find("a").removeClass("ccf0");
    });
    $(".floor ul li").click(function (e) {
        const viewClass = $(this).find("a").attr("content");
        //console.log(viewClass);
        e.stopPropagation();        //取消事件冒泡
        $("#iframe").attr("src",viewClass+".html");
    });

    // $(".c ul li").mouseover(function(){
    //     $(this).css("background-color","#BD2D30");
    // });
    // $(".c ul li").mouseout(function () {
    //     $(this).css("background-color","#FFFFFF");
    // });

    //初始化显示订单列表,其余列表隐藏
    // $(".order_magger").siblings().hide();
});

//收货地址ajax处理
//查找用户地址
$(".addressys").click(function () {
    addrfind();
});
function addrfind() {
    $.ajax({
        "url" : "/userAddress/orderFindAddr.xf",
        "type" : "post",
        "dataType" : "json",
        "success" : function(dataeaddr) {
            orderaddr(dataeaddr);
        },
        "error" : function() {
            toastr.warning("服务器错误!");
        }
    });
}

function orderaddr(dataeaddr) {
    const each_tr = dataeaddr.result;
    if (dataeaddr.code==="500") {
        toastr.error(dataeaddr.message);
        return ;
    }
    $(".orderaddr").html("");       //清空子元素
    for (i=0;i<each_tr.length;i++){
        const str =
            "<tr class='c' style='height: 60px;'>"
            + "<td class='w120 h70'>"
            + "<a class='c0e7' target='_blank' href='#'>" + each_tr[i].race_name + "</a>"
            + "</td>"
            + "<td width='200px'>"
            + "<span>" + each_tr[i].province + '-' + each_tr[i].area + '-' + each_tr[i].city + "</span>"
            + "</td>"
            + "<td class='w75 h70'>"
            + "<font class='c333'>" + each_tr[i].street + "</font>"
            + "</td>"
            + "<td class='w87 adjust01'>"
            + "<div>" + each_tr[i].phone + "</div>"
            + "</td>"
            + "<td class='w130 adjust03'>"
            + "<a class='c0e7' target='_blank' href='#'>" + '修改' + "</a>"
            + "<span>" + '|' + "</span>"
            + "<a class='c0e7' name='addrdel' >" + '删除' + "</a>"
            + "<input name='inp' type='hidden' value='" + each_tr[i].info_id + "'/>"
            + "</td>"
            /*+"<td class='w87 h70'>"
            +"<font class='c888'>"+'确认中'+"</font>"
            +"</td>"*/
            + "</tr>";
        $(".orderaddr").append(str);
    }
    $("a[name='addrdel']").click(function () {
        if (confirm("确定要删除这个地址吗?")) {
            var addrid = $(this).next().val();
            addrdel(addrid);
        } else {
        }
    });
}

function addrdel(addrid) {
    $.ajax({
        "url" : "/order/orderaddrde.xf/"+addrid+"",
        "type" : "post",
        "dataType" : "json",
        "success" : function(addrdeldata) {
            $(".c").hide();
            addrfind();
        },
        "error" : function() {
            toastr.warning("服务器错误!");
        }

    });
}