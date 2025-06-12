package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dto.ClassWork;

public class ClassWorkDAO {
	public List<ClassWork> select(ClassWork _cw) {
		Connection conn = null;
		List<ClassWork> cwList = new ArrayList<ClassWork>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM ClassWork "
					+ "WHERE classWorkId = ? AND teacherId = ? AND classId = ? AND "
					+ "year(date) like ? AND month(date) like ? date(date) like ? AND "
					+ "period = ? AND contents = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_cw.getDate());

			pStmt.setInt(1, _cw.getClassWorkId());
			pStmt.setInt(2, _cw.getTeacherId());
			pStmt.setInt(3, _cw.getClassId());
			pStmt.setInt(4, calendar.get(Calendar.YEAR));
			pStmt.setInt(5, calendar.get(Calendar.MONTH));
			pStmt.setInt(6, calendar.get(Calendar.DAY_OF_MONTH));

			if (_cw.getPeriod() != null) {
				pStmt.setString(7, "%" + _cw.getPeriod() + "%");
			} else {
				pStmt.setString(7, "%");
			}
			
			if (_cw.getContents() != null) {
				pStmt.setString(8, "%" + _cw.getContents() + "%");
			} else {
				pStmt.setString(8, "%");
			}

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdFormat.parse(rs.getString("date"));

				ClassWork cw = new ClassWork(rs.getInt("ClassWorkId"), rs.getInt("teacherId"), rs.getInt("classId"), date, 
						rs.getString("period"), rs.getString("contents"));
				cwList.add(cw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cwList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cwList = null;
		} catch (ParseException e) {
			e.printStackTrace();
			cwList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cwList = null;
				}
			}
		}

		// 結果を返す
		return cwList;
	}

	public boolean insert(ClassWork _cw) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "INSERT INTO ClassWork VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_cw.getDate());

			pStmt.setInt(1, _cw.getClassWorkId());
			pStmt.setInt(2, _cw.getTeacherId());
			pStmt.setInt(3, _cw.getClassId());
			pStmt.setString(4, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			
			if (_cw.getPeriod() != null) {
				pStmt.setString(5, "%" + _cw.getPeriod() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			
			if (_cw.getContents() != null) {
				pStmt.setString(6, "%" + _cw.getContents() + "%");
			} else {
				pStmt.setString(6, "%");
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

	public boolean update(ClassWork _cw) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "UPDATE ClassWork SET ClassWorkId=?, teacherId=?, date=?, studentId=?, "
					+ "contents=?, remarks=?, subjectId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_cw.getDate());

			pStmt.setInt(1, _cw.getClassWorkId());
			pStmt.setInt(2, _cw.getTeacherId());
			pStmt.setInt(3, _cw.getClassId());
			pStmt.setString(4, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			
			if (_cw.getPeriod() != null) {
				pStmt.setString(5, "%" + _cw.getPeriod() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			
			if (_cw.getContents() != null) {
				pStmt.setString(6, "%" + _cw.getContents() + "%");
			} else {
				pStmt.setString(6, "%");
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

	public boolean delete(ClassWork _cw) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "DELETE FROM ClassWork WHERE ClassWorkId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, _cw.getClassWorkId());

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

	public boolean delete(int ClassWorkId) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "DELETE FROM ClassWork WHERE ClassWorkId=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, ClassWorkId);

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
