package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
					+ "select * from smp where year(date) like ? AND month(date) like ? date(date) like ?"
					+ "period = ? AND subjectId = ? AND "
					+ "status = ? AND remarks = ?";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				AttendanceRecords ar = new AttendanceRecords();
				arList.add(ar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			arList = null;
		} catch (ClassNotFoundException e) {
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
