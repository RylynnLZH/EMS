<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>员工信息表</title>
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
	width: 80px;
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
	width: 100%;
	height: 100%;
	text-align: center;
}
select{
width: 100%;
	height: 100%;
	text-align: center;
}

.up {
	background-color: red;
}
#tiaozhuan{
float:left;
margin-left:-1px;
border: 1px solid #ddd;
width: 35px;
height: 34px;
}
#search{

margin:0 auto;
text-align:center;
width:450px;
height: 25px; 

}
#search input {
	text-align:center;
	width:100px;
	float: left;
	
}
#search select {
	text-align:center;
	width: 100px;
	float: left;
}
img{
width: 25px;

}
#pic{
width: 200px;
height: 200px; 
position: absolute;

  
}
#pic img{ 
width: 200px;
}
</style>
<link rel="stylesheet" href="css/bootstrap.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">

	$().ready(function() {

		//增加按钮 
		$(".add").click(function() {
			window.location.href = "add?type=showAdd";
		})

		//鼠标点击  增加class
		$("table tr").on("click", function() {
			//$(this).parent().find("tr.focus").toggleClass("focus");//取消原先选中行
			$(this).toggleClass("focus");//设定当前行为选中行
			//var tr = $(this).closest('tr'), td = tr.find('td');
			//var id1 = tr[0].rowIndex;
			//var id = td.eq(0).html();

		});

		//删除按钮
		$(".del").click(function() {
			
			//带有class行的数量
			var length = $("#emp .focus").length;

			//循环所有带有class的行 并加入ids
			var ids = new Array();
			$("#emp .focus").each(function() {
				ids.push($(this).data("id"));
			});
			if (length > 0) {
				var con = confirm("确认删除？");
				if (con) {
					window.location.href = "del?type=del&ids=" + ids;
				}
			} else {
				alert("请选中至少一行数据");
			}

		});

		//更新按钮
		$(".update").click(function() {
			var ids = new Array();
			var length = $("#emp .focus").length;

			$("#emp .focus").each(function() {
				ids.push($(this).data("id"));
			});
			
			if (length > 0) {
				window.location.href = "employee?type=showUpdate&ids=" + ids;
			} else {
				alert("请选中至少一行数据");
			}
		});
		
		
		//双击行 添加html事件
		$("table tr").dblclick(function(){
			
			//取消双击事件
			//$(this).unbind("dblclick");
			
			//如果存在多余一个 不重复执行
			if($(this).find("input").length>1){
				return false;
			}
			
			//判断当前值是否相等 加上默认选择框
			var sexhtml="";
			if($(this).children('td:eq(2)').text()=="男"){
				sexhtml='<select name="sex"><option value ="男" selected="selected">男</option><option value ="女" >女</option></select>';
			}else{
						sexhtml='<select name="sex"><option value ="男" >男</option><option value ="女" selected="selected">女</option></select>';
			}

			var dephtml="";
			
			dephtml='<select name="dep_Id"><option value="0"></option><c:forEach items="${deps}" var="dep"><option value="${dep.id}" >${dep.name}</option></c:forEach></select>';
			
				//添加输入框 并将原先数据填进去
			$(this).addClass("input");
			$(this).children('td:eq(1)').html('<input type="text" name="name" value='+ $(this).children('td:eq(1)').text() +' />')
			$(this).children('td:eq(2)').html(sexhtml);
			$(this).children('td:eq(3)').html('<input type="text" name="age" value="'+ $(this).children('td:eq(3)').text() +'" />')
			$(this).children('td:eq(4)').html(dephtml);
			$(this).children('td:eq(4)').find("[name=dep_Id]").prop("value",$(this).children('td:eq(4)').data("depid")); 
			})
			
	
		$(".update1").click(function(){
			
			var upd = new Array();
			var sum=0;
		$("#emp .input").each(function(index,element){
			sum=sum+1;
			var id = $(this).data("id");
			var name = $(this).find("[name=name]").val();
			var sex = $(this).find("[name=sex]").val();
			var age = $(this).find("[name=age]").val();
			var dep_id = $(this).find("[name=dep_Id]").val();
			
			//一条数据内容
			var emp={
					id:id,
					name:name,
					sex:sex,
					age:age,
					d_id:dep_id
			}
			upd.push(emp);
			
		})
		//转换成json字符串
			var js = encodeURIComponent(JSON.stringify(upd));
		
		//替换大括号
			
		window.location.href = "employee?type=update&emps=" + js;
		})
		
		$(".search_btn").click(function() {
			var name=$("#search_name").val();
			var sex=$("#search_sex").val();
			var age=$("#search_age").val();
			var depid=$("#search_dep").val();
			window.location.href="employee?type=search&name="+name+"&sex="+sex+"&age="+age+"&depid="+depid;
			
		})
		
		
	})
