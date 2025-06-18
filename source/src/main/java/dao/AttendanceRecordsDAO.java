package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords "
					+ "WHERE studentId LIKE ? AND classId LIKE ? AND "
					+ "year(date) LIKE ? AND month(date) LIKE ? AND "
					+ "period LIKE ? AND subjectId LIKE ? AND "
					+ "status LIKE ? AND remarks LIKE ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (_ar.getStudentId() > 0) {
				pStmt.setString(1, "" + _ar.getStudentId());
			} else {
				pStmt.setString(1, "%");
			}

			if (_ar.getClassId() > 0) {
				pStmt.setString(2, "" + _ar.getClassId());
			} else {
				pStmt.setString(2, "%");
			}

			if (_ar.getYear() > 0) {
				pStmt.setString(3, "" + _ar.getYear());
			} else {
				pStmt.setString(3, "%");
			}

			if (_ar.getMonth() > 0 && _ar.getMonth() <= 12) {
				pStmt.setString(4, "" + _ar.getMonth());
			} else {
				pStmt.setString(4, "%");
			}

			if (_ar.getPeriod() != null) {
				pStmt.setString(5, "%" + _ar.getPeriod() + "%");
			} else {
				pStmt.setString(5, "%");
			}

			if (_ar.getSubjectId() > 0) {
				pStmt.setString(6, "" + _ar.getSubjectId());
			} else {
				pStmt.setString(6, "%");
			}

			if (_ar.getStatus() != null) {
				pStmt.setString(7, "%" + _ar.getStatus() + "%");
			} else {
				pStmt.setString(7, "%");
			}

			if (_ar.getRemarks() != null) {
				pStmt.setString(8, "%" + _ar.getRemarks() + "%");
			} else {
				pStmt.setString(8, "%");
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords WHERE studentId LIKE ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			if (studentId > 0) {
				pStmt.setString(1, "" + studentId);
			} else {
				pStmt.setString(1, "%");
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

	public List<AttendanceRecords> select(int studentId, int subjectId) {
		Connection conn = null;
		List<AttendanceRecords> arList = new ArrayList<AttendanceRecords>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords WHERE studentId LIKE ? AND subjectId LIKE ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (studentId > 0) {
				pStmt.setString(1, "" + studentId);
			} else {
				pStmt.setString(1, "%");
			}
			if (subjectId > 0) {
				pStmt.setString(2, "" + subjectId);
			} else {
				pStmt.setString(2, "%");
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

	// クラス+科目ごとの検索
	public List<AttendanceRecords> select_Class(int classId, int subjectId) {
		Connection conn = null;
		List<AttendanceRecords> arList = new ArrayList<AttendanceRecords>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords WHERE classId LIKE ? AND subjectId LIKE ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (classId > 0) {
				pStmt.setString(1, "" + classId);
			} else {
				pStmt.setString(1, "%");
			}
			if (subjectId > 0) {
				pStmt.setString(2, "" + subjectId);
			} else {
				pStmt.setString(2, "%");
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
	
	// 学年+クラス+科目ごとの検索
	public List<AttendanceRecords> select_Grade(int grade, int classId, int month, int subjectId) {
		Connection conn = null;
		List<AttendanceRecords> arList = new ArrayList<AttendanceRecords>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords RIGHT OUTER Join Students "
					+ "On AttendanceRecords.studentId = Students.studentId"
					+ "WHERE grade LIKE ? AND classId LIKE ? AND month(date) LIKE ? AND subjectId LIKE ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (grade > 0 && grade <= 3) {
				pStmt.setString(1, "" + grade);
			} else {
				pStmt.setString(1, "%");
			}

			if (classId > 0) {
				pStmt.setString(2, "" + classId);
			} else {
				pStmt.setString(2, "%");
			}

			if (month > 0 && month <= 12) {
				pStmt.setString(3, "" + month);
			} else {
				pStmt.setString(3, "%");
			}

			if (subjectId > 0) {
				pStmt.setString(4, "" + subjectId);
			} else {
				pStmt.setString(4, "%");
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

	// 年度+クラス+科目ごとの検索 fiscalYearに年度を入力
	public List<AttendanceRecords> select_Fiscal(int fiscalYear, int classId, int month, int subjectId) {
		Connection conn = null;
		List<AttendanceRecords> arList = new ArrayList<AttendanceRecords>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM AttendanceRecords RIGHT OUTER Join Students "
					+ "On AttendanceRecords.studentId = Students.studentId"
					+ "WHERE year(date) LIKE ? AND classId LIKE ? AND month(date) LIKE ? AND subjectId LIKE ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			int year;
			if (month <= 3) year = fiscalYear + 1; // 年度から年に修正
			else year = fiscalYear;

			if (year > 0) {
				pStmt.setString(1, "" + year);
			} else {
				pStmt.setString(1, "%");
			}

			if (classId > 0) {
				pStmt.setString(2, "" + classId);
			} else {
				pStmt.setString(2, "%");
			}

			if (month > 0 && month <= 12) {
				pStmt.setString(3, "" + month);
			} else {
				pStmt.setString(3, "%");
			}

			if (subjectId > 0) {
				pStmt.setString(4, "" + subjectId);
			} else {
				pStmt.setString(4, "%");
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


	// 引数_arで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(AttendanceRecords _ar) {
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
				pStmt.setString(4, "");
			}

			pStmt.setInt(5, _ar.getSubjectId());

			if (_ar.getStatus() != null) {
				pStmt.setString(6, _ar.getStatus());
			} else {
				pStmt.setString(6, "");
			}

			if (_ar.getRemarks() != null) {
				pStmt.setString(7, _ar.getRemarks());
			} else {
				pStmt.setString(7, "");
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "UPDATE AttendanceRecords SET status=?, remarks=? WHERE studentId LIKE ? AND subjectId LIKE ?;";
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
				pStmt.setString(2, "");
			}

			if (_ar.getStudentId() > 0) {
				pStmt.setString(3, "" + _ar.getStudentId());
			} else {
				pStmt.setString(3, "");
			}

			if (_ar.getSubjectId() > 0) {
				pStmt.setString(4, "" + _ar.getSubjectId());
			} else {
				pStmt.setString(4, "");
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
	public boolean delete(AttendanceRecords _ar) {
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
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

	// 引数で指定した生徒の出席日数を返す
	public int GetAttendedNum(List<AttendanceRecords> ar) {
		int count = 0;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_WEEK);
		
		for (int i = 0; i < ar.size(); i++) {
			if (isFirstSemester(month, day) ) {
				// 前期
				if (ar.get(i).getDate().after(new Date(year, 3, 31)) && ar.get(i).getDate().before(new Date(year, 10, 15)) && ar.get(i).getStatus().equals("○")) {
					count++;
				}
			} else {
				// 年明け後
				if (month + 1 >= 1 && day >= 1) {
					if (ar.get(i).getDate().after(new Date(year-1, 10, 15)) && ar.get(i).getDate().before(new Date(year, 3, 31)) && ar.get(i).getStatus().equals("○")) {
						count++;
					}
				} else {
					if (ar.get(i).getDate().after(new Date(year, 10, 15)) && ar.get(i).getDate().before(new Date(year+1, 3, 31)) && ar.get(i).getStatus().equals("○")) {
						count++;
					}
				}
			}
		}
		
		// 結果を返す
		return count;
	}

	// 引数で指定した生徒の出席すべき日数を返す
	public int GetShouldAttendedNum(List<AttendanceRecords> ar) {
		int count = 0;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < ar.size(); i++) {
			if (isFirstSemester(month, day) ) {
				// 前期
				if (ar.get(i).getDate().after(new Date(year, 3, 31)) && ar.get(i).getDate().before(new Date(year, 10, 15)) && !ar.get(i).getStatus().equals("公欠")) {
					count++;
				}
			} else {
				// 年明け後
				if (month + 1 >= 1 && day >= 1) {
					if (ar.get(i).getDate().after(new Date(year-1, 10, 15)) && ar.get(i).getDate().before(new Date(year, 3, 31)) && !ar.get(i).getStatus().equals("公欠")) {
						count++;
					}
				} else {
					if (ar.get(i).getDate().after(new Date(year, 10, 15)) && ar.get(i).getDate().before(new Date(year+1, 3, 31)) && !ar.get(i).getStatus().equals("公欠")) {
						count++;
					}
				}
			}
		}
		return count;
	}

	// 出席率を百分率/小数点第二位までのString型で返す
	public String GetAttendedRate(int attendedNum, int shouldAttendedNum) {
		String rate = String.format("%.1f", (attendedNum / shouldAttendedNum * 100.0f));
		return rate;
	}

	// 入力した月日が前期かどうか判定する
	public boolean isFirstSemester(int month, int day) {
		if (month + 1 >= 4 && day >= 1 && month + 1 <= 10 && day <= 14) {
			return true;
		} else {
			return false;
		}
	}
}
