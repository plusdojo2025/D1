package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClassRoomDAO;
import dao.ScheduleDAO;
import dto.ClassRoom;
import dto.Schedule;
import dto.Teacher;

@WebServlet("/EditScheduleServlet")
public class EditScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	ClassRoomDAO classDao = new ClassRoomDAO();
        List<ClassRoom> classList = classDao.selectAll();

        Map<Integer, String> classIdNameMap = new HashMap<>();
        for (ClassRoom cls : classList) {
            String name = cls.getGrade() + "年" + cls.getClassName() + "組";
            classIdNameMap.put(cls.getClassId(), name);
        }

        request.setAttribute("classIdNameMap", classIdNameMap);

        request.setAttribute("classList", classList);
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_schedule.jsp");
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
                        // update or delete
                        int scheduleId = Integer.parseInt(scheduleIdStr);
                        if (hasContent || hasClassId) {
                            Schedule s = new Schedule(scheduleId, teacherId, classId, null, period, content, "class", year, semester, "", day);
                            sDao.update(s);
                        } else {
                            sDao.deleteByDayAndPeriod(teacherId, year, semester, day, period);
                        }
                    } else {
                        if (hasContent || hasClassId) {
                            // 既存データがない場合でも念のため該当行を削除してからinsert
                            sDao.deleteByDayAndPeriod(teacherId, year, semester, day, period);
                            Schedule s = new Schedule(0, teacherId, classId, null, period, content, "class", year, semester, "", day);
                            sDao.insert(s);
                        }
                    }
                }
            }

            Teacher teacher = (Teacher) session.getAttribute("loginTeacher");
            request.setAttribute("loginTeacher", teacher);

            if (semester == null || semester.isEmpty()) {
                semester = "前期"; // デフォルト値を明示的にセット
            }
            
            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
            request.setAttribute("action", "search");
            
            String encodedSemester = URLEncoder.encode(semester, StandardCharsets.UTF_8.name());
            response.sendRedirect("InfoScheduleServlet?year=" + year + "&semester=" + encodedSemester + "&action=search");
            return;
        }

        // 通常の検索処理
        List<Schedule> scheduleList = sDao.select(new Schedule(-1, teacherId, -1, null, "", "", "", year, semester, "", ""));
        request.setAttribute("scheduleList", scheduleList);
        request.setAttribute("year", year);
        request.setAttribute("semester", semester);
        request.setAttribute("paramYear", year);
        request.setAttribute("paramSemester", semester);
        request.setAttribute("teacherId", teacherId);
        
        
        
        ClassRoomDAO classDao = new ClassRoomDAO();
        List<ClassRoom> classList = classDao.selectAll();

        Map<Integer, String> classIdNameMap = new HashMap<>();
        for (ClassRoom cls : classList) {
            String name = cls.getGrade() + "年" + cls.getClassName() + "組";
            classIdNameMap.put(cls.getClassId(), name);
        }

        request.setAttribute("classIdNameMap", classIdNameMap);

        request.setAttribute("classList", classList);
        
        
        

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_schedule.jsp");
        dispatcher.forward(request, response);
    }
}
