<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% int i = 0; %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/list_student.css">
    <title>項目ごとに閲覧</title>
</head>

<body>
    <header></header>

    <main>
        <form action="EditAllStudentServlet.java" method ="POST" id="list_student_form">

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

            <input type="submit" name="edit" placeholder="編集" id="edit" value="編集">

            <input type="submit" name="download" placeholder="ダウンロード" id="download" value="ダウンロード">


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
                <p>出席状況</p>
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
									<td>${a.status}</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>

				</table>
			</div>

            <div id="submission">
                <span>
                    提出物状況
                </span>
                <span>
                    <button id="submissionButton" type="button">課題追加</button>
                    <div id="submissionModal" class="modal">
                        <div class="modal-content">
                            <span class="close">&times;</span>
                            <select name="subject">
                                <option value="modernSentence">現代文</option>
                                <option value="classic">古典</option>
                                <option value="mathematicsIA">数学IA</option>
                                <option value="mathematicsⅡB">数学ⅡB</option>
                                <option value="mathematicsⅢC">数学ⅢC</option>
                                <option value="englishExpression">英語表現</option>
                                <option value="communicationEnglish">コミュニケーション英語</option>
                                <option value="physics">物理</option>
                                <option value="chemistry">化学</option>
                                <option value="biology">生物</option>
                                <option value="geology">地学</option>
                                <option value="japaneseHistory">日本史</option>
                                <option value="worldHistory">世界史</option>
                                <option value="geography">地理</option>
                                <option value="citizens">公民</option>
                                <option value="information">情報</option>
                                <option value="technology">技術</option>
                                <option value="homeEconomics">家庭科</option>
                                <option value="art">美術</option>
                                <option value="calligraphy">書道</option>
                                <option value="physicalEducation">保健体育</option>
                                <option value="music">音楽</option>
                                <option value="academicActivities">学活</option>
                                <option value="others">その他</option>
                            </select>

                            <span>
                                課題内容
                            </span>
                            <span>
                                <input type="text" name="submissionAdd">
                            </span><br>

                            <span>
                                <button id="return">戻る</button>
                            </span>
                            <span>
                                <input type="submit" value="課題追加" name="add">
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
									<td>${a.submissionStatus}</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
                </table>
            </div>

            <div id="grades">
                <span>
                    成績状況
                </span>
                <span>
                    <button id="gradesButton">テスト追加</button>
                </span>

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
									<td>${a.score}</td>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
                </table>
            </div>

        </form>
    </main>

    <script>
        
        /*モーダル*/
        const submissionbutton = document.getElementById("submissionButton");
        const submissionModal = document.getElementById("submissionModal");
        const closeButton = document.getElementsByClassName("close")[0];

        // モーダルを開く関数
        function openModal() {
            modal.style.display = "block";
        }

        // モーダルを閉じる関数
        function closeModal() {
            modal.style.display = "none";
        }

        // ボタンクリックでモーダルを開くイベントリスナーの設定
        submissionbutton.addEventListener("click", openModal);

        // 閉じるボタンクリックでモーダルを閉じるイベントリスナーの設定
        closeButton.addEventListener("click", closeModal);
    </script>

	<script src="js/list_student.js"></script>
</body>

</html>