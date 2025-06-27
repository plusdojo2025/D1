package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemoDAO;
import dto.Memo;

@WebServlet("/MemoServlet")
public class MemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final MemoDAO    dao        = new MemoDAO();
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action       = req.getParameter("action");
        String classIdStr   = req.getParameter("classId");
        String period       = req.getParameter("period");
        String dateStr      = req.getParameter("date");
        String teacherIdStr = req.getParameter("teacherId");
        String memoIdStr    = req.getParameter("memoId");
        String content      = req.getParameter("content");
        

        /* ───────── 必須パラメータ不足の場合は memo.jsp へ forward ───────── */
        if (classIdStr == null || classIdStr.isEmpty()
         || period     == null || period.isEmpty()
         || teacherIdStr == null || teacherIdStr.isEmpty()) {

            req.setAttribute("errorMessage", "パラメータが不足しています。");
            req.getRequestDispatcher("/WEB-INF/jsp/memo.jsp")
               .forward(req, res);          
            return;
        }

        try {
            int classId   = Integer.parseInt(classIdStr);
            int teacherId = Integer.parseInt(teacherIdStr);

            Timestamp ts = (dateStr == null || dateStr.isEmpty())
                    ? new Timestamp(System.currentTimeMillis())
                    : Timestamp.valueOf(dateStr + " 09:00:00");

            /* ───────── 削除処理だけ既存メモに対して行う ───────── */
            if ("delete".equals(action) && memoIdStr != null && !memoIdStr.isEmpty()) {
                dao.delete(Integer.parseInt(memoIdStr));
            }
     
                    /* 新規メモ（最大3行） */
            if("update".equals(action)) {
                    for (String line : new String[]{
                            req.getParameter("memo1"),
                            req.getParameter("memo2"),
                            req.getParameter("memo3")}) {

                        if (line != null && !line.trim().isEmpty()) {
                            dao.insert(new Memo(0, teacherId, classId,
                                    line.trim(), ts, period));
                        }
                    }
                }

            /* ───────── メモ再取得 ───────── */
            List<Memo> memoList;
            if (dateStr != null && !dateStr.isEmpty()) {
                memoList = dao.selectByClassAndPeriod(
                        classId, period, java.sql.Date.valueOf(dateStr));
            } else {
                memoList = dao.selectAll();
                memoList.removeIf(m -> !(m.getClassId()==classId &&
                                         period.equals(m.getPeriod()) &&
                                         m.getTeacherId()==teacherId));
            }

            /* ───────── JSP へ渡す属性 ───────── */
            req.setAttribute("memoList", memoList);
            req.setAttribute("classId",  classIdStr);
            req.setAttribute("period",   period);
            req.setAttribute("date",     dateStr);
            req.setAttribute("teacherId",teacherIdStr);
            req.setAttribute("classNameDisplay",
                    String.format("%s年%s組", classId/10, classId%10));
    
            req.getRequestDispatcher("/WEB-INF/jsp/memo.jsp")
               .forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "メモ処理中にエラーが発生しました。");
            req.getRequestDispatcher("/WEB-INF/jsp/memo.jsp")
               .forward(req, res);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);  // すべて POST に集約
    }
}