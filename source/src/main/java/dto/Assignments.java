package dto;

import java.io.Serializable;
import java.util.Date;

public class Assignments implements Serializable{
	private int assignmentId; 	    // 提出物状況ID
	private int studentId; 	    // 生徒ID
	private int subjectId; 	    // 教科ID
	private String submissionStatus;     //　提出状況
	private String content;     //　内容
	private int createdYear;     //　登録年
	private int createdMonth;     //　登録月
	private Date submissionDate;     //　提出日
	
	public Assignments(int assignmentId,int studentId,int subjectId, 
			String submissionStatus,String content,int createdYear,int createdMonth,Date submissionDate) {
		
		this.assignmentId = assignmentId;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.submissionStatus = submissionStatus;
		this.content = content;
		this.createdYear = createdYear;
		this.createdMonth = createdMonth;
		this.submissionDate = submissionDate;
		
	}
	
	public Assignments(int studentId,int subjectId) {
		
		this.assignmentId = -1;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.submissionStatus = "";
		this.content = "";
		this.createdYear = -1;
		this.createdMonth = -1;
		this.submissionDate = new Date();
		
	}

	public Assignments() {
		
		this.assignmentId = -1;
		this.studentId = -1;
		this.subjectId = -1;
		this.submissionStatus = "";
		this.content = "";
		this.createdYear = -1;
		this.createdMonth = -1;
		this.submissionDate = new Date();
		
	}

	public int getCreatedYear() {
		return createdYear;
	}

	public void setCreatedYear(int createdYear) {
		this.createdYear = createdYear;
	}

	public int getCreatedMonth() {
		return createdMonth;
	}

	public void setCreatedMonth(int createdMonth) {
		this.createdMonth = createdMonth;
	}

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
}
