<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String error = (String) request.getAttribute("error");
  boolean hasError = (error != null && !error.isEmpty());
%>


<%
    // テスト用：ここで強制的に例外を発生させる
    throw new RuntimeException("テスト用エラー");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>スケジュール登録</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/regist_schedule.css">
</head>
<body>

<div class="container">
  <form name="scheduleForm" class="schedule-form"
      method="post"
      action="<%=request.getContextPath() %>/RegistScheduleServlet"
      onsubmit="return validateForm();">


    <h2>スケジュール登録フォーム</h2>
    
<%
  String errorStyle = "color: red; font-weight: bold; margin-bottom: 10px;" + (hasError ? "" : " display:none;");
%>
<p id="jsErrorMessage" style="<%= errorStyle %>">
  <%= hasError ? error : "" %>
</p>


	<label for="year">年度：
<%
    String selectedYear = (String) request.getAttribute("year");
    if (selectedYear == null) {
        selectedYear = request.getParameter("year");
    }
%>
	<select id="year" name="year">
	<option value="">-- 選択してください --</option>
	<%
	for (int y =2020; y <=2100;y++) {
		String selected = (String.valueOf(y).equals(selectedYear)) ?  "selected" : "";
	%>
	<option value="<%= y %>"<%= selected%>><%= y %>年度</option>
	<%	} %>
	</select>
</label>
<br><br>

<label for="semester">学期:
<%
    String selectedSemester = (String) request.getAttribute("semester");
    if (selectedSemester == null) {
        selectedSemester = request.getParameter("semester");
    }
%>
<select id="semester" name="semester">
<option value="">-- 選択してください --</option>
	<option value="前期"<%= "前期".equals(selectedSemester) ? " selected" : "" %>>前期</option>
	<option value="後期"<%= "後期".equals(selectedSemester) ? " selected" : "" %>>後期</option>
</select>
</label>
<br><br>

<%
    String selectedType = (String) request.getAttribute("type");
    if (selectedType == null) {
        selectedType = request.getParameter("type");
    }
%>

<label>種別：</label><br>
<span style="display: inline-block; margin-right: 10px;">
  <input type="radio" id="type-授業" name="type" value="授業" <%= "授業".equals(selectedType) ? " checked" : "" %>>
  <label for="type-授業">授業</label>
</span>
<span style="display: inline-block; margin-right: 10px;">
  <input type="radio" id="type-授業以外" name="type" value="授業以外" <%= "授業以外".equals(selectedType) ? " checked" : "" %>>
  <label for="type-授業以外">授業以外</label>
</span>


<label for="day_of_week">曜日:
<%

String selectedDay = (String) request.getAttribute("day_of_week");
if (selectedDay == null) {
    selectedDay = request.getParameter("day_of_week");
}

String[] days = {"月", "火", "水", "木", "金", "土", "日"};
%>
	<select id="day_of_week" name="day_of_week">
	<option value="">-- 選択してください --</option>
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

<label for="period">時限:
<%
    String selectedPeriod = (String) request.getAttribute("period");
    if (selectedPeriod == null) {
        selectedPeriod = request.getParameter("period");
    }
%>
<select id="period" name="period">
<option value="">-- 選択してください --</option>
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

<%
    String contentValue = (String) request.getAttribute("content");
    if (contentValue == null) {
        contentValue = request.getParameter("content");
    }
%>

<label for="content">各コマの予定:
  <input type="text" id="content" name="content" maxlength="100" value="<%= contentValue != null ? contentValue : "" %>">
</label>

<!--登録・クリアボタンの上に配置-->
<p id="alwaysMessage" style="color: gray; margin-bottom: 5px;">
  すべての項目を入力してください
</p>

<!--登録・クリアボタン-->
<div style="margin-bottom: 10px;">
<input type="submit" value="登録">
<input type="reset" value="クリア">
</div>
</div>


<!-- 戻るボタンを登録・クリアの下、左寄せ -->
<div style="text-align: left;">
<button type="button" onclick="history.back();">戻る</button>
</div>

<script src="<%= request.getContextPath() %>/js/regist_student.js"></script>


<script>
function validateForm() {
	  const year = document.getElementById('year');
	  const semester = document.getElementById('semester');
	  const type = document.querySelector('input[name="type"]:checked');
	  const day = document.getElementById('day_of_week');
	  const period = document.getElementById('period');
	  const content = document.getElementById('content');

	  const errorMessage = document.getElementById('jsErrorMessage');
	  const alwaysMessage = document.getElementById('alwaysMessage');

	  if (!year.value || !semester.value || !type || !day.value || !period.value || content.value.trim() === '') {
	    errorMessage.textContent = '必須項目を入力してください';
	    errorMessage.style.display = 'block';

	    // グレーのメッセージはそのまま表示し続けるので操作しない
	    // alwaysMessage.style.display = 'block'; // ←不要です

	    return false;
	  } else {
	    errorMessage.textContent = '';
	    errorMessage.style.display = 'none';

	    return true;
	  }
	}

</script>

</body>
</html>