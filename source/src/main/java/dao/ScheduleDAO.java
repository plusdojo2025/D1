package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachMate?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

            String sql = "SELECT scheduleId, teacherId, classId, date, period, content, type, year, semester, memo, day_of_week "
                       + "FROM Schedule "
                       + "WHERE teacherId LIKE ? AND classId LIKE ? AND date LIKE ? AND period LIKE ? "
                       + "AND content LIKE ? AND type LIKE ? AND semester LIKE ? AND day_of_week LIKE ? "
                       + "ORDER BY scheduleId";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            // teacherId と classId は int なので、-1なら全件対象、それ以外はその値で検索
            if(schedule.getTeacherId() != -1) {
                pStmt.setString(1, String.valueOf(schedule.getTeacherId()));
            } else {
                pStmt.setString(1, "%");
            }

            if(schedule.getClassId() != -1) {
                pStmt.setString(2, String.valueOf(schedule.getClassId()));
            } else {
                pStmt.setString(2, "%");
            }

            pStmt.setString(3, schedule.getDate() != null && !schedule.getDate().isEmpty() ? "%" + schedule.getDate() + "%" : "%");
            pStmt.setString(4, schedule.getPeriod() != null && !schedule.getPeriod().isEmpty() ? "%" + schedule.getPeriod() + "%" : "%");
            pStmt.setString(5, schedule.getContent() != null && !schedule.getContent().isEmpty() ? "%" + schedule.getContent() + "%" : "%");
            pStmt.setString(6, schedule.getType() != null && !schedule.getType().isEmpty() ? "%" + schedule.getType() + "%" : "%");
            pStmt.setString(7, schedule.getSemester() != null && !schedule.getSemester().isEmpty() ? "%" + schedule.getSemester() + "%" : "%");
            pStmt.setString(8, schedule.getDay_of_week() != null && !schedule.getDay_of_week().isEmpty() ? "%" + schedule.getDay_of_week() + "%" : "%");

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                Schedule sc = new Schedule(
                    rs.getInt("scheduleId"),
                    rs.getInt("teacherId"),
                    rs.getInt("classId"),
                    rs.getString("date"),
                    rs.getString("period"),
                    rs.getString("content"),
                    rs.getString("type"),
                    rs.getDate("year"),
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
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachMate?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

            String sql = "INSERT INTO Schedule(scheduleId, teacherId, classId, date, period, content, type, year, semester, memo, day_of_week) "
                       + "VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, schedule.getTeacherId());
            pStmt.setInt(2, schedule.getClassId());
            pStmt.setString(3, schedule.getDate() != null ? schedule.getDate() : "");
            pStmt.setString(4, schedule.getPeriod() != null ? schedule.getPeriod() : "");
            pStmt.setString(5, schedule.getContent() != null ? schedule.getContent() : "");
            pStmt.setString(6, schedule.getType() != null ? schedule.getType() : "");
            pStmt.setDate(7, schedule.getYear() != null ? new java.sql.Date(schedule.getYear().getTime()) : null);
            pStmt.setString(8, schedule.getSemester() != null ? schedule.getSemester() : "");
            pStmt.setString(9, schedule.getMemo() != null ? schedule.getMemo() : "");
            pStmt.setString(10, schedule.getDay_of_week() != null ? schedule.getDay_of_week() : "");

            if(pStmt.executeUpdate() == 1) {
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
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachMate?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

            String sql = "UPDATE Schedule SET teacherId=?, classId=?, date=?, period=?, content=?, type=?, year=?, semester=?, memo=?, day_of_week=? "
                       + "WHERE scheduleId=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, schedule.getTeacherId());
            pStmt.setInt(2, schedule.getClassId());
            pStmt.setString(3, schedule.getDate() != null ? schedule.getDate() : "");
            pStmt.setString(4, schedule.getPeriod() != null ? schedule.getPeriod() : "");
            pStmt.setString(5, schedule.getContent() != null ? schedule.getContent() : "");
            pStmt.setString(6, schedule.getType() != null ? schedule.getType() : "");
            pStmt.setDate(7, schedule.getYear() != null ? new java.sql.Date(schedule.getYear().getTime()) : null);
            pStmt.setString(8, schedule.getSemester() != null ? schedule.getSemester() : "");
            pStmt.setString(9, schedule.getMemo() != null ? schedule.getMemo() : "");
            pStmt.setString(10, schedule.getDay_of_week() != null ? schedule.getDay_of_week() : "");
            pStmt.setInt(11, schedule.getScheduleId());

            if(pStmt.executeUpdate() == 1) {
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
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachMate?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

            String sql = "DELETE FROM Schedule WHERE scheduleId=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, schedule.getScheduleId());

            if(pStmt.executeUpdate() == 1) {
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
