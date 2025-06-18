package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dto.Schedule;

public class ScheduleDAO {

    // スケジュール一覧（条件検索）を取得する
	public List<Schedule> select(Schedule schedule) {
	    Connection conn = null;
	    List<Schedule> scheduleList = new ArrayList<>();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?"
	                + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
	                "root", "password");

	        String sql = "SELECT scheduleId, teacherId, classId, date, period, content, type, year, semester, memo, day_of_week "
	                   + "FROM Schedule "
	                   + "WHERE (scheduleId = ? OR ? = -1) "
	                   + "AND (teacherId = ? OR ? = -1) "
	                   + "AND (classId = ? OR ? = -1) "
	                   + "AND (date = ? OR ? IS NULL) "
	                   + "AND period LIKE ? "
	                   + "AND content LIKE ? "
	                   + "AND type LIKE ? "
	                   + "AND (year = ? OR ? = -1) "
	                   + "AND semester LIKE ? "
	                   + "AND day_of_week LIKE ? "
	                   + "ORDER BY scheduleId";

	        PreparedStatement pStmt = conn.prepareStatement(sql);

	        // ここでパラメータを順番に設定する（計14個）

	     // scheduleId（1,2）
	        pStmt.setInt(1, schedule.getScheduleId());
	        pStmt.setInt(2, schedule.getScheduleId());

	        // teacherId（3,4）
	        pStmt.setInt(3, schedule.getTeacherId());
	        pStmt.setInt(4, schedule.getTeacherId());

	        // classId（5,6）
	        pStmt.setInt(5, schedule.getClassId());
	        pStmt.setInt(6, schedule.getClassId());

	        // date（7,8）
	        if (schedule.getDate() != null) {
	            Timestamp tsDate = Timestamp.valueOf(schedule.getDate());
	            pStmt.setTimestamp(7, tsDate);
	            pStmt.setTimestamp(8, tsDate);
	        } else {
	            pStmt.setNull(7, Types.TIMESTAMP);
	            pStmt.setNull(8, Types.TIMESTAMP);
	        }

	        // period（9）
	        pStmt.setString(9, schedule.getPeriod() != null && !schedule.getPeriod().isEmpty() ? "%" + schedule.getPeriod() + "%" : "%");

	        // content（10）
	        pStmt.setString(10, schedule.getContent() != null && !schedule.getContent().isEmpty() ? "%" + schedule.getContent() + "%" : "%");

	        // type（11）
	        pStmt.setString(11, schedule.getType() != null && !schedule.getType().isEmpty() ? "%" + schedule.getType() + "%" : "%");

	        // year（12,13）
	        pStmt.setInt(12, schedule.getYear());
	        pStmt.setInt(13, schedule.getYear());

	        // semester（14）
	        pStmt.setString(14, schedule.getSemester() != null && !schedule.getSemester().isEmpty() ? "%" + schedule.getSemester() + "%" : "%");

	        // day_of_week（15）
	        pStmt.setString(15, schedule.getDay_of_week() != null && !schedule.getDay_of_week().isEmpty() ? "%" + schedule.getDay_of_week() + "%" : "%");


	        ResultSet rs = pStmt.executeQuery();

	        while (rs.next()) {
	            Schedule sc = new Schedule(
	                rs.getInt("scheduleId"),
	                rs.getInt("teacherId"),
	                rs.getInt("classId"),
	                rs.getTimestamp("date") != null ? rs.getTimestamp("date").toLocalDateTime() : null,
	                rs.getString("period"),
	                rs.getString("content"),
	                rs.getString("type"),
	                rs.getInt("year"),
	                rs.getString("semester"),
	                rs.getString("memo"),
	                rs.getString("day_of_week")
	            );
	            scheduleList.add(sc);
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        scheduleList = null;
	    } finally {
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	    return scheduleList;
	}


    // 新規登録
    public boolean insert(Schedule schedule) {
        Connection conn = null;
        boolean result = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?"
                    + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
                    "root", "password");

            String sql = "INSERT INTO Schedule(scheduleId, teacherId, classId, date, period, content, type, year, semester, memo, day_of_week) "
                       + "VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, schedule.getTeacherId());
            pStmt.setInt(2, schedule.getClassId());
            pStmt.setTimestamp(3, schedule.getDate() != null ? Timestamp.valueOf(schedule.getDate()) : null);
            pStmt.setString(4, schedule.getPeriod() != null ? schedule.getPeriod() : "");
            pStmt.setString(5, schedule.getContent() != null ? schedule.getContent() : "");
            pStmt.setString(6, schedule.getType() != null ? schedule.getType() : "");
            pStmt.setInt(7, schedule.getYear());
            pStmt.setString(8, schedule.getSemester() != null ? schedule.getSemester() : "");
            pStmt.setString(9, schedule.getMemo() != null ? schedule.getMemo() : "");
            pStmt.setString(10, schedule.getDay_of_week() != null ? schedule.getDay_of_week() : "");

            if (pStmt.executeUpdate() == 1) {
                result = true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    // 更新
    public boolean update(Schedule schedule) {
        Connection conn = null;
        boolean result = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?"
                    + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
                    "root", "password");

            String sql = "UPDATE Schedule SET teacherId=?, classId=?, date=?, period=?, content=?, type=?, year=?, semester=?, memo=?, day_of_week=? "
                       + "WHERE scheduleId=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, schedule.getTeacherId());
            pStmt.setInt(2, schedule.getClassId());
            pStmt.setTimestamp(3, schedule.getDate() != null ? Timestamp.valueOf(schedule.getDate()) : null);
            pStmt.setString(4, schedule.getPeriod() != null ? schedule.getPeriod() : "");
            pStmt.setString(5, schedule.getContent() != null ? schedule.getContent() : "");
            pStmt.setString(6, schedule.getType() != null ? schedule.getType() : "");
            pStmt.setInt(7, schedule.getYear());
            pStmt.setString(8, schedule.getSemester() != null ? schedule.getSemester() : "");
            pStmt.setString(9, schedule.getMemo() != null ? schedule.getMemo() : "");
            pStmt.setString(10, schedule.getDay_of_week() != null ? schedule.getDay_of_week() : "");
            pStmt.setInt(11, schedule.getScheduleId());

            if (pStmt.executeUpdate() == 1) {
                result = true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    // 削除
    public boolean delete(Schedule schedule) {
        Connection conn = null;
        boolean result = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?"
                    + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
                    "root", "password");

            String sql = "DELETE FROM Schedule WHERE scheduleId=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, schedule.getScheduleId());

            if (pStmt.executeUpdate() == 1) {
                result = true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return result;
    }
}
