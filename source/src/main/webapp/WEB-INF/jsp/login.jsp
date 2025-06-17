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
			<td><input type="text" name="teacherId" required></td>
		</tr>
		<tr>
			<td>パスワード：</td>
			<td><input type="password" name="password" required></td>
		</tr>
	</table>
	<br>
	<input type="submit" value="ログイン">
</form>
<!-- フッター -->
<!-- JavaScript -->

</body>
</html>