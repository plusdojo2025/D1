package dto;

import java.util.Date;

public class ClassWork {
	private int classWorkId;
	private int teacherId;
	private int classId;
	private Date date;
	private String period;
	private String contents;
	
	public ClassWork(int classWorkId, int teacherId, int classId, Date date, String period, String contents) {
		this.classWorkId = classWorkId;
		this.teacherId = teacherId;
		this.classId = classId;
		this.date = date;
		this.period = period;
		this.contents = contents;
	}
	
	public ClassWork() {
		this.classWorkId = -1;
		this.teacherId = -1;
		this.classId = -1;
		this.date = new Date();
		this.period = "";
		this.contents = "";
	}

	public int getClassWorkId() {
		return classWorkId;
	}

	public void setClassWorkId(int classWorkId) {
		this.classWorkId = classWorkId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
