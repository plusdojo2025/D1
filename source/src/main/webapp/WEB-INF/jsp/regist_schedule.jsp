<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュール登録</title>
</head>
<body>

<h2>スケジュール登録フォーム</h2>

<%--エラーメッセージがあれば表示 --%>
<% 
	String error = (String) request.getAttribute("error");
	if (error != null && !error.isEmpty()) {
%>
	<p style = "color:red;"><%= error %></p>
<%
	}
%>

<form action="RegistScheduleServlet" method="post">
	<label>年度：
	<select name="year">
	<%
	for (int y =2020; y <=2100;y++) {
	%>
	<option value="<%= y %>"><%= y %>年度</option>
	<%
		}
	%>
	</select>
</label>

<label>学期:
<select name="semester">
	<option value="前期">前期</option>
	<option value="後期">後期</option>
</select>
</label>
<br><br>

<label>日付:
	<input type="date" name="date">
</label>
<br><br>

<label>時限:
	<select name="period">
	<%
		for (int i = 1; i <=7; i++) {
	%>
	<option value="<%= i %>"><%= i %>限</option>
	<%
		}
	%>
	</select>
</label>
<br><br>

<label>各コマの予定:
<input type="text" name="content" maxlength="100">
</label>
<br><br>

<input type="submit" value="登録">
<input type="reset" value="クリア">
<button type="button" onclick="history.back();">戻る</button>

</form>

</body>
</html>