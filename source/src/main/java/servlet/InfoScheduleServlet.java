package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ScheduleDAO;
import dto.Schedule;
import dto.Teacher;

@WebServlet("/InfoScheduleServlet")
public class InfoScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginTeacher") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        Teacher teacher = (Teacher) session.getAttribute("loginTeacher");
        int teacherId = teacher.getTeacherId();

        // デフォルトまたはリダイレクト後のパラメータ取得
        String yearStr = request.getParameter("year");
        String semester = request.getParameter("semester");
        String action = request.getParameter("action");

        int year = (yearStr != null && !yearStr.isEmpty()) ? Integer.parseInt(yearStr) : 2025;
        if (semester == null || semester.isEmpty()) {
            semester = "前期";
        }

        // 検索処理（action=search またはデフォルト）
        ScheduleDAO sDao = new ScheduleDAO();
        Schedule condition = new Schedule(-1, teacherId, -1, null, "", "", "", year, semester, "", "");
        List<Schedule> scheduleList = sDao.select(condition);

        request.setAttribute("loginTeacher", teacher);
        request.setAttribute("scheduleList", scheduleList);
        request.setAttribute("paramYear", year);
        request.setAttribute("paramSemester", semester);
        request.setAttribute("teacherId", teacherId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginTeacher") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        int teacherId = ((Teacher) session.getAttribute("loginTeacher")).getTeacherId();

        String action = request.getParameter("action");
        String yearStr = request.getParameter("year");
        String semester = request.getParameter("semester");
        int year = (yearStr != null && !yearStr.isEmpty()) ? Integer.parseInt(yearStr) : -1;

        ScheduleDAO sDao = new ScheduleDAO();

        if ("save".equals(action)) {
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
                        int scheduleId = Integer.parseInt(scheduleIdStr);
                        if (hasContent || hasClassId) {
                            Schedule s = new Schedule(scheduleId, teacherId, classId, null, period, content, "class", year, semester, "", day);
                            sDao.update(s);
                        } else {
                            sDao.delete(new Schedule(scheduleId, teacherId, -1, null, "", "", "", year, semester, "", day));
                        }
                    } else {
                        if (hasContent || hasClassId) {
                            Schedule s = new Schedule(0, teacherId, classId, null, period, content, "class", year, semester, "", day);
                            sDao.insert(s);
                        }
                    }
                }
            }

            List<Schedule> scheduleList = sDao.select(new Schedule(-1, teacherId, -1, null, "", "", "", year, semester, "", ""));
            request.setAttribute("scheduleList", scheduleList);
            request.setAttribute("paramYear", year);
            request.setAttribute("paramSemester", semester);
            request.setAttribute("teacherId", teacherId);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 通常の検索処理
        Schedule searchCond = new Schedule(-1, teacherId, -1, null, "", "", "", year, semester != null ? semester : "", "", "");
        List<Schedule> scheduleList = sDao.select(searchCond);

        request.setAttribute("scheduleList", scheduleList);
        request.setAttribute("paramYear", year);
        request.setAttribute("paramSemester", semester);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp");
        dispatcher.forward(request, response);
    }
}
