<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>生徒登録</title>
  <link rel="stylesheet" href="<c:url value='/css/regist_student.css'/>">
</head>
<body>
<div class="wrapper">
  <h1 id="logo">
    <a href="/webapp/InfoScheduleServlet"><img src="/webapp/img/.png" width="200" height="100" alt=""></a>
  </h1>
  <ul id="nav">
    <li><a href="<c:url value='/ListStudentServlet'/>">生徒管理</a></li>
    <li><a href="<c:url value='/InfoScheduleServlet'/>">スケジュール</a></li>
    <li><a href="<c:url value='/LoginServlet'/>">ログアウト</a></li>
  </ul>

  <div class="main-content">
    <h2 class="form-title">新規登録</h2>
    <form id="regist_form" method="POST" action="<c:url value='/RegistStudentServlet'/>">
      <!-- 暗黙送信防止ダミーボタン -->
      <button type="submit" disabled style="display:none" aria-hidden="true"></button>
      <table class="form-table">
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
            <input type="number" name="studentNum" id="studentNum" placeholder="例) 23" min="1" class="form-input-short">
            <span class="suffix-text">番</span>
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
          <td colspan="2" style="text-align:center">
            <button type="button" onclick="location.href='/D1/InfoScheduleServlet'">キャンセル</button>
            <button type="submit" name="regist" id="regist_btn">登録</button>
          </td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {
  const classMap = {
    "1": {"1":13,"2":14,"3":15,"4":16,"5":17,"6":18},
    "2": {"1":7,"2":8,"3":9,"4":10,"5":11,"6":12},
    "3": {"1":1,"2":2,"3":3,"4":4,"5":5,"6":6}
  };

  const gradeSelect = document.getElementById('grade-select');
  const classSelect = document.getElementById('classId-select');
  const classIdInput = document.getElementById('classId');
  const formObj = document.getElementById('regist_form');
  const errorMessageObj = document.getElementById('error_message');

  gradeSelect.addEventListener('change', () => {
    classSelect.innerHTML = '<option value="">-- クラスを選択 --</option>';
    const g = gradeSelect.value;
    if (classMap[g]) {
      Object.entries(classMap[g]).forEach(([num, id]) => {
        const opt = document.createElement('option');
        opt.value = num;
        opt.text = num + "組";
        classSelect.appendChild(opt);
      });
    }
    classIdInput.value = "";
  });

  classSelect.addEventListener('change', () => {
    const g = gradeSelect.value;
    const c = classSelect.value;
    classIdInput.value = (classMap[g] && classMap[g][c]) ? classMap[g][c] : "";
  });

  // Enterキーでも送信トリガー but 暗黙投稿防止
  formObj.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
      e.preventDefault();
      formObj.requestSubmit(); // Enterでもsubmitイベントへ
    }
  });

  formObj.addEventListener('submit', e => {
    e.preventDefault();  // とりあえず止める
    const fullWidth = '　';
    const name = formObj.name.value.trim();
    const ruby = formObj.nameRuby.value.trim();
    let msg = '';

    if (!formObj.grade.value) msg = '※学年を選択してください';
    else if (!formObj.classId.value) msg = '※クラスを選択してください';
    else if (!formObj.studentNum.value) msg = '※出席番号を入力してください';
    else if (!formObj.name.value) msg = '※氏名を入力してください';
    else if (!name.includes(fullWidth)) msg = '※姓と名の間に全角スペースを入力してください';
    else if (!formObj.nameRuby.value) msg = '※ふりがなを入力してください';
    else if (!ruby.includes(fullWidth)) msg = '※せいとめいの間に全角スペースを入力してください';

    if (msg) {
      errorMessageObj.textContent = msg;
      return; // 停止
    }
    formObj.submit(); // バリデーションOKなら送信
  });
});
</script>
</body>
</html>