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
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/D1/LoginServlet");
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
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/D1/LoginServlet");
			return;
		}

		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
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

		// 登録処理を行う
		StudentsDAO sDao = new StudentsDAO();
		if (sDao.insert(new Students(-1, grade, classId, studentNum, name, nameRuby, "", "", ""))) { // 登録成功時は、"InfoStudentServlet"にフォワード  "/webapp/InfoStudentServlet"のwebappをD1に変更 
			RequestDispatcher dispatcher = request.getRequestDispatcher("/D1/InfoStudentServlet");
            dispatcher.forward(request, response);
		} else {
            //登録失敗時は"InfoScheduleServlet"にフォワード                                                                         "/webapp/LoginServlet"のwebappをD1に変更
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/D1/InfoScheduleServlet");
            dispatcher.forward(request, response);
		}

	}
}