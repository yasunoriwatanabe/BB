<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規ユーザー登録</title>
</head>
<body>
<h1>新規ユーザー登録画面</h1>
<div class = "main-contents">
<c:if test = "${ not empty errorMessage }">
	<div class = "errorMessage">
		<ul>
			<c:forEach items = "${ errorMessage }" var = "message">
				<li><font size="6" color="red"><c:out value = "${ message }" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var = "errorMessage" scope = "session"/>
</c:if>

<form action = "signup" method = "post"><br />
	<label for = "name"> 名前 </label>
	<input name = "name" id = "name" value="${serchName}"/><br />

	<label for = "login_id">ログインID</label>
	<input name = "login_id" id = "login_id" value="${serchLogin}"/>
	<br />

	<label for = "password">パスワード</label>

	<input name = "password" type = "password" id = "password" /><br />
	<%--確認入力用 --%>
	<label for = "password2">パスワード</label>

	<input name = "password2" type = "password" id = "password2"/>(確認のため再入力してください。)<br />


	支店名<SELECT name="branch_id">
		<c:forEach items="${branches}" var="branch">
			<c:if test="${serchBranch==branch.id}">
				<OPTION value="${branch.id}" selected>${branch.name}</OPTION>
			</c:if>
			<c:if test="${serchBranch!=branch.id}">
				<OPTION value="${branch.id}">${branch.name}</OPTION>
			</c:if>

		</c:forEach>
	</SELECT>

	所属<SELECT name="department_id">
		<c:forEach items="${departments}" var="department">
			<c:if test="${serchDepartment==department.id}">
				<OPTION value="${department.id}" selected>${department.name}</OPTION>
			</c:if>
			<c:if test="${serchDepartment!=department.id}">
				<OPTION value="${department.id}">${department.name}</OPTION>
			</c:if>
		</c:forEach>

	</SELECT>

	<input type = "submit" value = "登録" /><br />
	<a href = "management">戻る</a>
</form>

</div>
</body>

</html>