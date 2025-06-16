<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="date" class="java.util.Date"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/info_student.css">
</head>
<body>
	<header>
		<!-- ヘッダー挿入箇所 -->
	</header>

	<main>
	<!-- 一覧ページから生徒IDを受け取り、データベースを検索→各項目に代入 -->
		<form method="get" action="?">
			<input type="hidden" name="number" value="${student.studentId}}">
			<input type="submit" name="edit" value="編集" formaction='/D1/EditStudentServlet'>
			<input type="submit" name="back" value="戻る" formaction='/D1/ListStudentServlet'>
		</form>
		
		<!-- 基本情報 -->
		<div class="baseInfo">
			<div>${student.nameRuby}</div>
			<div class="field">
				<div>${student.name}</div>
				<div>${student.grade}年</div>
				<div>${className}組</div>
				<div>${student.studentNum}番</div>
			</div>
			<div class="field">
				<div>出席率</div>
				<div>${attendedRate}</div>
				<div>出席日数</div>
				<div>${attendedNum}</div>
				<div>出席すべき日数</div>
				<div>${shouldAttendNum}</div>
			</div>
			<div class="field">
				<div>提出率</div>
				<div>${submittedRate}</div>
				<div>提出数</div>
				<div>${submittedNum}</div>
				<div>提出すべき課題数</div>
				<div>${shouldSubmitNum}</div>
			</div>
			<div class="field">
				<div>授業外活動</div>
				<div>休学・停学・復学・退学・留年等</div>
			</div>
			<div class="field">
				<div>${student.extracurricularActivities}</div>
				<div>${student.enrollmentStatus}</div>
			</div>
		</div>

		<!-- 科目・月選択プルダウン -->
		<select name="subject" id="subject" onchange="subjectChange()">
			<option value="modernSentence" class="subject">現代文</option>
			<option value="classic" class="subject">古典</option>
			<option value="mathematicsIA" class="subject">数学IA</option>
			<option value="mathematicsIIB" class="subject">数学ⅡB</option>
			<option value="mathematicsIIIC" class="subject">数学ⅢC</option>
			<option value="englishExpression" class="subject">英語表現</option>
			<option value="communicationEnglish" class="subject">コミュニケーション英語</option>
			<option value="physics" class="subject">物理</option>
			<option value="chemistry" class="subject">化学</option>
			<option value="biology" class="subject">生物</option>
			<option value="geology" class="subject">地学</option>
			<option value="japaneseHistory" class="subject">日本史</option>
			<option value="worldHistory" class="subject">世界史</option>
			<option value="geography" class="subject">地理</option>
			<option value="citizens" class="subject">公民</option>
			<option value="information" class="subject">情報</option>
			<option value="technology" class="subject">技術</option>
			<option value="homeEconomics" class="subject">家庭科</option>
			<option value="art" class="subject">美術</option>
			<option value="calligraphy" class="subject">書道</option>
			<option value="physicalEducation" class="subject">保健体育</option>
			<option value="music" class="subject">音楽</option>
			<option value="academicActivities" class="subject">学活</option>
			<option value="others" class="subject">その他</option>
		</select>
		
		<select name="year">
			<option value="1">1年次</option> 
			<option value="2">2年次</option>
			<option value="3">3年次</option>
			<!-- 入学年度（登録年度）と登録時の学年を元に、データがある年次だけ表示する -->
		</select>
		
		<select name="month">
			<option value="1">1月</option>
			<option value="2">2月</option>
			<option value="3">3月</option>
			<option value="4">4月</option>
			<option value="5">5月</option>
			<option value="6">6月</option>
			<option value="7">7月</option>
			<option value="8">8月</option>
			<option value="9">9月</option>
			<option value="10">10月</option>
			<option value="11">11月</option>
			<option value="12">12月</option>
		</select><br>

		<div id="display">表示項目選択 <br>
			<label><input type="checkbox" name="display" value="出席状況" id="attendanceCheck" checked>出席状況</label>
			<label><input type="checkbox" name="display" value="提出物状況" id="submissionCheck" checked>提出物状況</label>
			<label><input type="checkbox" name="display" value="成績状況" id="gradesCheck" checked>成績状況</label>
			<label><input type="checkbox" name="display" value="授業態度" id="attitudeCheck" checked>授業態度</label>
			<label><input type="checkbox" name="display" value="面談記録" id="interviewCheck" checked>面談記録</label>
		</div><br>
		
		<div id="attendance">出席状況
			<span>出席率</span>
			<span>${subjectAttendedRate}</span>
			<table>
				<tr>
					<td>日付</td>
					<td>曜日</td>
					<td>時限</td>
					<td>出席</td>
					<td>備考</td>
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
		
		<div id="submission">提出物状況
			<span>提出率</span>
			<span>${subjectSubmittedRate}</span>
			<table>
				<tr>
					<td>課題内容</td>
					<td>提出状況</td>
					<td>提出日</td>
				</tr>
				
				<c:forEach var="sub" items="${assignmentsList}">
				<tr>
					<td>${sub.content}</td>
					<td>${sub.submissionStatus}</td>
					<td>${sub.submissionDate}</td>
				</tr>
				</c:forEach>
			</table><br>
		</div>
		
		<div id="grades">成績状況 <br>
			<table>
				<tr>
					<td>種別</td>
					<td>点数</td>
					<td>平均点</td>
				</tr>
				
				<c:forEach var="gra" items="${gradesList}">
				<tr>
					<td>${gra.testType}</td>
					<td>${gra.score}</td>
					<td>${average}</td>
				</tr>
				</c:forEach>
			</table><br>
		</div>
		
		<div id="attitude">授業態度 <br>
			<table>
				<tr>
					<td>授業態度</td>
					<td>前年度授業態度</td>
				</tr>
				<tr>
					<td>${student.attitude}</td>
					<td></td>
				</tr>
			</table><br>
		</div>
		
		<div id="interview">
			面談記録 <br>
			<table>
				<tr>
					<td>日付</td>
					<td>曜日</td>
					<td>内容</td>
					<td>備考</td>
				</tr>
				
				<c:forEach var="itv" items="${interviewList}">
				<tr>
					<td>${itv.date}</td>
					<td>${itv.week}</td>
					<td>${itv.contents}</td>
					<td>${itv.remarks}</td>
				</tr>
				</c:forEach>
			</table><br>
			
			前年度面談記録 <br>
			<table>
				<tr>
					<td>日付</td>
					<td>曜日</td>
					<td>内容</td>
					<td>備考</td>
				</tr>
				
				<c:forEach var="itv" items="${lastInterviewList}">
				<tr>
					<td>${itv.date}</td>
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
	
	<script src="js/info_student.js"></script>
</body>
</html>