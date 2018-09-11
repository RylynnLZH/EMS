<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.css">
<script type="text/javascript" src="js/jquery.js"></script>
<style type="text/css">
#tiaozhuan{
float:left;
margin-left:-1px;
border: 1px solid #ddd;
width: 35px;
height: 34px;
}
table {
	border-collapse: collapse;
	margin: 0 auto;
	text-align: center;
}

table td {
	border: 1px solid #cad9ea;
	color: #666;
	height: 25px;
	width: 150px;
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
input {
width:120px; 
height:30px;
float:left;
margin: 0 5px;
}
select {
width:120px; 
height:30px;
float:left;
margin: 0 5px; 
}
#search{
padding: 3px 20px;
margin-left: 20px;
}
#save{
width: 110px;
height: 45;
margin: 10px auto;
}
.update{
padding:10px 40px; 
}
</style>

<script type="text/javascript">

$().ready(function(){
	
	$("#dname").change(function(){
		var did = $(this).val();
		
			$.ajax({
				url:"score",
				type:"post",
				dataType:"json",
				data:{
					type:"searchByDep",
					did:did 
				},
				success:function(data){
					$("#pname").empty();
					
					$("#pname").append("<option>请选择项目</option>");
					
					$.each(data,function(n,value){
						
						$("#pname").append("<option>"+value.pName+"</option>"); 
						
					})
						
				}
				
			})
		
		
	})
	
	
	$("#search").click(function(){
		var ename=$("#ename").val();
		var did=$("#dname").val();
		var pname=$("#pname").val();
		var value=$("#value").val();
		var grade=$("#grade").val();
		
		
		window.location.href="score?type=search&ename="+ename+"&did="+did+"&pname="+pname+"&value="+value+"&grade="+grade+"&nowpage="+1;
	})
	
	var ename=$("#ename").val();
	var did=$("#dname").val();
	var pname=$("#pname").val();
	var value=$("#value").val();
	var grade=$("#grade").val();

	
	
	var str ="score?type=search&ename="+ename+"&did="+did+"&pname="+pname+"&value="+value+"&grade="+grade+"&nowpage=";
	$("#test").click(function(){
		alert(str);
	})

	$("#next").click(function(){ 
	
		if(${p.nowpage}>=${p.allpage}){
			alert("已是最后一页！");
		}else{  
			window.location.href=str+${p.nowpage+1};
		
		$(this).addClass("active");

		}


	});

	$("#last").click(function(){

		if(${p.nowpage}<=1){ 
			alert("这是第一页！！！上面没有了！！！");
		}else{ 
			window.location.href=str+${p.nowpage-1};
			$(this).addClass("active");
		}
	 
	})

	$("#shou").click(function(){

		window.location.href=str+1;


	})

	$("#mo").click(function(){

		window.location.href=str+${p.allpage};

	})



	$("#jump").click(function(){
	var num=$("#tiaozhuan").val();
		if(num>0&&num<=${p.allpage}){
			window.location.href=str+num; 
		}else {
			alert("没有这页");
		}
	}) 

	$(".pageindex").click(function(){
		var index = $(this).children("a").text();
		window.location.href=str+index;
	}); 
	
	
	$("table tr").dblclick(function(){
		$(this).addClass("sel");
		$(this).unbind("dblclick");
		$(this).children('td:eq(3)').html('<input  style="width:100%;height:100%;margin:0;" type="text" name="value" value='+ $(this).children('td:eq(3)').text() +' >')
	
			var id = $(this).data("id");
			var ename = $(this).children().eq(0).data("eid");
			var dname = $(this).children().eq(1).data("did");
			var pname = $(this).children().eq(2).data("pid");
		
			var values = new Array();
			
			$("input").blur(function(){
			var value = $(this).val();
			
			var value={
					id:id,
					value:value,
					emp:{
						id:ename,
						dep:{
							id:dname
						}
					},
					
					pro:{
						id:pname
					}
			};
			
			values.push(value);
			
			var js = encodeURIComponent(JSON.stringify(values));
			
			window.location.href="score?type=update&values="+js;

			
			 })
	
	});
	
	$(".update").click(function(){
		var values = new Array();
		$(".sel").each(function(index,element){
			
			var value = $(this).find("[name=value]").val();
			var id = $(this).data("id");
			var ename = $(this).children().eq(0).data("eid");
			var dname = $(this).children().eq(1).data("did");
			var pname = $(this).children().eq(2).data("pid");
			
			
			var value={
					id:id,
					value:value,
					emp:{
						id:ename,
						dep:{
							id:dname
						}
					},
					
					pro:{
						id:pname
					}
			}
			
			
			values.push(value);
		})
		
		var js = encodeURIComponent(JSON.stringify(values));
		//var js=	JSON.stringify(values);
		
		
		if(values.length<1){
			alert("请选择数据!")
		}else{
			window.location.href="score?type=update&values="+js;
		}
		
	})
	

})   



