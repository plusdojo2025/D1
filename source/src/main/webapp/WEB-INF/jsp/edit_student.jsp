<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Javaから受け取ったDate型の変数を指定した形式に変換するためのライブラリ -->
<%@ page import="java.util.Date" %>
<%
	Date today = new Date();
%>
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
		<form method="post" action="?">
			<!-- 一覧ページから生徒IDを受け取り、データベースを検索→各項目に代入 -->
			<input type="hidden" name="studentId" value="${student.studentId}">
			<input type="hidden" name="subjectId" value="${subjectId}">
			<input type="hidden" name="grade" value="${grade}">
			<input type="hidden" name="year" value="${year}">
			<input type="hidden" name="month" value="${month}">
			<input type="submit" name="edit" value="編集完了" formaction='/D1/EditStudentServlet'> 
			<input type="submit" name="back" value="キャンセル" formaction='/D1/InfoStudentServlet'>

			<!-- 基本情報 -->
			<div class="baseInfo">
				<div><input type="text" name="nameRuby" value="${student.nameRuby}" placeholder="ふりがな"></div>
				<div class="field">
					<div><input type="text" name="name" value="${student.name}" placeholder="氏名"> </div>
					<div>${student.grade}年</div>
					<div>${className}</div>
					<div>${student.studentNum}番</div>
				</div>
				<div class="field">
					<div>今学期 出席率</div>
					<div>${attendedRate}%</div>
					<div>出席日数</div>
					<div>${attendedNum}</div>
					<div>出席すべき日数</div>
					<div>${shouldAttendNum}</div>
				</div>
				<div class="field">
					<div>今学期 提出率</div>
					<div>${submittedRate}%</div>
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
					<div><input type="text" name="extracurricularActivities" value="${student.extracurricularActivities}" placeholder="授業外活動"></div>
					<div><input type="text" name="enrollmentStatus" value="${student.enrollmentStatus}" placeholder="在籍状況"></div>
				</div>
			</div><br>

			<span><c:choose>
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
			<span>${grade}年次</span>
			<span>${month}月</span><br><br>

			<div id="display">表示項目選択 <br> 
			<label><input type="checkbox" name="display" value="出席状況" id="attendanceCheck" checked>出席状況</label>
			<label><input type="checkbox" name="display" value="提出物状況" id="submissionCheck" checked>提出物状況</label> 
			<label><input type="checkbox" name="display" value="成績状況" id="gradesCheck" checked>成績状況</label> 
			<label><input type="checkbox" name="display" value="授業態度" id="attitudeCheck" checked>授業態度</label>
			<label><input type="checkbox" name="display" value="面談記録" id="interviewCheck" checked>面談記録</label>
			</div>
			<br>

			<div id="attendance">
				出席状況 <span>出席率</span> <span>${subjectAttendedRate}%</span>
				<table>
					<tr>
						<td>日付</td>
						<td>時限</td>
						<td>出席</td>
						<td>備考</td>
					</tr>

					<c:forEach var="att" items="${attendanceRecords}" varStatus="status">
						<input type="hidden" name="recordAmount" value="${attendanceRecords.size()}">
						<input type="hidden" name="recordId${status.index}" value="${att.recordId}">
						<tr>
							<td><fmt:formatDate value="${att.date}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
							<td>${att.period}</td>
							<td>
								<select name="attendedStatus${status.index}">
									<option value="◯" class="attendedStatus" selected>◯</option>
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
				提出物状況 <span>提出率</span> <span>${subjectSubmittedRate}%</span>
				<table>
					<tr>
						<td>課題内容</td>
						<td>提出状況</td>
						<td>提出日</td>
					</tr>

					<c:forEach var="sub" items="${assignmentsList}" varStatus="status">
						<input type="hidden" name="assignmentId${status.index}" value="${sub.assignmentId}">
						<tr>
							<td><input type="text" name="assignmentContent${status.index}" value="${sub.content}"></td>
							<td>
								<select name="submittionStatus${status.index}">
									<option value="◯" class="submittedStatus">◯</option>
									<option value="✕" class="submittedStatus">✕</option>
								</select>
							</td>
							<td><input type="date" name="submittionDate${status.index}" value="<fmt:formatDate value="${sub.submissionDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
						</tr>
					</c:forEach>
					
					<!-- 空白欄 -->
					<tr id="addSubmission">
						<td><input type="text" name="addAssignmentContent0"></td>
						<td>
							<select name="addSubmittionStatus0">
								<option value="◯" class="submittedStatus0">◯</option>
								<option value="✕" class="submittedStatus0" selected>✕</option>
							</select>
						</td>
						<td><input type="date" name="addSubmittionDate0" value="<fmt:formatDate value="<%= today %>" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
					</tr>

				</table>
				<input type="button" value="＋" onclick="addSubmission()">
				<br>
			</div>

			<div id="grades">
				成績状況 <br>
				<table>
					<tr>
						<td>種別</td>
						<td>点数</td>
					</tr>

					<c:forEach var="gra" items="${gradesList}">
					<input type="hidden" name="gradeId" value="${gra.gradeId}">
						<tr>
							<td><input type="text" name="gradeTestType" value="${gra.testType}"></td>
							<td><input type="number" name="gradeScore" value="${gra.score}"></td>
						</tr>
					</c:forEach>
					
					<!-- 空白欄 -->
				</table>
				<!-- 欄追加ボタン -->
				<br>
			</div>


			<div id="attitude">
				授業態度 <br>
				<table>
					<tr>
						<td>授業態度</td>
						<td>前年度授業態度</td>
					</tr>
					<tr>
						<td><input type="text" name="attitude" value="${student.attitude}"></td>
						<td><input type="text" name="attitude" value="${student.attitude}"></td>
					</tr>
				</table>
				<br>
			</div>

			<div id="interview">
				面談記録 <br>
				<table>
					<tr>
						<td>日付</td>
						<td>内容</td>
						<td>備考</td>
					</tr>

					<c:forEach var="itv" items="${interviewList}">
						<input type="hidden" name="interviewId" value="${itv.interviewId}">
						<tr>
							<td><input type="date" name="interviewDate" value="<fmt:formatDate value="${itv.date}" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
							<td><input type="text" name="interviweContents" value="${itv.contents}"></td>
							<td><input type="text" name="interviweRemarks" value="${itv.remarks}"></td>
						</tr>
					</c:forEach>
				</table>
				<br> 前年度面談記録 <br>
				<table>
					<tr>
						<td>日付</td>
						<td>内容</td>
						<td>備考</td>
					</tr>

					<c:forEach var="itv" items="${lastInterviewList}">
						<input type="hidden" name="lastInterviewId" value="${itv.interviewId}">
						<tr>
							<td><input type="date" name="lastInterviewDate" value="<fmt:formatDate value="${itv.date}" pattern="yyyy-MM-dd"></fmt:formatDate>"></td>
							<td><input type="text" name="lastInterviweContents" value="${itv.contents}"></td>
							<td><input type="text" name="lastInterviweRemarks" value="${itv.remarks}"></td>
						</tr>
					</c:forEach>
				</table>
				<br>
			</div>

		</form>
	</main>

	<footer> </footer>

	<script src="js/info_student.js"></script>
	<script src="js/edit_student.js"></script>
</body>
</html>