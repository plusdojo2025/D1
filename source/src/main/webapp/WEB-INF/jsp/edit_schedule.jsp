<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<c:set var="paramYear" value="${year}" />
<c:set var="paramSemester" value="${semester}" />
<!-- スケジュール表示 -->
<form action="InfoScheduleServlet" method="post">
  <input type="hidden" name="action" value="save"> <!-- 保存フラグ -->
  <input type="hidden" name="year" value="${year}">
  <input type="hidden" name="semester" value="${semester}">

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
              <c:set var="found" value="false"/>
              <c:forEach var="item" items="${scheduleList}">
                <c:if test="${item.year == year && item.semester == semester && item.day_of_week == day && item.period == period}">
                  <input type="hidden" name="scheduleId" value="${item.scheduleId}" />
                  <input type="text" name="content_${day}_${period}" value="${item.content}" />
                  <input type="text" name="classId_${day}_${period}" value="${item.classId}" />
                  <c:set var="found" value="true"/>
                </c:if>
              </c:forEach>
              <c:if test="${!found}">
                <input type="text" name="content_${day}_${period}" value="" />
                <input type="text" name="classId_${day}_${period}" value="" />
              </c:if>
            </td>
          </c:forEach>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <button type="submit" class="btn">保存</button>
</form>


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

<form action="InfoScheduleServlet" method="post" style="display: inline;">
  <input type="hidden" name="year" value="${year}">
  <input type="hidden" name="semester" value="${semester}">
  <button type="submit" class="btn">キャンセル</button>
</form>


</body>
</html>