'use strict';

let submissionObj = document.getElementById("addSubmission");
function addSubmission() {
	// 複製
	let clone_submission = submissionObj.cloneNode(true);
	
	
	
	// 複製したHTML要素をページに挿入
	submissionObj.after(clone_submission);
}