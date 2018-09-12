<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.css">
<style type="text/css">


div .focus {
	background-color: #ccc;
	font-weight: bold;
}

table tr.focus {
	background-color: #eee;
	
} 
select{
width:150px;
height: 33px;
}

cho{
background: #666;
}

.sel{
 width: 138px;
 height: 30px;
 background: #eee;
 margin: 5px;
 float: left;
 text-align: center;
 line-height: 30px;
 font-size: 12px;
 user-select:none;
 border-radius:5px;
 box-shadow: 3px 3px 3px #A8A8A8	;
}

.sel:hover{
cursor: pointer;
box-shadow: 3px 3px 3px #888;
}

.btn{
margin: 3px;
}

p {
	position:absolute;
	bottom:0;
	left:70px;
	text-align: center;
	line-height: 50px;
	font-size: 35px;
	color:#C1CDCD;
	letter-spacing: 5px;
	 user-select:none;
	
}

#have,#nothave{
	width: 300px;
	height: 400px;
	border: 1px solid #ccc;
	position: relative;
	float: left;
	background: #F8F8F8;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
$().ready(function () {
	
	//增加按钮
	$("#add").click(function () {
		 
		var dps = new Array();
		var pids = new Array();
		$("#nothave .focus").each(function () {
			var did = ${did};
			var pid = $(this).data("id");
			pids.push(pid);
			var dp={
					did:did,
					pid:pid
				}
			dps.push(dp);
		})
	
		var js = JSON.stringify(dps);
		js = js.replace(/{/g,"%7b");
		js = js.replace(/}/g,"%7d");
		if(dps.length<1){
			alert("请选择项目！")
		}else{
			//window.location.href="deppro?type=add&did="+did+"&pid="+pid;
			$.ajax({
				url:"deppro",
				type:"post",
				data:{type:"add",
							dps:js}, 
				dataType:"text",
				success:function(data){
					if(data=="true"){
				
						$.each(pids,function(index,value){
					  
					 <c:forEach items="${pros}" var="pro">
					 	if (value==${pro.id}) { 
					 		var div = "<div class='sel' data-id=${pro.id}>${pro.id} | ${pro.name }</div>";
					 		$("#have").append(div);
					 	}
					 </c:forEach>
					  
				  })

				  $("#nothave .focus").each(function () {
								$(this).remove();
							}) 
						}
				}
			})
			
		}
		
		
	})
	
	//删除按钮
	$("#del").click(function () {
		 
		var dps = new Array();
		var pids = new Array();
		$("#have .focus").each(function () {
			var did = ${did};
			var pid = $(this).data("id");
			pids.push(pid);
			
			var dp={
					did:did,
					pid:pid
				}
			dps.push(dp);
			
		})
	
		var js = JSON.stringify(dps);
		
		js = js.replace(/{/g,"%7b");
		js = js.replace(/}/g,"%7d");
		
		if(dps.length<1){
			alert("请选择项目！")
		}else{
			
			$.ajax({
				url:"deppro",
				type:"post",
				data:{type:"del",
							dps:js}, 
			
				success:function(data){
						if(data=="true"){
						
						$.each(pids,function(index,value){
					  
					 <c:forEach items="${pros}" var="pro">
					 	if (value==${pro.id}) {    
					 		var div = "<div class='sel' data-id=${pro.id}>${pro.id} | ${pro.name }</div>";
					 		$("#nothave").append(div);
					 	}
					 </c:forEach>
					  
				  }) 

				  $("#have .focus").each(function () {
								$(this).remove();
							}) 
						}; 
				}
			})
			
		}
		 
		
	})
	
	//文档全部加载
	$(document).on("click", "#have div",function() {
		
		$(this).toggleClass("focus");//设定当前行为选中行
	});
	
	$(document).on("click","#nothave div", function() {
		
		$(this).toggleClass("focus");//设定当前行为选中行
	});
	
});

</script>
<title>部门-项目管理</title>
</head>
<body>
<h1 style="text-align: center;">${dep.name}</h1>

<div style="width: 680px;height: 410px;position: relative;margin: 20px auto;">

	<div id="have">
		
		<c:forEach items="${list}" var="li">
			<div class="sel" data-id="${li.pid}">
				${li.pid } | ${li.pName }
			</div>
		</c:forEach>
		
		
		<p>已选项目</p>
		
	</div>
	
	<div style="width: 80px;height: 150px;position: relative;float: left;margin-top: 150px;">
		<button id="add" class="btn btn-default"><< 添加</button>
		<button id="del" class="btn btn-default">删除 >></button>
	</div>
	
	<div id="nothave">
		<c:forEach items="${lies }" var="lis">
			<div class="sel" data-id="${lis.id}">
				${lis.id } | ${lis.name }
			</div>
		</c:forEach>
	
		
		<p>未选项目</p>
		
	</div>
	
</div>



</body>
</html>