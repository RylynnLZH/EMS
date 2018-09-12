<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/jquery.js" ></script>
<title>增加员工</title>


<link rel="stylesheet" href="css/bootstrap.css">
<style type="text/css">
#main{
	margin:0 auto;  
	margin-top: 50px;
	width: 200px;
	height: 300px;
	}
input[type="radio"]{
margin-left: 30px;
}
.btn{
width:100px;
margin-top: 10px;
margin-left: 45px;  
}
#show{
width: 200px;
height: 200px;
position: absolute;
left:900px;   
top:200px;

}
#show img{
width: 200px;
}
</style>

<script type="text/javascript">

$().ready(function(){
	 $('#photo').change(function () {
	        // 获取FileList的第一个元素
	   	var url = $(this)[0].files[0];
	        
	    var url1 = window.URL.createObjectURL(url);
	    
	     $("#show").append("<img src="+url1+">");
	    });
	
	
	
})

</script>

</head>
<body>

	<div id="show"></div>
	<div id="main">
		<form action="employee?type=add" method="post" class="form-horizontal" enctype="multipart/form-data">
		<label for="exampleInputEmail1">姓名:</label>
		<input type="text" name="name" class="form-control" placeholder="Name"/>	<br />
		
		
		<label for="exampleInputEmail1">性别:</label>
		<input type="radio" class="rad" name="sex" value="男" checked/>男	
		<input type="radio" class="rad" name="sex" value="女"/>女<br />	<br />
		
		<label for="exampleInputEmail1">年龄:</label>
		<input type="text" name="age"class="form-control" placeholder="Age"/>	<br />
		
		<label for="exampleInputEmail1">部门:</label>
		<select name="dep" class="form-control">
							<option></option>
								<c:forEach items="${deps}" var="dep">
							<option value="${dep.id }" >${dep.name }</option>
								</c:forEach>
		</select><br />
		
		<label for="exampleInputFile">文件选择</label>
		<input type="file" name="touxiang" id="photo"/>
		
		
		<input type="submit" name="sub" value="增加" class="btn btn-default"/>
		</form>
	
	
	</div>

</body>
</html>