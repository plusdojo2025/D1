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
	// 引数cr指定された項目で検索して、取得されたデータのリストを返す
	public List<ClassRoom> select(ClassRoom cr) {
		Connection conn = null;
		List<ClassRoom> crList = new ArrayList<ClassRoom>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			// SQL文を準備する
			String sql = "SELECT * FROM Class "
					+ "WHERE classId LIKE ? AND grade LIKE ? AND "
					+ " className LIKE ?;";
					
			PreparedStatement pStmt = conn.prepareStatement(sql);	
		
            if (cr.getClassId() >0) {
        		pStmt.setString(1, ""+ cr.getClassId());
            } else {
        		pStmt.setString(1, "%");
            }			

            if (cr.getGrade() >0) {
        		pStmt.setString(2, ""+ cr.getGrade());
            } else {
        		pStmt.setString(2, "%");
            }
            
            if (cr.getClassName() != null) {
        		pStmt.setString(3, ""+ cr.getClassName());
            } else {
        		pStmt.setString(3, "%");
            }	            


			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();  

			// 結果表をコレクションにコピーする
			while (rs.next()) { 
				ClassRoom classroom = new ClassRoom(rs.getInt("classId"),
                                                    rs.getInt("grade"),
                                                    rs.getString("className")); 
				                                    
				crList.add(classroom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			crList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			crList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					crList = null;
				}
			}
		}
		
		// 結果を返す
		return crList;
	}

	// 引数crで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(ClassRoom cr) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
	
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");


			// SQL文を準備する
			String sql = "INSERT INTO Class VALUES (?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);			
			
			// SQL文を完成させる			
			pStmt.setInt(1, cr.getClassId());
			pStmt.setInt(2, cr.getGrade());
			//pStmt.setString(3, cr.getClassName());
			

			if (cr.getClassName() != null) {
				pStmt.setString(3, cr.getClassName());
			} else {
				pStmt.setString(3, "");
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
	
	// 引数crで指定されたレコードを更新し、成功したらtrueを返す 
	public boolean update(ClassRoom cr) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			// SQL文を準備する
			String sql = "UPDATE Class SET grade=?,"
					+ "className=? WHERE classId LIKE ?";
			//String sql = "UPDATE Class SET classId =?, grade=?, className=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);			
    
			// SQL文を完成させる			
			pStmt.setInt(1, cr.getGrade());
			//pStmt.setString(2, cr.getClassName());
			
			
			if (cr.getClassName() != null) {
				pStmt.setString(2, cr.getClassName());
			} else {
				pStmt.setString(2, "");
			}

            if (cr.getClassId() >0) {
        		pStmt.setString(3, ""+ cr.getClassId());
            } else {
        		pStmt.setString(3, "%");
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

	// 引数crで指定された番号のレコードを削除し、成功したらtrueを返す
	public boolean delete(ClassRoom cr) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "DELETE FROM Class WHERE classId=?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, cr.getClassId());

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
	
	
	
	
	//テストで追加
	public List<ClassRoom> selectAll() {
	    Connection conn = null;
	    List<ClassRoom> crList = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d1?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "root", "password");

	        String sql = "SELECT * FROM Class";
	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        ResultSet rs = pStmt.executeQuery();

	        while (rs.next()) {
	            ClassRoom classroom = new ClassRoom(rs.getInt("classId"), rs.getInt("grade"), rs.getString("className"));
	            crList.add(classroom);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        crList = null;
	    } finally {
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	    return crList;
	}
}	
