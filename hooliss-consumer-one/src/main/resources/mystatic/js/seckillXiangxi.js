$(document).ready(function () {
    console.log("aaa");
    var startTime =new Date($(".start_time").text()) ;
    var end_time = new Date($(".end_time").text());
    var clearTime;
    // var targetDate = new Date(end_time.toLocaleDateString());
    // run(end_time);

    clearTime= setInterval(function () {
        oDate = new Date();
        console.log("当前时间:"+oDate.getTime());
        console.log("秒杀时间"+startTime.getTime());
        if (oDate.getTime()>startTime.getTime()&&oDate.getTime()<end_time.getTime()){

            $(".seckillStart>span").text("立即秒杀");
        }
        if (oDate.getTime()<startTime.getTime()){
            $(".seckillStart>span").text("即将开始");


        }
        if (oDate.getTime()>end_time.getTime()){
            $(".seckillStart>span").text("已经结束");



        }
    },1000);


    //秒杀点击事件
    $(".seckillStart").click(function () {
        if (user_id!==0){
            $(this).attr("href","/seckill/");
        }
    });
});

function secKillisOver(){
    var  watch_id = $(".watch_id").val();
    $.ajax({
        url:"/seckill/isover/"+watch_id,
        type:"post",
        dataType:"text",
        success:function (data) {
            if (data=="false"){
                toastr.error("该秒杀活动已结束!");
            }else{
                toastr.success("ok");
            }
        }

    });
}
// function run(enddate) {
//     getDate(enddate);
//     setInterval("getDate('" + enddate + "')", 500);
// }
//
// function getDate(enddate) {
//     var oDate = new Date(); //获取日期对象
//
//     var nowTime = oDate.getTime(); //现在的毫秒数
//     var enddate = new Date(enddate);
//     var targetTime = enddate.getTime(); // 截止时间的毫秒数
//     var second = Math.floor((targetTime - nowTime) / 1000); //截止时间距离现在的秒数
//
//     var day = Math.floor(second / 24 * 60 * 60); //整数部分代表的是天；一天有24*60*60=86400秒 ；
//     second = second % 86400; //余数代表剩下的秒数；
//     var hour = Math.floor(second / 3600); //整数部分代表小时；
//     second %= 3600; //余数代表 剩下的秒数；
//     var minute = Math.floor(second / 60);
//     second %= 60;
//     var spanH = $('.se-txt')[0];
//     var spanM = $('.se-txt')[1];
//     var spanS = $('.se-txt')[2];
//
//     spanH.innerHTML = tow(hour);
//     spanM.innerHTML = tow(minute);
//     spanS.innerHTML = tow(second);
// }
//
// function tow(n) {
//     return n >= 0 && n < 10 ? '0' + n : '' + n;
// }