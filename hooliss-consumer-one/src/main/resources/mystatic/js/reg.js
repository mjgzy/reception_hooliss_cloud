var errMessage;
	//密码验证
	function pwd() {
		var pwds = /^[a-zA-Z0-9]{6,10}$/;
		var pwd = $("#textpwd").val();
		if (pwd == "") {
			$("#password2").html("密码不能为空");
			$("#password2").css("color", "red");
			return false;
		}
		if (pwds.test(pwd) == false) {
			$("#password2").html("密码格式不正确");
			$("#password2").css("color", "red");
			return false;
		}
		$("#password2").html("✔");
		$("#password2").css("color", "#0066FF");
		return true;
	}
	//确认密码验证登录
	function cpwd() {
		var pwd = $("#pwd").val();
		var cpwd = $("#textnpwd").val();
		if (cpwd == "") {
			$("#confirm_password2").html("密码不能为空");
			$("#confirm_password2").css("color", "red");
			return false;
		}
		if (cpwd != pwd) {
			$("#confirm_password2").html("密码输入不一致");
			$("#confirm_password2").css("color", "red");
			return false;
		}
		$("#confirm_password2").html("✔");
		$("#confirm_password2").css("color", "#0066FF");
		return true;
	}
	//电子邮箱验证
	function emial() {
		var rpwd = $("#textemail").val();
		if (rpwd == "") {
			$("#email2").html("email不能为空");
			$("#email2").css("color", "red");
			return false;
		}
		if (rpwd.indexOf("@") == -1) {
			$("#email2").html("email输入的格式不正确，必须包含@符号");
			$("#email2").css("color", "red");
			return false;
		}
		if (rpwd.indexOf(".") == -1) {
			$("#email2").html("email输入的格式不正确，必须包含.符号");
			$("#email2").css("color", "red");
			return false;
		}
		if (rpwd.indexOf("@") > rpwd.indexOf(".")) {
			$("#email2").html("email输入的格式不正确，@符号必须在.符号前面");
			$("#email2").css("color", "red");
			return false;
		}
		$("#email2").html("✔");
		$("#email2").css("color", "#0066FF");
		return true;
	}
	//手机号码验证
	function ipho() {
		var ipho = $("#textphone").val();
		var iphos = /^1\d{10}$/;
		if (ipho == "") {
			$("#username2").html("手机号码不能为空");
			$("#username2").css("color", "red");
			return false;
		}
		if (iphos.test(ipho) == false) {
			$("#username2").html("手机号码输入格式错误");
			$("#username2").css("color", "red");
			return false;
		}
		$("#username2").html("✔");
		$("#username2").css("color", "#0066FF");
		return true;
	}

	$(function() {
		$("#textname").blur(function () {
			if ($(this).val()!=null && $(this).val()!=''){
				$.ajax({
					url:"/user/yan.xf",
					type:"get",
					data:{"textname":$(this).val()},
					dataType:"text",
					success:function(result){
						if(result=="false"){
							toastr.error("该用户已被注册");
							// $("#textname").focus();		//获取焦点
							$("#form1").submit(false);
						}else{
							toastr.success("该用户可以注册");
							$("#form1").submit(true);
						}
					},
					error:function(){
						toastr.warning("内部有误");
						$("#form1").submit(false);

					}
				});
			}
		});
		$("#form1").submit(function() {
			var name = $("#textname").val();
			if (name == null || name == "") {
				toastr.warning("用户不能为空");
				return false;
			}
			var pwds = /^[a-zA-Z0-9]{6,10}$/;
			var pwd=$("#textpwd").val();
			if(pwds.test(pwd)==false){
				toastr.warning("用户密码要为6位数");
				return false;
			}
			if (pwd == "") {
				toastr.warning("密码不能为空");

				return false;
			}

			var npwd=$("#textnpwd").val();
			if (npwd == "") {
				toastr.warning("密码不能为空");

				return false;
			}
			if(npwd!=pwd){
				toastr.warning("两次密码不一致");
				return false;
			}
			var phone=/^1\d{10}$/;
			var u_phone=$("#textphone").val();
			if(phone.test(u_phone)==false){
				toastr.warning("手机号输入错误");
				return false;
			}
			if(u_phone == ""){
				toastr.warning("手机号不能为空不能为空");
				return false;
			}
			var email = $("#textemail").val();
			if (email == "") {
				toastr.warning("email不能为空");

				return false;
			}
			if (email.indexOf("@") == -1) {
				toastr.warning("email输入的格式不正确，必须包含@符号");
				return false;
			}
			if (email.indexOf(".") == -1) {
				toastr.warning("email输入的格式不正确，必须包含.符号");
				return false;
			}
			if (email.indexOf("@") > rpwd.indexOf(".")) {
				toastr.warning.html("email输入的格式不正确，@符号必须在.符号前面");
				return false;
			}


		});
		errMessage=$("#errMessage").val();
		if(errMessage!='' && errMessage!=null){
			toastr.error(errMessage);
		}
		var i = 60;
		var flag = true;
		var overTime;
		function flagFunc(){
			if(i==1){

				console.log( $("#get_code2>span").text());
				clearTimeout(overTime);
				$("#get_code2>span").text("获取验证码");
				flag=true;
				i=60;
				return ;
			}
			i--;
			$("#get_code2>span").text(i+"秒后重试");
		}
		function commback(ppq){
			if(ppq.result.length==6){
				flag=false;
				overTime= setInterval(flagFunc,1000);
			}
		}
		$("#get_code2>span").click(function(){
			var phone=$("#textphone").val();
			if(!flag){
				return false;
			}
			var url ="/user/getCode/"+phone ;
			$.ajax({
				url:"/user/phoneOnlyVerification.xf",
				data:"phone="+phone,
				type:"post",
				dataType:"json",
				success:function (data) {
					if (!data.result){
                        toastr.error("该用户已注册,请直接登录");
                        setTimeout(function () {
                            location.href="/user/login.xf";
                        },2000);
					}else{
                        $.ajax({
                            url:url,
                            type:"post",
                            dataType:"json",
                            success:function (ppq) {
                                var message=$.parseJSON(ppq.data);
                                if(message.Message!=="OK"){
                                    toastr.error("发送失败:"+message.Message);
                                    ppq.result="error";
                                }else{
                                    toastr.success("发送成功!");
                                }
                                commback(ppq);
                            }
                        });
					}
                },
				error:function(){
					toastr.error("校验手机号失败!");
				}
			});
		});
	});
