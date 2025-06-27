<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:url value="/img/header_banner.jpg" var="headerBannerUrl"/>
<c:url value="/img/IMG_ロゴ2.png" var="logo2Url"/>
<c:url value="/ListStudentServlet" var="listStudentUrl"/>
<c:url value="/InfoScheduleServlet" var="infoScheduleUrl"/>
<c:url value="/LoginServlet" var="logoutUrl"/>
<c:url value="/RegistScheduleServlet" var="registScheduleUrl"/>
<c:url value="/js/regist_student.js" var="registJsUrl"/>
<jsp:useBean id="date" class="java.util.Date"/>
<!-- Javaから受け取ったDate型の変数を指定した形式に変換するためのライブラリ -->
<%@ page import="java.util.Date" %>
<%
	Date today = new Date();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='css/common.css'/>">
<link rel="stylesheet" href="<c:url value='css/edit_student.css'/>">
</head>
<body>
	<header>
		<div style="text-align: center; margin-bottom: 20px;">
			<img src="${headerBannerUrl}" alt="バナー画像"
				style="width: 100%; max-width: 650px; height: 400px; object-fit: cover;">
		</div>
		<!-- ナビ全体を中央に寄せる -->
		<div style="text-align: center;">
			<nav
				style="display: inline-flex; align-items: center; gap: 30px; padding: 10px;">
				<!-- ロゴ画像 -->
				<img src="${logo2Url}" alt="ロゴ2"
					style="height: 50px; position: relative; top: -0.2cm;">

				<!-- メニュー -->
				<ul class="cute-menu"
					style="display: flex; list-style: none; gap: 20px;">
					<li class="menu-item-with-sub"><a class="menu-label" href="#">🐰
							生徒管理</a>
						<div class="submenu">
							<a href="${pageContext.request.contextPath}/RegistStudentServlet">➕
								生徒登録</a> <a href="${listStudentUrl}">📄 生徒一覧</a>
						</div></li>
					<li><a href="${infoScheduleUrl}">📅 スケジュール</a></li>
					<li><a href="${logoutUrl}">🚪 ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<main>
		<form method="post" action="?">
			<div class="submitButtons">
				<!-- 一覧ページから生徒IDを受け取り、データベースを検索→各項目に代入 -->
				<input type="hidden" name="studentId" value="${student.studentId}">
				<input type="hidden" name="subjectId" value="${subjectId}">
				<input type="hidden" name="grade" value="${grade}">
				<input type="hidden" name="year" value="${year}">
				<input type="hidden" name="month" value="${month}">
				<input type="submit" name="edit" value="編集完了" formaction="<c:url value='/EditStudentServlet'/>"> 
				<input type="submit" name="back" value="キャンセル" formaction="<c:url value='/InfoStudentServlet'/>">
			</div>

			<!-- 基本情報 -->
			<div class="baseInfo">
				<div style="width: 200px; padding: 2px 8px;"><input type="text" name="nameRuby" value="${student.nameRuby}" placeholder="ふりがな"></div>
				<div class="field">
					<div style="width: 200px"><input type="text" name="name" value="${student.name}" placeholder="氏名"> </div>
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
					<div class="extra" style="height: 80px">
						<textarea name="extracurricularActivities" rows=4 maxlength="200" style="width: 98%; height: 90%;">${student.extracurricularActivities}</textarea>
					</div>
					<div class="enroll" style="height: 80px">
						<textarea name="enrollmentStatus" rows=4 maxlength="200" style="width: 100%; height: 90%;">${student.enrollmentStatus}</textarea>
					</div>
				</div>
			</div><br>

			<div id="selection">
			<span id="subjectSelect"><c:choose>
				<c:when test="${subjectId == 1}">現代文</c:when>
				<c:when test="${subjectId == 2}">古典</c:when>
				<c:when test="${subjectId == 3}">数学IA</c:when>
				<c:when test="${subjectId == 4}">数学ⅡB</c:when>
				<c:when test="${subjectId == 5}">数学ⅢC</c:when>
				<c:when test="${subjectId == 6}">英語表現</c:when>
				<c:when test="${subjectId == 7}">コミュニケーション英語</c:when>
				<c:when test="${subjectId == 8}">物理</c:when>
				<c:when test="${subjectId == 9}">化学</c:when>
				<c:when test="${subjectId == 10}">生物</c:when>
				<c:when test="${subjectId == 11}">地学</c:when>
				<c:when test="${subjectId == 12}">日本史</c:when>
				<c:when test="${subjectId == 13}">世界史</c:when>
				<c:when test="${subjectId == 14}">地理</c:when>
				<c:when test="${subjectId == 15}">公民</c:when>
				<c:when test="${subjectId == 16}">情報</c:when>
				<c:when test="${subjectId == 17}">技術</c:when>
				<c:when test="${subjectId == 18}">家庭科</c:when>
				<c:when test="${subjectId == 19}">美術</c:when>
				<c:when test="${subjectId == 20}">書道</c:when>
				<c:when test="${subjectId == 21}">保健体育</c:when>
				<c:when test="${subjectId == 22}">音楽</c:when>
				<c:when test="${subjectId == 23}">学活</c:when>
				<c:when test="${subjectId == 24}">その他</c:when>
				<c:otherwise>その他</c:otherwise>
			</c:choose></span>
			<span id="gradeSelect">${grade}年次</span>
			<span id="monthSelect">${month}月</span>
			</div><br><br>

			<div class="contentTitle">表示項目選択</div>
			<div id="display">
				<label><input type="checkbox" name="display" value="出席状況" id="attendanceCheck" checked><span>出席状況</span></label>
				<label><input type="checkbox" name="display" value="提出物状況" id="submissionCheck" checked><span>提出物状況 </span></label>
				<label><input type="checkbox" name="display" value="成績状況" id="gradesCheck" checked><span>成績状況 </span></label>
				<label><input type="checkbox" name="display" value="授業態度" id="attitudeCheck" checked><span>授業態度 </span></label>
				<label><input type="checkbox" name="display" value="面談記録" id="interviewCheck" checked><span>面談記録 </span></label>
			</div><br>

			<div id="attendance" style="width: 100%;">
				<input type="hidden" name="recordAmount" value="${attendanceRecords.size()}">
				<div class="contentTitle">出席状況</div>
				<div class="rate">
					<div>出席率</div>
					<div style="border-right: 1px solid black;">${subjectAttendedRate}%</div>
				</div>
				<table style="width: 100%;">
					<tr style="width: 100%;">
						<td style="width: 14%;">日付</td>
						<td style="width: 6%;">時限</td>
						<td style="width: 6%;">出席</td>
						<td style="width: 68%;">備考</td>
					</tr>

					<c:forEach var="att" items="${attendanceRecords}" varStatus="status">
						<input type="hidden" name="recordId${status.index}" value="${att.recordId}">
						<tr>
							<td><fmt:formatDate value="${att.date}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
							<td>${att.period}</td>
							<td>
								<select name="attendedStatus${status.index}">
									<option value="${att.status}" selected>${att.status}</option>
									<option value="◯" class="attendedStatus">◯</option>
									<option value="✕" class="attendedStatus">✕</option>
									<option value="早" class="attendedStatus">早</option>
									<option value="遅" class="attendedStatus">遅</option>
									<option value="公" class="attendedStatus">公</option>
								</select>
							</td>
							<td><input type="text" name="attendanceRemarks${status.index}" value="${att.remarks}"></td>
						</tr>
					</c:forEach>
				</table>
				<br>
			</div>

			<div id="submission">
				<div class="contentTitle">提出物状況</div>
				<div class="rate">
					<div>提出率</div>
					<div style="border-right: 1px solid black;">${subjectSubmittedRate}%</div>
				</div>
				<input type="hidden" name="submissionAmount" value="${assignmentsList.size()}">
				<table>
					<tr>
						<td style="width: 100px;">課題内容</td>
						<td style="width: 100px;">提出状況</td>
						<td style="width: 100px;">提出日</td>
					</tr>

					<c:forEach var="sub" items="${assignmentsList}" varStatus="status">
						<input type="hidden" name="assignmentId${status.index}" value="${sub.assignmentId}">
						<tr>
							<td><input type="text" name="assignmentContent${status.index}" value="${sub.content}"></td>
							<td>
								<select name="submittionStatus${status.index}">
									<option value="${sub.status}" selected>${sub.submissionStatus}</option>
									<option value="◯" class="submittedStatus">◯</option>
									<option value="✕" class="submittedStatus">✕</option>
								</select>
							</td>
							<td>
								<c:if test="${sub.submissionStatus == '◯'}">
									<input type="date" name="submittedDate${status.index}" value="<fmt:formatDate value="${sub.submissionDate}" pattern="yyyy-MM-dd"></fmt:formatDate>">
								</c:if>
								<c:if test="${sub.submissionStatus == '✕'}">
									<input type="date" name="submittedDate${status.index}" value="<fmt:formatDate value="<%= today %>" pattern="yyyy-MM-dd"></fmt:formatDate>">
								</c:if>
							</td>
						</tr>
					</c:forEach>
					
					<tr id="addSubmission">
						<td style="width: 100px;"><input type="text" name="addAssignmentContent0" class="addSubmittionContent"></td>
						<td style="width: 100px;">
							<select name="addSubmittionStatus0" class="addSubmittionStatus">
								<option value="◯" class="submittedStatus">◯</option>
								<option value="✕" class="submittedStatus" selected>✕</option>
							</select>
						</td>
						<td style="width: 100px;"><input type="date" name="addSubmittedDate0" class="addSubmittionDate" value="<fmt:formatDate value="<%= today %>" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
					</tr>

				</table>
				<input type="button" value="＋" onclick="addSubmission()" class="addButton">
				<input type="hidden" name="addSubmittionAmount" id="addSubmittionAmount" value="1">
				<br><br>
			</div>

			<div id="grades">
				<input type="hidden" name="gradesAmount" value="${gradesList.size()}">
				<div class="contentTitle">成績状況</div>
				<table>
					<tr>
						<td style="width: 100px;">種別</td>
						<td style="width: 80px;">点数</td>
					</tr>

					<c:forEach var="gra" items="${gradesList}" varStatus="status">
					<input type="hidden" name="gradeId${status.index}" value="${gra.gradesId}">
						<tr>
							<td><input type="text" name="gradeTestType${status.index}" value="${gra.testType}"></td>
							<td><input type="number" name="gradeScore${status.index}" value="${gra.score}"></td>
						</tr>
					</c:forEach>
					
					<tr id="addGrade">
						<td><input type="text" name="addGradeTestType0" class="addGradeTestType"></td>
						<td><input type="number" name="addGradeScore0" class="addGradeScore"></td>
					</tr>
				</table>
				<input type="button" value="＋" onclick="addGrade()" class="addButton">
				<input type="hidden" name="addGradesAmount" id="addGradesAmount" value="0">
				<br><br>
			</div>

			<div id="attitude" style="width: 100%;">
				<div class="contentTitle">授業態度</div>
				<table style="width: 100%;">
					<tr style="width: 100%;">
						<td style="width: 50%;">授業態度</td>
						<td style="width: 50%;">前年度授業態度</td>
					</tr>
					<tr>
						<td>
							<textarea name="attitude" rows=4 maxlength="200" style="width: 90%; height: 90%;">${student.attitude}</textarea>
						</td>
						<td>
							<textarea name="attitude" rows=4 maxlength="200" style="width: 90%; height: 90%;">${student.attitude}</textarea>
						</td>
					</tr>
				</table>
				<br>
			</div>

			<div id="interview" style="width: 100%;">
				<div class="contentTitle">面談記録</div>
				<input type="hidden" name="interviewAmount" value="${interviewList.size()}">
				<table style="width: 100%;">
					<tr>
						<td style="width: 14%;">日付</td>
						<td style="width: 43%;">内容</td>
						<td style="width: 43%;">備考</td>
					</tr>

					<c:forEach var="itv" items="${interviewList}" varStatus="status">
						<input type="hidden" name="interviewId${status.index}" value="${itv.interviewId}">
						<tr>
							<td><input type="date" name="interviewDate${status.index}" value="<fmt:formatDate value="${itv.date}" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
							<td><input type="text" name="interviewContents${status.index}" value="${itv.contents}"></td>
							<td><input type="text" name="interviewRemarks${status.index}" value="${itv.remarks}"></td>
						</tr>
					</c:forEach>
					
					<tr id="addInterview">
						<td><input type="date" name="addInterviewDate0" class="addInterviewDate" value="<fmt:formatDate value="<%= today %>" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
						<td><input type="text" name="addInterviewContents0" class="addInterviewContents"></td>
						<td><input type="text" name="addInterviewRemarks0" class="addInterviewRemarks"></td>
					</tr>
				</table>
				<input type="button" value="＋" onclick="addInterview()" class="addButton">
				<input type="hidden" name="addInterviewAmount" id="addInterviewAmount" value="1">
				<br><br>
				
				<div class="contentTitle">前年度面談記録</div>
				<input type="hidden" name="lastInterviewAmount" value="${lastInterviewList.size()}">
				<table style="width: 100%;">
					<tr style="width: 100%;">
						<td style="width: 14%;">日付</td>
						<td style="width: 43%;">内容</td>
						<td style="width: 43%;">備考</td>
					</tr>

					<c:forEach var="itv" items="${lastInterviewList}" varStatus="status">
						<input type="hidden" name="lastInterviewId${status.index}" value="${itv.interviewId}">
						<tr>
							<td><input type="date" name="lastInterviewDate${status.index}" value="<fmt:formatDate value="${itv.date}" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
							<td><input type="text" name="lastInterviewContents${status.index}" value="${itv.contents}"></td>
							<td><input type="text" name="lastInterviewRemarks${status.index}" value="${itv.remarks}"></td>
						</tr>
					</c:forEach>
					
					<tr id="addLastInterview">
						<td><input type="date" name="addLastInterviewDate0" class="addLastInterviewDate" value="<fmt:formatDate value="<%= today %>" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
						<td><input type="text" name="addLastInterviewContents0" class="addLastInterviewContents"></td>
						<td><input type="text" name="addLastInterviewRemarks0" class="addLastInterviewRemarks"></td>
					</tr>
				</table>
				<input type="button" value="＋" onclick="addLastInterview()" class="addButton">
				<input type="hidden" name="addLastInterviewAmount" id="addLastInterviewAmount" value="1">
				<br><br>
			</div>

		</form>
	</main>

	<footer> </footer>

	<script src="<c:url value='/js/info_student.js' />"></script>
	<script src="<c:url value='/js/edit_student.js' />"></script>
	<script>
	console.log(${assignmentsList.size()});
	</script>
</body>
</html>