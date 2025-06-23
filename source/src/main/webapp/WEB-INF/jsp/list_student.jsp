<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>


<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value='/css/list_student.css' />">
<title>項目ごとに閲覧</title>
</head>

<body>
	<header></header>

	<main>
	
		<form action="<c:url value='/ListStudentServlet'/>" method="POST"
			id="list_student_form">

			
			<input type="hidden" name="number" value="${subjectId}}"> <select
				name="grade" onchange="this.form.submit()">
				<option>${grade}</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
			</select>
			<spsan>年</span> <select name="className"
				onchange="this.form.submit()">
				<option>${className}</option>
				<option value="1組">1組</option>
				<option value="2組">2組</option>
				<option value="3組">3組</option>
				<option value="4組">4組</option>
				<option value="5組">5組</option>
				<option value="6組">6組</option>
			</select> <select name="year" onchange="this.form.submit()">
				<c:forEach var="e" begin="0" end="6">
					<c:choose>
						<c:when test="${yearNow + e - 5 == year}">
							<option value="${yearNow + e - 5}" selected>${yearNow + e - 5}</option>
						</c:when>

						<c:otherwise>
							<option value="${yearNow + e - 5}">${yearNow + e - 5}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> <span>年</span> <select name="month" onchange="this.form.submit()">
				<c:forEach var="e" begin="1" end="12">
					<c:choose>
						<c:when test="${e == month}">
							<option value="${e}" selected>${e}</option>
						</c:when>

						<c:otherwise>
							<option value="${e}">${e}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> <span>月</span> <select name="subjectName" id="dropSubject"
				onchange="this.form.submit()">
				<option>${subjectName}</option>
				<option value="現代文">現代文</option>
				<option value="古典">古典</option>
				<option value="数学IA">数学IA</option>
				<option value="数学ⅡB">数学ⅡB</option>
				<option value="数学ⅢC">数学ⅢC</option>
				<option value="英語表現">英語表現</option>
				<option value="コミュニケーション英語">コミュニケーション英語</option>
				<option value="物理">物理</option>
				<option value="化学">化学</option>
				<option value="生物">生物</option>
				<option value="地学">地学</option>
				<option value="日本史">日本史</option>
				<option value="世界史">世界史</option>
				<option value="地理">地理</option>
				<option value="公民">公民</option>
				<option value="情報">情報</option>
				<option value="技術">技術</option>
				<option value="家庭科">家庭科</option>
				<option value="美術">美術</option>
				<option value="書道">書道</option>
				<option value="保健体育">保健体育</option>
				<option value="音楽">音楽</option>
				<option value="学活">学活</option>
				<option value="その他">その他</option>
			</select> 
			<input type="submit" name="edit" placeholder="編集" id="edit" value="編集"> 
			<input type="submit" name="download" placeholder="ダウンロード" id="download" value="ダウンロード">


			<p>表示項目選択</p>

			<span> <input type="checkbox" checked value="attendance"
				id="attendanceCheck">
			</span> <span> 出席状況 </span> <span> <input type="checkbox" checked
				value="submission" id="submissionCheck">
			</span> <span> 提出物状況 </span> <span> <input type="checkbox" checked
				value="grades" id="gradesCheck">
			</span> <span> 成績状況 </span>


			<div id="attendance">
				<p>出席状況</p>
				<table border="1">
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<c:forEach var="e" items="${attendanceDateList}">
							<td><fmt:formatDate value="${e.date}" type="DATE"
									pattern="dd日" /></td>
						</c:forEach>
					</tr>

					<tr>
						<td>
							<p>出席番号</p>
						</td>
						<td>
							<p>氏名</p>
						</td>
						<td>
							<p>ふりがな</p>
						</td>

						<c:forEach var="e" items="${attendanceDateList}">
							<td>${e.period}限</td>
						</c:forEach>
					</tr>


					<c:forEach var="e" items="${studentList}">
						<tr>
							<td>${e.studentNum}</td>
							<!-- <td><a href="InfoStudentServlet">${e.name}</a></td>  -->
							<td><input type="submit" name="studentNum" placeholder="${e.name}" id="studentNum" value="${e.name}"></td>
							
							<td>${e.nameRuby}</td>
							<c:forEach var="a" items="${attendanceList}">
								<c:if test="${e.studentId == a.studentId}">
									<td>${a.status}</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>

				</table>
			</div>

			<div id="submission">
				<span> 提出物状況 </span> <span>
					<button id="submissionButton" type="button">課題追加</button>
					<div id="submissionModal" class="modal">
						<div class="modal-content">
							<span class="close">&times;</span> <select name="subject">
								<option value="現代文">現代文</option>
								<option value="古典">古典</option>
								<option value="数学IA">数学IA</option>
								<option value="数学ⅡB">数学ⅡB</option>
								<option value="数学ⅢC">数学ⅢC</option>
								<option value="英語表現">英語表現</option>
								<option value="コミュニケーション英語">コミュニケーション英語</option>
								<option value="物理">物理</option>
								<option value="化学">化学</option>
								<option value="生物">生物</option>
								<option value="地学">地学</option>
								<option value="日本史">日本史</option>
								<option value="世界史">世界史</option>
								<option value="地理">地理</option>
								<option value="公民">公民</option>
								<option value="情報">情報</option>
								<option value="技術">技術</option>
								<option value="家庭科">家庭科</option>
								<option value="美術">美術</option>
								<option value="書道">書道</option>
								<option value="保健体育">保健体育</option>
								<option value="音楽">音楽</option>
								<option value="学活">学活</option>
								<option value="その他">その他</option>
							</select> <span> 課題内容 </span> <span> <input type="text"
								name="submissionAdd">
							</span><br> <span>
								<button id="return">戻る</button>
							</span> <span> <input type="submit" value="課題追加" name="add">
							</span>
						</div>
					</div>
				</span>

				<table border="1">

					<tr>
						<td>
							<p>出席番号</p>
						</td>

						<td>
							<p>氏名</p>
						</td>

						<td>
							<p>ふりがな</p>
						</td>

						<c:forEach var="e" items="${contentList}">
							<td>${e.content}</td>
						</c:forEach>
					</tr>

					<c:forEach var="e" items="${studentList}">
						<tr>
							<td>${e.studentNum}</td>
							<td><input type="submit" name="studentNum" placeholder="${e.name}" id="studentNum" value="${e.name}"></td>
							<td>${e.nameRuby}</td>
							<c:forEach var="a" items="${assignmentsList}">
								<c:if test="${e.studentId == a.studentId}">
									<td>${a.submissionStatus}</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</div>

			<div id="grades">
				<span> 成績状況 </span> <span>
					<button id="gradesButton">テスト追加</button>
				</span>

				<table border="1">
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<c:forEach var="e" items="${testTypeList}">
							<td>${e.testType}</td>
						</c:forEach>
					</tr>

					<tr>
						<td>
							<p>出席番号</p>
						</td>

						<td>
							<p>氏名</p>
						</td>

						<td>
							<p>ふりがな</p>
						</td>

						<c:forEach var="e" begin="1" end="${testTypeListSize}">
							<td>
								<p>点数</p>
							</td>
						</c:forEach>
					</tr>

					<c:forEach var="e" items="${studentList}">
						<tr>
							<td>${e.studentNum}</td>
							<td><input type="submit" name="studentNum" placeholder="${e.name}" id="studentNum" value="${e.name}"></td>
							<td>${e.nameRuby}</td>
							<c:forEach var="a" items="${gradesList}">
								<c:if test="${e.studentId == a.studentId}">
									<td>${a.score}</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</main>
	<script src="<c:url value='/js/list_student.js' />"></script>
	

</body>

</html>