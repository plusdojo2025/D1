package dto;

import java.io.Serializable;
import java.util.Date;

public class Memo implements Serializable {
	private int memoId;     // メモID
	private int teacherId;  // 教員ID
	private int classId;    // 対象クラスID
	private String content; // メモ内容
	private Date date;      // 授業日
	private String period;  // 時限
	
	public Memo(int memoId, int teacherId, int classId, String content,
			Date date, String period) { 
		this.memoId = memoId;
		this.teacherId = teacherId;
		this.classId = classId;
		this.content = content;
		this.date = date;
		this.period = period;
	}

	public Memo() {
		this.memoId = -1;
		this.teacherId = -1;
		this.classId = -1;
		this.content = "";
		this.date = new Date();
		this.period = "";
	}

	public int getMemoId() {
		return memoId;
	}

	public void setMemoId(int memoId) {
		this.memoId = memoId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
}
