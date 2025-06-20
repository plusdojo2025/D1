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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM Assignments "
					+ "WHERE assignmentId like ? AND studentId like ? AND subjectId like ? AND "
					+ "year(createdDate) like ? AND month(createdDate) like ? ";

			PreparedStatement pStmt = conn.prepareStatement(sql);


			if (as.getAssignmentId() > 0) {
				pStmt.setString(1, "" + as.getAssignmentId());
			} else {
				pStmt.setString(1, "%");
			}
			if (as.getStudentId() > 0) {
				pStmt.setString(2, "" + as.getStudentId());
			} else {
				pStmt.setString(2, "%");
			}
			if (as.getSubjectId() > 0) {
				pStmt.setString(3, "" + as.getSubjectId());
			} else {
				pStmt.setString(3, "%");
			}
			if (as.getCreatedYear() > 0) {
				pStmt.setString(4, "" + as.getCreatedYear());
			} else {
				pStmt.setString(4, "%");
			}
			if (as.getCreatedMonth() > 0) {
				pStmt.setString(5, "" + as.getCreatedMonth());
			} else {
				pStmt.setString(5, "%");
			}

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdFormat.parse(rs.getString("submissionDate"));
				
				// year
				SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
				int createdYear = Integer.parseInt(ysdf.format(rs.getDate("createdDate")));
				// month
				SimpleDateFormat msdf = new SimpleDateFormat("MM");
				int createdMonth = Integer.parseInt(msdf.format(rs.getDate("createdDate")));

				Assignments ar = new Assignments(
						rs.getInt("assignmentId"), 
						rs.getInt("studentId"), 
						rs.getInt("subjectId"), 
						rs.getString("submissionStatus"), 
						rs.getString("content"),
						createdYear,
						createdMonth,
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "INSERT INTO Assignments VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(as.getSubmissionDate());

			if (as.getAssignmentId() != -1) {
				pStmt.setString(1, "" + as.getAssignmentId());
			} else {
				pStmt.setString(1, "");
			}
			if (as.getStudentId() != -1) {
				pStmt.setString(2, "" + as.getStudentId());
			} else {
				pStmt.setString(2, "");
			}
			if (as.getSubjectId() != -1) {
				pStmt.setString(3, "" + as.getSubjectId());
			} else {
				pStmt.setString(3, "");
			}
			
			if (as.getSubmissionStatus() != null) {
				pStmt.setString(4, as.getSubmissionStatus());
			} else {
				pStmt.setString(4, "");
			}
			
			pStmt.setString(5, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			
			if (as.getContent() != null) {
				pStmt.setString(6, as.getContent());
			} else {
				pStmt.setString(6, "");
			}
			
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "UPDATE Assignments SET submissionStatus=?, content=?, submissionDate=? "
					+ "WHERE assignmentId like ? AND studentId like ? AND subjectId like ? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(as.getSubmissionDate());
			
			if (as.getSubmissionStatus() != null) {
				pStmt.setString(1, as.getSubmissionStatus());
			} else {
				pStmt.setString(1, "");
			}
			
			if (as.getContent() != null) {
				pStmt.setString(2, as.getContent());
			} else {
				pStmt.setString(2, "");
			}
			
			pStmt.setString(3, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));

						
			if (as.getAssignmentId() > 0) {
				pStmt.setString(4, "" + as.getAssignmentId());
			} else {
				pStmt.setString(4, "%");
			}
			if (as.getStudentId() > 0) {
				pStmt.setString(5, "" + as.getStudentId());
			} else {
				pStmt.setString(5, "%");
			}
			if (as.getSubjectId() > 0) {
				pStmt.setString(6, "" + as.getSubjectId());
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

	// 引数_arで指定された番号のレコードを削除し、成功したらtrueを返す
	public boolean delete(Assignments as) {
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
			String sql = "DELETE FROM Assignments WHERE assignmentId=?";
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "DELETE FROM Assignments WHERE assignmentId=?";
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
