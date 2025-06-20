'use strict'
// チェックボックスのエレメントを取得
let attendanceCheck = document.getElementById('attendanceCheck');
let submissionCheck = document.getElementById('submissionCheck');
let gradesCheck = document.getElementById('gradesCheck');

//　表示・非表示を切り替えたいエレメントを取得
let attendance = document.getElementById('attendance'); // 出欠
let submission = document.getElementById('submission'); // 提出物
let grades = document.getElementById('grades'); // 成績

// 値の変更を捕捉
attendanceCheck.addEventListener('change', {check: attendanceCheck, name: attendance, handleEvent: changeDisplay});
submissionCheck.addEventListener('change', {check: submissionCheck, name: submission, handleEvent: changeDisplay});
gradesCheck.addEventListener('change', {check: gradesCheck, name: grades, handleEvent: changeDisplay});
// addEventListenerで変更を検知。変更したタイミングでchangeDisplayを呼出し（handleEvent: の後ろに関数名）
// checkでチェックボックスのエレメントを、nameで表示切替したい項目の名前を渡すと、関数側で「this.check」「this.name」で取得できる

//　変更時の処理
function changeDisplay(event) {
	let element = attendance;
	// 名前に応じてエレメントを設定
	switch (this.name) {
		case attendance:
		element = attendance;
		break;
		case submission:
		element = submission;
		break;
		case grades:
		element = grades;
		break;
	}
	
	if (this.check.checked) {
		// 表示
		element.style.display = 'block';
	} else {
		// 非表示
		element.style.display = 'none';
	}
};

/*
function changeGradesValue() {
  const editCompleted = editCompleted;
  if(editCompleted == 'ok') {
    let grades = document.getElementById('grades');
    const gradesValue = document.getElementById('grades').value;
	grades.value = "置き換える値";
}
*/

















