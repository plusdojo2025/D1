package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemoDAO;
import dao.SubjectDAO;
import dto.Memo;
import dto.Subject;

@WebServlet("/MemoServlet")
public class MemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MemoDAO    dao        = new MemoDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action       = req.getParameter("action");
        String classIdStr   = req.getParameter("classId");
        String period       = req.getParameter("period");
        String dateStr      = req.getParameter("date");
        String teacherIdStr = req.getParameter("teacherId");
        String subjectIdStr = req.getParameter("subjectId");
        String memoIdStr    = req.getParameter("memoId");
        String content      = req.getParameter("content");

        /* ───────── 必須パラメータ不足の場合は memo.jsp へ forward ───────── */
        if (classIdStr == null || classIdStr.isEmpty()
         || period     == null || period.isEmpty()
         || teacherIdStr == null || teacherIdStr.isEmpty()) {

            req.setAttribute("errorMessage", "パラメータが不足しています。");
            req.getRequestDispatcher("/WEB-INF/jsp/memo.jsp")
               .forward(req, res);                // ← forward なので 404 にならない
            return;
        }

        try {
            int classId   = Integer.parseInt(classIdStr);
            int teacherId = Integer.parseInt(teacherIdStr);
            int subjectId = (subjectIdStr != null && !subjectIdStr.isEmpty())
                            ? Integer.parseInt(subjectIdStr) : 23;

            Timestamp ts = (dateStr == null || dateStr.isEmpty())
                    ? new Timestamp(System.currentTimeMillis())
                    : Timestamp.valueOf(dateStr + " 09:00:00");

            /* ───────── 更新・削除処理 ───────── */
            if ("delete".equals(action) && memoIdStr != null && !memoIdStr.isEmpty()) {
                dao.delete(Integer.parseInt(memoIdStr));

            } else if ("update".equals(action)) {
                if (memoIdStr != null && !memoIdStr.isEmpty()) {
                    /* 既存メモ更新 */
                    if (content != null && !content.trim().isEmpty()) {
                        dao.update(new Memo(Integer.parseInt(memoIdStr), teacherId,
                                classId, content.trim(), ts, period, subjectId));
                    }
                } else {
                    /* 新規メモ（最大3行） */
                    for (String line : new String[]{
                            req.getParameter("memo1"),
                            req.getParameter("memo2"),
                            req.getParameter("memo3")}) {

                        if (line != null && !line.trim().isEmpty()) {
                            dao.insert(new Memo(0, teacherId, classId,
                                    line.trim(), ts, period, subjectId));
                        }
                    }
                }
            }

            /* ───────── 一覧再取得 ───────── */
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

            /* ───────── 科目マップ作成 ───────── */
            Map<Integer,String> subjectMap = new HashMap<>();
            for (Subject s : subjectDAO.select(new Subject())) {
                subjectMap.put(s.getSubjectId(), s.getSubjectName());
            }

            /* ───────── JSP へ渡す属性 ───────── */
            req.setAttribute("memoList", memoList);
            req.setAttribute("classId",  classIdStr);
            req.setAttribute("period",   period);
            req.setAttribute("date",     dateStr);
            req.setAttribute("teacherId",teacherIdStr);
            req.setAttribute("subjectMap", subjectMap);
            req.setAttribute("classNameDisplay",
                    String.format("%s年%s組", classId/10, classId%10));
            if (!memoList.isEmpty()) {
                req.setAttribute("subjectName",
                        subjectMap.getOrDefault(memoList.get(0).getSubjectId(),"不明"));
            }

            /* ───────── 正常時 forward ───────── */
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