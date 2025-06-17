<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, dto.Memo" %>
<%
    List<Memo> memoList = (List<Memo>) request.getAttribute("memoList");
    String className = (String) request.getAttribute("className");
    String period = (String) request.getAttribute("period");
    Memo editMemo = (Memo) request.getAttribute("editMemo");
    String editContent = editMemo != null ? editMemo.getContent() : "";
    String editId = editMemo != null ? String.valueOf(editMemo.getMemoId()) : "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>授業のメモ欄</title>
</head>
<body>
<!-- メイン -->
 <h2>授業のメモ欄</h2>
 <p>クラス：<%= className %> <%= period %></p>
 
 <!-- メモ一覧表示 -->
 <h3>メモ一覧</h3>
　<hr>
<ul>
<% if (memoList != null && !memoList.isEmpty()) {
	for (Memo memo : memoList) { %>
  <li>
   ・<%= memo.getContent() %>
 <form action="MemoServlet" method="post" style="display:inline;">
  	<input type="hidden" name="action" value="edit">
    <input type="hidden" name="id" value="<%= memo.getMemoId() %>">
    <input type="hidden" name="period" value="<%= period %>">
    <input type="submit" value="編集">
 </form>
 <form action="MemoServlet" method="post" style="display:inline;">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="id" value="<%= memo.getMemoId() %>">
    <input type="hidden" name="period" value="<%= period %>">
    <input type="submit" value="削除">
 </form>
  </li>
 <% } } else { %>
  <li>メモは登録されていません。</li>
 <% } %>
</ul>
<hr>

<!-- メモ新規登録・編集フォーム -->
<h3>メモ新規登録</h3>
<form action="MemoServlet" method="post">
	<input type="hidden" name="period" value="<%= period %>">
    <input type="hidden" name="id" value="<%= editId %>">
    <textarea name="memo" rows="2" cols="60"><%= editContent %></textarea><br>
    <% if (editMemo != null) { %>
        <input type="submit" name="action" value="更新">
    <% } else { %>
        <input type="submit" name="action" value="register">
    <% } %>
</form>
<!-- 戻るボタン -->
<form action="BackServlet" method="get">
	<input type="submit" value="戻る">
</form>

</body>
</html>