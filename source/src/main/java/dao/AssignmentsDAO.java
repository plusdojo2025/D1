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

import dto.Assignments;

public class AssignmentsDAO {
	public List<Assignments> select(Assignments as) {
		Connection conn = null;
		List<Assignments> arList = new ArrayList<Assignments>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM Assignments "
					+ "WHERE assignmentId = ? AND studentId = ? AND content = ? AND submissionStatus like ? "
					+ "AND content like ? year(createdDate) like ? AND month(createdDate) like ? "
					+ "date(createdDate) like ? AND year(submissionDate) like ? AND month(submissionDate) like ? "
					+ "date(submissionDate) like ? ;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(as.getCreatedDate());
			calendar.setTime(as.getSubmissionDate());

			pStmt.setInt(1, as.getAssignmentId());
			pStmt.setInt(2, as.getStudentId());
			pStmt.setInt(3, as.getSubjectId());
			if (as.getSubmissionStatus() != null) {
				pStmt.setString(4, "%" + as.getSubmissionStatus() + "%");
			} else {
				pStmt.setString(4, "%");
			}
			if (as.getContent() != null) {
				pStmt.setString(5, "%" + as.getContent() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			
			pStmt.setInt(6, calendar.get(Calendar.YEAR));
			pStmt.setInt(7, calendar.get(Calendar.MONTH));
			pStmt.setInt(8, calendar.get(Calendar.DAY_OF_MONTH));
			pStmt.setInt(9, calendar.get(Calendar.YEAR));
			pStmt.setInt(10, calendar.get(Calendar.MONTH));
			pStmt.setInt(11, calendar.get(Calendar.DAY_OF_MONTH));

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdFormat.parse(rs.getString("date"));

				Assignments ar = new Assignments(
						rs.getInt("assignmentId"), 
						rs.getInt("studentId"), 
						rs.getInt("subjectId"), 
						rs.getString("submissionStatus"), 
						rs.getString("content"),
						date, 
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
	public boolean insert(Assignments as) {
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
			String sql = "INSERT INTO Assignments VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(as.getCreatedDate());
			calendar.setTime(as.getSubmissionDate());

			pStmt.setInt(1, as.getAssignmentId());
			pStmt.setInt(2, as.getStudentId());
			pStmt.setInt(3, as.getSubjectId());
			
			if (as.getSubmissionStatus() != null) {
				pStmt.setString(4, as.getSubmissionStatus());
			} else {
				pStmt.setString(4, "");
			}
			if (as.getContent() != null) {
				pStmt.setString(5, as.getContent());
			} else {
				pStmt.setString(5, "");
			}
			
			pStmt.setString(6, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			pStmt.setString(7, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));

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
	public boolean update(Assignments as) {
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
			String sql = "UPDATE Assignments SET assignmentId=?, studentId=?, subjectId=?, content=?,"
					+ "submissionStatus=?, content=?, createdDate=?, submissionDate;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(as.getCreatedDate());
			calendar.setTime(as.getSubmissionDate());

			pStmt.setInt(1, as.getAssignmentId());
			pStmt.setInt(2, as.getStudentId());
			pStmt.setInt(3, as.getSubjectId());
			
			if (as.getSubmissionStatus() != null) {
				pStmt.setString(4, as.getSubmissionStatus());
			} else {
				pStmt.setString(4, "");
			}
			if (as.getContent() != null) {
				pStmt.setString(5, as.getContent());
			} else {
				pStmt.setString(5, "");
			}
			
			pStmt.setString(6, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			pStmt.setString(7, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			
			

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
	public boolean delete(Assignments as) {
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
			String sql = "DELETE FROM Assignments WHERE assignmentId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, as.getAssignmentId());

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
	public boolean delete(int assignmentId) {
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
			String sql = "DELETE FROM Assignments WHERE assignmentId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, assignmentId);

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
