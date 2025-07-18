package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * スケジュール登録処理用サーブレット
 */
@WebServlet("/RegistScheduleServlet")
public class RegistScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegistScheduleServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginTeacher") == null) {
        	response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        int teacherId = ((Teacher) session.getAttribute("loginTeacher")).getTeacherId();
        request.setAttribute("error", null);
        
        ClassRoomDAO classDao = new ClassRoomDAO();
        List<ClassRoom> classList = classDao.selectAll();

        Map<Integer, String> classIdNameMap = new HashMap<>();
        for (ClassRoom cls : classList) {
            String name = cls.getGrade() + "年" + cls.getClassName();
            classIdNameMap.put(cls.getClassId(), name);
        }

        request.setAttribute("classIdNameMap", classIdNameMap);

        request.setAttribute("classList", classList);
        request.getRequestDispatcher("/WEB-INF/jsp/regist_schedule.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginTeacher") == null) {
        	response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        int teacherId = ((Teacher) session.getAttribute("loginTeacher")).getTeacherId();

        String yearStr = request.getParameter("year");
        String semester = request.getParameter("semester");
        String type = request.getParameter("type");
        String day_of_week = request.getParameter("day_of_week");
        String period = request.getParameter("period");
        String classIdStr = request.getParameter("classId");
        String content = request.getParameter("content");
        
        if (isEmpty(yearStr) || isEmpty(semester) || isEmpty(type)
                || isEmpty(day_of_week) || isEmpty(period) || isEmpty(classIdStr) || isEmpty(content)) {

            request.setAttribute("error", "必須項目を入力してください");

            request.setAttribute("year", yearStr);
            request.setAttribute("semester", semester);
            request.setAttribute("type", type);
            request.setAttribute("day_of_week", day_of_week);
            request.setAttribute("period", period);
            request.setAttribute("classId", classIdStr);
            request.setAttribute("content", content);

            ClassRoomDAO classDao = new ClassRoomDAO();
            List<ClassRoom> classList = classDao.selectAll();

            Map<Integer, String> classIdNameMap = new HashMap<>();
            for (ClassRoom cls : classList) {
                String name = cls.getGrade() + "年" + cls.getClassName();
                classIdNameMap.put(cls.getClassId(), name);
            }

            request.setAttribute("classIdNameMap", classIdNameMap);

            request.setAttribute("classList", classList);
            
            request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp").forward(request, response);
            return;
        }

        int year = Integer.parseInt(yearStr);
        int classId = Integer.parseInt(classIdStr);

        
        Schedule s = new Schedule(0, teacherId, classId, null, period, content, "class", year, semester, "", day_of_week);
       
        ScheduleDAO dao = new ScheduleDAO();
        boolean success = dao.insert(s);

        if (success) {
            request.setAttribute("message", "スケジュールを登録しました");
            request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "スケジュールの登録に失敗しました");
            request.getRequestDispatcher("/WEB-INF/jsp/regist_schedule.jsp").forward(request, response);
        }
    }

    // ここがメソッドの外、クラスの中
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}