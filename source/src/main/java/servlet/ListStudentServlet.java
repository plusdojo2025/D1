package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AttendanceRecordsDAO;
import dto.AttendanceRecords;

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
				Date date=new Date(); ;
				try {
					date = DateFormat.getDateInstance().parse(request.getParameter("date"));
				} catch (ParseException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				
				String year = request.getParameter("year");      //年
				String month = request.getParameter("month");      //月
				String day = request.getParameter("day");      //日
				
				int grade = Integer.parseInt(request.getParameter("grade")); //学年
				int studentId = Integer.parseInt(request.getParameter("studentNum"));    //出席番号
				int classId = Integer.parseInt(request.getParameter("classId"));      //クラス
				String className = request.getParameter("className");      //クラス
				int subjectId = Integer.parseInt(request.getParameter("subjectId"));      //教科Id
				String subjectName = request.getParameter("subjectName");  //教科
				String period = request.getParameter("period");            //時限
				String studentNum = request.getParameter("studentNum");    //出席番号
				String name = request.getParameter("name");                //氏名
				String nameRuby = request.getParameter("nameRuby");        //ふりがな
				String status = request.getParameter("status");            //出欠
				String content = request.getParameter("content");          //提出内容
				String submissionStatus = request.getParameter("submissionStatus");  //提出状況
				String testType = request.getParameter("testType");        //テスト種別
				int score = Integer.parseInt(request.getParameter("score"));              //点数
				String remarks = request.getParameter("remarks");              //備考
				
				// 検索処理を行う
				AttendanceRecordsDAO aDao = new AttendanceRecordsDAO();
				List<AttendanceRecords> List = aDao.select(new AttendanceRecords
						(0, studentId, classId, date, period, subjectId, status, remarks));

				// 検索結果をリクエストスコープに格納する
				request.setAttribute("cardList", List);

				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list_student.jsp");
				dispatcher.forward(request, response);
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	}

}
