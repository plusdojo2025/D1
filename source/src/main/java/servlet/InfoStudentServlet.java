package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AttendanceRecordsDAO;
import dao.StudentsDAO;
import dto.AttendanceRecords;
import dto.Students;

/**
 * Servlet implementation class InfoStudentServlet
 */
@WebServlet("/InfoStudentServlet")
public class InfoStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// プルダウンの情報
		if (request.getParameter("subjectId") != null) {
			String subject = request.getParameter("subjectId");
			request.setAttribute("subjectId", subject);
		} else {
			request.setAttribute("subjectId", 1);
		}
		
		StudentsDAO stuDAO = new StudentsDAO();
		
		String studentId = request.getParameter("studentId");
		//List<Students> studentsList = stuDAO.select(null);
		Students st = new Students(1,1,1,1,"山田太郎","やまだたろう","","","");
		List<Students> studentsList = new ArrayList<Students>();
		studentsList.add(st);
		
		AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
		List<AttendanceRecords> arList = arDAO.select(1);
		
		request.setAttribute("student", studentsList.get(0));
		request.setAttribute("className", null); // クラス名
		
		// 全体の出席
		request.setAttribute("attendedRate", null);
		request.setAttribute("attendedNum", null);
		request.setAttribute("shouldAttendNum", null);

		// 全体の提出物
		request.setAttribute("submittedRate", null);
		request.setAttribute("submittedNum", null);
		request.setAttribute("shouldSubmitNum", null);
		
		// 指定した教科の出席・提出物
		request.setAttribute("subjectAttendedRate", null);
		request.setAttribute("subjectSubmittedRate", null);
		request.setAttribute("attendanceRecords", arList);
		request.setAttribute("assignmentsList", null);
		
		// 成績
		request.setAttribute("gradesList", null);
		request.setAttribute("average", null);
		
		// 面談
		request.setAttribute("interviewList", null);
		request.setAttribute("lastInterviewList", null);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
StudentsDAO stuDAO = new StudentsDAO();
		
		request.setCharacterEncoding("UTF-8");
		String studentId = request.getParameter("studentId");
		//List<Students> studentsList = stuDAO.select(null);
		Students st = new Students(1,1,1,1,"山田太郎","やまだたろう","","","");
		List<Students> studentsList = new ArrayList<Students>();
		studentsList.add(st);
		
		AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
		List<AttendanceRecords> arList = arDAO.select(1);
		
		request.setAttribute("student", studentsList.get(0));
		request.setAttribute("className", null); // クラス名
		
		// 全体の出席
		request.setAttribute("attendedRate", null);
		request.setAttribute("attendedNum", null);
		request.setAttribute("shouldAttendNum", null);

		// 全体の提出物
		request.setAttribute("submittedRate", null);
		request.setAttribute("submittedNum", null);
		request.setAttribute("shouldSubmitNum", null);
		
		// 指定した教科の出席・提出物
		request.setAttribute("subjectAttendedRate", null);
		request.setAttribute("subjectSubmittedRate", null);
		request.setAttribute("attendanceRecords", arList);
		request.setAttribute("assignmentsList", null);
		
		// 成績
		request.setAttribute("gradesList", null);
		request.setAttribute("average", null);
		
		// 面談
		request.setAttribute("interviewList", null);
		request.setAttribute("lastInterviewList", null);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
		dispatcher.forward(request, response);
	}

}
