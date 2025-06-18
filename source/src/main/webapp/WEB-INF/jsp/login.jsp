<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
<!-- メイン -->
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
<!-- フッター -->
<!-- JavaScript -->
<script src="<%= request.getContextPath() %>/js/login.js"></script>
</body>
</html>