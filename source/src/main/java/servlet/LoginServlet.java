package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TeachersDAO;
import dto.Teacher;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		
		String teacherId = request.getParameter("teacherId");
		String password = request.getParameter("password");
		
		if (teacherId == null || teacherId.isEmpty()) {
			response.sendRedirect("LoginServlet?error=1");
			return;
		}
		
		// DTOに格納
		Teacher input = new Teacher(teacherId, null, password);
		
		// DAOで照合
		TeachersDAO dao = new TeachersDAO();
		Teacher loginTeacher = dao.findByIdAndPassword(input);
		
		if (loginTeacher != null) { 
		    // ログイン成功→セッションスコープにIDを格納する
		    HttpSession session = request.getSession();
		    session.setAttribute("loginTeacher", loginTeacher);
		    
		    // スケジュール閲覧用のサーブレットへリダイレクト
		    response.sendRedirect("InfoScheduleServlet");
		} else { 
		    // 認証失敗時にリダイレクト
		    response.sendRedirect("LoginServlet?error=1");
		}
	}
}
