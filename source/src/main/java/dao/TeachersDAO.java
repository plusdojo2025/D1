package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Teacher;

public class TeachersDAO {
	public boolean isLoginOK(Teacher teacher) {
		boolean isOK = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// JDBCドライバ読み込み
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?"
                    + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
                    "root", "password");
			
			// SQL文の準備する
            String sql = "SELECT * FROM Teachers WHERE teacherId = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, teacher.getTeacherId());
            stmt.setString(2, teacher.getPassword());
            
            // SELECT文を実行し、結果表を取得する
            rs = stmt.executeQuery();
            
            // ユーザーIDとパスワードが一致するユーザーがいれば結果をtrueにする
         	if(rs.next()) {
         		isOK = true;
         	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch(Exception e) {}
			try { if (stmt != null) stmt.close(); } catch(Exception e) {}
			try { if (conn != null) conn.close(); } catch(Exception e) {}
		}
		
		return isOK;
	}
}
            
            
            
            