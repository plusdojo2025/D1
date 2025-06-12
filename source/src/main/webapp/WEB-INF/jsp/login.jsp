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
	
<!-- ログインフォーム -->	
<form id="login_form" method="post" action="LoginServlet">
	<table>
	 <tr>
	  <td>
	   <label>教員ID<br>
	   <input type="text" name="teacherId">
	   </label>
	  </td>
	 </tr>
	 <tr>
	  <td>
	   <label>パスワード<br>
	   <input type="password" name="password">
	   </label>
	  </td>
	 </tr>
	 <tr>
	  <td>
	  	<input type="submit" name="submit" value="ログイン">
	  	<input type="reset" name="reset" value="リセット">
	  	<span id="error_message"></span>
	  </td>
	 </tr>
	</table>	
</form>
<!-- フッター -->


</body>
</html>