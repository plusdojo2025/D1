package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistScheduleServlet
 */
@WebServlet("/RegistScheduleServlet")
public class RegistScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//入力値取得
		String yearstr = request.getParameter("year");
		String semester = request.getParameter("semester");
		String type = request.getParameter("type");
		String date = request.getParameter("date");
		String period = request.getParameter("period");
		String content = request.getParameter("content");
		
		
		//バリエーション：全て必須
		if (isEmpty(yearstr) || isEmpty(semester) || isEmpty(type) ||isEmpty(date) || isEmpty(period) ||isEmpty(content)) {
			
			request.setAttribute("error", "すべての項目を入力してください。");
			request.getRequestDispatcher("regist_schedule.jsp").forward(request, response);
			return;
			
		}

		
		//ここでDB登録処理を行う想定
		
		//登録完了メッセージ表示用
		request.setAttribute("message", "スケジュールの登録を登録しました。");
		request.getRequestDispatcher("info_schedule.jsp").forward(request, response);
		
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	//空文字またはnullチェック用メソッド
	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

}
