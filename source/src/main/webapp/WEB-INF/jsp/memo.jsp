<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, dto.Memo" %>
<%!

/* クラスIDを「〇年〇組」に変換 */
public String formatClassId(String classId) {
    try {
        int id = Integer.parseInt(classId);
        int grade = id / 10;
        int group = id % 10;
        return grade + "年" + group + "組";
    } catch (Exception e) {
        return "不明";
    }
}
/* yyyy-MM-dd → 曜日に変換 */
public String getWeekday(String dateStr) {
    try {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse(dateStr);
        String[] week = { "日", "月", "火", "水", "木", "金", "土" };
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return week[cal.get(java.util.Calendar.DAY_OF_WEEK) - 1] + "曜";
    } catch (Exception e) {
        return "不明";
    }
}
%>

<%
/* ―――― サーブレットから受け取る値 ―――― */
List<Memo> memoList     = (List<Memo>) request.getAttribute("memoList");
String classId          = (String) request.getAttribute("classId");
String period           = (String) request.getAttribute("period");
String date             = (String) request.getAttribute("date");
String teacherId        = (String) request.getAttribute("teacherId");
String memoIdStr        = (String) request.getAttribute("memoId");     // 選択中メモ
String memoContent      = (String) request.getAttribute("memoContent");// 選択中メモ内容
String subjectName      = (String) request.getAttribute("subjectName");// 科目名
boolean isEditing       = memoIdStr != null && !memoIdStr.isEmpty();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>授業のメモ欄</title>
<link rel="stylesheet" type="text/css" href="css/memo.css">
</head>
<body>
<div class="memo-wrapper">

<h2>授業のメモ欄</h2>

<!-- ① 授業ヘッダー（クラス／曜日／時限／科目名）  -->
<p>
  クラス：<%= formatClassId(classId) %>&nbsp;
  曜日：<%= getWeekday(date) %>&nbsp;
  時限：<%= period != null ? period + "限" : "不明" %>&nbsp;
  科目名：<%= subjectName != null ? subjectName : "不明" %>
</p>

<!-- ② メモ一覧 -->
<h3>メモ一覧</h3>
<hr>
<ul>
<% if (memoList != null && !memoList.isEmpty()) {
     for (Memo memo : memoList) { %>
     <li>
       ・<%= memo.getContent() %>
       （<%= new java.text.SimpleDateFormat("yyyy/MM/dd").format(memo.getDate()) %>）
       <!-- 削除ボタン -->
       <form action="MemoServlet" method="post" style="display:inline;">
           <input type="hidden" name="action"    value="delete">
           <input type="hidden" name="memoId"    value="<%= memo.getMemoId() %>">
           <input type="hidden" name="classId"   value="<%= classId %>">
           <input type="hidden" name="period"    value="<%= period %>">
           <input type="hidden" name="date"      value="<%= date %>">
           <input type="hidden" name="teacherId" value="<%= teacherId %>">
           <input type="submit" value="削除">
       </form>
     </li>
<% } } else { %>
     <li>メモは登録されていません。</li>
<% } %>
</ul>

<!-- ③ メモ登録フォーム（更新のみ） -->
<h3>メモ新規登録</h3>
<form action="MemoServlet" method="post">
    <input type="hidden" name="action"  value="update">
    <input type="hidden" name="memoId"  value="<%= isEditing ? memoIdStr : "" %>">
    <input type="hidden" name="classId" value="<%= classId %>">
    <input type="hidden" name="period"  value="<%= period %>">
    <input type="hidden" name="date"    value="<%= date %>">
    <input type="hidden" name="teacherId" value="<%= teacherId %>">

    <textarea name="content" rows="4" cols="200"><%= memoContent != null ? memoContent : "" %></textarea><br>
    <button type="submit">更新</button>
</form>

<!-- ④ 戻るボタン -->
<form action="InfoScheduleServlet" method="get">
    <input type="hidden" name="classId" value="<%= classId %>">
    <input type="submit" value="戻る">
</form>

</div>
</body>
</html>