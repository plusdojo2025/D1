<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュールの閲覧</title>
<link rel="stylesheet" href="/webapp/css/info_schedule.css">
</head>
<body>

<h1>スケジュールの閲覧</h1>
<form action="ScheduleServlet" method="get">
  <div class="form-row">
    <label>① 年度：</label>
    <select name="year">
      <option value="">選択してください</option>
      <option value="2025">2025</option>
      <option value="2024">2024</option>
      <option value="2023">2023</option>
    </select>

    <label>① 学期：</label>
    <select name="term">
      <option value="">選択してください</option>
      <option value="前期">前期</option>
      <option value="後期">後期</option>
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
      <th>授業名</th>
      <th>対象クラス</th>
    </tr>
    <c:forEach var="item" items="${scheduleList}">
      <tr>
        <td>${item.day}</td>
        <td>${item.period}</td>
        <td>${item.subject}</td>
        <td>${item.targetClass}</td>
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
<form action="edit_schedule.jsp" method="get" style="display: inline;">
  <button type="submit" class="btn">③ 編集</button>
</form>

<form action="menu.jsp" method="get" style="display: inline;">
  <button type="submit" class="btn">戻る</button>
</form>

</body>
</html>