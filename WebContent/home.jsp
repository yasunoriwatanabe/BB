<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page isELIgnored = "false"%>
 <%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/datepicker/jquery-ui.css" >
<script>
  $(function() {
    $("#datepicker").datepicker();
  });
</script>
<script>
  $(function() {
    $("#datepicker1").datepicker();
  });
</script>
<script>
  <%--$(function() {
    $("#datepicker").datepicker();
    $("#datepicker").datepicker("option", "showOn", 'button');
    $("#datepicker").datepicker("option", "buttonImageOnly", true);
    $("#datepicker").datepicker("option", "buttonImage", 'workspace\Bulletinboard\ico_calendar.png');
  });--%>
</script>
<title>掲示板システムホーム画面</title>
</head>
<body>
<h1>HOME</h1>
<div class = "main-contents">


	<c:if test = "${ not empty loginUser}">
		<h2><c:out value = "${loginUser.name}" />さんがログインしています</h2><br />
		<c:if test="${loginUser.department_id==1}">
			<a href = "management">ユーザー管理画面</a>
		</c:if>

		<a href = "newMessage">新規投稿画面</a>
		<a href = "logout">ログアウト</a>
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
		<div class = "form_area">

		<form action = "./" method = "get">
		<h3>日付入力_ここから～ここまで　　　　カテゴリー</h3>
		<input type="text" id="datepicker" name="start_day">
～
		<input type="text" id="datepicker1" name="end_day">

						<SELECT name="category">
						<OPTION value=""></OPTION>
						<c:forEach items="${categoryList}" var="messageC">

							<OPTION value="${messageC.category}">${messageC.category}</OPTION>

						</c:forEach>

						</SELECT>
		<input type = "submit" value = "絞込み">
		</form>
		</div>
		<br><br><br><br><br><br>
		<br>
		<br>

		<div class="messages">
			<c:forEach items="${messages}" var="message">
				<br>
				<h2>題名　　　：　<c:out value="${message.title}" /></h2>
				<h4>カテゴリ　：　<c:out value="${message.category}" /></h4>

				-------------------------------------------------------<br/>
				<h4>本文　　　：　<c:out value="${message.body}" /></h4><br>
				-------------------------------------------------------<br />
				<h4>投稿者　　：　<c:out value="${message.name}" /></h4>
				<h4>投稿日時<fmt:formatDate value = "${message.insertDate }" pattern = "yyyy/MM/dd HH:mm:ss" /></h4><br />

				<c:if test="${loginUser.department_id==2||loginUser.department_id==3&&message.branch_id==loginUser.branch_id}">
					<form action = "messageDelete" method = "post">
		 			<button id = "md" type = "submit" name="md" value="md">削除</button>
		 			<input type = "hidden" name = "messageDelete" value = "${message.id}">
		 			</form>
				</c:if>
				<br>
				<br>
				<div align="right">
					<c:forEach items="${comments}" var="comment">
						<c:if test="${comment.message_id==message.id}" >

						-------------------------------------------------------<br/>
						<c:out value="${comment.body}" /><br>
						-------------------------------------------------------<br />
						投稿者　  ：<c:out value="${comment.name}" /><br>
						投稿日時<fmt:formatDate value = "${comment.insertDate }" pattern = "yyyy/MM/dd HH:mm:ss" /><br />
						<br>
						<c:if test="${loginUser.department_id==2||loginUser.department_id==3&&comment.branch_id==loginUser.branch_id}">
							<form action = "commentDelete" method = "post">

			 				<button id = "cd" type = "submit" name="cd" value="cd">削除</button>
			 				<input type = "hidden" name = "commentDelete" value = "${comment.id}">
			 				</form>
			 			</c:if>
						<br>
						</c:if>
					</c:forEach>
				</div>
						<form action = "newComment" method = "post">
						<input type = "hidden" name = "message_id" value = "${message.id}">
						<input type = "hidden" name = "user_id" value = "${loginUser.id}">
						<h3>コメントする</h3><br />
						本文<br />
						<textarea name="body" class="body${message.id}" cols="50" rows="10" onkeyup="CountDownLength( 'cdlength${message.id}' , value , 500 );">${serchBody}</textarea>
						<p id="cdlength${message.id}">あと500文字</p>
						<script type="text/javascript"><!--
						function CountDownLength( idn, str, mnum ) {
						   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
						}
						// --></script>
						<input type = "submit" value = "投稿">500文字まで<br />
						</form>

						<br />
						～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～<br />
			</c:forEach>
		</div>
		<div class = "heder"></div>
	</c:if>
</div>


</body>
</html>