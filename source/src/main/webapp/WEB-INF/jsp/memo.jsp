<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, dto.Memo" %>
<%  
	List<Memo> memoList = (List<Memo>) request.getAttribute("memoList");	
	String classId = (String) request.getAttribute("classId");
	String period = (String) request.getAttribute("period");
	String date = (String) request.getAttribute("date");
	String teacherId = (String) request.getAttribute("teacherId");
	String memoIdStr = (String) request.getAttribute("memoId");
	String memoContent = (String) request.getAttribute("memoContent");
	boolean isEditing = memoIdStr != null && !memoIdStr.isEmpty();
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
 <p>クラスID:<%= classId %> 時限:<%= period %> 日付:<%= date %></p>
 
 <!-- メモ一覧表示 -->
 <h3>メモ一覧</h3>
 <hr>
<ul>
<% if (memoList != null && !memoList.isEmpty()) {
	for (Memo memo : memoList) { %>
		<li>
			・<%= memo.getContent() %>(<%= new java.text.SimpleDateFormat("yyyy/MM/dd").format(memo.getDate()) %>）
 			<form action="MemoServlet" method="get" style="display:inline;">
			  	<input type="hidden" name="classId" value="<%= classId %>">
                <input type="hidden" name="period" value="<%= period %>">
                <input type="hidden" name="date" value="<%= date %>">
                <input type="hidden" name="teacherId" value="<%= teacherId %>">
                <input type="hidden" name="memoId" value="<%= memo.getMemoId() %>">
                <input type="hidden" name="memoContent" value="<%= memo.getContent() %>">
                <input type="submit" value="編集">
 			</form>
 			<form action="MemoServlet" method="post" style="display:inline;">
			    <input type="hidden" name="action" value="delete">
                <input type="hidden" name="memoId" value="<%= memo.getMemoId() %>">
                <input type="hidden" name="classId" value="<%= classId %>">
                <input type="hidden" name="period" value="<%= period %>">
                <input type="hidden" name="date" value="<%= date %>">
                <input type="hidden" name="teacherId" value="<%= teacherId %>">
                <input type="submit" value="削除">
 			</form>
  		</li>
 <% } } else { %>
  	<li>メモは登録されていません。</li>
 <% } %>
</ul>

<!-- メモ新規登録・編集フォーム -->
<h3>メモ新規登録・編集</h3>
<form action="MemoServlet" method="post">
	<input type="hidden" name="action" value="save">
    <input type="hidden" name="memoId" value="<%= isEditing ? memoIdStr : "" %>">
    <input type="hidden" name="classId" value="<%= classId %>">
    <input type="hidden" name="period" value="<%= period %>">
    <input type="hidden" name="date" value="<%= date %>">
    <input type="hidden" name="teacherId" value="<%= teacherId %>">
    
    <% String[] lines = (memoContent != null ? memoContent : "").split("\n", -1); %>
    <textarea name="content" rows="1"><%= lines.length > 0 ? lines[0] : "" %></textarea><br>
    <textarea name="content" rows="1"><%= lines.length > 1 ? lines[1] : "" %></textarea><br>
    <textarea name="content" rows="1"><%= lines.length > 2 ? lines[2] : "" %></textarea><br>
    
    <% if (isEditing) { %>
    	<button type="submit" name="action" value="update">更新</button>
        <button type="submit" name="action" value="delete">削除</button>
	<% } else { %>
    	<button type="submit" name="action" value="save">登録</button>
    <% } %>
</form>
<!-- 戻るボタン -->
<form action="InfoScheduleServlet" method="post">
	<input type="hidden" name="year" value="<%= date != null && date.length() >= 4 ? date.substring(0, 4) : "2025" %>">
    <input type="hidden" name="semester" value="前期">
    <input type="submit" value="戻る">
</form>

</body>
</html>