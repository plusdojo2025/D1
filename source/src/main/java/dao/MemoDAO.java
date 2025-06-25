package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Memo;

public class MemoDAO {

	// 全件取得
	public List<Memo> selectAll() {
		Connection conn = null;
		List<Memo> memoList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql = "SELECT * FROM Memo ORDER BY memoid";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				Memo memo = new Memo(
					rs.getInt("memoid"),
					rs.getInt("teacherId"),
					rs.getInt("classId"),
					rs.getString("content"),
					rs.getDate("date"),
					rs.getString("period")
				);
				memoList.add(memo);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			memoList = null;
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				memoList = null;
			}
		}
		return memoList;
	}

	// 条件検索（content の部分一致）
	public List<Memo> select(Memo search) {
		Connection conn = null;
		List<Memo> memoList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
					"root", "password");

			String sql = "SELECT * FROM Memo WHERE content LIKE ? ORDER BY memoid";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (search.getContent() != null && !search.getContent().isEmpty()) {
				pStmt.setString(1, "%" + search.getContent() + "%");
			} else {
				pStmt.setString(1, "%");
			}

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				Memo memo = new Memo(
					rs.getInt("memoid"),
					rs.getInt("teacherId"),
					rs.getInt("classId"),
					rs.getString("content"),
					rs.getDate("date"),
					rs.getString("period")
				);
				memoList.add(memo);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			memoList = null;
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				memoList = null;
			}
		}
		return memoList;
	}

	// 追加
	public boolean insert(Memo memo) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
					"root", "password");

			String sql = "INSERT INTO Memo (teacherId, classId, content, date, period) "
					   + "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, memo.getTeacherId());
			pStmt.setInt(2, memo.getClassId());
			pStmt.setString(3, memo.getContent());
			pStmt.setDate(4, new java.sql.Date(memo.getDate().getTime()));
			pStmt.setString(5, memo.getPeriod());

			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 更新
	public boolean update(Memo memo) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
					"root", "password");

			String sql = "UPDATE Memo SET teacherId = ?, classId = ?, content = ?, date = ?, period = ? "
					   + "WHERE memoid = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, memo.getTeacherId());
			pStmt.setInt(2, memo.getClassId());
			pStmt.setString(3, memo.getContent());
			pStmt.setDate(4, new java.sql.Date(memo.getDate().getTime()));
			pStmt.setString(5, memo.getPeriod());
			pStmt.setInt(6, memo.getMemoId());

			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 削除
	public boolean delete(int memoId) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
					"root", "password");

			String sql = "DELETE FROM Memo WHERE memoid = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, memoId);

			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}