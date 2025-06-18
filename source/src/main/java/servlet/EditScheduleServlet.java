package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ScheduleDAO;
import dto.Schedule;

/**
 * Servlet implementation class EditScheduleServlet
 */
@WebServlet("/EditScheduleServlet")
public class EditScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_schedule.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");

	    String yearStr = request.getParameter("year");
	    String semester = request.getParameter("semester");

	    int year = -1;
	    if (yearStr != null && !yearStr.isEmpty()) {
	        try {
	            year = Integer.parseInt(yearStr);
	        } catch (NumberFormatException e) {
	            // ログやデフォルト処理
	        }
	    }

	    // スケジュール検索
	    ScheduleDAO sDao = new ScheduleDAO();
	    List<Schedule> scheduleList = sDao.select(new Schedule(-1, -1, -1, null, "", "", "", year, semester, "", ""));

	    // リクエストにデータを保存
	    request.setAttribute("scheduleList", scheduleList);
	    request.setAttribute("year", year);
	    request.setAttribute("semester", semester);
	    
	    request.setAttribute("paramYear", year);
	    request.setAttribute("paramSemester", semester);


	    // 編集画面にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_schedule.jsp");
	    dispatcher.forward(request, response);
	}


}
