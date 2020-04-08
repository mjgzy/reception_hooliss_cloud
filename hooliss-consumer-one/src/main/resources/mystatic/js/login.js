
var errMessage;

$(function() {
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

        $("#form1").hide()
        $("#form2").show();

    $(".phone_dynamic").click(function () {
        $("#form1").show();
        $("#form2").hide();
    })
    $(".phone_dynamic1").click(function () {
        $("#form2").show();
        $("#form1").hide();
    });
    var i = 60;
    var flag = true;
    var overTime;
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

});

