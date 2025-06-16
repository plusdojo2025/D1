package dto;

import java.io.Serializable;

public class Grades implements Serializable{
	private int gradesId; 	    // 成績ID
	private int studentId; 	    // 生徒ID
	private int subjectId; 	    // 教科ID
	private int score;     //　点数
	private String testType;     //　テスト種別
	private int year;     //　日付
	private int month;     //　日付
	
	public Grades(int gradesId,int studentId,int subjectId, 
			int score,String testType,int year,int month) {
		
		this.gradesId = gradesId;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.score = score;
		this.testType = testType;
		this.year = year;
		this.month = month;

	}

	public Grades(int studentId,int subjectId,int year,int month) {

		this.gradesId = -1;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.score = -1;
		this.testType = "";
		this.year = year;
		this.month = month;

	}

	public Grades(int studentId,int subjectId) {
		
		this.gradesId = -1;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.score = -1;
		this.testType = "";
		this.year = -1;
		this.month = -1;
		
	}

	public Grades() {
		
		this.gradesId = -1;
		this.studentId = -1;
		this.subjectId = -1;
		this.score = -1;
		this.testType = "";
		this.year = -1;
		this.month = -1;
		
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getGradesId() {
		return gradesId;
	}

	public void setGradesId(int gradesId) {
		this.gradesId = gradesId;
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

}
