package dto;

import java.util.Date;

public class AttendanceRecords {
	private int recordId;
	private int studentId;
	private int classId;
	private Date date;
	private String period;
	private int subjectId;
	private String status;
	private String remarks;
	
	public AttendanceRecords(int recordId, int studentId, int classId, Date date, String period, int subjectId, String status, String remarks) {
		this.recordId = recordId;
		this.studentId = studentId;
		this.classId = classId;
		this.date = date;
		this.period = period;
		this.subjectId = subjectId;
		this.status = status;
		this.remarks = remarks;
	}
	
	public AttendanceRecords() {
		this.recordId = -1;
		this.studentId = -1;
		this.classId = -1;
		this.date = new Date();
		this.period = "";
		this.subjectId = -1;
		this.status = "";
		this.remarks = "";
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
