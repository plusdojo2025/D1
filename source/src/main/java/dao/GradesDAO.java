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

import dto.Grades;

public class GradesDAO {
	public List<Grades> select(Grades gs) {
		Connection conn = null;
		List<Grades> arList = new ArrayList<Grades>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM Grades "
					+ "WHERE gradesId = ? AND studentId = ? AND subjectId = ? AND score like ? "
					+ "AND testType like ? year(date) like ? AND month(date) like ? date(date) like ? ;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(gs.getDate());

			pStmt.setInt(1, gs.getGradesId());
			pStmt.setInt(2, gs.getStudentId());
			pStmt.setInt(3, gs.getSubjectId());
			pStmt.setInt(4, gs.getScore());
			if (gs.getTestType() != null) {
				pStmt.setString(5, "%" + gs.getTestType() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			
			pStmt.setInt(6, calendar.get(Calendar.YEAR));
			pStmt.setInt(7, calendar.get(Calendar.MONTH));
			pStmt.setInt(8, calendar.get(Calendar.DAY_OF_MONTH));

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdFormat.parse(rs.getString("date"));

				Grades ar = new Grades(
						rs.getInt("gradesId"), 
						rs.getInt("studentId"), 
						rs.getInt("subjectId"), 
						rs.getInt("score"), 
						rs.getString("testType"), 
						date
						);
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
	public boolean insert(Grades gs) {
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
			String sql = "INSERT INTO Grades VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(gs.getDate());

			pStmt.setInt(1, gs.getGradesId());
			pStmt.setInt(2, gs.getStudentId());
			pStmt.setInt(3, gs.getSubjectId());
			pStmt.setInt(4, gs.getScore());
			
			if (gs.getTestType() != null) {
				pStmt.setString(5, gs.getTestType());
			} else {
				pStmt.setString(5, "");
			}
			
			pStmt.setString(6, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));

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
	public boolean update(Grades gs) {
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
			String sql = "UPDATE Grades SET gradesId=?, studentId=?, subjectId=?, "
					+ "score=?, testType=?, date=?, submissionDate;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(gs.getDate());

			pStmt.setInt(1, gs.getGradesId());
			pStmt.setInt(2, gs.getStudentId());
			pStmt.setInt(3, gs.getSubjectId());
			pStmt.setInt(4, gs.getScore());
			
			if (gs.getTestType() != null) {
				pStmt.setString(5, gs.getTestType());
			} else {
				pStmt.setString(5, "");
			}
			
			pStmt.setString(6, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			

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
	public boolean delete(Grades gs) {
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
			String sql = "DELETE FROM Grades WHERE gradesId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, gs.getGradesId());

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
	public boolean delete(int gradesId) {
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
			String sql = "DELETE FROM Grades WHERE gradesId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, gradesId);

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
