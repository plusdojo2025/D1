<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<p>教師ID：<c:out value="${loginTeacher.teacherId}" /></p>

<!-- リクエスト属性を変数にセット -->
<c:set var="selectedYear" value="${requestScope.paramYear}" />
<c:set var="selectedSemester" value="${requestScope.paramSemester}" />

<!-- 年度・学期の検索フォーム -->
<form action="InfoScheduleServlet" method="post">
  <div class="form-row">
    <label>① 年度：</label>
    <select name="year">
      <option value="2025" <c:if test="${selectedYear eq 2025}">selected</c:if>>2025</option>
      <option value="2024" <c:if test="${selectedYear eq 2024}">selected</c:if>>2024</option>
      <option value="2023" <c:if test="${selectedYear eq 2023}">selected</c:if>>2023</option>
    </select>

    <label>① 学期：</label>
    <select name="semester">
      <option value="前期" <c:if test="${selectedSemester eq '前期'}">selected</c:if>>前期</option>
      <option value="後期" <c:if test="${selectedSemester eq '後期'}">selected</c:if>>後期</option>
    </select>

    <button type="submit" class="btn">検索</button>
  </div>
</form>

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
                <c:if test="${item.year == selectedYear 
                            and item.semester == selectedSemester 
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

<!-- メモ表示欄 -->
<div class="memo-section">
  <label class="memo-label">② メモ欄：</label><br>
  <div id="memoDisplay" class="memo-box" style="white-space: pre-wrap; border: 1px solid #ccc; padding: 10px; min-height: 100px;"></div>
</div>

<!-- メモ表示スクリプト（教師IDだけでローカルストレージを参照） -->
<script>
window.addEventListener('load', () => {
  const teacherId = "${loginTeacher.teacherId}";
  const memoKey = `scheduleMemo_${teacherId}`;
  const display = document.getElementById('memoDisplay');
  const savedMemo = localStorage.getItem(memoKey);
  display.textContent = savedMemo || 'メモは保存されていません。';
});
</script>

<!-- 編集ボタン -->
<form action="EditScheduleServlet" method="post" style="display: inline;">
  <input type="hidden" name="year" value="${selectedYear}">
  <input type="hidden" name="semester" value="${selectedSemester}">
  <button type="submit" class="btn">編集</button>
</form>

<!-- 登録ボタン -->
<form action="RegistScheduleServlet" method="get" style="display: inline;">
  <button type="submit" class="btn">登録</button>
</form>

</body>
</html>
