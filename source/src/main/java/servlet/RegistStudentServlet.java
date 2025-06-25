package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StudentsDAO;
import dto.Students;


/**
 * Servlet implementation class RegistStudentServlet
 */
@WebServlet("/RegistStudentServlet")
public class RegistStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする "/webapp/LoginServlet"のwebappをD1に変更
		 
		 
		 
		 
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginTeacher") == null) {
        	response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

		 
		 
		 
		 

		// 登録ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regist_student.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする "/webapp/LoginServlet"のwebappをD1に変更
		
		
		
		
		
		
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginTeacher") == null) {
        	response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

		
		
		
		
		
		
		
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		//入学年
        int year = -1;        
        String yearStr = request.getParameter("year");
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                // 数値に変換できない場合の処理（エラーメッセージの設定）
                request.setAttribute("error", "入学年は整数で入力してください。");
            }
        }		
		//学年
        int grade = -1;        
        String gradeStr = request.getParameter("grade");
        if (gradeStr != null && !gradeStr.isEmpty()) {
            try {
                grade = Integer.parseInt(gradeStr);
            } catch (NumberFormatException e) {
                // 数値に変換できない場合の処理（エラーメッセージの設定）
                request.setAttribute("error", "学年は整数で入力してください。");
            }
        }
        //クラス
        int classId = -1;
        String classIdStr = request.getParameter("classId");
        if (classIdStr != null && !classIdStr.isEmpty()) {
        	try {
        		classId = Integer.parseInt(classIdStr);
        	} catch (NumberFormatException e) {
                // 数値に変換できない場合の処理（エラーメッセージの設定）
                request.setAttribute("error", "クラスは整数で入力してください。");
        	}
        }
        
        
        
        
        //追加
        if (classIdStr != null && !classIdStr.trim().isBlank()) {
            try {
                classId = Integer.parseInt(classIdStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "クラスは整数で入力してください。");
            }
        } else {
            request.setAttribute("error", "クラスを選択してください。");
        }
        
        
        
        
        
        
        //出席番号 
        int studentNum = -1;
        String studentNumStr = request.getParameter("studentNum");
        if (studentNumStr != null && !studentNumStr.isEmpty()) {
            try {
                studentNum = Integer.parseInt(studentNumStr);
            } catch (NumberFormatException e) {
                // 数値に変換できない場合の処理（エラーメッセージの設定）
                request.setAttribute("error", "出席番号は整数で入力してください。");
            }
        }
        //氏名とふりがな
		String name = request.getParameter("name");
		String nameRuby = request.getParameter("nameRuby");

		StudentsDAO sDao = new StudentsDAO();
		

		//重複チェックを追加
		if (sDao.exists(classId, studentNum)) {
		    request.setAttribute("error", "登録済みの出席番号です");

		    // 入力値を保持（オプション）
		    request.setAttribute("year", year);
		    request.setAttribute("grade", grade);
		    request.setAttribute("classId", classId);
		    request.setAttribute("studentNum", studentNum);
		    request.setAttribute("name", name);
		    request.setAttribute("nameRuby", nameRuby);

		    
		    
		    
		    
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regist_student.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
		
		
		
		// 登録処理を行う
		if (sDao.insert(new Students(0, year, grade, classId, studentNum, name, nameRuby, "", "", ""))) { // 登録成功時は、"InfoStudentServlet"にフォワード  "/webapp/InfoStudentServlet"のwebappをD1に変更 
			response.sendRedirect(request.getContextPath() + "/ListStudentServlet");
		    return;
		} else {
            request.setAttribute("error", "登録済みの出席番号です");
            //登録失敗時は"Servlet"にフォワード                                "/RegistStudentServlet"                                 "/webapp/LoginServlet"のwebappをD1に変更
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/regist_student.jsp");
            dispatcher.forward(request, response);
            return;
		}

	}
}