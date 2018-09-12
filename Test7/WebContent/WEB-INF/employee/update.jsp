<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="bean.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>更新员工</title>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	
	$().ready(function(){
		var upd = new Array();
		$("#save").click(function(){
			
			//遍历每个emp表单 将每个数据存到字符串
			$(".emp").each(function(index,element){
				var id = $(this).find("[name=id]").val();
				var name = $(this).find("[name=name]").val();
				var sex = $(this).find("[name=sex]:checked").val();
				var age = $(this).find("[name=age]").val();
				var d_id = $(this).find("[name=dep]").val();
				
				var emp={
						id:id,
						name:name,
						sex:sex,
						age:age,
						d_id:d_id
				}
				upd.push(emp);
				
			});
			

			var js = encodeURIComponent(JSON.stringify(upd));	
			
			window.location.href="employee?type=update&emps="+js;
		})
	})
	
	
	
</script>
<link rel="stylesheet" href="css/bootstrap.css">

</head>
<body>

	<div>
	<c:forEach items="${emps }" var="emp">
	
		<form  method="post" class="emp" >
		
			姓名：<input type="text" name="name" value="${emp.name }" /> <br /> 
			性别：<input type="radio" name="sex"  value="男" <c:if test="${emp.sex=='男' }"> checked</c:if>  />男
					<input type="radio" name="sex"  value="女"  <c:if test="${emp.sex=='女' }"> checked</c:if> />女 <br /> 
			年龄：<input type="text" name="age"  value="${emp.age }" /> <br /> 
				  <input type="hidden" name="id" value="${emp.id }" /> <br /> 
			部门:<select name="dep">
							<option></option>
								<c:forEach items="${deps}" var="dep">
							
							<option value="${dep.id }" <c:if test="${emp.dep.id == dep.id }">selected</c:if>           >${dep.name }</option>
								
								</c:forEach>
						</select>
			
			<input type="hidden" name="type" value="update" />
			</form>
		</c:forEach>
		<input type="button" name="sub" value="保存" id="save"/> 
		


	</div>

</body>
</html>