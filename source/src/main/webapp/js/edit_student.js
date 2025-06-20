'use strict';
let addSubmitNum = 1;
let submissionObj = document.getElementById("addSubmission");
let addSubmissionAmount = document.getElementById("addSubmittionAmount");
function addSubmission() {
	// 複製
	let clone_submission = submissionObj.cloneNode(true);
	
	// 複製したHTML要素をページに挿入
	submissionObj.after(clone_submission);
	
	let content = clone_submission.getElementsByClassName("submittionContent");
	content[0].setAttribute('name', "addAssignmentContent" + addSubmitNum);
	
	let status = clone_submission.getElementsByClassName("submittionStatus");
	status[0].setAttribute('name', "addSubmittionStatus" + addSubmitNum);
	
	let date = clone_submission.getElementsByClassName("submittionDate");
	date[0].setAttribute('name', "addSubmittionDate" + addSubmitNum);
	
	addSubmitNum++;
	addSubmissionAmount.value = addSubmitNum;
};