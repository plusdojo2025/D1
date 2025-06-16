package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AssignmentsDAO;
import dao.AttendanceRecordsDAO;
import dao.ClassRoomDAO;
import dao.GradesDAO;
import dao.StudentsDAO;
import dao.SubjectDAO;
import dto.Assignments;
import dto.AttendanceRecords;
import dto.ClassRoom;
import dto.Grades;
import dto.Students;
import dto.Subject;

/**
 * Servlet implementation class ListStudentServlet
 */
@WebServlet("/ListStudentServlet")
public class ListStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		// 検索ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
				HttpSession session = request.getSession();
				if (session.getAttribute("id") == null) {
					response.sendRedirect("LoginServlet");
					return;
				}
				
				// リクエストパラメータを取得する
				request.setCharacterEncoding("UTF-8");
				Date date=new Date(); ;
				try {
					date = DateFormat.getDateInstance().parse(request.getParameter("date"));
				} catch (ParseException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				
				int year = Integer.parseInt(request.getParameter("year"));;                //年
				int month = Integer.parseInt(request.getParameter("month"));;                //月
				
				int grade = Integer.parseInt(request.getParameter("grade"));;          //学年
				int studentId = Integer.parseInt(request.getParameter("studentNum"));  //出席番号
				int classId = Integer.parseInt(request.getParameter("classId"));       //クラス
				String className =request.getParameter("className");                   //クラス
				int subjectId = Integer.parseInt(request.getParameter("subjectId"));   //教科Id
				String subjectName = request.getParameter("subjectName");              //教科
				String period = request.getParameter("period");                        //時限
				//String studentNum = request.getParameter("studentNum");              //出席番号
				//String name = request.getParameter("name");                          //氏名
				//String nameRuby = request.getParameter("nameRuby");                  //ふりがな
				String status = request.getParameter("status");                        //出欠
				//String content = request.getParameter("content");                    //提出内容
				//String submissionStatus = request.getParameter("submissionStatus");  //提出状況
				//String testType = request.getParameter("testType");                  //テスト種別
				//int score = Integer.parseInt(request.getParameter("score"));         //点数
				String remarks = request.getParameter("remarks");                      //備考
				
				// 検索処理を行う
				AttendanceRecordsDAO aDao = new AttendanceRecordsDAO();
				List<AttendanceRecords> attendanceList = aDao.select(new AttendanceRecords
						(0, studentId, classId, date, period, subjectId, status, remarks));

				request.setAttribute("List", attendanceList);
				
				studentId = Integer.parseInt(request.getParameter("studentId"));    //出席番号
				classId = Integer.parseInt(request.getParameter("classId"));      //クラス
				subjectId = Integer.parseInt(request.getParameter("subjectId"));      //教科Id
				
				
				StudentsDAO studentDao = new StudentsDAO();
				List<Students> studentList = studentDao.select(new Students(studentId));
				request.setAttribute("studentList", studentList);
				
				ClassRoomDAO classDao = new ClassRoomDAO();
				List<ClassRoom> classList = classDao.select(new ClassRoom(0,grade,className));
				request.setAttribute("classList", classList);
				
				SubjectDAO subjectDao = new SubjectDAO();
				List<Subject> subjectList = subjectDao.select(new Subject(0,subjectName));
				request.setAttribute("subjectList", subjectList);

				AssignmentsDAO assignmentsDao = new AssignmentsDAO();
				List<Assignments> assignmentsList = assignmentsDao.select(new Assignments(studentId,subjectId));
				request.setAttribute("assignmentsList", assignmentsList);
				
				GradesDAO gradesDao = new GradesDAO();
				List<Grades> gradesList = gradesDao.select(new Grades(studentId,subjectId));
				request.setAttribute("gradesList", gradesList);
				
				request.setAttribute("grade", grade);
				request.setAttribute("className", className);
				request.setAttribute("year", year);
				request.setAttribute("month", month);
				request.setAttribute("subjectName", subjectName);
				
				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list_student.jsp");
				dispatcher.forward(request, response);
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	}

}
