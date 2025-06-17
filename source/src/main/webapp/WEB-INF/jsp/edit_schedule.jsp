<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュールの編集</title>

<link rel="stylesheet" href="<c:url value='/css/edit_schedule.css' />">

</head>
<body>

<h1>スケジュールの編集</h1>
<form action="InfoScheduleServlet" method="post">
  <div class="form-row">
    <label>① 年度：</label>
    <select name="year">
	  	<option value="">選択してください</option>
	  	<option value="2025" <c:if test="${year == 2025}">selected</c:if>>2025</option>
  		<option value="2024" <c:if test="${year == 2024}">selected</c:if>>2024</option>
  		<option value="2023" <c:if test="${year == 2023}">selected</c:if>>2023</option>
	</select>

    <label>① 学期：</label>
	  <select name="semester">
	  <option value="">選択してください</option>
	  <option value="前期" <c:if test="${semester == '前期'}">selected</c:if>>前期</option>
	  <option value="後期" <c:if test="${semester == '後期'}">selected</c:if>>後期</option>
	</select>


    <button type="submit" class="btn">検索</button>
  </div>
</form>

<!-- 時間割テーブル（仮表示） -->
<c:if test="${not empty scheduleList}">
  <table>
    <tr>
      <th>曜日</th>
      <th>時限</th>
      <th>内容</th>
      <th>対象クラス</th>
    </tr>
    <c:forEach var="item" items="${scheduleList}">
      <tr>
        <td>${item.day_of_week}</td>
        <td>${item.period}</td>
        <td>${item.content}</td>
        <td>${item.classId}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>

<!-- メモ欄 -->
<div class="memo-section">
  <label class="memo-label">② メモ欄：</label><br>
  <div class="memo-box">
    ・金曜は放課後に保護者面談あり。
  </div>
</div>

<!-- 編集・戻るボタン -->
<form action="EditScheduleServlet" method="get" style="display: inline;">
  <button type="submit" class="btn">保存</button>
</form>

<form action="InfoScheduleServlet" method="post" style="display: inline;">
  <input type="hidden" name="year" value="${year}">
  <input type="hidden" name="semester" value="${semester}">
  <button type="submit" class="btn">キャンセル</button>
</form>


</body>
</html>