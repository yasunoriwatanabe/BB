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
<h3>${userList.name}さんの編集画面</h3>
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
	<a href = "management">戻る</a>
<form action = "settings" method = "post"><br />
	<input type = "hidden" name = "id" value = "${userList.id}">
	<input type = "hidden" name = "list" value = "${userList}">


	<label for = "name">名前   </label>　元の名前「${ userList.name }」<br>
	<c:if test="${ serchName!=null}">
		<input name = "name" value = "${serchName}" id = "name" />
	</c:if>
	<c:if test="${ userList.name!=null&&serchName==null}">
		<input name = "name" value = "${ userList.login_id }" id = "name" />
	</c:if>
	<br />


	<label for = "login_id">ログインID</label>　元のログインID「${userList.login_id }」<br>
	<c:if test="${ serchLogin!=null}">
		<input name = "login_id" value = "${serchLogin}" /><br />
	</c:if>
	<c:if test="${ userList.login_id!=null&&serchLogin==null}">
		<input name = "name" value = "${ userList.name }" id = "name" />
	</c:if>


	<br />

	<label for = "password">パスワード</label><br />
	<input name = "password" type = "password" id = "password"/><br />

	<label for = "password2">パスワード</label><br />
	<input name = "password2" type = "password" id = "password2"/><br />

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


</form>
</div>
</body>
</html>