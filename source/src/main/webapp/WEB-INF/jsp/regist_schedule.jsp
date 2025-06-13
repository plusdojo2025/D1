<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュール登録</title>
<script src="<%=request.getContextPath() %>/js/regist_student.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>

<h2>スケジュール登録フォーム</h2>

<p id="jsErrorMessage" style="color: red; display: none;"></p>

<!-- 赤文字メッセージ（errorがあれば表示、なければ何も出ない） -->
<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

	<form name="scheduleForm" 
      action="<%=request.getContextPath() %>/RegistScheduleServlet" 
      method="post"
      onsubmit="return validateForm();">

	<label>年度：
<%
    String selectedYear = (String) request.getAttribute("year");
    if (selectedYear == null) {
        selectedYear = request.getParameter("year");
    }
%>
	<select name="year">
	<%
	for (int y =2020; y <=2100;y++) {
		String selected = (String.valueOf(y).equals(selectedYear)) ?  "selected" : "";
	%>
	<option value="<%= y %>"<%= selected%>><%= y %>年度</option>
	<%	} %>
	</select>
</label>
<br><br>

<label>学期:
<%
    String selectedSemester = (String) request.getAttribute("semester");
    if (selectedSemester == null) {
        selectedSemester = request.getParameter("semester");
    }
%>
<select name="semester">
	<option value="前期"<%= "前期".equals(selectedSemester) ? " selected" : "" %>>前期</option>
	<option value="後期"<%= "後期".equals(selectedSemester) ? " selected" : "" %>>後期</option>
</select>
</label>
<br><br>

<label>種別:
<%
    String selectedType = (String) request.getAttribute("type");
    if (selectedType == null) {
        selectedType = request.getParameter("type");
    }
%>
	<input type="radio" name="type" value="授業" <%= "授業".equals(selectedType) ? " checked" : "" %>> 授業
	<input type="radio" name="type" value="授業以外" <%= "授業以外".equals(selectedType) ? " checked" : "" %>> 授業以外
</label>
<br><br>

<label>曜日:
<%

String selectedDay = (String) request.getAttribute("day_of_week");
if (selectedDay == null) {
    selectedDay = request.getParameter("day_of_week");
}

String[] days = {"月", "火", "水", "木", "金", "土", "日"};
%>
	<select name="day_of_week">
	<%
	for (String day : days) {
		String selected = day.equals(selectedDay) ? "selected" : "";
	%>
		<option value="<%= day %>" <%= selected %>><%= day %></option>
	<%
		}
	%>
	</select>
</label>
<br><br>

<label>時限:
<%
    String selectedPeriod = (String) request.getAttribute("period");
    if (selectedPeriod == null) {
        selectedPeriod = request.getParameter("period");
    }
%>
<select name="period">
<%
	for (int i = 1; i <= 7; i++) {
		String selected = String.valueOf(i).equals(selectedPeriod) ? " selected" : "";
%>
	<option value="<%= i %>" <%= selected %>><%= i %>限</option>
<%
	}
%>
</select>
</label>
<br><br>

<label>各コマの予定:
<input type="text" name="content" maxlength="100"
       value="<%= request.getParameter("content") != null ? request.getParameter("content") : "" %>">
</label>

<!--登録・クリアボタン-->
<div style="margin-bottom: 10px;">
<input type="submit" value="登録">
<input type="reset" value="クリア">
</div>

<!-- 戻るボタンを登録・クリアの下、左寄せ -->
<div style="text-align: left;">
<button type="button" onclick="history.back();">戻る</button>
</div>

</form>

</body>
</html>