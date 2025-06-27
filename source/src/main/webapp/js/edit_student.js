'use strict';
let addSubmitNum = 1;
let submissionObj = document.getElementById("addSubmission");
function addSubmission() {
	// 複製
	let clone_submission = submissionObj.cloneNode(true);
	
	// 複製したHTML要素をページに挿入
	submissionObj.after(clone_submission);
	
	let content = clone_submission.getElementsByClassName("addSubmittionContent");
	content[0].setAttribute('name', "addAssignmentContent" + addSubmitNum);
	
	let status = clone_submission.getElementsByClassName("addSubmittionStatus");
	status[0].setAttribute('name', "addSubmittionStatus" + addSubmitNum);
	
	let date = clone_submission.getElementsByClassName("addSubmittionDate");
	date[0].setAttribute('name', "addSubmittedDate" + addSubmitNum);
	
	addSubmitNum++;
	let addSubmissionAmount = document.getElementById("addSubmittionAmount");
	addSubmissionAmount.value = addSubmitNum;
};

let addGradeNum = 1;
let gradeObj = document.getElementById("addGrade");
function addGrade() {
	let clone_grade = gradeObj.cloneNode(true);
	
	gradeObj.after(clone_grade);
	
	let testType = clone_grade.getElementByClassName("addGradeTestType");
	testType[0].setAttribute('name', "addGradeTestType" + addGradeNum);
	
	let score = clone_grade.getElementByClassName("addGradeScore");
	score[0].setAttribute('name', "addGradescore" + addGradeNum);
	
	addGradeNum++;
	let addGradeAmount = document.getElementById("addGradeAmount");
	addGradeAmount.value = addGradeNum;
}

let addInterviewNum = 1;
let InterviewObj = document.getElementById("addInterview");
function addInterview() {
	let clone_Interview = InterviewObj.cloneNode(true);
	
	InterviewObj.after(clone_Interview);
	
	let date = clone_Interview.getElementByClassName("addInterviewDate");
	date[0].setAttribute('name', "addInterviewDate" + addInterviewNum);
	
	let contents = clone_Interview.getElementByClassName("addInterviewContents");
	contents[0].setAttribute('name', "addInterviewContents" + addInterviewNum);
	
	let remarks = clone_Interview.getElementByClassName("addInterviewRemarks");
	remarks[0].setAttribute('name', "addInterviewRemarks" + addInterviewNum);
	
	addInterviewNum++;
	let addInterviewAmount = document.getElementById("addInterviewAmount");
	addInterviewAmount.value = addInterviewNum;
}

let addLastInterviewNum = 1;
let LastInterviewObj = document.getElementById("addLastInterview");
function addLastInterview() {
	let clone_LastInterview = LastInterviewObj.cloneNode(true);
	
	LastInterviewObj.after(clone_LastInterview);
	
	let date = clone_LastInterview.getElementByClassName("addLastInterviewDate");
	date[0].setAttribute('name', "addLastInterviewDate" + addLastInterviewNum);
	
	let contents = clone_LastInterview.getElementByClassName("addLastInterviewContents");
	contents[0].setAttribute('name', "addLastInterviewContents" + addLastInterviewNum);
	
	let remarks = clone_LastInterview.getElementByClassName("addLastInterviewRemarks");
	remarks[0].setAttribute('name', "addLastInterviewRemarks" + addLastInterviewNum);
	
	addLastInterviewNum++;
	let addLastInterviewAmount = document.getElementById("addLastInterviewAmount");
	addLastInterviewAmount.value = addLastInterviewNum;
}
