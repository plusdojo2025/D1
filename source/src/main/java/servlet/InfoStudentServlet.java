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

import org.apache.el.parser.ParseException;

import dao.AttendanceRecordsDAO;
import dao.ClassRoomDAO;
import dao.InterviewDAO;
import dao.StudentsDAO;
import dto.AttendanceRecords;
import dto.ClassRoom;
import dto.Interview;
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

		try {
			int subjectId = 1, year = 1, month = 1, studentId = 1;
			// プルダウンの情報
			if (request.getParameter("subjectId") != null) {
				subjectId = Integer.parseInt(request.getParameter("subjectId"));
			}

			if (request.getParameter("yearId") != null) {
				year = Integer.parseInt(request.getParameter("yearId"));
			}

			if (request.getParameter("monthId") != null) {
				month = Integer.parseInt(request.getParameter("monthId"));
			}

			request.setAttribute("subjectId", subjectId);
			request.setAttribute("yearId", year);
			request.setAttribute("monthId", month);

			StudentsDAO stuDAO = new StudentsDAO();

			if (request.getParameter("studentId") != null) {
				studentId = Integer.parseInt(request.getParameter("studentId"));
			}
			List<Students> studentsList = stuDAO.select(new Students(studentId, -1, -1, -1, "", "", "", "", ""));
			Students student = new Students();
			if (studentsList.size() > 0) student = studentsList.get(0);
			request.setAttribute("student", student);

			AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
			List<AttendanceRecords> arList = arDAO.select_Fiscal(-10, student.getClassId(), month, subjectId);
			
			ClassRoomDAO crDAO = new ClassRoomDAO();
			List<ClassRoom> classList = crDAO.select(new ClassRoom(student.getClassId(),student.getGrade(),""));
			ClassRoom classRoom = new ClassRoom();
			if (classList.size() > 0) classRoom = classList.get(0);
			request.setAttribute("className", classRoom.getClassName());

			// 全体の出席
			request.setAttribute("attendedRate", arDAO.GetAttendedRate(studentId));
			request.setAttribute("attendedNum", arDAO.GetAttendedNum(studentId));
			request.setAttribute("shouldAttendNum", arDAO.GetShouldAttendedNum(studentId));

			// 全体の提出物
			request.setAttribute("submittedRate", null);
			request.setAttribute("submittedNum", null);
			request.setAttribute("shouldSubmitNum", null);

			// 指定した教科の出席・提出物
			request.setAttribute("subjectAttendedRate", arDAO.GetAttendedRate(studentId, subjectId));
			request.setAttribute("attendanceRecords", arList);
			request.setAttribute("subjectSubmittedRate", null);
			request.setAttribute("assignmentsList", null);

			// 成績
			request.setAttribute("gradesList", null);
			request.setAttribute("average", null);

			// 面談
			InterviewDAO itvDAO = new InterviewDAO();
			List<Interview> itvList = itvDAO.select_thisYear(studentId);
			List<Interview> lastItvList = itvDAO.select_lastYear(studentId);
			request.setAttribute("interviewList", itvList);
			request.setAttribute("lastInterviewList", lastItvList);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int subjectId = -1, year = -1, month = -1;
		// プルダウンの情報
		if (request.getParameter("subjectId") != null) {
			subjectId = Integer.parseInt(request.getParameter("subjectId"));
			request.setAttribute("subjectId", subjectId);
		} else {
			request.setAttribute("subjectId", 1);
		}

		if (request.getParameter("yearId") != null) {
			year = Integer.parseInt(request.getParameter("yearId"));
			request.setAttribute("yearId", year);
		} else {
			request.setAttribute("yearId", 1);
		}

		if (request.getParameter("monthId") != null) {
			month = Integer.parseInt(request.getParameter("monthId"));
			request.setAttribute("monthId", month);
		} else {
			request.setAttribute("monthId", 1);
		}

		StudentsDAO stuDAO = new StudentsDAO();

		int studentId = Integer.parseInt(request.getParameter("studentId"));
		List<Students> studentsList = stuDAO.select(new Students(studentId, -1, -1, -1, "", "", "", "", ""));
		Students student = studentsList.get(0);

		AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
		List<AttendanceRecords> arList = arDAO.select_Fiscal(-10, student.getClassId(), month, subjectId);

		request.setAttribute("student", studentsList.get(0));

		ClassRoomDAO crDAO = new ClassRoomDAO();
		List<ClassRoom> classList = crDAO.select(new ClassRoom(1,1,""));
		request.setAttribute("className", classList.get(0).getClassName());

		// 全体の出席
		request.setAttribute("attendedRate", arDAO.GetAttendedRate(studentId));
		request.setAttribute("attendedNum", arDAO.GetAttendedNum(studentId));
		request.setAttribute("shouldAttendNum", arDAO.GetShouldAttendedNum(studentId));

		// 全体の提出物
		request.setAttribute("submittedRate", null);
		request.setAttribute("submittedNum", null);
		request.setAttribute("shouldSubmitNum", null);

		// 指定した教科の出席・提出物
		request.setAttribute("subjectAttendedRate", arDAO.GetAttendedRate(studentId, subjectId));
		request.setAttribute("attendanceRecords", arList);
		request.setAttribute("subjectSubmittedRate", null);
		request.setAttribute("assignmentsList", null);

		// 成績
		request.setAttribute("gradesList", null);
		request.setAttribute("average", null);

		// 面談
		InterviewDAO itvDAO = new InterviewDAO();
		List<Interview> itvList = itvDAO.select_thisYear(studentId);
		List<Interview> lastItvList = itvDAO.select_lastYear(studentId);
		request.setAttribute("interviewList", itvList);
		request.setAttribute("lastInterviewList", lastItvList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
		dispatcher.forward(request, response);
	}

}
