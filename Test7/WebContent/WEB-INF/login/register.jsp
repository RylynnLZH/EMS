 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/loginstyle.css" />
<script src="js/common.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<!--
背景图片自动更换
<script src="js/supersized.3.2.7.min.js"></script>
<script src="js/supersized-init.js"></script>
<script src="js/jquery.js"></script>
-->
<!--表单验证-->
<script src="js/jquery.validate.min.js?var1.14.0"></script>


<style>
body{
background-image: url(img/login.jpg);
}

</style>

<script type="text/javascript">
	$().ready(function(){
		
		
		
		$(".username").blur(function(){
			var username = $(this).val();	
			$.ajax({
				url:"user",
				type:"post",
				dataType:"text",
				data:{
					type:"rename",
					username:username
				},
				success:function(data){
					if(data=="true"){
						alert("用户名重复!");
						$("#submit").attr({"disabled":"disabled"});
					}else{
						$("#submit").removeAttr("disabled");
					}
				}
				
			})
		})
		
		
		
		$(".confirm_password").blur(function(){
			
			if($(this).val()!=$(".password").val()){
				alert("两次密码不一致，请重新输入！")
			}
			
			
		})
		
		
	})
	
	function myCheck(form){
		if(form.username.value==''){
			alert('用户名不能为空！');
			form.username.focus();
			return false;
		}
		if(form.password.value==''){
			alert('密码不能为空！');
			form.password.focus();
			return false;
		}
		if(form.password.value!=form.confirm_password.value) {
			alert('你两次输入的密码不一致，请重新输入！');
			form.confirm_password.focus();
			return false;
		}
			
		return true;
	}
	
		
</script> 

<title>注册账号</title>


</head>
<body>



<div class="register-container">
	<h1> 注册账号 </h1>
	
	<div class="connect">
		<p>Link the world. Share to world.</p>
	</div>
	
	<form action="user?type=doRegister" method="post" id="registerForm" onSubmit="return myCheck(this)">
		<div>
			<input type="text" name="username" class="username" placeholder="您的用户名" autocomplete="off"/>
		</div>
		<div>
			<input type="password" name="password" class="password" placeholder="输入密码" oncontextmenu="return false" onpaste="return false" />
		</div>
		<div>
			<input type="password" name="confirm_password" class="confirm_password" placeholder="再次输入密码" oncontextmenu="return false" onpaste="return false" />
		</div>
		
		
		<input type="hidden" name="type" value="doRegister" />
		<button id="submit" type="submit">注 册</button> 
	</form>
	<a href="user">
		<button type="button" class="register-tis">已经有账号？</button>
	</a>

</div>


</body>
</html>