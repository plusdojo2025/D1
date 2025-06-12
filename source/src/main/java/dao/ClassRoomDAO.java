package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClassRoom;

public class ClassRoomDAO {
	// 引数cs指定された項目で検索して、取得されたデータのリストを返す
	public List<ClassRoom> select(ClassRoom cs) {
		Connection conn = null;
		List<ClassRoom> csList = new ArrayList<ClassRoom>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			// SQL文を準備する
			String sql = "SELECT * FROM Class"
					+ "WHERE classId = ? AND grade = ? AND"
					+ "className = ?";
					
			PreparedStatement pStmt = conn.prepareStatement(sql);	
		
			pStmt.setInt(1, cs.getClassId());
			pStmt.setInt(2, cs.getGrade());
						
            if (cs.getClassName() != null) {
            		pStmt.setString(3, "%" + st.getClassName() + "%");
            } else {
            		pStmt.setString(3, "%");
            }

			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();  

			// 結果表をコレクションにコピーする
			while (rs.next()) { 
				ClassRoom classroom = new ClassRoom(rs.getInt("classId"),
                                                 rs.getInt("grade"),
                                            rs.getString("ClassName")); 
				csList.add(classroom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			csList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			csList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					csList = null;
				}
			}
		}
		
		// 結果を返す
		return csList;
	}

}
