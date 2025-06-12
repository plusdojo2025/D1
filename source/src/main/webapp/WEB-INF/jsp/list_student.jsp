<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
        <form action="EditAllStudentServlet.java">

            <select name="grede">
                <option value="firstGrade" >1年</option>
                <option value="secondGrade">2年</option>
                <option value="thirdGrade">3年</option>
            </select>

            <select name="class">
                <option value="firstclass">1組</option>
                <option value="secondclass">2組</option>
                <option value="thirdclass">3組</option>
                <option value="thirdclass">4組</option>
                <option value="thirdclass">5組</option>
                <option value="thirdclass">6組</option>
            </select>

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

            <input type="submit" name="edit" placeholder="編集" id="edit" value="編集">

            <input type="submit" name="download" placeholder="ダウンロード" id="download" value="ダウンロード">


            <p>表示項目選択</p>

            <span>
                <input type="checkbox" checked value="attendance" id="attendanceCheckbox">
            </span>
            <span>
                出席状況
            </span>

            <span>
                <input type="checkbox" checked value="submission" id="submissionCheckbox">
            </span>
            <span>
                提出物状況
            </span>

            <span>
                <input type="checkbox" checked value="grades" id="gradesCheckbox">
            </span>
            <span>
                成績状況
            </span>


            <div id="attendanceList">
                <p>出席状況</p>
                <table border="1">
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <c:forEach var="e" items="${studentList}">
                        	<td>
                            	${e.data}
                        	</td>
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

						<c:forEach var="e" items="${studentList}">
							<td>${e.period}限</td>
						</c:forEach>
					</tr>

					<tr>
						<c:forEach var="e" items="${studentList}">
							<td>${e.studentNum}限</td>
						</c:forEach>
                        <td>
                            <p>1</p>
                        </td>

                        <td>
                            <p>相沢</p>
                        </td>

                        <td>
                            <p>あいざわ</p>
                        </td>

                        <td>
                            <p>◯</p>
                        </td>

                        <td>
                            <p>◯</p>
                        </td>

                        <td>
                            <p>公</p>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="submissionList">
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

                        <td>
                            <p>1</p>
                        </td>

                        <td>
                            <p>2</p>
                        </td>

                        <td>
                            <p>3</p>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <p>1</p>
                        </td>

                        <td>
                            <p>相沢</p>
                        </td>

                        <td>
                            <p>あいざわ</p>
                        </td>

                        <td>
                            <p>◯</p>
                        </td>

                        <td>
                            <p>✕</p>
                        </td>

                        <td>
                            <p>◯</p>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="gradesList">
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
                        <td>
                            <p>1学期中間</p>
                        </td>

                        <td>
                            <p>1学期期末</p>
                        </td>

                        <td>
                            <p>小テスト</p>
                        </td>
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
                    </tr>

                    <tr>
                        <td>
                            <p>1</p>
                        </td>

                        <td>
                            <p>相沢</p>
                        </td>

                        <td>
                            <p>あいざわ</p>
                        </td>

                        <td>
                            80
                        </td>

                        <td>
                            76
                        </td>

                        <td>
                            60
                        </td>
                    </tr>
                </table>
            </div>





        </form>
    </main>

    <script>
        /*チェックボックスで表示切替*/
        if (attendanceCheckbox.checked) {
            console.log('出席状況');
        } else {
            document.getElementById('attendanceList').textContent = "出席状況";
        }

        if (submissionCheckbox.checked) {
            console.log('提出物状況');
        } else {
            document.getElementById('submissionList').textContent = "出席状況";
        }

        if (gradesCheckbox.checked) {
            console.log('成績状況');
        } else {
            document.getElementById('gradesList').textContent = "出席状況";
        }

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


</body>

</html>