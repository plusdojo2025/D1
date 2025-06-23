<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/css/edit_allstudent.css' />">
    <title>項目ごとに編集</title>
</head>

<body>
    <header></header>

    <main>
        <form action="<c:url value='/EditAllStudentServlet' />" method ="POST" id="edit_allstudent_form">

			<c:out value="${grade}" />年
			<c:out value="${className}" />
			<c:out value="${year}" />年
			<c:out value="${month}" />月
			<c:out value="${subjectName}" />
			
			<input type="hidden" name="grade" id="editCompleted" value="${grade}">
			<input type="hidden" name="className" id="editCompleted" value="${className}">
			<input type="hidden" name="year" id="editCompleted" value="${year}">
			<input type="hidden" name="month" id="editCompleted" value="${month}">
			<input type="hidden" name="subjectName" id="editCompleted" value="${subjectName}">
			
            <input type="submit" name="editCompleted" id="editCompleted" value="編集完了">
            <input type="submit" name="cancel" id="cancel" value="キャンセル">


            <p>表示項目選択</p>

            <span><input type="checkbox" checked value="attendance" id="attendanceCheck"></span>
            <span>出席状況</span>

            <span><input type="checkbox" checked value="submission" id="submissionCheck"></span>
            <span>提出物状況</span>

            <span><input type="checkbox" checked value="grades" id="gradesCheck"></span>
            <span>成績状況</span>


            <div id="attendance">
                <p class="attendance">出席状況</p>
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
							<td>${e.name}</td>
							<td>${e.nameRuby}</td>
							<c:forEach var="a" items="${attendanceList}">
								<c:if test="${e.studentId == a.studentId}">
									<td><select name="${a.recordId}record" id="attendanceDropdown">
											<option>${a.status}</option>
											<option value="◯">◯</option>
											<option value="✕">✕</option>
											<option value="公">公</option>
											<option value="早">早</option>
											<option value="遅">遅</option>
									</select></td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
            </div>

            <div id="submission">
                <p class="submission">提出物状況</p>
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
							<td>${e.name}</td>
							<td>${e.nameRuby}</td>
							<c:forEach var="a" items="${assignmentsList}">
								<c:if test="${e.studentId == a.studentId}">
									<td><select name="${a.assignmentId}assign" id="submissionDropdown">
											<option>${a.submissionStatus}</option>
											<option value="◯">◯</option>
											<option value="✕">✕</option>
									</select></td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
            </div>

            <div id="grades">
                <p class="grades">成績状況</p>
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
							<td>${e.name}</td>
							<td>${e.nameRuby}</td>
							<c:forEach var="a" items="${gradesList}">
								<c:if test="${e.studentId == a.studentId}">
									<td>
										<input type="text" name="${a.gradesId}grades" class="score" value="${a.score}">
									</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
                </table>
            </div>





        </form>
    </main>
    <script src="<c:url value='/js/edit_student.js' />"></script>

</body>

</html>