</script>
<script type="text/javascript">
$().ready(function(){
	
	var name=$("#search_name").val();
	var sex=$("#search_sex").val();
	var age=$("#search_age").val();
	var depid=$("#search_dep").val();
	
	var str ="employee?type=search&name="+name+"&sex="+sex+"&age="+age+"&depid="+depid+"&nowpage=";
	
	
	
	$("#next").click(function(){
	
			if(${p.nowpage}>=${p.allpage}){
				alert("已是最后一页！");
			}else{ 
				window.location.href=str+${p.nowpage+1};
				$(this).addClass("active");
			}
		})
	
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
	})
	
	
	$("#emp img").mouseover(function(event){
		
		var src = $(this)[0].src;
		$("#pic").css({left:event.pageX+20,top:event.pageY});
		$("#pic").append("<img src="+src+">").fadeIn(200);
	})
	
	$("#emp img").mouseleave(function(){
		$("#pic").empty().fadeOut(200);
	});
	
});
</script>

</head>
<body>
	<table style="margin-top: 100px" id="emp">
		<tr> 
		
			<td>id</td>
			<td>name</td>
			<td>sex</td>
			<td>age</td>
			<td>department</td>
			<td>img</td>

		</tr>
		
		<c:forEach items="${emps}" var ="emp">
			
			<tr data-id="${emp.id }">
				<td>${emp.id }</td>
				<td>${emp.name }</td>
				<td>${emp.sex }</td>
				<td>${emp.age }</td>
				<td data-depId="${emp.dep.id}">${emp.dep.name }</td>
				<td><c:if test="${not empty emp.img}"><img src="img/${emp.img}"></c:if></td> 
				
				<input type="hidden" name="id" value="${emp.id }" />
			</tr>

		</c:forEach>
	</table>
	
	<div id="pic">
	
	
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
	<div id="search">
	<input type="text" placeholder="姓名" id="search_name" value=${inputemp.name }>
	<select  id="search_sex">
		<option>请选择性别</option>
		<option value="男"  <c:if test="${inputemp.sex  eq'男' }"> selected </c:if>>男</option>
		<option value="女" <c:if test="${inputemp.sex eq'女'}"> selected </c:if>>女</option>
	</select >
	<input type="text" placeholder="年龄" id="search_age"  value=${inputemp.age!=-1?inputemp.age:'' }>
	<select id="search_dep">
		<option>请选择部门</option>
		<c:forEach items="${deps}" var="dep">
			<option value="${dep.id }" <c:if test="${dep.id==inputemp.d_id }">selected</c:if> >${dep.name }</option>
		</c:forEach>
	</select>
	
	</div>
	
	
	<div style="text-align: center; margin: 20px auto; float: none;">

		<button class="add btn btn-default">增加</button>
		<button class="update btn btn-default">修改</button>
		<button class="update1 btn btn-default">修改1</button>
		<button class="del btn btn-default">删除</button>
		<button class="search_btn btn btn-default">搜索</button>
	</div>

</body>
</html>