</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>绩效管理</title>
</head>
<body>

	
	<div style="width: 750px;margin: 10px auto">
	<form id="show" action="">
		<input id="ename" type="text" value="${sc.emp.name}" />
		
		<select id="dname" >
				<option value="0">请选择部门</option>
			<c:forEach items="${alldeps}" var="dep">
				<option value="${dep.id}" <c:if test="${sc.emp.dep.id==dep.id}">selected</c:if> >${dep.name}</option>
			</c:forEach>
		</select>
		
		<select id="pname">
				<option>请选择项目</option>
			<c:forEach items="${allpros}" var="pro">
				<option <c:if test="${sc.pro.name==pro.pName}">selected</c:if> >${pro.pName}</option>
			</c:forEach>
		</select>
		
		<input id="value" type="text" value="${sc.value}"/>
		
		<select id="grade"> 
				<option>请选择等级</option>
			<c:forEach items="${allgrades}" var="gra">
				<option <c:if test="${sc.grade==gra.name}">selected</c:if> >${gra.name}</option>
			</c:forEach>
		</select>
		</form>
	<button class="btn btn-default" id="search">搜索</button>
	</div>

	<div>
		<table>

			<tr>
				<td>用户名</td>
				<td>部门</td>
				<td>项目</td>
				<td>绩效</td>
				<td>等级</td>
			</tr>

			<c:forEach items="${scos}" var="sco">
				<tr data-id="${sco.id}">
					
					<td data-eid="${sco.emp.id}">${sco.emp.name}</td>
					<td data-did="${sco.emp.dep.id}">${sco.emp.dep.name}</td>
					<td data-pid="${sco.pro.id}">${sco.pro.name}</td>
					<td>${sco.value}</td>
					<td>${sco.grade}</td>
				</tr>
			</c:forEach>

		</table>

	</div>
			<div id="save">
				<button class="update btn btn-default">保存</button>
			</div>
			
	<div style="text-align: center;">
	
	<ul class="pagination">
		<li <c:if test="${p.nowpage==1 }"> class="disabled"</c:if>><a id="shou">首页</a></li>
		<li <c:if test="${p.nowpage==1 }"> class="disabled"</c:if>><a id="last">上一页</a></li>
		<c:forEach begin="${p.start}" end="${p.allpage>p.allstart?p.start+p.allstart-1:p.allpage}" varStatus="status">
		<li  <c:if test="${p.nowpage== status.index}"  > class="active"</c:if> class="pageindex">
	
			<a class="index" value="${status.index}">${status.index}</a>
				</li></c:forEach>
		<li><input id="tiaozhuan" type="text"/></li>
		<li><a id="jump">跳转</a></li>
		<li <c:if test="${p.nowpage==p.allpage }"> class="disabled"</c:if>><a id="next">下一页</a></li>
		<li<c:if test="${p.nowpage==p.allpage }"> class="disabled"</c:if>><a id="mo">末页</a></li>
		
	</ul>
	</div>
</body>
</html>