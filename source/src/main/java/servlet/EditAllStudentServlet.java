package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AssignmentsDAO;
import dao.AttendanceRecordsDAO;
import dao.GradesDAO;
import dto.Assignments;
import dto.AttendanceRecords;
import dto.Grades;

/**
 * Servlet implementation class EditAllStudentServlet
 */
@WebServlet("/EditAllStudentServlet")
public class EditAllStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		
		
		request.setCharacterEncoding("UTF-8");
		Date date=new Date(); ;
		try {
			date = DateFormat.getDateInstance().parse(request.getParameter("date"));
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		Date submissionDate=new Date(); ;
		try {
			submissionDate = DateFormat.getDateInstance().parse(request.getParameter("submissionDate"));
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		int year = Integer.parseInt(request.getParameter("year"));;                //年
		int month = Integer.parseInt(request.getParameter("month"));;                //月
		
		int grade = Integer.parseInt(request.getParameter("grade"));;                //学年
		int studentId = Integer.parseInt(request.getParameter("studentNum"));        //出席番号
		int classId = Integer.parseInt(request.getParameter("classId"));             //クラスId
		String className =request.getParameter("className");                       //クラス
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));         //教科Id
		String subjectName = request.getParameter("subjectName");                  //教科
		String period = request.getParameter("period");                              //時限
		//String studentNum = request.getParameter("studentNum");                    //出席番号
		//String name = request.getParameter("name");                                //氏名
		//String nameRuby = request.getParameter("nameRuby");                        //ふりがな
		int recordId = Integer.parseInt(request.getParameter("recordId"));           //出欠情報ID
		String status = request.getParameter("status");                              //出欠
		int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));   //提出物状況ID
		String content = request.getParameter("content");                            //提出内容
		String submissionStatus = request.getParameter("submissionStatus");          //提出状況
		int gradesId = Integer.parseInt(request.getParameter("gradesId"));           //成績ID
		String testType = request.getParameter("testType");                          //テスト種別
		int score = Integer.parseInt(request.getParameter("score"));                 //点数
		String remarks = request.getParameter("remarks");                            //テスト種別
		
		if (request.getParameter("submit").equals("編集")) {
			
			request.setAttribute("grade", grade);
			request.setAttribute("classId", className);
			request.setAttribute("grade", year);
			request.setAttribute("grade", month);
			request.setAttribute("subjectId", subjectName);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ListStudentServlet");
	        dispatcher.forward(request, response);

		}else if (request.getParameter("submit").equals("編集完了")) {

			AttendanceRecordsDAO attendanceRecordsDao = new AttendanceRecordsDAO();
			if (attendanceRecordsDao.update(new AttendanceRecords(recordId, studentId, classId, date, period, 
					subjectId, status, remarks))) { // 更新成功
				System.out.println("更新成功");

			} else { // 更新失敗
				System.out.println("出席更新失敗");

			}

			AssignmentsDAO assignmentsDao = new AssignmentsDAO();
			if (assignmentsDao.update(new Assignments(assignmentId, studentId, subjectId, 
					submissionStatus, content, year, month, submissionDate))) { // 更新成功
				System.out.println("更新成功");

			} else { // 更新失敗
				System.out.println("提出物更新失敗");

			}

			GradesDAO gradesDao = new GradesDAO();
			if (gradesDao.update(new Grades( gradesId, studentId, subjectId, score, testType, year, month))) { // 更新成功
				System.out.println("更新成功");

			} else { // 更新失敗
				System.out.println("テスト更新失敗");

			}
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
