package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		/*
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		// 検索ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
		dispatcher.forward(request, response);
		
		*/
		
		
		request.setCharacterEncoding("UTF-8");
		
		
		Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        
		int grade = 1;
		String className = "1組";
		String subjectName = "現代文";
		
		// 検索処理を行う
		//classIDを取得
		ClassRoomDAO classDao = new ClassRoomDAO();
		List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
		request.setAttribute("classList", classList);
		
		//subjectIDを取得
		SubjectDAO subjectDao = new SubjectDAO();
		List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));
		request.setAttribute("subjectList", subjectList);
		
		int classId = classList.get(0).getClassId();      //クラス
		int subjectId = subjectList.get(0).getSubjectId();      //教科Id
		
		//生徒情報を取得
		StudentsDAO studentDao = new StudentsDAO();
		List<Students> studentList = studentDao.select(new Students(-1,classId));
		request.setAttribute("studentList", studentList);
		
		int studentId = studentList.get(0).getStudentId();    //出席番号

		//出欠情報を取得
		AttendanceRecordsDAO aDao = new AttendanceRecordsDAO();
		List<AttendanceRecords> attendanceList = aDao.select(studentId, classId);
		request.setAttribute("attendanceList", attendanceList);
		
		//提出物状況を取得
		AssignmentsDAO assignmentsDao = new AssignmentsDAO();
		List<Assignments> assignmentsList = assignmentsDao.select(new Assignments(studentId,subjectId));
		request.setAttribute("assignmentsList", assignmentsList);
		
		//成績情報を取得
		GradesDAO gradesDao = new GradesDAO();
		List<Grades> gradesList = gradesDao.select(new Grades(studentId,subjectId));
		request.setAttribute("gradesList", gradesList);
		
		request.setAttribute("grade", grade);
		request.setAttribute("className", className);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("subjectName", subjectName);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
				
				/*
				HttpSession session = request.getSession();
				if (session.getAttribute("id") == null) {
					response.sendRedirect("LoginServlet");
					return;
				}
				*/
				
				if (request.getParameter("submit").equals("infoStudent")) {
					
					//studentID subjectID year month
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/InfoStudentServlet");
			        dispatcher.forward(request, response);
				}else {
				// リクエストパラメータを取得する
				request.setCharacterEncoding("UTF-8");
				Date date=new Date(); ;
				try {
					date = DateFormat.getDateInstance().parse(request.getParameter("date"));
				} catch (ParseException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				
				int year = Integer.parseInt(request.getParameter("year"));;            //年
				if(year ==0 || year == -1) {
					year = Calendar.getInstance().get(Calendar.YEAR);;
				}
				
				int month = Integer.parseInt(request.getParameter("month"));;          //月
				if(month ==0 || month == -1) {
					month = Calendar.getInstance().get(Calendar.MONTH);;
				}
				
				int grade = Integer.parseInt(request.getParameter("grade"));;          //学年
				if(grade ==0 || grade == -1) {
					grade = 1;
				}
				
				int studentId = Integer.parseInt(request.getParameter("studentId"));  //出席番号
				int classId = Integer.parseInt(request.getParameter("classId"));       //クラス
				String className =request.getParameter("className");                   //クラス
				if(className == null || className.isEmpty()) {
					className = "1";
				}
				
				int subjectId = Integer.parseInt(request.getParameter("subjectId"));   //教科Id
				String subjectName = request.getParameter("subjectName");              //教科
				if(subjectName == null || subjectName.isEmpty()) {
					subjectName = "現代文";
				}
				
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
				//classIDを取得
				ClassRoomDAO classDao = new ClassRoomDAO();
				List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
				request.setAttribute("classList", classList);
				
				//subjectIDを取得
				SubjectDAO subjectDao = new SubjectDAO();
				List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));
				request.setAttribute("subjectList", subjectList);
				
				classId = Integer.parseInt(request.getParameter("classId"));      //クラス
				subjectId = Integer.parseInt(request.getParameter("subjectId"));      //教科Id
				
				//生徒情報を取得
				StudentsDAO studentDao = new StudentsDAO();
				List<Students> studentList = studentDao.select(new Students(-1,classId));
				request.setAttribute("studentList", studentList);
				
				studentId = Integer.parseInt(request.getParameter("studentId"));    //出席番号

				//出欠情報を取得
				AttendanceRecordsDAO aDao = new AttendanceRecordsDAO();
				List<AttendanceRecords> attendanceList = aDao.select(new AttendanceRecords
						(-1, studentId, classId, date, period, subjectId, status, remarks));
				request.setAttribute("attendanceList", attendanceList);
				
				//提出物状況を取得
				AssignmentsDAO assignmentsDao = new AssignmentsDAO();
				List<Assignments> assignmentsList = assignmentsDao.select(new Assignments(studentId,subjectId));
				request.setAttribute("assignmentsList", assignmentsList);
				
				//成績情報を取得
				GradesDAO gradesDao = new GradesDAO();
				List<Grades> gradesList = gradesDao.select(new Grades(studentId,subjectId));
				request.setAttribute("gradesList", gradesList);
				
				request.setAttribute("grade", grade);
				request.setAttribute("className", className);
				request.setAttribute("year", year);
				request.setAttribute("month", month);
				request.setAttribute("subjectName", subjectName);
				
				if(request.getParameter("submit").equals("編集")) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_allstudent.jsp");
					dispatcher.forward(request, response);
				}else {
				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list_student.jsp");
				dispatcher.forward(request, response);
				}
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	}

}
