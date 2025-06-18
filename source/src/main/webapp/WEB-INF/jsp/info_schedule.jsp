<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュールの閲覧</title>
<link rel="stylesheet" href="<c:url value='/css/info_schedule.css' />">
</head>
<body>

<h1>スケジュールの閲覧</h1>
<form action="InfoScheduleServlet" method="post">
  <div class="form-row">
    <label>① 年度：</label>
    <select name="year">
      <option value="2025" <c:if test="${param.year == '2025' || paramYear == 2025}">selected</c:if>>2025</option>
      <option value="2024" <c:if test="${param.year == '2024'}">selected</c:if>>2024</option>
      <option value="2023" <c:if test="${param.year == '2023'}">selected</c:if>>2023</option>
    </select>

    <label>① 学期：</label>
    <select name="semester">
      <option value="前期" <c:if test="${param.semester == '前期' || paramSemester == '前期'}">selected</c:if>>前期</option>
      <option value="後期" <c:if test="${param.semester == '後期'}">selected</c:if>>後期</option>
    </select>

    <button type="submit" class="btn">検索</button>
  </div>
</form>

<c:set var="year" value="${param.year}" />
<c:set var="semester" value="${param.semester}" />
<!-- スケジュール表示 -->
<c:if test="${not empty scheduleList}">
  <c:set var="days" value="月曜日,火曜日,水曜日,木曜日,金曜日,土曜日,日曜日" />
<c:set var="periods" value="1限,2限,3限,4限,5限,6限,7限" />

<table border="1">
  <thead>
    <tr>
      <th>時限/曜日</th>
      <c:forEach var="day" items="${fn:split(days, ',')}">
        <th>${day}</th>
      </c:forEach>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="period" items="${fn:split(periods, ',')}">
      <tr>
        <th>${period}</th>
        <c:forEach var="day" items="${fn:split(days, ',')}">
          <td>
            <c:forEach var="item" items="${scheduleList}">
              <c:if test="${item.year == paramYear 
                          and item.semester == paramSemester 
                          and item.day_of_week == day 
                          and item.period == period}">
                <div>
                  ${item.content} <br/>
                  ${item.classId}
                </div>
              </c:if>
            </c:forEach>
          </td>
        </c:forEach>
      </tr>
    </c:forEach>
  </tbody>
</table>
</c:if>

<c:if test="${empty scheduleList}">
  <p>該当するスケジュールはありません。</p>
</c:if>

<!-- メモ欄 -->
<div class="memo-section">
  <label class="memo-label">② メモ欄：</label><br>
  <div class="memo-box">
    ・金曜は放課後に保護者面談あり。
  </div>
</div>

<!-- 編集・戻るボタン -->
<form action="EditScheduleServlet" method="post" style="display: inline;">
  <input type="hidden" name="year" value="${param.year}">
  <input type="hidden" name="semester" value="${param.semester}">
  <button type="submit" class="btn">編集</button>
</form>

</body>
</html>
