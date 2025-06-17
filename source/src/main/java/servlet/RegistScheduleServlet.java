package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // 初期表示は赤文字メッセージなし
        request.setAttribute("error", null);
        request.getRequestDispatcher("/WEB-INF/jsp/regist_schedule.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 入力値の取得
        String year = request.getParameter("year");
        String semester = request.getParameter("semester");
        String type = request.getParameter("type");
        String day_of_week = request.getParameter("day_of_week");
        String period = request.getParameter("period");
        String classId = request.getParameter("classId");
        String content = request.getParameter("content");

        // 必須チェック（空ならエラー）
        if (isEmpty(year) || isEmpty(semester) || isEmpty(type)
                || isEmpty(day_of_week) || isEmpty(period) || isEmpty(classId) || isEmpty(content)) {

        	request.setAttribute("error", "必須項目を入力してください");

            // 入力値の再設定（フォーム再表示用）
            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
            request.setAttribute("type", type);
            request.setAttribute("day_of_week", day_of_week);
            request.setAttribute("period", period);
            request.setAttribute("classId", classId);
            request.setAttribute("content", content);

            request.getRequestDispatcher("/WEB-INF/jsp/regist_schedule.jsp").forward(request, response);
            return;
        }

        // --- DB登録処理等 ---

        // 登録完了メッセージを設定
        request.setAttribute("message", "スケジュールを登録しました");
        request.getRequestDispatcher("/WEB-INF/jsp/info_schedule.jsp").forward(request, response);
    }

    /**
     * null または 空文字かどうかのチェック用メソッド
     */
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
