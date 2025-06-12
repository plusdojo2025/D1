<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/info_student.css">
</head>
<body id="top">
	<header>
		<!-- ヘッダー挿入箇所 -->
	</header>

	<main>
		<!-- 基本情報 -->
		<div>やまだたろう</div>
		<div class="field">
			<div>山田太郎</div>
			<div>2年</div>
			<div>1組</div>
			<div>30番</div>
		</div>
		<div class="field">
			<div>出席率</div>
			<div>98.8%</div>
			<div>出席日数</div>
			<div>89</div>
			<div>出席すべき日数</div>
			<div>90</div>
		</div>
		<div class="field">
			<div>提出率</div>
			<div>88.%</div>
			<div>提出数</div>
			<div>15</div>
			<div>提出すべき課題数</div>
			<div>17</div>
		</div>
		<div class="field">
			<div>授業外活動</div>
			<div>休学・停学・復学・退学・留年等</div>
		</div>
		<div class="field">
			<div>サッカー部 県大会8位</div>
			<div>なし</div>
		</div>

		<!-- 科目・月選択プルダウン -->
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
		</select> <select name="month">
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
		</select>

		<div>表示項目選択 <br>
			<input type="checkbox" name="display" value="出席状況">出席状況
			<input type="checkbox" name="display" value="提出物状況">提出物状況
			<input type="checkbox" name="display" value="成績状況">成績状況 
			<input type="checkbox" name="display" value="授業態度">授業態度 
			<input type="checkbox" name="display" value="面談記録">面談記録
		</div>
		
		<div>出席状況
		</div>
	</main>
</body>
</html>