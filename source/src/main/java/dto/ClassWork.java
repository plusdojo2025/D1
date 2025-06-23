package dto;

import java.util.Calendar;
import java.util.Date;

public class ClassWork {
	private int classWorkId;
	private int teacherId;
	private int classId;
	private Date date;
	private String period;
	private String contents;
	private int subjectId;
	
	private int year;
	private int month;
	private int day;
	private String week; // 曜日
	
	public ClassWork(int classWorkId, int teacherId, int classId, Date date, String period, String contents, int subjectId) {
		this.classWorkId = classWorkId;
		this.teacherId = teacherId;
		this.classId = classId;
		this.date = date;
		this.period = period;
		this.contents = contents;
		this.subjectId = subjectId;
		
		this.year = dateToYear(date);
		this.month = dateToMonth(date);
		this.day = dateToDay(date);
		setWeek(this.date);
	}
	
	public ClassWork(int classWorkId, int teacherId, int classId, int year, int month, int day, String period, String contents, int subjectId) {
		this.classWorkId = classWorkId;
		this.teacherId = teacherId;
		this.classId = classId;
		this.period = period;
		this.contents = contents;
		this.subjectId = subjectId;
		
		this.year = year;
		this.month = month;
		this.day = day;
		setWeek(this.date);
	}
	
	public ClassWork() {
		this.classWorkId = -1;
		this.teacherId = -1;
		this.classId = -1;
		this.date = new Date();
		this.period = "";
		this.contents = "";
		this.subjectId = -1;
		
		setWeek(this.date);
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

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
		return calendar.get(Calendar.MONTH) + 1;
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
