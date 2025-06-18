package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Students;

public class StudentsDAO {
	// 引数st指定された項目で検索して、取得されたデータのリストを返す
	public List<Students> select(Students st) {
		Connection conn = null;
		List<Students> stList = new ArrayList<Students>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			// SQL文を準備する
			String sql = "SELECT * FROM Students "     //変更箇所//
					+ "WHERE studentId like ? AND year like ? AND grade like ? AND "
					+ "classId = ? AND studentNum = ?;";
					
			PreparedStatement pStmt = conn.prepareStatement(sql);	

            if (st.getStudentId() >0) {
        		pStmt.setString(1, ""+ st.getStudentId());
            } else {
        		pStmt.setString(1, "%");
            }
            if (st.getYear() >0) {
        		pStmt.setString(2, ""+ st.getYear());
            } else {
        		pStmt.setString(2, "%");
            }            
            if (st.getGrade() >0) {
        		pStmt.setString(3, ""+ st.getGrade());
            } else {
        		pStmt.setString(3, "%");
            }
            if (st.getClassId() >0) {
        		pStmt.setString(4, ""+ st.getClassId());
            } else {
        		pStmt.setString(4, "%");
            }            
            if (st.getStudentNum() >0) {
            	System.out.println("if "+st.getStudentNum());
        		pStmt.setString(5, ""+ st.getStudentNum());
            } else {
            	System.out.println("else "+st.getStudentNum());
        		pStmt.setString(5, "%");
            }


			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();  

			// 結果表をコレクションにコピーする
			while (rs.next()) { 
				Students students = new Students(rs.getInt("studentId"),
						                         rs.getInt("year"),
                                                 rs.getInt("grade"),
                                                 rs.getInt("classId"),
                                                 rs.getInt("studentNum"),
                                                 rs.getString("name"),
                                                 rs.getString("nameRuby"),
                                                 rs.getString("enrollmentStatus"),
                                                 rs.getString("extracurricularActivities"),
                                                 rs.getString("attitude"));
				stList.add(students);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			stList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			stList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					stList = null;
				}
			}
		}
		
		// 結果を返す
		return stList;
	}
	
	// 引数stで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(Students st) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
	
			// データベースに接続する　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　変更箇所
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");


			// SQL文を準備する      変更箇所
			String sql = "INSERT INTO Students VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);			
			
			// SQL文を完成させる			
			pStmt.setInt(1, st.getStudentId());
			pStmt.setInt(2, st.getYear());
			pStmt.setInt(3, st.getGrade());
			pStmt.setInt(4, st.getClassId());

            if (st.getStudentNum() >0) {
        		pStmt.setString(5, ""+ st.getStudentNum());
            } else {
        		pStmt.setString(5, "%");
            }            


			if (st.getName() != null) {
				pStmt.setString(6, st.getName());
			} else {
				pStmt.setString(6, "");
			}
			if (st.getNameRuby() != null) {
				pStmt.setString(7, st.getNameRuby());
			} else {
				pStmt.setString(7, "");
			}
			if (st.getEnrollmentStatus() != null) {
				pStmt.setString(8, st.getEnrollmentStatus());
			} else {
				pStmt.setString(8, "");
			}
            if (st.getExtracurricularActivities() != null) {
				pStmt.setString(9, st.getExtracurricularActivities());
			} else {
				pStmt.setString(9, "");
			}
            if (st.getAttitude() != null) {
				pStmt.setString(10, st.getAttitude());
			} else {
				pStmt.setString(10, "");
			}
            // SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;			  
			}
			else {
				result = false;
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
	
	// 引数stで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(Students st) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する                                      変更箇所
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			// SQL文を準備する  変更箇所
			String sql = "UPDATE Students SET year=?, grade=?, classId=?, "
					+ "studentNum =?, name=?, nameRuby=?, enrollmentStatus=?, "
					+ "extracurricularActivities=?, attitude=? WHERE studentId like ? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);			
 
			//set grade=?  where studentId=?
			
			
			// SQL文を完成させる
			pStmt.setInt(1, st.getYear());
			pStmt.setInt(2, st.getGrade());
			pStmt.setInt(3, st.getClassId());
            if (st.getStudentNum() >0) {
        		pStmt.setString(4, ""+ st.getStudentNum());
            } else {
        		pStmt.setString(4, "%");
            }			


			
			if (st.getName() != null) {
				pStmt.setString(5, st.getName());
			} else {
				pStmt.setString(5, "");
			}


			if (st.getNameRuby() != null) {
				pStmt.setString(6, st.getNameRuby());
			} else {
				pStmt.setString(6, "");
			}


			if (st.getEnrollmentStatus() != null) {
				pStmt.setString(7, st.getEnrollmentStatus());
			} else {
				pStmt.setString(7, "");
			}


			if (st.getExtracurricularActivities() != null) {
				pStmt.setString(8, st.getExtracurricularActivities());
			} else {
				pStmt.setString(8, "");
			}

			if (st.getAttitude() != null) {
				pStmt.setString(9, st.getAttitude());
			} else {
				pStmt.setString(9, "");
			}
						
			
            if (st.getStudentId() >0) {
        		pStmt.setString(10, ""+ st.getStudentId());
            } else {
        		pStmt.setString(10, "%");
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

	// 引数stで指定された番号のレコードを削除し、成功したらtrueを返す
	public boolean delete(Students st) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　変更箇所
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D1?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する            変更箇所
			String sql = "DELETE FROM Students WHERE studentId=? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, st.getStudentId());

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
			

			
