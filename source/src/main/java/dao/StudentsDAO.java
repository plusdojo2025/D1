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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentssample_test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			// SQL文を準備する
			String sql = "SELECT * FROM studentssample_test "     //変更箇所//
					+ "WHERE studentId like ? AND grade like ? AND "
					+ "classId = ? AND studentNum = ? AND "
					+ "name = ? AND nameRuby = ? AND enrollmentStatus = ? AND "
					+ "extracurricularActivities = ? AND attitude = ? ";
					
			PreparedStatement pStmt = conn.prepareStatement(sql);	

            if (st.getStudentId() >0) {
        		pStmt.setString(1, ""+ st.getStudentId());
            } else {
        		pStmt.setString(1, "%");
            }
            if (st.getGrade() >0) {
        		pStmt.setString(2, ""+ st.getGrade());
            } else {
        		pStmt.setString(2, "%");
            }
            

			pStmt.setInt(3, st.getClassId());
			pStmt.setInt(4, st.getStudentNum());
			
            if (st.getName() != null) {
            		pStmt.setString(5, "%" + st.getName() + "%");
            } else {
            		pStmt.setString(5, "%");
            }

            if (st.getNameRuby() != null) {
            		pStmt.setString(6, "%" + st.getNameRuby() + "%");
            } else {
            		pStmt.setString(6, "%");
            }

            if (st.getEnrollmentStatus() != null) {
            		pStmt.setString(7, "%" + st.getEnrollmentStatus() + "%");
            } else {
            		pStmt.setString(7, "%");
            }

            if (st.getExtracurricularActivities() != null) {
            		pStmt.setString(8, "%" + st.getExtracurricularActivities() + "%");
            } else {
            		pStmt.setString(8, "%");
            }

            if (st.getAttitude() != null) {
            		pStmt.setString(9, "%" + st.getAttitude() + "%");
            } else {
            		pStmt.setString(9, "%");
            }	
			
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();  

			// 結果表をコレクションにコピーする
			while (rs.next()) { 
				Students students = new Students(rs.getInt("studentId"),
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentssample_test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");


			// SQL文を準備する      変更箇所
			String sql = "INSERT INTO studentssample_test VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);			
			
			// SQL文を完成させる			
			pStmt.setInt(1, st.getStudentId());
			pStmt.setInt(2, st.getGrade());
			pStmt.setInt(3, st.getClassId());
            pStmt.setInt(4, st.getStudentNum());
			//pStmt.setString(5, st.getName());
			//pStmt.setString(6, st.getNameRuby());
			//pStmt.setString(7, st.getEnrollmentStatus());
			//pStmt.setString(8, st.getExtracurricularActivities());
			//pStmt.setString(9, st.getAttitude());

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
	
	// 引数stで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(Students st) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する                                      変更箇所
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentssample_test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			
			// SQL文を準備する  変更箇所
			String sql = "UPDATE studentssample_test SET grade=?, classId=?, "
					+ "studentNum =?, name=?, nameRuby=?, enrollmentStatus=?, "
					+ "extracurricularActivities=?, attitude=? WHERE studentId like ? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);			
 
			//set grade=?  where studentId=?
			
			
			// SQL文を完成させる
			pStmt.setInt(1, st.getGrade());
			pStmt.setInt(2, st.getClassId());
			pStmt.setInt(3, st.getStudentNum());
			//pStmt.setString(4, st.getName());
			//pStmt.setString(5, st.getNameRuby());
			//pStmt.setString(6, st.getEnrollmentStatus());
			//pStmt.setString(7, st.getExtracurricularActivities());
			//pStmt.setString(8, st.getAttitude());
			
			if (st.getName() != null) {
				pStmt.setString(4, st.getName());
			} else {
				pStmt.setString(4, "");
			}


			if (st.getNameRuby() != null) {
				pStmt.setString(5, st.getNameRuby());
			} else {
				pStmt.setString(5, "");
			}


			if (st.getEnrollmentStatus() != null) {
				pStmt.setString(6, st.getEnrollmentStatus());
			} else {
				pStmt.setString(6, "");
			}


			if (st.getExtracurricularActivities() != null) {
				pStmt.setString(7, st.getExtracurricularActivities());
			} else {
				pStmt.setString(7, "");
			}

			if (st.getAttitude() != null) {
				pStmt.setString(8, st.getAttitude());
			} else {
				pStmt.setString(8, "");
			}
						
			
            if (st.getStudentId() >0) {
        		pStmt.setString(9, ""+ st.getStudentId());
            } else {
        		pStmt.setString(9, "%");
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentssample_test?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する            変更箇所
			String sql = "DELETE FROM studentssample_test WHERE studentId=? ";
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
			

			
