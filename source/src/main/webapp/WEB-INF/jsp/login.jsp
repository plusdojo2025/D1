<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<!-- メイン -->
<div class = "login-wrapper">
	<h2>ログイン</h2>
	
<form action="LoginServlet" method="post">
	<table>
		<tr>
			<td>教員ID：</td>
			<td><input type="text" name="teacherId"></td>
		</tr>
		<tr>
			<td>パスワード：</td>
			<td><input type="password" name="password"></td>
		</tr>
	</table>
	<br>
	<input type="submit" value="ログイン">
	
	<!-- エラー表示用の場所 -->
	<p id="errorBox"></p>
 </form>
</div>
<!-- フッター -->
<!-- JavaScript -->
<script src="<%= request.getContextPath() %>/js/login.js"></script>
</body>
</html>