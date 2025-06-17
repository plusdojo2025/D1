package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Schedule implements Serializable {
    private int scheduleId;     // スケジュールID
    private int teacherId;      // 教師ID
    private int classId;        // 授業対象クラスID
    private LocalDateTime date; // 日付
    private String period;      // 時限
    private String content;     // 各コマの予定
    private String type;        // 対象種別
    private LocalDateTime year; // 年度
    private String semester;    // 学期
    private String memo;        // メモ欄
    private String day_of_week; // 曜日

    public Schedule(int scheduleId, int teacherId, int classId, LocalDateTime date, String period, String content, String type,
                    LocalDateTime year, String semester, String memo, String day_of_week) {
        this.scheduleId = scheduleId;
        this.teacherId = teacherId;
        this.classId = classId;
        this.date = date;
        this.period = period;
        this.content = content;
        this.type = type;
        this.year = year;
        this.semester = semester;
        this.memo = memo;
        this.day_of_week = day_of_week;
    }

    public Schedule() {
        this.scheduleId = -1;
        this.teacherId = -1;
        this.classId = -1;
        this.date = LocalDateTime.now();
        this.period = "";
        this.content = "";
        this.type = "";
        this.year = LocalDateTime.now();
        this.semester = "";
        this.memo = "";
        this.day_of_week = "";
    }

    public int getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LocalDateTime getYear() {
        return year;
    }
    public void setYear(LocalDateTime year) {
        this.year = year;
    }
    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getDay_of_week() {
        return day_of_week;
    }
    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }
}
