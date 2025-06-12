package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dto.AttendanceRecords;

public class AttendanceRecordsDAO {
	public List<AttendanceRecords> select(AttendanceRecords _ar) {
		Connection conn = null;
		List<AttendanceRecords> arList = new ArrayList<AttendanceRecords>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords "
					+ "WHERE recordId = ? AND studentId = ? AND classId = ? AND "
					+ "year(date) like ? AND month(date) like ? date(date) like ?"
					+ "period = ? AND subjectId = ? AND "
					+ "status = ? AND remarks = ?";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_ar.getDate());
			
			pStmt.setInt(1, _ar.getRecordId());
			pStmt.setInt(2, _ar.getStudentId());
			pStmt.setInt(3, _ar.getClassId());
			pStmt.setInt(4, calendar.get(calendar.YEAR));
			pStmt.setInt(5, calendar.get(calendar.MONTH));
			pStmt.setInt(6, calendar.get(calendar.DAY_OF_MONTH));
			
			if (_ar.getPeriod() != null) {
				pStmt.setString(7, _ar.getPeriod());
			} else {
				pStmt.setString(7, "%");
			}
			
			pStmt.setInt(8, _ar.getSubjectId());
			
			if (_ar.getStatus() != null) {
				pStmt.setString(9, _ar.getStatus());
			} else {
				pStmt.setString(9, "%");
			}
			
			if (_ar.getRemarks() != null) {
				pStmt.setString(10, _ar.getRemarks());
			} else {
				pStmt.setString(10, "%");
			}
			
			// SQLの実行
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date date = sdFormat.parse(rs.getString("date"));
				
				AttendanceRecords ar = new AttendanceRecords(rs.getInt("recordId"), rs.getInt("studentId"), rs.getInt("classId"), 
						date, rs.getString("period"), rs.getInt("subjectId"), rs.getString("status"), rs.getString("remarks"));
				arList.add(ar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			arList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			arList = null;
		} catch (ParseException e) {
            e.printStackTrace();
            arList = null;
        } finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					arList = null;
				}
			}
		}
		
		// 結果を返す
		return arList;
	}
}
