<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>请登陆</title>
<link rel="stylesheet" href="css/loginstyle.css" />
<style type="text/css">
body{
background-image: url(img/login.jpg);
}
#form{
height: 300px; 
}
.mylog{ 
	width: 270px;
	height: 42px;
	line-height: 42px;
	color:white;
	padding: 0 15px;
	border: none;
	background: rgba(45, 45, 45, 0.33);
	border-radius: 6px;
	
}

#loginBtn{
background: rgba(6, 127, 228, 0.71);

}

</style>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">

if(self!=top){
	top.lacation="user";
}


	$().ready(function(){
		
		$("#loginBtn").click(function(){
			
			$.ajax({
				url:"user?type=doLogin",
				type: "POST",
		        dataType: "text",
		        data:$("#form").serialize(),
		        success: function(data){
		        	if(data=="true"){
		        		location.href="index";
		        	}else{
		        		alert("用户名或密码错误,请重新输入!");
		        	}
		        
		        }
				
			})
		})
		
		
		$("#image").click(function(){
			
			$(this).attr("src","user?type=randomImage&"+Math.random());
		})
		
		
	})
	
	
	
</script>
</head>
<body>
<div class="login-container">
	<form id="form">
		<input type="hidden" name="type" value="doLogin" />
        <div style="color:white">
        	<h2>请 登 录</h2> 
        </div>
                <br />
        <div>
           <input type="text" name="username" class="username" value="${userName }" placeholder="请输入账户名" autocomplete="off"/>
        </div>
               	 <br />
        <div>
           <input type="password" name="password" class="password" placeholder="请输入密码" oncontextmenu="return false" onpaste="return false" />
        </div>
               	 <br />
        
        <div id="yanzheng">
            <input type="text" name="random" placeholder="验证码" id="yz"/> 
        </div> 
         	  	 <br />
        <div style="position: absolute;top:190px;right: -70px;">  
           <img id="image" src="user?type=randomImage" /> 
        </div>
            
        <div>
          <button type="button" id="loginBtn" class="mylog">登录</button>
        </div>
    
        </div>
    </form>
        <a href="user?type=showRegister">
			<button type="button" class="mylog">还有没有账号？</button>
		</a>
</div>
</body>
</html>