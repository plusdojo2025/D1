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

	    String action = request.getParameter("action");
	    String yearStr = request.getParameter("year");
	    String semester = request.getParameter("semester");
	    int year = (yearStr != null && !yearStr.isEmpty()) ? Integer.parseInt(yearStr) : -1;

	    String[] days = {"月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日", "日曜日"};
	    String[] periods = {"1限", "2限", "3限", "4限", "5限", "6限", "7限"};

	    ScheduleDAO sDao = new ScheduleDAO();

	    if ("save".equals(action)) {
	        // 入力ループ処理
	        for (String day : days) {
	            for (String period : periods) {
	                String content = request.getParameter("content_" + day + "_" + period);
	                String classIdStr = request.getParameter("classId_" + day + "_" + period);
	                String scheduleIdStr = request.getParameter("scheduleId_" + day + "_" + period);

	                if ((content != null && !content.trim().isEmpty()) ||
	                    (classIdStr != null && !classIdStr.trim().isEmpty())) {

	                    int classId = classIdStr != null && !classIdStr.trim().isEmpty() ? Integer.parseInt(classIdStr) : -1;

	                    Schedule schedule = new Schedule();
	                    schedule.setTeacherId(1); // 仮の教師ID（適宜変更）
	                    schedule.setClassId(classId);
	                    schedule.setPeriod(period);
	                    schedule.setContent(content);
	                    schedule.setType("class"); // 仮
	                    schedule.setYear(year);
	                    schedule.setSemester(semester);
	                    schedule.setDay_of_week(day);

	                    if (scheduleIdStr != null && !scheduleIdStr.trim().isEmpty()) {
	                        schedule.setScheduleId(Integer.parseInt(scheduleIdStr));
	                        sDao.update(schedule);
	                    } else {
	                        sDao.insert(schedule);
	                    }
	                } else {
	                    // 空の入力欄は削除対象
	                    if (scheduleIdStr != null && !scheduleIdStr.trim().isEmpty()) {
	                        sDao.deleteByDayAndPeriod(year, semester, day, period);
	                    }
	                }
	            }
	        }

	        // 保存後は閲覧画面へリダイレクト
	        response.sendRedirect("InfoScheduleServlet?year=" + year + "&semester=" + java.net.URLEncoder.encode(semester, "UTF-8"));
	        return;
	    }

	    // 検索表示時（初期表示 or 再検索）
	    List<Schedule> scheduleList = sDao.select(new Schedule(-1, -1, -1, null, "", "", "", year, semester, "", ""));
	    request.setAttribute("scheduleList", scheduleList);
	    request.setAttribute("year", year);
	    request.setAttribute("semester", semester);
	    request.setAttribute("paramYear", year);
	    request.setAttribute("paramSemester", semester);

	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_schedule.jsp");
	    dispatcher.forward(request, response);
	}


}
