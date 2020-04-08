//初始化显示订单信息
let zt = 0;
let sj = 0;
let order_id = 0;
// $("#element", document).doStuff();
function initVarValue(){
    zt=$("select[name='order_status']").val()===''?0:$("select[name='order_status']").val();
    sj=$("select[name='datetime']").val()===''?0:$("select[name='datetime']").val();
    order_id=$("input[name='order_id']").val()===''?0:$("input[name='order_id']").val();
}
function initColumn() {
    const columns = [
        {align: 'center',width:20, title: "序号", halign: 'center', formatter: index},
        {
            align: 'center',width:60, field: 'order_id', title: '订单号',
            formatter: function (value) {
                return value;
            }
        },
        {align: 'center',width:120, field: 'lt.watch_name', title: '订单详情'},
        {align: 'center',width:40, field: 'watch_receinfo.race_name', title: '收货人姓名'},
        {align: 'center',width:40, field: 'order_price', title: '订单金额'},
        {align: 'center',width:60, field: 'order_date', title: '下单时间'},
        {align: 'center',width:35, field: 'order_status.statudName', title: '订单状态'},
        {
            align: 'center',width:50, field: 'statu', title: '操作',
            formatter: function (value, row, index) {
                var model = JSON.stringify(row);
                var btn = "<button class=\'btn btn-danger\' onclick='del($(this))' style=\'width:50px;border-radius: 6px;\' title=\'删除\'>删除</button>";
                if(row.order_status.statusId===1){
                    btn += "<span>&nbsp;&nbsp;</span><button onclick='pay($(this))' class='btn btn-success' title='支付' >支付</button>    ";
                }
                return btn;
            }
        }
    ];
    return columns;
}
function index(value,row,index){
    return index+1;
}
function pay(doc) {
    const order_id =doc.parent().parent().find("td:eq(1)").text();
    if(order_id!==null||order_id!==''){
        //获取订单号
        windows.open("/order/findOrderById?order_id="+order_id);
    }else{
        toastr.error("订单获取失败!");
    }
}
function del(doc){
    const order_id = doc.parent().parent().find("td:eq(1)").text();
    if(order_id!==null||order_id!==''){
        //获取订单号
        CommLayer.comfirm("确认删除这条订单吗？",
            function(){
                CommAjax.LoadAjaxCallBack("/order/delOrderById",{order_id:order_id},
                    function(res){
                        console.log(res);
                        if(res.code==='200'&&res.result===true){
                            $(".table").bootstrapTable("refresh");
                        }
                        CommLayer.msg(res.message);
                    },
                    null);
            }
        );
    }else{
        toastr.error("订单获取失败!");
    }
}
initVarValue();

$(function () {
    var defaultColunms = initColumn(true);
    var table = new BSTable("table","/order/orderFind.xf", defaultColunms);
    table.responseHandler = function(result){
        //获取数据完成后可在此处处理一些逻辑问题
        return result;
    };
    //分页模式：server后端分页，client前端分页
    table.setPaginationType("server");
    //查询时的额外参数
    table.queryParams = function(params){
        initVarValue();     //更新条件值
        console.log("params");
        console.log(params);
        const param = {
            date_type: sj,
            order_status_id: zt,
            order_id: order_id,
            offset: params.offset, // 页面大小
            limit: params.limit
        };
        return $.extend(params,param);
    };
    table.init();
    $(".fixed-table-toolbar").append($(".left,.right").detach());
    //时间的下拉框的值
    $("select[name='datetime']").change(function(){
        table.refresh($(this).val());
    });
    //状态的下拉框的值
    $("select[name='order_status']").change(function(){
        table.refresh($(this).val());
    });
    $("button[name='selectbut']").click(function () {
        table.refresh();
    });
});