package dto;

import java.util.Calendar;
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
	
	private int year;
	private int month;
	private int day;
	private String week; // 曜日
	
	public AttendanceRecords(int recordId, int studentId, int classId, Date date, String period, int subjectId, String status, String remarks) {
		this.recordId = recordId;
		this.studentId = studentId;
		this.classId = classId;
		this.date = date;
		this.period = period;
		this.subjectId = subjectId;
		this.status = status;
		this.remarks = remarks;
		
		if (date != null) {
			this.setYear(dateToYear(date));
			this.setMonth(dateToMonth(date));
			this.setDay(dateToDay(date));
			setWeek(this.date);
		}
		
	}
	
	public AttendanceRecords(int recordId, int studentId, int classId, int year, int month, int day, String period, int subjectId, String status, String remarks) {
		this.recordId = recordId;
		this.studentId = studentId;
		this.classId = classId;
		this.period = period;
		this.subjectId = subjectId;
		this.status = status;
		this.remarks = remarks;
		
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
		setWeek(this.date);
	}
	
	public AttendanceRecords(int recordId, int studentId, int classId, String period, int subjectId, String status, String remarks) {
		this.recordId = recordId;
		this.studentId = studentId;
		this.classId = classId;
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
		
		this.setYear(dateToYear(date));
		this.setMonth(dateToMonth(date));
		this.setDay(dateToDay(date));
		setWeek(this.date);
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
	
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
	public void setWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] weekDays = {"", "日", "月", "火", "水", "木", "金", "土"};
        this.setWeek(weekDays[dayOfWeek]);
	}
	
	public int dateToYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public int dateToMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}
	
	public int dateToDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
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

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
