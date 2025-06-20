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
	                   + "WHERE (? = -1 OR scheduleId = ?) "
	                   + "AND (? = -1 OR teacherId = ?) "
	                   + "AND (? = -1 OR classId = ?) "
	                   + "AND (? IS NULL OR date = ?) "
	                   + "AND (? = '' OR period = ?) "
	                   + "AND (? = '' OR content LIKE ?) "
	                   + "AND (? = '' OR type = ?) "
	                   + "AND (? = -1 OR year = ?) "
	                   + "AND (? = '' OR semester = ?) "
	                   + "AND (? = '' OR day_of_week = ?) "
	                   + "ORDER BY scheduleId";

	        PreparedStatement pStmt = conn.prepareStatement(sql);

	        // 各項目を2回ずつバインド（順に注意）
	        int i = 1;

	        pStmt.setInt(i++, schedule.getScheduleId()); // 1
	        pStmt.setInt(i++, schedule.getScheduleId()); // 2

	        pStmt.setInt(i++, schedule.getTeacherId());  // 3
	        pStmt.setInt(i++, schedule.getTeacherId());  // 4

	        pStmt.setInt(i++, schedule.getClassId());    // 5
	        pStmt.setInt(i++, schedule.getClassId());    // 6

	        if (schedule.getDate() != null) {
	            Timestamp ts = Timestamp.valueOf(schedule.getDate());
	            pStmt.setTimestamp(i++, ts);             // 7
	            pStmt.setTimestamp(i++, ts);             // 8
	        } else {
	            pStmt.setNull(i++, Types.TIMESTAMP);     // 7
	            pStmt.setNull(i++, Types.TIMESTAMP);     // 8
	        }

	        pStmt.setString(i++, schedule.getPeriod() != null ? schedule.getPeriod() : ""); // 9
	        pStmt.setString(i++, schedule.getPeriod() != null ? schedule.getPeriod() : ""); // 10

	        pStmt.setString(i++, schedule.getContent() != null ? "%" + schedule.getContent() + "%" : ""); // 11
	        pStmt.setString(i++, schedule.getContent() != null ? "%" + schedule.getContent() + "%" : ""); // 12

	        pStmt.setString(i++, schedule.getType() != null ? schedule.getType() : ""); // 13
	        pStmt.setString(i++, schedule.getType() != null ? schedule.getType() : ""); // 14

	        pStmt.setInt(i++, schedule.getYear());       // 15
	        pStmt.setInt(i++, schedule.getYear());       // 16

	        pStmt.setString(i++, schedule.getSemester() != null ? schedule.getSemester() : ""); // 17
	        pStmt.setString(i++, schedule.getSemester() != null ? schedule.getSemester() : ""); // 18

	        pStmt.setString(i++, schedule.getDay_of_week() != null ? schedule.getDay_of_week() : ""); // 19
	        pStmt.setString(i++, schedule.getDay_of_week() != null ? schedule.getDay_of_week() : ""); // 20

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
    
    public void deleteByDayAndPeriod(int teacherId, int year, String semester, String day, String period) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM Schedule WHERE teacherId = ? AND year = ? AND semester = ? AND day_of_week = ? AND period = ?";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?"
                    + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
                    "root", "password");

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, teacherId);
            pstmt.setInt(2, year);
            pstmt.setString(3, semester);
            pstmt.setString(4, day);
            pstmt.setString(5, period);
            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }


}
