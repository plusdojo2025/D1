<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>授業のメモ欄</title>
</head>
<body>
<!-- メイン -->
	<h2>授業のメモ欄</h2>
	
<!-- メモ一覧表示 -->

<!-- メモ新規登録・編集フォーム -->

<form action="MemoServlet" method="post">
 <input type="hidden" name="action" value="${formMode}"/>
 <input type="hidden" name="id" value="${editingMemo}"/>
  <p>[ <input type="text" name="memo1" value="${editingMemo.memo1}" size="80"> ]</p>
  <p>[ <input type="text" name="memo2" value="${editingMemo.memo2}" size="80"> ]</p>
  <p>[ <input type="text" name="memo3" value="${editingMemo.memo3}" size="80"> ]</p>
 
 <input type="submit" name="submit" value="登録">
 <input type="submit" name="submit" value="更新">
 <input type="submit" name="submit" value="削除">
</form>

<!-- 戻るボタン -->
<form action="MenuServlet" method="get">
  <input type="submit" value="戻る">
</form>
	
</body>
</html>