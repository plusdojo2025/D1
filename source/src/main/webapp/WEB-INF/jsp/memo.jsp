<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, dto.Memo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
List<Memo> memoList = (List<Memo>) request.getAttribute("memoList");
String classId = (String) request.getAttribute("classId");
String period = (String) request.getAttribute("period");
String date = (String) request.getAttribute("date");
String teacherId = (String) request.getAttribute("teacherId");
String subjectName = (String) request.getAttribute("subjectName");
String classNameDisp = (String) request.getAttribute("classNameDisplay");
Map<Integer, String> subjectMap = (Map<Integer, String>) request.getAttribute("subjectMap");

int subjectId = -1;
if (memoList != null && !memoList.isEmpty()) {
    subjectId = memoList.get(0).getSubjectId();
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>授業のメモ欄</title>
<link rel="stylesheet" href="<c:url value='/css/memo.css'/>">
<script src="<c:url value='/js/memo.js'/>"></script>
</head>
<body>
<div class="memo-wrapper">
<h2>授業のメモ欄</h2>

<p>
  クラス：<%= classNameDisp != null ? classNameDisp : "" %>　
  時限：<%= period != null ? period + "限" : "" %>　
  科目名：<%= subjectName != null ? subjectName : "" %>
</p>

<!-- メモ一覧 -->
<h3>メモ一覧</h3>
<hr>
<ul>
<% if (memoList != null && !memoList.isEmpty()) {
   for (Memo m : memoList) { %>
  <li>
    ・<%= m.getContent() %>
    （<%= new java.text.SimpleDateFormat("yyyy/MM/dd").format(m.getDate()) %>）
    <form action="MemoServlet" method="post" style="display:inline;">
      <input type="hidden" name="action" value="delete">
      <input type="hidden" name="memoId" value="<%= m.getMemoId() %>">
      <input type="hidden" name="classId" value="<%= classId %>">
      <input type="hidden" name="period" value="<%= period %>">
      <input type="hidden" name="date" value="<%= date != null && !date.isEmpty() ? date : new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
      <input type="hidden" name="teacherId" value="<%= teacherId %>">
      <input type="hidden" name="subjectId" value="<%= m.getSubjectId() %>">
      <button type="submit" class="delete-btn">削除</button>
    </form>
  </li>
<% }} else { %>
  <li>メモは登録されていません。</li>
<% } %>
</ul>

<!-- メモ新規登録 -->
<h3>メモ新規登録</h3>
<form action="MemoServlet" method="post" id="newMemoForm">
  <input type="hidden" name="action" value="update">
  <input type="hidden" name="classId" value="<%= classId %>">
  <input type="hidden" name="period" value="<%= period %>">
  <input type="hidden" name="date" value="<%= date != null && !date.isEmpty() ? date : new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
  <input type="hidden" name="teacherId" value="<%= teacherId %>">
  <input type="hidden" name="subjectId" value="<%= subjectId %>">

  <input type="text" name="memo1" size="100" placeholder="1行目"><br><br>
  <input type="text" name="memo2" size="100" placeholder="2行目"><br><br>
  <input type="text" name="memo3" size="100" placeholder="3行目"><br><br>

  <div class="button-right">
    <button type="submit" id="confirmUpdate">更新</button>
  </div>
</form>

<!-- 戻る -->
<form action="InfoScheduleServlet" method="get">
  <input type="hidden" name="classId" value="<%= classId %>">
  <button type="submit">戻る</button>
</form>
</div>
</body>
</html>
