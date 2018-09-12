<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

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
	width: 150px;
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

#searchDiv {
	position:relative;
	margin: 10px auto;
	width: 100px; 
	height: 30px
}

#depcount{
	float:left;
	width: 100px;
	height: 30px;
}
#searchDiv select {
	width: 100px;
	height: 30px;
}
#tiaozhuan{
float:left;
margin-left:-1px;
border: 1px solid #ddd;
width: 35px;
height: 34px;
}
</style>

<script type="text/javascript">
	$().ready(function() {

		$("#save").click(function(){
			var name=$("#boxname").val();
			window.location.href="project?type=add&name="+name;
		})

		//鼠标点击  增加class
		$("table tr").on("click", function() {
			$(this).toggleClass("focus");//设定当前行为选中行
		});

		//删除按钮
		$(".del").click(function() {

			//带有class行的数量
			var length = $("#dep .focus").length;

			//循环所有带有class的行 并加入ids
			var ids = new Array();
			$("#dep .focus").each(function() {
				ids.push($(this).data("id"));
			});
			if (length > 0) {
				var con = confirm("确认删除？");
						if (con) {
							window.location.href = "project?type=del&ids="+ ids;
						}
						} else {
							alert("请选中至少一行数据");
						}

			});

			$("table tr").dblclick(function() {

				$(this).unbind("dblclick");
				$(this).addClass("input");
				$(this).children('td:eq(1)').html('<input type="text" name="name" value='+ $(this).children('td:eq(1)').text()+ ' />')

			})

			$(".update").click(function() {

				var upd = new Array();
				var sum = 0;
				$("#dep .input").each(function(index,element) {
					sum = sum + 1;
					var id = $(this).data("id");
					var name = $(this).find("[name=name]").val();

					//一条数据内容
					var emp = {
						id : id,
						name : name,

					}
					upd.push(emp);

				})
				//转换成json字符串
				var js = JSON.stringify(upd);

		        //替换大括号
				js = js.replace(/{/g, "%7b");
				js = js.replace(/}/g, "%7d");

				window.location.href = "project?type=update&deps="+ js;
													
			});
			
			$(".search").click(function(){
				
				var depname = $("#depname").val();
			
				
				window.location.href="project?type=search&depname="+depname+"&nowpage="+${p.nowpage};
			})
			
			
			
			
	});
</script>

<script type="text/javascript">

$().ready(function(){
	var name=$("#depname").val();
	
	var str ="project?type=search&depname="+name+"&nowpage=";


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

}); 


</script>

<title>项目管理</title>
</head>
<body style="margin-top: 100px">
	<div>
		<table id="dep">
			<tr>
				<td>id</td>
				<td>name</td>
			
			</tr>

			<c:forEach items="${deps }" var="dep">
				<tr data-id="${dep.id }">
					<td>${dep.id }</td>
					<td>${dep.name }</td>
				
				</tr>
			</c:forEach>
		</table>

		<div id="searchDiv">
			<select id="depname">
					<option value="0">请选择部门</option>
				<c:forEach items="${lists }" var="list">
					<option value="${list.name}" <c:if test="${list.name==inputemp.name }">selected</c:if> >${list.name}</option>
				</c:forEach>
			</select>

		</div>

		<div style="text-align: center; margin: 20px auto; float: none;">


			<button class="add btn btn-default" data-toggle="modal"  data-target="#myModal">增加</button>
			<button class="del btn btn-default">删除</button>
			<button class="update btn btn-default">修改</button>
			<button class="search btn btn-default">搜索</button>
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
	
	<div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">增加项目</h4>
            </div>
            <div class="modal-body">
            	<input type="text"  id="boxname" style="width: 200px;height: 30px;margin-left: 180px;"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</div>

	</div>
</body>
</html>