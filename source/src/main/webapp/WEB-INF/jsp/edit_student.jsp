<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<!-- ヘッダー挿入箇所 -->
	</header>

	<main>
	<form method="post" action="?">
	
		<!-- 基本情報 -->
		<div class="baseInfo">
			<input type="text" name="">やまだたろう
			<div class="field">
				<input type="text" name="name">山田太郎
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
				<input type="text" name="extracurricularActivities">サッカー部 県大会8位
				<input type="text" name="enrollmentStatus">なし
			</div>
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
		</select>
		
		<select name="month">
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

		<div id="display">表示項目選択 <br>
			<label><input type="checkbox" name="display" value="出席状況" id="attendanceCheck" checked>出席状況</label>
			<label><input type="checkbox" name="display" value="提出物状況" id="submissionCheck" checked>提出物状況</label>
			<label><input type="checkbox" name="display" value="成績状況" id="gradesCheck" checked>成績状況</label>
			<label><input type="checkbox" name="display" value="授業態度" id="attitudeCheck" checked>授業態度</label>
			<label><input type="checkbox" name="display" value="面談記録" id="interviewCheck" checked>面談記録</label>
		</div>
		
		<div id="attendance">出席状況
			<span>出席率</span>
			<span>90.0％</span>
			<table>
				<tr>
					<td>日付</td>
					<td>曜日</td>
					<td>時限</td>
					<td>出席</td>
					<td>備考</td>
				</tr>
				
				<!-- 繰り返し処理 -->
				<tr>
					<td><input type="hidden" name="recordId"><input type="text" name="date"></td>
					<td><input type="text" name="week"></td>
					<td><input type="text" name="period"></td>
					<td><input type="text" name="status"></td>
					<td><input type="text" name="remarks"></td>
				</tr>
			</table>
		</div>
		
		<div id="submission">提出物状況
			<span>提出率</span>
			<span>50.0％</span>
			<table>
				<tr>
					<td>課題内容</td>
					<td>提出状況</td>
					<td>提出日</td>
				</tr>
				
				<!-- 繰り返し処理 -->
				<tr>
					<td><input type="hidden" name="assignmentId"><input type="text" name="content"></td>
					<td><input type="text" name="submissionStatus"></td>
					<td><input type="text" name="submissionDate"></td>
				</tr>
			</table>
		</div>
		
		<div id="grades">成績状況 <br>
			<table>
				<tr>
					<td>種別</td>
					<td>点数</td>
					<td>平均点</td>
				</tr>
				
				<!-- 繰り返し処理 -->
				<tr>
					<td>中間考査</td>
					<td>88</td>
					<td>65</td>
				</tr>
			</table>
		</div>
		
		<div id="attitude">授業態度 <br>
			<table>
				<tr>
					<td>授業態度</td>
					<td>前年度授業態度</td>
				</tr>
				<tr>
					<td>特になし</td>
					<td>特になし</td>
				</tr>
			</table>
		</div>
		
		<div id="interview">
			面談記録 <br>
			<table>
				<tr>
					<td>日付</td>
					<td>曜日</td>
					<td>内容</td>
					<td>備考</td>
				</tr>
				
				<!-- 繰り返し処理 -->
				<tr>
					<td>05/15</td>
					<td>木</td>
					<td>三者面談</td>
					<td>特になし</td>
				</tr>
			</table>
			
			前年度面談記録 <br>
			<table>
				<tr>
					<td>日付</td>
					<td>曜日</td>
					<td>内容</td>
					<td>備考</td>
				</tr>
				
				<!-- 繰り返し処理 -->
				<tr>
					<td>05/15</td>
					<td>木</td>
					<td>進路面談</td>
					<td>ABC大学希望</td>
				</tr>
			</table>
		</div>
		</form>
	</main>
	
	<footer>
	</footer>
	
	<script src="js/info_student.js"></script>
</body>
</html>