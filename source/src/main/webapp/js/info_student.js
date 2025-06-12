'use strict'
let attendanceCheck = document.getElementById('attendanceCheck');
let submissionCheck = document.getElementById('submissionCheck');
let gradesCheck = document.getElementById('gradesCheck');
let attitudeCheck = document.getElementById('attitudeCheck');
let interviewCheck = document.getElementById('interviewCheck');
let attendance = document.getElementById('attendance');
let submission = document.getElementById('submission');
let grades = document.getElementById('grades');
let attitude = document.getElementById('attitude');
let interview = document.getElementById('interview');

// 値の変更を捕捉
attendanceCheck.addEventListener('change', {check: attendanceCheck, name: attendance, handleEvent: changeDisplay});
submissionCheck.addEventListener('change', {check: submissionCheck, name: submission, handleEvent: changeDisplay});
gradesCheck.addEventListener('change', {check: gradesCheck, name: grades, handleEvent: changeDisplay});
attitudeCheck.addEventListener('change', {check: attitudeCheck, name: attitude, handleEvent: changeDisplay});
interviewCheck.addEventListener('change', {check: interviewCheck, name: interview, handleEvent: changeDisplay});

//　変更時の処理
function changeDisplay(event) {
	let element = attendance;
	// エレメントの変更
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
		case attitude:
		element = attitude;
		break;
		case interview:
		element = interview;
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