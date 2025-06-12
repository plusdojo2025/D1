package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ListStudentServlet
 */
@WebServlet("/ListStudentServlet")
public class ListStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/webappAns/LoginServlet");
			return;
		}

		// 検索ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
				HttpSession session = request.getSession();
				if (session.getAttribute("id") == null) {
					response.sendRedirect("/webappAns/LoginServlet");
					return;
				}
				
				// リクエストパラメータを取得する
				request.setCharacterEncoding("UTF-8");
				int grade = Integer.parseInt(request.getParameter("grade"));              //学年
				String className = request.getParameter("className");      //クラス
				String month = request.getParameter("month");                //月
				String subjectId = request.getParameter("subjectId");      //教科Id
				String subjectName = request.getParameter("subjectName");  //教科
				String date = request.getParameter("date");                //日付(日)
				String period = request.getParameter("period");            //時限
				String studentNum = request.getParameter("studentNum");    //出席番号
				String name = request.getParameter("name");                //氏名
				String nameRuby = request.getParameter("nameRuby");        //ふりがな
				String status = request.getParameter("status");            //出欠
				String content = request.getParameter("content");          //提出内容
				String submissionStatus = request.getParameter("submissionStatus");  //提出状況
				String testType = request.getParameter("testType");        //テスト種別
				String score = request.getParameter("score");              //点数
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	}

}
