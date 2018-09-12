<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel='stylesheet prefetch' href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/themes/smoothness/jquery-ui.css'>
<link rel='stylesheet' href='css/style.css'>
<script type="text/javascript" src="js.jquery.js"></script>
<style type="text/css">
#show{
	position:absolute;
	left:240px;
	top:100px;
	width:1270px;
	height: 590px;
	border: none;
	background: white; 
}

#logo{
	position: relative;
	float: left;
	width: 100%;
	height: 100px;
	background: #003366;
}
h1 {
	text-align: center;
	letter-spacing: 10px;
	color: #080808;
}
#username{
	width: 200px;
	height: 30px;
	position: absolute;
	bottom: 10px;
	right: 50px;
	color: white;
}
p{
	float:left;
}

#online,#count {
float:left;
margin: 0;
}
#online{
width: 20px;
height: 20px;

}
</style>

<script type="text/javascript">
var websocket = null;

//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
	
	websocket = new WebSocket("ws://192.168.0.168:8080/Test7/websocket");
} else {
	alert('没有建立websocket连接')
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
window.onbeforeunload = function() {
	websocket.close();
}

//连接成功建立的回调方法
websocket.onopen = function(event) {
	
}

//接收到消息的回调方法
websocket.onmessage = function(event) {
	$("#online").html(event.data);
	
}



//关闭连接
function closeWebSocket() {
	websocket.close();
}


</script>
<title>管理系统</title>
</head>
<body>
<div style="text-align:center;clear:both">
			<script src="/gg_bd_ad_720x90.js" type="text/javascript"></script>
			<script src="/follow.js" type="text/javascript"></script>
		</div>
		
		<div id="logo">
			<div>
				<h1>XXX管理系统</h1>
			</div>
			
			<div id="count">
				历史访问人数：${applicationScope.count}
			</div>
			<p>当前访问人数：</p>
			<div id="online">
				${applicationScope.online}
			</div>
			
			<div id="username">
				<p>${sessionScope.username}</p>
				<!--  <a href="index?type=exit">退出</a>-->
			</div>
			
			
		</div>
		
		<aside class="sidebar">
			<div id="leftside-navigation" class="nano">
				<ul class="nano-content">
					
					<li>
						<a href="" target=""><i class="fa fa-dashboard"></i><span>主页</span></a>
					</li>
					
					<!--员工-->
					<li class="sub-menu">
						<a href="javascript:void(0);"><i class="fa fa-cogs"></i><span>员工管理</span><i class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
							<li>
								<a href="employee" target="showBox">显示员工</a>
							</li>
							<li>
								<a href="employee?type=showAdd" target="showBox">新增员工</a>
							</li>
						</ul>
					</li>
					
					<!--部门-->
					<li class="sub-menu">
						<a href="javascript:void(0);"><i class="fa fa-table"></i><span>部门管理</span><i class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
							<li>
								<a href="department" target="showBox">显示部门</a>
							</li>
							<li>
								<a href="">新增部门</a>
							</li>
						</ul>
					</li>
					
					<!--项目-->
					<li class="sub-menu">
						<a href="javascript:void(0);"><i class="fa fa fa-tasks"></i><span>项目管理</span><i class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
							<li>
								<a href="project" target="showBox">显示项目</a>
							</li>
							<li>
								<a href="">新增项目</a>
							</li>
						</ul>
					</li>
					
					<!--绩效-->
					<li class="sub-menu">
						<a href="javascript:void(0);"><i class="fa fa-envelope"></i><span>绩效管理</span><i class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
							<li>
								<a href="score" target="showBox">显示绩效</a>
							</li>
							
						</ul>
					</li>
				</ul>
			</div>
		</aside>
		
		
		<script src='js/jquery.js'></script>
		<script src='js/jquery-ui.min.js'></script>
		<script src="js/index.js"></script>






<iframe src="" name="showBox" id="show"></iframe>




</body>
</html>