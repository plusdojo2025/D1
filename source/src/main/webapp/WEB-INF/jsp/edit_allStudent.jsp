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
                        <td>
                            <p>1日</p>
                        </td>

                        <td>
                            <p>2日</p>
                        </td>

                        <td>
                            <p>3日</p>
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
                            <p>1限</p>
                        </td>

                        <td>
                            <p>3限</p>
                        </td>

                        <td>
                            <p>5限</p>
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
                            <select name="attendanceDropdown" id="attendanceDropdown">
                                <option value="attendance">◯</option>
                                <option value="attendance">✕</option>
                                <option value="attendance">公</option>
                                <option value="attendance">早</option>
                                <option value="attendance">遅</option>
                            </select>
                        </td>

                        <td>
                            <select name="attendanceDropdown" id="attendanceDropdown">
                                <option value="attendance">◯</option>
                                <option value="attendance">✕</option>
                                <option value="attendance">公</option>
                                <option value="attendance">早</option>
                                <option value="attendance">遅</option>
                            </select>
                        </td>

                        <td>
                            <select name="attendanceDropdown" id="attendanceDropdown">
                                <option value="attendance">◯</option>
                                <option value="attendance">✕</option>
                                <option value="attendance">公</option>
                                <option value="attendance">早</option>
                                <option value="attendance">遅</option>
                            </select>
                        </td>
                    </tr>
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
                            <select name="submissionDropdown" id="submissionDropdown">
                                <option value="attendance">◯</option>
                                <option value="attendance">✕</option>
                            </select>
                        </td>

                        <td>
                            <select name="submissionDropdown" id="submissionDropdown">
                                <option value="attendance">◯</option>
                                <option value="attendance">✕</option>
                            </select>
                        </td>

                        <td>
                            <select name="submissionDropdown" id="submissionDropdown">
                                <option value="attendance">◯</option>
                                <option value="attendance">✕</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="grades">
                <p class="grades">成績状況</p>
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
                            <input type="text" name="score" class="score">
                        </td>

                        <td>
                            <input type="text" name="score" class="score">
                        </td>

                        <td>
                            <input type="text" name="score" class="score">
                        </td>
                    </tr>
                </table>
            </div>





        </form>
    </main>
    <script src="list_student.js"></script>

</body>

</html>