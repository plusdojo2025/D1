package dto;

import java.io.Serializable;

public class Subject implements Serializable {
	private int subjectId; 	    // 教科ID
	private String subjectName;     //　教科名
	
	public Subject(int subjectId, String subjectName) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}
	
	public Subject(String subjectName) {
		this.subjectId = -1;
		this.subjectName = subjectName;
	}

	public Subject() {
		this.subjectId = -1;
		this.subjectName = "";
		
	}
	
	
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	
}
