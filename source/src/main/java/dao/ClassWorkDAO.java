package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM ClassWork "
					+ "WHERE year(date) like ? AND month(date) like ? AND "
					+ "period　like ? AND subjectId like ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (_cw.getYear() > 0) {
				pStmt.setString(1, "%" + _cw.getYear());
			} else {
				pStmt.setString(1, "%");
			}
			
			if (_cw.getMonth() > 0 && _cw.getMonth() <= 12) {
				pStmt.setString(2, "%" + _cw.getMonth());
			} else {
				pStmt.setString(2, "%");
			}
			
			if (_cw.getPeriod() != null) {
				pStmt.setString(3, "%" + _cw.getPeriod() + "%");
			} else {
				pStmt.setString(3, "%");
			}
			
			if (_cw.getSubjectId() > 0) {
				pStmt.setString(4, "%" + _cw.getSubjectId());
			} else {
				pStmt.setString(4, "%");
			}
			
			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdFormat.parse(rs.getString("date"));

				ClassWork cw = new ClassWork(rs.getInt("ClassWorkId"), rs.getInt("teacherId"), rs.getInt("classId"), date, 
						rs.getString("period"), rs.getString("contents"), rs.getInt("subjectId"));
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "INSERT INTO ClassWork VALUES (0, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, _cw.getTeacherId());
			pStmt.setInt(2, _cw.getClassId());
			pStmt.setString(3, _cw.getYear() + "-" + _cw.getMonth() + "-" + _cw.getDay());
			
			if (_cw.getPeriod() != null) {
				pStmt.setString(4, _cw.getPeriod());
			} else {
				pStmt.setString(4, "");
			}
			
			if (_cw.getContents() != null) {
				pStmt.setString(5,_cw.getContents());
			} else {
				pStmt.setString(5, "");
			}
			
			pStmt.setInt(6, _cw.getSubjectId());

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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "UPDATE ClassWork SET teacherId=?, classId=?, date=?, period=?, "
					+ "contents=?, subjectId=? WHERE classWorkId LIKE ?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, _cw.getTeacherId());
			pStmt.setInt(2, _cw.getClassId());
			pStmt.setString(3, _cw.getYear() + "-" + _cw.getMonth() + "-" + _cw.getDay());
			
			if (_cw.getPeriod() != null) {
				pStmt.setString(4, _cw.getPeriod());
			} else {
				pStmt.setString(4, "");
			}
			
			if (_cw.getContents() != null) {
				pStmt.setString(5, _cw.getContents());
			} else {
				pStmt.setString(5, "");
			}
			
			pStmt.setInt(6, _cw.getSubjectId());
			pStmt.setInt(7, _cw.getClassWorkId());
			
			if (_cw.getClassWorkId() > 0) {
				pStmt.setString(5, "" + _cw.getClassWorkId());
			} else {
				pStmt.setString(5, "");
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

	public boolean delete(int ClassWorkId) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
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
