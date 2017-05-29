<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿画面</title>
</head>
<body>
<div class = "form_area">

	<form action = "newMessage" method = "post">
	<input type = "hidden" name = "userId" value = "${loginUser.id}">
<h1>新規投稿</h1>
<a href = "./">戻る</a><br />
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

	題名<br />
	<textarea name="title"cols="24" rows="3" class="title" onkeyup="CountDownLength( 'cdlength2' , value , 100 );"></textarea>
						<p id="cdlength2">あと100文字</p>
						<script type="text/javascript"><!--
						function CountDownLength( idn, str, mnum ) {
						   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
						}
						// --></script>
	カテゴリ<br />

	<textarea name="category"cols="24" rows="3" class="category" onkeyup="CountDownLength( 'cdlength1' , value , 20 );"></textarea>
						<p id="cdlength1">あと20文字</p>
						<script type="text/javascript"><!--
						function CountDownLength( idn, str, mnum ) {
						   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
						}
						// --></script>
						<br>
	本文<br />
	<textarea name="body"cols="50" rows="20" class="body"  onkeyup="CountDownLength( 'cdlength3' , value , 1000 );"></textarea>
						<p id="cdlength3">あと1000文字</p>
						<script type="text/javascript"><!--
						function CountDownLength( idn, str, mnum ) {
						   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
						}
						// --></script>
	<input type = "submit" value = "投稿">1000文字まで
	</form>
</div>

</body>
</html>