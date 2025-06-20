<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>生徒登録</title>
  <link rel="stylesheet" href="<c:url value='/css/regist_student.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/header.css'/>">

</head>
<body>
<div class="wrapper">
  
  <div class="main-content">
    
    <form id="regist_form" method="POST" action="<c:url value='/RegistStudentServlet'/>">
    <h2 class="form-title">新規登録</h2>
      <!-- 暗黙送信防止ダミーボタン -->
      <button type="submit" disabled style="display:none" aria-hidden="true"></button>
      <table class="form-table">
        <tr>
          <td><label  for="year">入学年（半角数字のみ）</label></td>
          <td>
          <div class="with-suffix">
           <input type="number" name="year" id="year" placeholder="例) 2023" class="form-input">
            <span class="suffix-text">年</span>
           
          </div>
        </tr>
        <tr>
          <td><label for="grade-select">学年</label></td>
          <td>
            <select name="grade" id="grade-select" class="form-input">
              <option value="">-- 学年を選択 --</option>
              <option value="1">1年</option>
              <option value="2">2年</option>
              <option value="3">3年</option>
            </select>
          </td>
        </tr>
        <tr>
          <td><label for="classId-select">クラス</label></td>
          <td>
            <select name="classNum" id="classId-select" class="form-input">
              <option value="">-- クラスを選択 --</option>
            </select>
            <input type="hidden" id="classId" name="classId" value="">
          </td>
        </tr>
        <tr>
          <td><label>出席番号（半角数字のみ）</label></td>
          <td>
           <div class="with-suffix">
            <input type="number" name="studentNum" id="studentNum" placeholder="例) 23" min="1" class="form-input-short">
            <span class="suffix-text">番</span>
            </div>
          </td>
        </tr>
        <tr>
          <td><label for="name">氏名</label></td>
          <td><input type="text" name="name" id="name" placeholder="例) 山田　太郎" class="form-input"></td>
        </tr>
        <tr>
          <td><label for="nameRuby">ふりがな</label></td>
          <td><input type="text" name="nameRuby" id="nameRuby" placeholder="例) やまだ　たろう" class="form-input"></td>
        </tr>
        <tr>
          <td colspan="2">
            <p><span class="zenkaku">※【氏名・ふりがな】は姓名の間に全角1文字空けてください</span></p>
            <p><span class="hankaku">※【入学年・出席番号】は半角数字で入力してください</span></p>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <span id="error_message" style="color:red">
              <c:if test="${not empty error}">${error}</c:if>
            </span>
          </td>
        </tr>
        <tr>
          <td colspan="2">
           <div class="button-area">
            <button type="button" onclick="location.href="<c:url value='/InfoScheduleServlet'/>">キャンセル</button>
            <button type="submit" name="regist" id="regist_btn">登録</button>
           </div> 
          </td>
        </tr>
      </table>
    </form>
  </div>
</div>
<!-- メイン（ここまで） -->

  <!-- フッター（ここから） -->
  <div id="footer">
    <p>&copy; MATINY</p> 
    
  </div>
  <!-- フッター（ここまで） -->

	<script src="<c:url value='/js/regist_student.js'/>"> </script>
	<script>
	'use strict';
	</script>

</body>
</html>