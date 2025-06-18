<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生徒登録</title>
<!-- CSSファイルをリンク -->
<link rel="stylesheet"  href="<c:url value='/css/common.css'/>">
<link rel="stylesheet"  href="<c:url value='/css/regist_student.css'/>">
</head>
<body>
<div class="wrapper">
	<!-- ヘッダー（ここから） -->
	<h1 id="logo">
    <a href="/webapp/InfoScheduleServlet"><img src="/webapp/img/.png" width="200" height="100" alt=""></a>
  </h1>


    <ul id="nav">
      <li><a href="<c:url value='/ListStudentServlet'/>">生徒管理</a></li>
      <li><a href="<c:url value='/InfoScheduleServlet'/>">スケジュール</a></li>
      <li><a href="<c:url value='/LoginServlet'/>">ログアウト</a></li>
    </ul>
  <!-- ヘッダー（ここまで） -->


<!-- メイン（ここから） -->
<h2>新規登録</h2>
<form id="regist_form" method="POST" action="<c:url value='/RegistStudentServlet'/>">
  <table>
	  <tr>
		 <td>
		    <label for="grade-select">学年
		     <select name="grade" id="grade-select">
                      <option value="">-- 学年を選択 --</option>
                      <option value="1">1年</option>
                      <option value="2">2年</option>
                      <option value="3">3年</option>
             </select>         
		    </label>

		 </td>
          </tr>   
	  	 
	  <tr>
		 <td>	
		    <label for="classId-select">クラス</label>
		     <select name="classNum" id="classId-select">
                      <option value="">-- クラスを選択 --</option>

             </select>
            <!--classIdを送信用に保持-->
            <input type="hidden" id="classId" name="classId" value="">          
		    
		 </td>  
	  </tr>

	  <tr> 
		 <td>
		    <label>出席番号（数字は半角のみ）
		    <input type="number" name="studentNum" placeholder="例) 23" min="1"> 番
		    </label>
		 </td>
      </tr>
      <tr>
		 <td>
		    <label>氏名
		    <input type="text" name="name" placeholder="例) 山田　太郎">
		    </label>
		 </td> 
	  </tr>
	  <tr>	  
		 <td>
		    <label>ふりがな
		    <input type="text" name="nameRuby" placeholder="例) やまだ　たろう">
		    </label>
		 </td>	    			    
	  </tr>
	  <tr>
	     <td>
	        <p><span class="zenkaku">※【氏名・ふりがな】は姓名の間に全角1文字空けてください</span></p>
	     </td>
	  </tr>
	  <tr>
		 <td colspan="2">
            <span id="error_message">
               <c:if test="${not empty error}">
                <span style="color:red;">${error}</span>   
               </c:if>
            </span>  
         </td>
      </tr>
      <tr>
         <td>
            <input type="submit" name="cancel" value="キャンセル" onclick="window.location.href='/D1/InfoScheduleServlet';">
            <input type="submit" name="regist" value="登録">
         </td>
      </tr>
  </table>
</form>     
<!-- メイン（ここまで）syussek -->

<!-- フッター（ここから） -->

<!-- フッター（ここまで） -->
</div>

<!-- JavaScript（ここから） -->
<script>

document.addEventListener("DOMContentLoaded", function () {
  const classMap = {
    "1": { "1": 13, "2": 14, "3": 15, "4": 16, "5": 17, "6": 18 },
    "2": { "1": 7,  "2": 8,  "3": 9,  "4": 10, "5": 11, "6": 12 },
    "3": { "1": 1,  "2": 2,  "3": 3,  "4": 4,  "5": 5,  "6": 6  }
  };

  const gradeSelect = document.getElementById('grade-select');
  const classSelect = document.getElementById('classId-select');
  const classIdInput = document.getElementById('classId');
  const formObj = document.getElementById('regist_form');
  const errorMessageObj = document.getElementById('error_message');

  gradeSelect.addEventListener('change', function () {
    const selectedGrade = this.value;
    classSelect.innerHTML = '<option value="">-- クラスを選択 --</option>';

    if (classMap[selectedGrade]) {
      for (let i = 1; i <= 6; i++) {
        const strI = i.toString();
        const classId = classMap[selectedGrade][strI];
        if (classId !== undefined) {
          const option = document.createElement('option');
          option.value = strI;
          option.text = strI + "組";
          classSelect.appendChild(option);
        }
      }
    }

    classIdInput.value = "";
  });

  classSelect.addEventListener('change', function () {
    const selectedGrade = gradeSelect.value;
    const selectedClass = this.value;

    if (selectedGrade && selectedClass && classMap[selectedGrade][selectedClass]) {
      classIdInput.value = classMap[selectedGrade][selectedClass];
    } else {
      classIdInput.value = "";
    }
  });

  formObj.onsubmit = function () {
    const fullWidthSpace = '　'; // 全角スペース
	const name = formObj.name.value.trim();
    const nameRuby = formObj.nameRuby.value.trim();	  
	  
    if (!formObj.grade.value) {
      errorMessageObj.textContent = '※学年を選択してください';
      return false;
    }
    if (!formObj.classId.value) {
      errorMessageObj.textContent = '※クラスを選択してください';
      return false;
    }
    if (!formObj.studentNum.value) {
      errorMessageObj.textContent = '※出席番号を入力してください';
      return false;
    }
    if (!formObj.name.value) {
      errorMessageObj.textContent = '※氏名を入力してください';
      return false;
    }  
    if (!name.includes(fullWidthSpace)) {
      errorMessageObj.textContent = '※姓と名の間に全角スペースを入力してください';
      return false;
    }
    if (!formObj.nameRuby.value) {
      errorMessageObj.textContent = '※ふりがなを入力してください';
      return false;
    }
    if (!nameRuby.includes(fullWidthSpace)) {
      errorMessageObj.textContent = '※せいとめいの間に全角スペースを入力してください';
      return false;
    }
    errorMessageObj.textContent = null;
  };

});

</script>
<!-- JavaScript（ここまで） -->  
</body>
</html>