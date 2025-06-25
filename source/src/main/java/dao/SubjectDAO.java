package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Subject;

public class SubjectDAO {
	public List<Subject> select(Subject sub) {
		Connection conn = null;
		List<Subject> cardList = new ArrayList<Subject>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM subject WHERE subjectId LIKE ? AND subjectName LIKE ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (sub.getSubjectId() > 0) {
				pStmt.setString(1, "" + sub.getSubjectId());
			} else {
				pStmt.setString(1, "%");
			}
			if (sub.getSubjectName() != null) {
				pStmt.setString(2, "%" + sub.getSubjectName() + "%");
			} else {
				pStmt.setString(2, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				Subject bc = new Subject(
						rs.getInt("subjectId"), 
						rs.getString("subjectName")
						);
				cardList.add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}

		// 結果を返す
		return cardList;
	}	
}
