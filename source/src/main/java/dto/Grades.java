package dto;

import java.io.Serializable;
import java.util.Date;

public class Grades implements Serializable{
	private int GradesId; 	    // 成績ID
	private int studentId; 	    // 生徒ID
	private int subjectId; 	    // 教科ID
	private int score;     //　点数
	private String testType;     //　テスト種別
	private Date date;     //　日付
	
	public Grades(int GradesId,int studentId,int subjectId, 
			int score,String testType,Date date) {
		
		this.GradesId = GradesId;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.score = score;
		this.testType = testType;
		this.date = date;
		
	}

	public Grades() {
		
		this.GradesId = -1;
		this.studentId = -1;
		this.subjectId = -1;
		this.score = -1;
		this.testType = "";
		this.date = new Date();
		
	}

	public int getGradesId() {
		return GradesId;
	}

	public void setGradesId(int gradesId) {
		GradesId = gradesId;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
