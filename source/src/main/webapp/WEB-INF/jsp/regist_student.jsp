<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生徒登録</title>
<!-- CSSファイルをリンク -->
<link rel="stylesheet" type="text/css" href="/webapp/css/common.css"> 
</head>
<body>
<div class="wrapper">
	<!-- ヘッダー（ここから） -->
	<h1 id="logo">
    <a href="/webapp/InfoScheduleServlet"><img src="/webapp/img/.png" width="200" height="100" alt=""></a>
  </h1>


    <ul id="nav">
    <li><a href="/webapp/Servlet">生徒管理</a></li>
    <li><a href="/webapp/InfoScheduleServlet">スケジュール</a></li>
    <li><a href="/webapp/LoginServlet">ログアウト</a></li>
    </ul>
  <!-- ヘッダー（ここまで） -->


<!-- メイン（ここから） -->
<h2>新規登録</h2>
<form id="regist_form" method="POST" action="/webapp/RegistStudentServlet">
  <table>
	  <tr>
		 <td>
		    <label for="grade-select">学年<br>
		     <select name="grade" id="grade-select">
                      <option value="">学年</option>
                      <option value="1">1年</option>
                      <option value="2">2年</option>
                      <option value="3">3年</option>
             </select>         
		    </label>

		 </td>
          </tr>   
	  	 
	  <tr>
		 <td>	
		    <label for="classId-select">クラス<br>
		     <select name="classId" id="classId-select">
                      <option value="">クラス</option>
                      <option value="1">1組</option>
                      <option value="2">2組</option>
                      <option value="3">3組</option>
                      <option value="4">4組</option>
                      <option value="5">5組</option>
                      <option value="6">6組</option>
             </select>         
		    </label>
		 </td>  
	  </tr>

	  <tr> 
		 <td>
		    <label>出席番号<br>
		    <input type="text" name="studentNum">
		    </label>
		</td>
          </tr>
          <tr>
		 <td>
		    <label>氏名<br>
		    <input type="text" name="name" placeholder="姓と名の間に全角1文字空けてください">
		    </label>
		 </td> 
	  </tr>
	  <tr>	  
		 <td>
		    <label>ふりがな<br>
		    <input type="text" name="nameRuby" placeholder="せいとめいの間に全角1文字空けてください">
		    </label>
		 </td>	    			    
	  </tr>
	  <tr>
		 <td colspan="2">
            <span id="error_message"></span>
            <input type="submit" name="cancel" value="キャンセル">
            <input type="submit" name="regist" value="登録">	    			         </td>
          </tr>
  </table>
</form>     
<!-- メイン（ここまで） -->

<!-- フッター（ここから） -->

<!-- フッター（ここまで） -->
</div>

<!-- JavaScript（ここから） -->
<script>


/* HTML要素をオブジェクトとして取得する */
	
let formObj = document.getElementById('regist_form');
let errorMessageObj = document.getElementById('error_message');

const nameInput = document.getElementById('name');
const nameRubyInput = document.getElementById('nameRuby');


/* [登録]ボタンをクリックしたときの処理 */
formObj.onsubmit = function() {
  /* クラスを必須選択項目とします */
  if (!formObj.classId.value) {
    errorMessageObj.textContent = '※クラスを選択してください';
    return false;
  }
  /* 出席番号を必須入力項目とします */
  if (!formObj.studentNum.value) {
    errorMessageObj.textContent = '※出席番号を入力してください';
    return false;
  }

  /* 入力時に全角スペースを挿入する関数 */
  function insertFullWidthSpace(inputElement) {
    inputElement.addEventListener('input', function() {
      let value = inputElement.value.trim();

      /* 姓と名の間に全角スペースがない場合、全角スペースを挿入 */
      if (!/　/.test(value) && value.length > 0) {
        const words = value.split(/\s+/);
        if (words.length > 1) {
          inputElement.value = words.join('　'); // 全角スペースで結合
        }
      }
    });
  }

  // 入力欄に対して全角スペース挿入機能を適用
  insertFullWidthSpace(nameInput);
  insertFullWidthSpace(nameRubyInput);


  /* 氏名を必須入力項目とします */
  if (!formObj.name.value) {
    errorMessageObj.textContent = '※氏名を入力してください';
    return false;
  }

  /* ふりがなを必須入力項目とします */
  if (!formObj.nameRuby.value) {
    errorMessageObj.textContent = '※ふりがなを入力してください';
    return false;
  }

  
  errorMessageObj.textContent = null;
};

/* [キャンセル]ボタンをクリックしたときの処理 */
formObj.onreset = function() {
  errorMessageObj.textContent = null;
};



</script>
<!-- JavaScript（ここまで） -->  
</body>
</html>