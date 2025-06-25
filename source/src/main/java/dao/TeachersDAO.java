package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.Teacher;

public class TeachersDAO {
	// 引数で指定されたteacherIdとpasswordに一致する教員を取得し、存在すればTeacherオブジェクトを返す
	public Teacher findByIdAndPassword(Teacher t) {
		Connection conn = null;
		Teacher teacher = null;
	
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
                    "root", "password");
			
			// SQL文の準備する
            String sql = "SELECT * FROM Teachers WHERE userId = ? AND password = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            
            // SQL文を完成させる
            pStmt.setString(1, t.getUserId());
			pStmt.setString(2, t.getPassword());
            
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			
			// 該当する教員が存在した場合、その情報をteacherに格納
			if (rs.next()) {
				teacher = new Teacher(
					rs.getInt("teacherId"),
					rs.getString("name"),
					rs.getString("userId"),
					rs.getString("password"));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			teacher = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			teacher = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					teacher = null;
				}
			}
		}
		
		// 結果を返す（nullならログイン失敗、Teacherが返れば成功）
		return teacher;
	}
}
          