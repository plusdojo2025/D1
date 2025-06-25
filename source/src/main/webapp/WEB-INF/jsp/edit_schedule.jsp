<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:url value="/img/header_banner.jpg" var="headerBannerUrl"/>
<c:url value="/img/IMG_ロゴ2.png" var="logo2Url"/>
<c:url value="/ListStudentServlet" var="listStudentUrl"/>
<c:url value="/InfoScheduleServlet" var="infoScheduleUrl"/>
<c:url value="/LoginServlet" var="logoutUrl"/>
<c:url value="/RegistScheduleServlet" var="registScheduleUrl"/>
<c:url value="/js/regist_student.js" var="registJsUrl"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スケジュールの編集</title>
<link rel="stylesheet" href="<c:url value='/css/edit_schedule.css' />">
<style>
  .button-row {
    margin-top: 20px;
    display: flex;
    gap: 10px;
    align-items: center;
  }
  .memo-section {
    margin-top: 30px;
  }
  .memo-box {
    width: 100%;
    height: 100px;
    resize: vertical;
  }
</style>
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
    <ul class="cute-menu" style="display: flex; list-style: none; margin: 0; padding: 0; gap: 20px;">
     <li><a href="${listStudentUrl}">🐰 生徒管理</a></li>
      <li><a href="${infoScheduleUrl}">📅 スケジュール</a></li>
      <li><a href="${logoutUrl}">🚪 ログアウト</a></li>
    </ul>
  </nav>
</div>

<h1>スケジュールの編集</h1>

<p>教師ID：<c:out value="${loginTeacher.teacherId}" /></p>

<!-- 年度・学期の検索フォーム -->
<form action="<c:url value='/EditScheduleServlet' />" method="post">
  <div class="form-row">
    <label>① 年度：</label>
    <select name="year">
      <option value="2025" <c:if test="${year == 2025}">selected</c:if>>2025</option>
      <option value="2024" <c:if test="${year == 2024}">selected</c:if>>2024</option>
      <option value="2023" <c:if test="${year == 2023}">selected</c:if>>2023</option>
    </select>

    <label>① 学期：</label>
    <select name="semester">
      <option value="前期" <c:if test="${semester == '前期'}">selected</c:if>>前期</option>
      <option value="後期" <c:if test="${semester == '後期'}">selected</c:if>>後期</option>
    </select>

    <button type="submit" class="btn">検索</button>
  </div>
</form>

<!-- 表示設定 -->
<c:set var="days" value="月曜日,火曜日,水曜日,木曜日,金曜日,土曜日,日曜日" />
<c:set var="periods" value="1限,2限,3限,4限,5限,6限,7限" />

<!-- 編集フォーム -->
<form action="<c:url value='/EditScheduleServlet' />" id="editForm" method="post">
  <input type="hidden" name="action" value="save">
  <input type="hidden" name="year" value="${year}" />
  <input type="hidden" name="semester" value="${semester}" />

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
              <c:set var="found" value="false" />
              <c:forEach var="item" items="${scheduleList}">
                <c:if test="${item.year == year 
                            and item.semester == semester 
                            and item.day_of_week == day 
                            and item.period == period}">
                  
                  <input type="hidden" name="scheduleId_${day}_${period}" value="${item.scheduleId}" />
                  <input type="text" name="content_${day}_${period}" value="${item.content}" placeholder="内容" /><br>
                  <select name="classId_${day}_${period}">
  <option value="">（未選択）</option>
  <c:forEach var="cls" items="${classList}">
    <option value="${cls.classId}" <c:if test="${item.classId == cls.classId}">selected</c:if>>
      ${cls.grade}年${cls.className}組
    </option>
  </c:forEach>
</select>
                  <c:set var="found" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${!found}">
  <input type="text" name="content_${day}_${period}" value="" placeholder="内容" /><br>
  <select name="classId_${day}_${period}">
    <option value="" selected>（未選択）</option>
    <c:forEach var="cls" items="${classList}">
      <option value="${cls.classId}">
        ${cls.grade}年${cls.className}組
      </option>
    </c:forEach>
  </select>
</c:if>
            </td>
          </c:forEach>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <!-- メモ欄 -->
  <div class="memo-section">
    <label class="memo-label">② メモ欄：</label><br>
    <textarea id="memoBox" name="memo" class="memo-box" placeholder="ここにメモを入力してください" style="white-space: pre-wrap; border: 1px solid #ccc; padding: 10px; min-height: 100px;"></textarea>
  </div>

  <div class="button-row">
  	<input type="hidden" name="semester" value="${semester}" />
    <button type="submit" class="btn">保存</button>
  </div>
</form>

<!-- キャンセルボタン -->
<form action="<c:url value='/InfoScheduleServlet' />" method="get" style="display: inline;">
  <input type="hidden" name="year" value="${year}" />
  <input type="hidden" name="semester" value="${semester}" />
  <input type="hidden" name="action" value="search" />
  <button type="submit" class="btn cancel">戻る</button>
</form>

<!-- JavaScript：教師IDごとにメモを保存・読み込み -->
<script>
document.getElementById('editForm').addEventListener('submit', function(event) {
	  event.preventDefault();

	  const confirmSave = confirm("変更を保存してよろしいですか？");
	  if (!confirmSave) {
	    return; // 「いいえ」が選ばれたら送信中止
	  }

	  const teacherId = "${loginTeacher.teacherId}";
	  const memoKey = `scheduleMemo_${teacherId}`;
	  const memoContent = document.getElementById('memoBox').value;

	  try {
	    localStorage.setItem(memoKey, memoContent);
	    this.submit();  // 「はい」なら送信実行
	  } catch (e) {
	    console.error("メモ保存エラー", e);
	    this.submit();  // エラーでも送信は行う
	  }
	});

  window.addEventListener('load', () => {
    const teacherId = "${loginTeacher.teacherId}";
    const memoKey = `scheduleMemo_${teacherId}`;
    const savedMemo = localStorage.getItem(memoKey);
    document.getElementById('memoBox').value = savedMemo || "";
  });
  
  document.querySelector('form[action="InfoScheduleServlet"]').addEventListener("submit", function(event) {
	  const confirmCancel = confirm("変更内容が保存されていません。\n戻ると全て破棄されます。\nこのまま画面を閉じてもよろしいですか？");

	  if (!confirmCancel) {
	    event.preventDefault();  // 「いいえ」なら送信キャンセル
	    return;
	  }

	  const teacherId = "${loginTeacher.teacherId}";
	  const memoKey = `scheduleMemo_${teacherId}`;
	  const memoContent = document.getElementById('memoBox').value;
	  localStorage.setItem(memoKey, memoContent);
	});
  
</script>

</body>
</html>
