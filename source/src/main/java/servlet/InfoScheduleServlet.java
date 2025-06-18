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
 * Servlet implementation class InfoScheduleServlet
 */
@WebServlet("/InfoScheduleServlet")
public class InfoScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int defaultYear = 2025;
    	String defaultSemester = "前期";

    	// スケジュール取得
    	ScheduleDAO sDao = new ScheduleDAO();
    	List<Schedule> scheduleList = sDao.select(new Schedule(-1, -1, -1, null, "", "", "", defaultYear, defaultSemester, "", ""));

    	// スケジュールとパラメータをセット
    	request.setAttribute("scheduleList", scheduleList);
    	request.setAttribute("paramYear", defaultYear);
    	request.setAttribute("paramSemester", defaultSemester);
    	
    	// JSPへフォワード
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp");
    	dispatcher.forward(request, response);
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        int year = -1;
        String semester = request.getParameter("semester");
        String yearParam = request.getParameter("year");

        if (yearParam != null && !yearParam.isEmpty()) {
            try {
                year = Integer.parseInt(yearParam);
            } catch (NumberFormatException e) {
                year = -1;
            }
        }
        
        ScheduleDAO sDao = new ScheduleDAO();
        Schedule searchCond = new Schedule(-1, -1, -1, null, "", "", "", year, semester != null ? semester : "", "", "");
        List<Schedule> scheduleList = sDao.select(searchCond);

        request.setAttribute("scheduleList", scheduleList);
        request.setAttribute("paramYear", year);
        request.setAttribute("paramSemester", semester);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp");
        dispatcher.forward(request, response);
    }

}
