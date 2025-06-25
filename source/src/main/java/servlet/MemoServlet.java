package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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
	private final MemoDAO dao = new MemoDAO();

	/* ========== メモ一覧表示(GET) ========== */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String classIdStr   = request.getParameter("classId");
		String period       = request.getParameter("period");
		String dateStr      = request.getParameter("date");
		String teacherIdStr = request.getParameter("teacherId");

		List<Memo> memoList = null;
		Map<Integer, String> subjectMap = new HashMap<>();

		try {
			if (classIdStr != null && period != null && teacherIdStr != null) {

				int classId   = Integer.parseInt(classIdStr);
				int teacherId = Integer.parseInt(teacherIdStr);

				/* ▼ メモ取得ロジック */
				if (dateStr != null && !dateStr.isEmpty()) {
					/*   日付が送られてくるパターン   */
					java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
					memoList = dao.selectByClassAndPeriod(classId, period, sqlDate);

				} else {
					/*   date が送れない場合の救済処理   */
					memoList = dao.selectAll();
					// classId・period・teacherId で絞り込み
					memoList.removeIf(m -> !(m.getClassId() == classId &&
					                         period.equals(m.getPeriod())   &&
					                         m.getTeacherId() == teacherId));
				}

				/* ▼ 科目名マッピング（SubjectDAO は変更不可なので全件取得 → Map） */
				SubjectDAO sdao = new SubjectDAO();
				for (Subject s : sdao.select(new Subject())) {
					subjectMap.put(s.getSubjectId(), s.getSubjectName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* ▼ JSP へ渡す */
		request.setAttribute("memoList",  memoList);
		request.setAttribute("classId",   classIdStr);
		request.setAttribute("period",    period);
		request.setAttribute("date",      dateStr);
		request.setAttribute("teacherId", teacherIdStr);
		request.setAttribute("subjectMap", subjectMap);

		RequestDispatcher dsp = request.getRequestDispatcher("/WEB-INF/jsp/memo.jsp");
		dsp.forward(request, response);
	}

	/* ========== メモ更新・削除処理(POST) ========== */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String action        = request.getParameter("action");   // update / delete 以外は info_schedule からの POST
		String classIdStr    = request.getParameter("classId");
		String period        = request.getParameter("period");
		String dateStr       = request.getParameter("date");
		String teacherIdStr  = request.getParameter("teacherId");
		String memoIdStr     = request.getParameter("memoId");
		String content       = request.getParameter("content");

		/* ▼ 1) info_schedule から来る POST (action 無し) → GET 相当の処理 */
		if (action == null || action.isEmpty()) {
			doGet(request, response);   // 同じロジックで表示
			return;
		}

		/* ▼ 2) 更新 or 削除 */
		try {
			if ("update".equals(action)) {
				if (content != null && !content.trim().isEmpty()) {
					Memo memo = new Memo();
					memo.setMemoId(Integer.parseInt(memoIdStr));
					memo.setClassId(Integer.parseInt(classIdStr));
					memo.setTeacherId(Integer.parseInt(teacherIdStr));
					memo.setPeriod(period);
					memo.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
					memo.setContent(content.trim());
					dao.update(memo);
				}
			} else if ("delete".equals(action)) {
				if (memoIdStr != null && !memoIdStr.isEmpty()) {
					dao.delete(Integer.parseInt(memoIdStr));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* ▼ PRG: 編集・削除後は GET 表示にリダイレクト */
		response.sendRedirect(String.format("MemoServlet?classId=%s&period=%s&date=%s&teacherId=%s",
				classIdStr, period, dateStr, teacherIdStr));
	}
}