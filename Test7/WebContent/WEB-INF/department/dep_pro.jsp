<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.css">
<style type="text/css">
table {
	border-collapse: collapse;
	margin: 0 auto;
	text-align: center;
}

table td {
	border: 1px solid #cad9ea;
	color: #666;
	height: 25px;
	width:160px;
}

input {
	width: 100%;
	height: 100%;
	text-align: center;
}

table tr:nth-child(odd) {
	background: #fff;
}

table tr:nth-child(even) {
	background: #F5FAFA;
}

table tr.focus {
	background-color: #eee;
}

table tr.focus {
	background-color: #eee;
}

select{
	width:150px;
	height: 33px;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
$().ready(function () {
	
	//增加按钮
	$("#add").click(function () {
		var did = ${did};
		var pid = $("#proid").val();
		if(pid==""){
			alert("已经全部添加！")
		}else{
			window.location.href="deppro?type=add&did="+did+"&pid="+pid;
		}
		
	});
	
	//单击tr
	$("table tr").on("click", function() {
		$(this).parent().find("tr.focus").toggleClass("focus");//取消原先选中行
		$(this).toggleClass("focus");//设定当前行为选中行
	});
	
	//删除按钮
	$("#del").click(function () {
		var pid;
		$("#pro .focus").each(function() {
			pid=$(this).data("id");
		});
		var did = ${did};
		if(pid==null){
			alert("请选择要删除的项目")
		} else{
			window.location.href="deppro?type=del&did="+did+"&pid="+pid;
		}
		
	
	});
	
	
})

</script>
<title>部门-项目管理</title>
</head>
<body>

<div style="margin-top: 100px">

	<h2 style="text-align: center;">${dep.name}</h2>
	
	<table id="pro">
		<tr>
			<td>id</td>
			<td>project</td>
		</tr>
		
		<c:forEach items="${list }" var="li">
			<tr data-id="${li.pid}">
				<td>${li.pid }</td>
				<td>${li.pName }</td>
			</tr>
		</c:forEach>
		
	</table>

	<div style="margin: 20px auto;width: 280px;line-height:34px;height: 35px;">
		<select id="proid">
			<c:forEach items="${lies }" var="lie">
				<option value="${lie.id }">${lie.name}</option>
			</c:forEach>
			
				<option>
				</option>
		</select>
		
		<button id="add" class="btn btn-default">添加</button>
		<button id="del" class="btn btn-default">删除</button>
	
	</div>

</div>

</body>
</html>