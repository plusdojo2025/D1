<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="edit_allstudent.css">
    <title>項目ごとに編集</title>
</head>

<body>
    <header></header>

    <main>
        <form action="EditAllStudentServlet.java" method ="POST" id="edit_allstudent_form">

            <select name="grede">
                <option value="1" >1年</option>
                <option value="2">2年</option>
                <option value="3">3年</option>
            </select>

            <select name="className">
                <option value="1">1組</option>
                <option value="2">2組</option>
                <option value="3">3組</option>
                <option value="4">4組</option>
                <option value="5">5組</option>
                <option value="6">6組</option>
            </select>
            
            <select name="year">
            	<option value="${year - 5}">${year - 5}</option>
                <option value="${year - 4}">${year - 4}</option>
                <option value="${year - 3}">${year - 3}</option>
                <option value="${year - 2}">${year - 2}</option>
                <option value="${year - 1}">${year - 1}</option>
                <option value="${year}" selected>${year}</option>
                <option value="${year + 1}">${year + 1}</option>
            </select>
            
            <select name="month">
            	<c:forEach var="e" begin="1" end="12">
					<c:choose>
						<c:when test="${e == month}">
							<option value="${e}" selected>${e}月</option>
						</c:when>

						<c:otherwise>
							<option value="${e}">${e}月</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
            </select>

            <select name="subjectName">
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

            <input type="submit" name="editCompleted" placeholder="編集完了" id="editCompleted" value="編集完了">

            <input type="submit" name="cancel" placeholder="キャンセル" id="cancel" value="キャンセル">


            <p>表示項目選択</p>

            <span>
                <input type="checkbox" checked value="attendance" id="attendanceCheck">
            </span>
            <span>
                出席状況
            </span>

            <span>
                <input type="checkbox" checked value="submission" id="submissionCheck">
            </span>
            <span>
                提出物状況
            </span>

            <span>
                <input type="checkbox" checked value="grades" id="gradesCheck">
            </span>
            <span>
                成績状況
            </span>


            <div id="attendance">
                <p class="attendance">出席状況</p>
                <table border="1">
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <c:forEach var="e" items="${attendanceList}">
							<td>${e.date}</td>
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

						<c:forEach var="e" items="${attendanceList}">
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
									<td><select name="attendanceDropdown"
										id="attendanceDropdown" value="${a.status}">
											<option value="attendance">◯</option>
											<option value="attendance">✕</option>
											<option value="attendance">公</option>
											<option value="attendance">早</option>
											<option value="attendance">遅</option>
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

						<c:forEach var="e" items="${assignmentsList}">
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
									<td><select name="submissionDropdown"
										id="submissionDropdown" value="${a.submissionStatus}">
											<option value="attendance">◯</option>
											<option value="attendance">✕</option>
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
                        <c:forEach var="e" items="${gradesList}">
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

                        <td>
                            <p>点数</p>
                        </td>

                        <td>
                            <p>点数</p>
                        </td>

                        <td>
                            <p>点数</p>
                        </td>
                        
                        <td>
                            <p>点数</p>
                        </td>
                    </tr>

					<c:forEach var="e" items="${studentList}">
						<tr>
							<td>${e.studentNum}</td>
							<td>${e.name}</td>
							<td>${e.nameRuby}</td>
							<c:forEach var="a" items="${gradesList}">
								<c:if test="${e.studentId == a.gradesList}">
									<td>
									<input type="text" name="score" class="score" value="${a.score}">
									</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
                </table>
            </div>





        </form>
    </main>
    <script src="list_student.js"></script>

</body>

</html>