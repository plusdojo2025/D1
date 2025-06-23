package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
	private MemoDAO dao = new MemoDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String classIdStr = request.getParameter("classId");
		String period = request.getParameter("period");
		String dateStr = request.getParameter("date");
		String teacherIdStr = request.getParameter("teacherId");

		List<Memo> filteredList = new ArrayList<>();

		if (classIdStr != null && period != null && dateStr != null && teacherIdStr != null) {
			try {
				int classId = Integer.parseInt(classIdStr);
				int teacherId = Integer.parseInt(teacherIdStr);

				List<Memo> all = dao.selectAll();
				for (Memo m : all) {
					String dbDate = new SimpleDateFormat("yyyy-MM-dd").format(m.getDate());
					if (m.getClassId() == classId && period.equals(m.getPeriod())
							&& dateStr.equals(dbDate) && m.getTeacherId() == teacherId) {
						filteredList.add(m);
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace(); 
			}
		}

		String memoIdStr = request.getParameter("memoId");
		String memoContent = request.getParameter("memoContent");

		request.setAttribute("memoList", filteredList);
		request.setAttribute("classId", classIdStr);
		request.setAttribute("period", period);
		request.setAttribute("date", dateStr);
		request.setAttribute("teacherId", teacherIdStr);
		request.setAttribute("memoId", memoIdStr);
		request.setAttribute("memoContent", memoContent);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/memo.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		String content = request.getParameter("content");
		String classIdStr = request.getParameter("classId");
		String period = request.getParameter("period");
		String dateStr = request.getParameter("date");
		String teacherIdStr = request.getParameter("teacherId");
		String memoIdStr = request.getParameter("memoId");

		try {
			// 空文字またはnullの場合、なにもしない（insert/updateをスキップ）
			if ((content == null || content.trim().isEmpty()) && ("save".equals(action) || "update".equals(action))) {
				// 再表示のためのリダイレクト
				response.sendRedirect(String.format("MemoServlet?classId=%s&period=%s&date=%s&teacherId=%s",
						classIdStr, period, dateStr, teacherIdStr));
				return;
			}

			int classId = Integer.parseInt(classIdStr);
			int teacherId = Integer.parseInt(teacherIdStr);
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

			Memo memo = new Memo();
			memo.setClassId(classId);
			memo.setTeacherId(teacherId);
			memo.setPeriod(period);
			memo.setDate(date);
			memo.setContent(content);

			if ("update".equals(action)) {
				if (memoIdStr != null && !memoIdStr.isEmpty()) {
					memo.setMemoId(Integer.parseInt(memoIdStr));
					dao.update(memo);
				}
			} else if ("delete".equals(action)) {
				if (memoIdStr != null && !memoIdStr.isEmpty()) {
					dao.delete(Integer.parseInt(memoIdStr));
				}
			} else {
				dao.insert(memo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect(String.format("MemoServlet?classId=%s&period=%s&date=%s&teacherId=%s",
				classIdStr, period, dateStr, teacherIdStr));
	}
}