package dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Interview {
	private int interviewId;
	private int teacherId;
	private Date date;
	private int studentId;
	private String contents;
	private String remarks;
	private int subjectId;
	
	private int year;
	private int month;
	private int day;
	private String week; // 曜日
	
	public Interview(int interviewId, int teacherId, Date date, int studentId, String contents, String remarks, int subjectId) {
		this.interviewId = interviewId;
		this.teacherId = teacherId;
		this.date = date;
		this.studentId = studentId;
		this.contents = contents;
		this.remarks = remarks;
		this.subjectId = subjectId;
		
		this.year = dateToYear(date);
		this.month = dateToMonth(date);
		this.day = dateToDay(date);
		setWeek(this.date);
	}
	
	public Interview(int interviewId, int teacherId, int year, int month, int day, int studentId, String contents, String remarks, int subjectId) {
		this.interviewId = interviewId;
		this.teacherId = teacherId;
		this.studentId = studentId;
		this.contents = contents;
		this.remarks = remarks;
		this.subjectId = subjectId;
		
		this.year = year;
		this.month = month;
		this.day = day;
		
		try {
			setWeek(this.year, this.month, this.day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Interview() {
		this.interviewId = -1;
		this.teacherId = -1;
		this.date = new Date();
		this.studentId = -1;
		this.contents = "";
		this.remarks = "";
		this.subjectId = -1;
		
		setWeek(this.date);
	}

	public int getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	
	public void setWeek(int year, int month, int day) throws ParseException {
		String inpDateStr = year + "/" + month + "/" + day;
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
		this.date = sdformat.parse(inpDateStr);
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
