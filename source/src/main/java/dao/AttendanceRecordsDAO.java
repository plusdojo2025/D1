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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords "
					+ "WHERE studentId = ? AND classId = ? AND "
					+ "year(date) like ? AND month(date) like ? AND day(date) like ? AND "
					+ "period = ? AND subjectId = ? AND "
					+ "status = ? AND remarks = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_ar.getDate());

			pStmt.setInt(1, _ar.getStudentId());
			pStmt.setInt(2, _ar.getClassId());
			pStmt.setInt(3, calendar.get(Calendar.YEAR));
			pStmt.setInt(4, calendar.get(Calendar.MONTH));
			pStmt.setInt(5, calendar.get(Calendar.DAY_OF_MONTH));

			if (_ar.getPeriod() != null) {
				pStmt.setString(6, _ar.getPeriod());
			} else {
				pStmt.setString(6, "%");
			}

			pStmt.setInt(7, _ar.getSubjectId());

			if (_ar.getStatus() != null) {
				pStmt.setString(8, _ar.getStatus());
			} else {
				pStmt.setString(8, "%");
			}

			if (_ar.getRemarks() != null) {
				pStmt.setString(9, _ar.getRemarks());
			} else {
				pStmt.setString(9, "%");
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
	
	public List<AttendanceRecords> select(int studentId) {
		Connection conn = null;
		List<AttendanceRecords> arList = new ArrayList<AttendanceRecords>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords WHERE studentId = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, studentId);

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

	public List<AttendanceRecords> select(int studentId, int subjectId) {
		Connection conn = null;
		List<AttendanceRecords> arList = new ArrayList<AttendanceRecords>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords WHERE studentId = ? AND subjectId = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, studentId);
			pStmt.setInt(2, subjectId);

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
	
	// 引数_arで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(AttendanceRecords _ar) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "INSERT INTO AttendanceRecords VALUES (0, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_ar.getDate());

			pStmt.setInt(1, _ar.getStudentId());
			pStmt.setInt(2, _ar.getClassId());
			pStmt.setString(3, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));

			if (_ar.getPeriod() != null) {
				pStmt.setString(4, _ar.getPeriod());
			} else {
				pStmt.setString(4, "%");
			}

			pStmt.setInt(5, _ar.getSubjectId());

			if (_ar.getStatus() != null) {
				pStmt.setString(6, _ar.getStatus());
			} else {
				pStmt.setString(6, "%");
			}

			if (_ar.getRemarks() != null) {
				pStmt.setString(7, _ar.getRemarks());
			} else {
				pStmt.setString(7, "%");
			}

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// 引数_arで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(AttendanceRecords _ar) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "UPDATE AttendanceRecords SET status=?, remarks=? WHERE studentId = ? AND subjectId = ?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (_ar.getStatus() != null) {
				pStmt.setString(1, _ar.getStatus());
			} else {
				pStmt.setString(1, "%");
			}

			if (_ar.getRemarks() != null) {
				pStmt.setString(2, _ar.getRemarks());
			} else {
				pStmt.setString(2, "%");
			}
			
			pStmt.setInt(3, _ar.getStudentId());
			pStmt.setInt(4, _ar.getSubjectId());
			
			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// 引数_arで指定された番号のレコードを削除し、成功したらtrueを返す
	public boolean delete(AttendanceRecords _ar) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "DELETE FROM AttendanceRecords WHERE recordId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, _ar.getRecordId());

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// 引数recordIdで指定された番号のレコードを削除し、成功したらtrueを返す
	public boolean delete(int recordId) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "DELETE FROM AttendanceRecords WHERE recordId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, recordId);

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}
}
