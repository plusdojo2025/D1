<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="date" class="java.util.Date"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='css/common.css'/>">
<link rel="stylesheet" href="<c:url value='css/info_student.css'/>">
</head>
<body>
	<header>
		<div class="wrapper">
			<div style="text-align: center; margin-bottom: 20px;">
				<img src="<%=request.getContextPath()%>/img/header_banner.jpg"
					alt="バナー画像"
					style="width: 100%; max-width: 650px; height: 400px; object-fit: cover;">
			</div>
			<nav>
				<ul class="cute-menu">
					<li><a href="<c:url value='/ListStudentServlet'/>">🐰 生徒管理</a></li>
					<li><a href="<c:url value='/InfoScheduleServlet'/>">📅 スケジュール</a></li>
					<li><a href="<c:url value='/LoginServlet'/>">🚪 ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<main>
	<!-- 一覧ページから生徒IDを受け取り、データベースを検索→各項目に代入 -->
		<form method="get" action="?" class="submitButtons">
			<input type="hidden" name="studentId" value="${student.studentId}">
			<input type="hidden" name="subjectId" value="${subjectId}">
			<input type="hidden" name="grade" value="${grade}">
			<input type="hidden" name="year" value="${year}">
			<input type="hidden" name="month" value="${month}">
			<input type="submit" name="edit" value="編集" formaction="<c:url value='/EditStudentServlet' />">
			<input type="submit" name="back" value="戻る" formaction="<c:url value='/ListStudentServlet' />">
		</form>
		
		<!-- 基本情報 -->
		<div class="baseInfo">
			<div style="font-size: 15px; padding: 5px 7px;">${student.nameRuby}</div>
			<div class="field">
				<div style="width: 150px">${student.name} </div>
				<div style="width: 70px">${student.grade}年</div>
				<div style="width: 70px">${className}</div>
				<div style="width: 70px">${student.studentNum}番</div>
			</div>
			<div class="field">
				<div class="rateExplain">今学期 出席率</div>
				<div class="rate">${attendedRate}%</div>
				<div class="amountExplain">出席日数</div>
				<div class="amount">${attendedNum}</div>
				<div class="shouldAmountExplain">出席すべき日数</div>
				<div class="shouldAmount">${shouldAttendNum}</div>
			</div>
			<div class="field">
				<div class="rateExplain">今学期 提出率</div>
				<div class="rate">${submittedRate}%</div>
				<div class="amountExplain">提出数</div>
				<div class="amount">${submittedNum}</div>
				<div class="shouldAmountExplain">提出すべき課題数</div>
				<div class="shouldAmount">${shouldSubmitNum}</div>
			</div>
			<div class="field">
				<div class="extra">授業外活動</div>
				<div class="enroll">休学・停学・復学・退学・留年等</div>
			</div>
			<div class="field">
				<div class="extra" style="height: 80px">${student.extracurricularActivities}</div>
				<div class="enroll" style="height: 80px">${student.enrollmentStatus}</div>
			</div>
		</div><br>

		<!-- 科目・月選択プルダウン -->
		<!-- 初期値を一覧ページで選択した値にする -->
		<form action="<c:url value='/InfoStudentServlet'/>" method="post" id="pulldowns">
		<select name="subjectId" id="subjectSelect" onchange="this.form.submit()" >
			<option value="1" class="subject">現代文</option>
			<option value="2" class="subject">古典</option>
			<option value="3" class="subject">数学IA</option>
			<option value="4" class="subject">数学ⅡB</option>
			<option value="5" class="subject">数学ⅢC</option>
			<option value="6" class="subject">英語表現</option>
			<option value="7" class="subject">コミュニケーション英語</option>
			<option value="8" class="subject">物理</option>
			<option value="9" class="subject">化学</option>
			<option value="10" class="subject">生物</option>
			<option value="11" class="subject">地学</option>
			<option value="12" class="subject">日本史</option>
			<option value="13" class="subject">世界史</option>
			<option value="14" class="subject">地理</option>
			<option value="15" class="subject">公民</option>
			<option value="16" class="subject">情報</option>
			<option value="17" class="subject">技術</option>
			<option value="18" class="subject">家庭科</option>
			<option value="19" class="subject">美術</option>
			<option value="20" class="subject">書道</option>
			<option value="21" class="subject">保健体育</option>
			<option value="22" class="subject">音楽</option>
			<option value="23" class="subject">学活</option>
			<option value="24" class="subject">その他</option>
		</select>
		
		<select name="grade" id="gradeSelect" onchange="this.form.submit()">
			<c:forEach var="i" begin="1" end="3">
				<option value="${i}" class="grade">${i}年次</option> 
			</c:forEach>
		</select>
		
		<select name="month" id="monthSelect" onchange="this.form.submit()">
			<c:forEach var="i" begin="1" end="12">
			<option value="${i}" class="month">${i}月</option>
			</c:forEach>
		</select><br>
		</form><br>
		

		<div class="contentTitle">表示項目選択</div>
		<div id="display">
			<label><input type="checkbox" name="display" value="出席状況" id="attendanceCheck" checked><span>出席状況</span></label>
			<label><input type="checkbox" name="display" value="提出物状況" id="submissionCheck" checked><span>提出物状況 </span></label>
			<label><input type="checkbox" name="display" value="成績状況" id="gradesCheck" checked><span>成績状況 </span></label>
			<label><input type="checkbox" name="display" value="授業態度" id="attitudeCheck" checked><span>授業態度 </span></label>
			<label><input type="checkbox" name="display" value="面談記録" id="interviewCheck" checked><span>面談記録 </span></label>
		</div><br>
		
		<div id="attendance" style="width: 100%;">
			<div class="contentTitle">出席状況</div>
			<div class="rate">
				<div>出席率</div>
				<div style="border-right: 1px solid black;">${subjectAttendedRate}%</div>
			</div>
			<table style="width: 100%;">
				<tr style="width: 100%;">
					<td style="width: 14%;">日付</td>
					<td style="width: 6%;">曜日</td>
					<td style="width: 6%;">時限</td>
					<td style="width: 6%;">出席</td>
					<td style="width: 68%;">備考</td>
				</tr>
				
				<c:forEach var="att" items="${attendanceRecords}">
				<tr>
					<td><fmt:formatDate value="${att.date}" pattern="yyyy/MM/dd"></fmt:formatDate></td>
					<td>${att.week}</td>
					<td>${att.period}</td>
					<td>${att.status}</td>
					<td>${att.remarks}</td>
				</tr>
				</c:forEach>
			</table><br>
		</div>
		
		<div id="submission">
			<div class="contentTitle">提出物状況</div>
			<div class="rate">
				<div>提出率</div>
				<div style="border-right: 1px solid black;">${subjectSubmittedRate}%</div>
			</div>
			<table>
				<tr>
					<td style="width: 100px;">課題内容</td>
					<td style="width: 100px;">提出状況</td>
					<td style="width: 100px;">提出日</td>
				</tr>
				
				<c:forEach var="sub" items="${assignmentsList}">
				<tr>
					<td>${sub.content}</td>
					<td>${sub.submissionStatus}</td>
					<td>
						<c:if test="${sub.submissionStatus == '◯'}">
							<fmt:formatDate value="${sub.submissionDate}" pattern="yyyy/MM/dd"></fmt:formatDate>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</table><br>
		</div>
		
		<div id="grades">
			<div class="contentTitle">成績状況</div>
			<table>
				<tr>
					<td style="width: 100px;">種別</td>
					<td style="width: 80px;">点数</td>
					<td style="width: 80px;">平均点</td>
				</tr>
				
				<c:forEach var="gra" items="${gradesList}" varStatus="status">
				<tr>
					<td>${gra.testType}</td>
					<td>${gra.score}</td>
					<td>${average[status.index]}</td>
				</tr>
				</c:forEach>
			</table><br>
		</div>
		
		<div id="attitude" style="width: 100%;">
			<div class="contentTitle">授業態度</div>
			<table style="width: 100%;">
				<tr style="width: 100%;">
					<td style="width: 50%;">授業態度</td>
					<td style="width: 50%;">前年度授業態度</td>
				</tr>
				<tr>
					<td style="height: 80px;">${student.attitude}</td>
					<td style="height: 80px;"></td>
				</tr>
			</table><br>
		</div>
		
		<div id="interview"  style="width: 100%;">
			<div class="contentTitle">面談記録</div>
			<table  style="width: 100%;">
				<tr>
					<td style="width: 14%;">日付</td>
					<td style="width: 6%;">曜日</td>
					<td style="width: 40%;">内容</td>
					<td style="width: 40%;">備考</td>
				</tr>
				
				<c:forEach var="itv" items="${interviewList}">
				<tr>
					<td><fmt:formatDate value="${itv.date}" pattern="yyyy/MM/dd"></fmt:formatDate></td>
					<td>${itv.week}</td>
					<td>${itv.contents}</td>
					<td>${itv.remarks}</td>
				</tr>
				</c:forEach>
			</table><br>
			
			<div class="contentTitle">前年度面談記録</div>
			<table style="width: 100%;">
				<tr style="width: 100%;">
					<td style="width: 14%;">日付</td>
					<td style="width: 6%;">曜日</td>
					<td style="width: 40%;">内容</td>
					<td style="width: 40%;">備考</td>
				</tr>
				
				<c:forEach var="itv" items="${lastInterviewList}">
				<tr>
					<td><fmt:formatDate value="${itv.date}" pattern="yyyy/MM/dd"></fmt:formatDate></td>
					<td>${itv.week}</td>
					<td>${itv.contents}</td>
					<td>${itv.remarks}</td>
				</tr>
				</c:forEach>
			</table><br>
		</div>	
		 
	</main>
	
	<footer>
	</footer>
	
	<script src="<c:url value='/js/info_student.js' />"></script>
	<script>
	'use strict';
	// 前画面で設定した学年・月・科目を持ち越す
	let subject = '${subjectId}';
	var subjects = document.getElementsByClassName("subject");
	for (var i = 0; i < subjects.length; i++) {
		if (subjects[i].value === subject) {
			subjects[i].setAttribute('selected', 'selected');
		}
	}
	
	let grade = '${grade}';
	var selectGrades = document.getElementsByClassName("grade");
	for (var i = 0; i < selectGrades.length; i++) {
		if (selectGrades[i].value === grade) {
			selectGrades[i].setAttribute('selected', 'selected');
		}
	}
	
	let month = '${month}';
	var months = document.getElementsByClassName("month");
	for (var i = 0; i < months.length; i++) {
		if (months[i].value === month) {
			months[i].setAttribute('selected', 'selected');
		}
	}
	</script>
</body>
</html>