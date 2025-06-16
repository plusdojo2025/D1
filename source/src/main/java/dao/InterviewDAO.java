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

import dto.Interview;

public class InterviewDAO {
	public List<Interview> select(Interview _itv) {
		Connection conn = null;
		List<Interview> itvList = new ArrayList<Interview>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM Interview "
					+ "WHERE interviewId = ? AND teacherId = ? AND "
					+ "year(date) like ? AND month(date) like ? AND day(date) like ? AND "
					+ "studentId = ? AND remarks = ? AND subjectId?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_itv.getDate());

			pStmt.setInt(1, _itv.getInterviewId());
			pStmt.setInt(2, _itv.getTeacherId());
			pStmt.setInt(3, calendar.get(Calendar.YEAR));
			pStmt.setInt(4, calendar.get(Calendar.MONTH));
			pStmt.setInt(5, calendar.get(Calendar.DAY_OF_MONTH));
			pStmt.setInt(6, _itv.getStudentId());
			
			if (_itv.getContents() != null) {
				pStmt.setString(7, "%" + _itv.getContents() + "%");
			} else {
				pStmt.setString(7, "%");
			}
			
			if (_itv.getRemarks() != null) {
				pStmt.setString(8, "%" + _itv.getRemarks() + "%");
			} else {
				pStmt.setString(8, "%");
			}
			
			pStmt.setInt(9, _itv.getSubjectId());

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdFormat.parse(rs.getString("date"));

				Interview itv = new Interview(rs.getInt("interviewId"), rs.getInt("teacherId"), date, 
						rs.getInt("studentId"), rs.getString("contents"), rs.getString("remarks"), rs.getInt("subjectId"));
				itvList.add(itv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			itvList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			itvList = null;
		} catch (ParseException e) {
			e.printStackTrace();
			itvList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					itvList = null;
				}
			}
		}

		// 結果を返す
		return itvList;
	}

	public boolean insert(Interview _itv) {
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
			String sql = "INSERT INTO Interview VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_itv.getDate());

			pStmt.setInt(1, _itv.getInterviewId());
			pStmt.setInt(2, _itv.getTeacherId());
			pStmt.setString(3, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			pStmt.setInt(4, _itv.getStudentId());
			
			if (_itv.getContents() != null) {
				pStmt.setString(5, "%" + _itv.getContents() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			
			if (_itv.getRemarks() != null) {
				pStmt.setString(6, "%" + _itv.getRemarks() + "%");
			} else {
				pStmt.setString(6, "%");
			}
			
			pStmt.setInt(7, _itv.getSubjectId());

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

	public boolean update(Interview _itv) {
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
			String sql = "UPDATE Interview SET interviewId=?, teacherId=?, date=?, studentId=?, "
					+ "contents=?, remarks=?, subjectId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(_itv.getDate());

			pStmt.setInt(1, _itv.getInterviewId());
			pStmt.setInt(2, _itv.getTeacherId());
			pStmt.setString(3, calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			pStmt.setInt(4, _itv.getStudentId());
			
			if (_itv.getContents() != null) {
				pStmt.setString(5, "%" + _itv.getContents() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			
			if (_itv.getRemarks() != null) {
				pStmt.setString(6, "%" + _itv.getRemarks() + "%");
			} else {
				pStmt.setString(6, "%");
			}
			
			pStmt.setInt(7, _itv.getSubjectId());

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

	public boolean delete(Interview _itv) {
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
			String sql = "DELETE FROM Interview WHERE interviewId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, _itv.getInterviewId());

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

	public boolean delete(int interviewId) {
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
			String sql = "DELETE FROM Interview WHERE interviewId=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, interviewId);

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