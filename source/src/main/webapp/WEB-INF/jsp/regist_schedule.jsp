<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url value="/img/header_banner.jpg" var="headerBannerUrl"/>
<c:url value="/img/IMG_ロゴ2.png" var="logo2Url"/>
<c:url value="/ListStudentServlet" var="listStudentUrl"/>
<c:url value="/InfoScheduleServlet" var="infoScheduleUrl"/>
<c:url value="/LoginServlet" var="logoutUrl"/>
<c:url value="/RegistScheduleServlet" var="registScheduleUrl"/>
<c:url value="/js/regist_student.js" var="registJsUrl"/>
<%
  String error = (String) request.getAttribute("error");
  boolean hasError = (error != null && !error.isEmpty());
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>スケジュール登録</title>

 <c:url value="/css/common.css" var="commonCssUrl" />
<c:url value="/css/regist_schedule.css" var="registScheduleCssUrl" />

<link rel="stylesheet" href="${commonCssUrl}">
<link rel="stylesheet" href="${registScheduleCssUrl}">

</head>

<body>
<div style="text-align: center; margin-bottom: 20px;">
  <img src="${headerBannerUrl}" alt="バナー画像"
       style="width: 100%; max-width: 650px; height: 400px; object-fit: cover;">
</div>
<!-- ナビ全体を中央に寄せる -->
<div style="text-align: center;">
  <nav style="display: inline-flex; align-items: center; gap: 30px; padding: 10px;">
    <!-- ロゴ画像 -->
     <img src="${logo2Url}" alt="ロゴ2" style="height: 50px; position: relative; top: -0.2cm;">

    <!-- メニュー -->
  <ul class="cute-menu" style="display: flex; list-style: none; gap: 20px;">
  <li class="menu-item-with-sub">
  <a class="menu-label" href="#">🐰 生徒管理</a>
  <div class="submenu">
    <a href="${pageContext.request.contextPath}/RegistStudentServlet">➕ 生徒登録</a>
    <a href="${listStudentUrl}">📄 生徒一覧</a>
  </div>
</li>
  <li><a href="${infoScheduleUrl}">📅 スケジュール</a></li>
  <li><a href="${logoutUrl}">🚪 ログアウト</a></li>
</ul>
  </nav>
</div>

<div class="container">


   <form method="post" action="${registScheduleUrl}" onsubmit="return validateForm();">

    <h1>スケジュール登録フォーム</h1>
    
<%
  String errorStyle = "color: red; font-weight: bold; margin-bottom: 10px;" + (hasError ? "" : " display:none;");
%>
<p id="jsErrorMessage" class="message-error" style="<%= errorStyle %>">
  <%= hasError ? error : "" %>
</p>


	<label for="year">年度:
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

<label>種別:</label><br>
<div class="type-radio-group">
  <span>
    <input type="radio" id="type-授業" name="type" value="授業" <%= "授業".equals(selectedType) ? " checked" : "" %>>
    <label for="type-授業" class="cute-radio-label">授業</label>
  </span>
  <span>
    <input type="radio" id="type-授業以外" name="type" value="授業以外" <%= "授業以外".equals(selectedType) ? " checked" : "" %>>
    <label for="type-授業以外" class="cute-radio-label">授業以外</label>
  </span>
</div>
<br><br>

<label for="day_of_week">曜日:
<br>

<%
String selectedDay = (String) request.getAttribute("day_of_week");
if (selectedDay == null) {
    selectedDay = request.getParameter("day_of_week");
}

String[] days = {"月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日", "日曜日"};
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
	<option value="<%= i %>限" <%= selected %>><%= i %>限</option>
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

<label for="classId">クラス
<select id="classId" name="classId">
    <option value="" selected>-- 選択してください --</option>
    <c:forEach var="cls" items="${classList}">
     <option value="${cls.classId}" <c:if test="${cls.classId == param.classId}">selected</c:if>>
  ${cls.grade}年${cls.className}組
	</option>
    </c:forEach>
  </select>
</label>
<br><br>

<label for="content">各コマの予定:
  <input type="text" id="content" name="content" maxlength="100" value="<%= contentValue != null ? contentValue : "" %>">
</label>

<!--登録・クリアボタンの上に配置-->
<p id="alwaysMessage" style="color: gray; margin-bottom: 5px;">
  すべての項目を入力してください
</p>

<!-- 登録・クリアボタン -->
<div style="text-align: center; margin-bottom: 10px;">
  <input type="submit" value="登録">
  <input type="reset" value="リセット">
</div>

  <button class="back-button" type="button" onclick="history.back();">
  ← 戻る
</button>

 <div id="footer">
    <p>&copy; MATINY</p> 
    
  </div>

<script src="${registJsUrl}"></script>

<script>
function validateForm() {
	  const year = document.getElementById('year');
	  const semester = document.getElementById('semester');
	  const type = document.querySelector('input[name="type"]:checked');
	  const day = document.getElementById('day_of_week');
	  const period = document.getElementById('period');
	  const classId = document.getElementById('classId');  
	  const content = document.getElementById('content');

	  const errorMessage = document.getElementById('jsErrorMessage');

	  if (!year.value || !semester.value || !type || !day.value || !period.value  || !classId.value || content.value.trim() === '') {
	    errorMessage.textContent = '必須項目を入力してください';
	    errorMessage.style.display = 'block';

	    // スクロールしてエラー位置に移動
	    errorMessage.scrollIntoView({ behavior: 'smooth', block: 'center' });

	    return false;
	  } else {
	    errorMessage.textContent = '';
	    errorMessage.style.display = 'none';
	    return true;
	  }
	}
</script>
<script>
function goToClassSchedule(classId) {
  if (classId) {
    const url = '<c:url value="/InfoScheduleServlet" />?classId=' + encodeURIComponent(classId);
    window.location.href = url;
  }
}
</script>

</body>
</html>