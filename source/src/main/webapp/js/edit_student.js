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
	
	let score = clone_grade.getElementByClassName("addGradescore");
	score[0].setAttribute('name', "addGradescore" + addGradeNum);
	
	addGradeNum++;
	let addGradeAmount = document.getElementById("addGradeAmount");
	addGradeAmount.value = addGradeNum;
}