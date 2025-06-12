<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header></header>
	<main>
        <form action="EditAllStudentServlet.java">

            <select name="grede">
                <option value="firstGrade" name>1年</option>
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

            <select name="script">
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
                <option value="thirdclass">日本史</option>
                <option value="thirdclass">世界史</option>
                <option value="thirdclass">地理</option>
                <option value="thirdclass">公民</option>
                <option value="thirdclass">情報</option>
                <option value="thirdclass">技術</option>
                <option value="thirdclass">家庭科</option>
                <option value="thirdclass">美術</option>
                <option value="thirdclass">書道</option>
                <option value="thirdclass">保健体育</option>
                <option value="thirdclass">学活</option>
                <option value="thirdclass">その他</option>
            </select>







        </form>
    </main>

</body>
</html>