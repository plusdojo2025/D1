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
        request.setCharacterEncoding("UTF-8");

        String yearStr = request.getParameter("year");
        String semester = request.getParameter("semester");

        int year = 2025;
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                year = 2025;
            }
        }

        if (semester == null || semester.isEmpty()) {
            semester = "前期";
        }

        // データ取得
        ScheduleDAO sDao = new ScheduleDAO();
        List<Schedule> scheduleList = sDao.select(new Schedule(-1, -1, -1, null, "", "", "", year, semester, "", ""));

        // パラメータセット
        
        request.setAttribute("scheduleList", scheduleList);
        request.setAttribute("paramYear", year);
        request.setAttribute("paramSemester", semester); // ←これが抜けていると表示されない

        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp");
        dispatcher.forward(request, response);
    }




	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        int year = -1;
        String yearStr = request.getParameter("year");
        String semester = request.getParameter("semester");

        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                year = -1;
            }
        }

        if ("save".equals(action)) {
            // 保存処理
            ScheduleDAO sDao = new ScheduleDAO();
            String[] days = {"月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日", "日曜日"};
            String[] periods = {"1限", "2限", "3限", "4限", "5限", "6限", "7限"};

            for (String day : days) {
                for (String period : periods) {
                    String content = request.getParameter("content_" + day + "_" + period);
                    String classIdStr = request.getParameter("classId_" + day + "_" + period);
                    String scheduleIdStr = request.getParameter("scheduleId_" + day + "_" + period);

                    boolean hasContent = content != null && !content.trim().isEmpty();
                    boolean hasClassId = classIdStr != null && !classIdStr.trim().isEmpty();
                    int classId = hasClassId ? Integer.parseInt(classIdStr) : -1;

                    if (scheduleIdStr != null && !scheduleIdStr.trim().isEmpty()) {
                        // 既存データがある場合
                        int scheduleId = Integer.parseInt(scheduleIdStr);

                        if (hasContent || hasClassId) {
                            // 更新
                            Schedule s = new Schedule(scheduleId, -1, classId, null, period, content, "", year, semester, "", day);
                            sDao.update(s);
                        } else {
                            // 削除
                            sDao.delete(new Schedule(scheduleId, -1, -1, null, "", "", "", year, semester, "", day));
                        }
                    } else {
                        // 新規追加（内容またはクラスIDがあるときのみ）
                        if (hasContent || hasClassId) {
                            Schedule s = new Schedule(0, -1, classId, null, period, content, "", year, semester, "", day);
                            sDao.insert(s);
                        }
                    }
                }
            }

            // 保存後に再表示
            List<Schedule> scheduleList = new ScheduleDAO().select(new Schedule(-1, -1, -1, null, "", "", "", year, semester, "", ""));
            request.setAttribute("scheduleList", scheduleList);
            request.setAttribute("paramYear", year);
            request.setAttribute("paramSemester", semester);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 通常の検索処理
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
