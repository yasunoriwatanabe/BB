<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>
<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
<script type="text/javascript">

function confirm() {
    if (window.confirm('実行しますか？')) {
        return true;
    }
    return false;
}

</script>
</head>
<body>
<div class = "header">
	<h1>ユーザー管理画面</h1>
	<c:if test = "${ empty loginUser}">
		<h3>ログインしていません</h3>
		<%System.out.println("user.name"); %>
	</c:if>
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
	<c:if test = "${ not empty loginUser}">
		<a href = "signup">ユーザー新規登録</a>
        <a href = "./">戻る</a>
		<div class = "profile">
		<div class = "name">
		<h2><c:out value = "${loginUser.name}" />さんがログインしています</h2></div>

		</div>

		<c:out value="${userName.name}"/>
		<table border=4 cellspacing=5 cellpadding=7 width=1000 align=center>
			<tr>
			<th>名前</th><th>ログインID</th><th>支店名</th><th>所属</th><th>アカウントの状態</th><th>アカウント操作</th>
		 	</tr>
			<c:forEach items="${userList}" var="userList">
			 	<tr>
			 	<td><c:out value="${userList.name}"/></td>
			 	<td><c:out value="${userList.login_id }"/></td>
				<c:forEach items="${branches}" var="branch">

					<c:if test = "${userList.branch_id == branch.id}">
						<td>
							<c:out value="${branch.name }"/>
						</td>
					</c:if>

				</c:forEach>

			 	<c:forEach items="${departments}" var="department">
			 		<c:if test = "${userList.department_id == department.id}">
			 			<td>
			 				<c:out value="${department.name}"/>
			 			</td>
			 		</c:if>
			 	</c:forEach>
			 	<td>
			 	<c:if test="${userList.is_stopped==0 }">
			 		<c:out value="稼働中"/>
			 	</c:if>
			 	<c:if test="${userList.is_stopped==1 }">
			 		<c:out value="停止中"/>
			 	</c:if>


			 	</td>
			 	<td><button id = "a" name="edit" value="${userList}" onclick="location.href='settings?id=${userList.id}'">編集</button>


			 	<c:if test="${userList.is_stopped==0}">
				 	<form action ="management" method = "post">
				 	<button id = "b" type="submit" value="1" >停止</button>
				 	<input type="hidden" name="id" value ="${userList.id}" >
				 	<input type="hidden" name="stopped" value ="1" >
				 	</form>
				 </c:if>
				 <c:if test="${userList.is_stopped==1}">
				 	<form action ="management" method = "post">
				 	<button id = "b" type = "submit" name="a" value="0" >復活</button>
				 	<input type="hidden" name="id" value ="${userList.id}" >
				 	<input type="hidden" name="stopped" value ="0" >
				 	</form>
				 </c:if>


			 	<form action = "delete" method = "post">
			 	<button id = "c" type = "submit" name="a" value="cart1">削除</button>
			 	<input type = "hidden" name = "delete" value = "${userList.id}">


			 	</form>
			 	 </td>
			 	</tr>
				<br />
			</c:forEach>
		</table>
	</c:if>
</div>
</body>
</html>