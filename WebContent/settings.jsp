<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored = "false"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${userList.name}の設定</title>
	<link href = "css/style.css" rel = "stylesheet" type = "text/cs">
</head>
<body>
<div class = "main-contents">
<h3>${userList.name}</h3>
<c:if test = "${not empty errorMessage }">
	<div class = "errorMessage">
		<ul>

			<c:forEach items = "${errorMessage }" var = "message">
				<li><font size="6" color="red"><c:out value = "${ message }" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var = "errorMessage" scope = "session"/>
</c:if>
<form action = "settings" method = "post"><br />
	<input type = "hidden" name = "id" value = "${userList.id}">
	<input type = "hidden" name = "list" value = "${userList}">

	<label for = "name">名前   </label>
	<input name = "name" value = "${ userList.name }" id = "name" />

	<label for = "login_id">ログインID</label>
	<input name = "login_id" value = "${userList.login_id }" /><br />

	<label for = "password">パスワード</label>
	<input name = "password" type = "password" id = "password"/><br />

	<label for = "password2">パスワード</label>
	<input name = "password2" type = "password" id = "password2"/><br />

	支店名<SELECT name="branch_id">
		<c:forEach items="${branches}" var="branch">

			<OPTION value="${branch.id}">${branch.name}</OPTION>

		</c:forEach>
			<option lavel="${userList.name}"/>
	</SELECT>

	所属<SELECT name="department_id">
		<c:forEach items="${departments}" var="department">

			<OPTION value="${department.id}">${department.name}</OPTION>
		</c:forEach>
	</SELECT>
	<input type = "submit" value = "登録" /><br />
	<a href = "management">戻る</a>

</form>
</div>
</body>
</html>