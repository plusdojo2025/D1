package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
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
import dao.InterviewDAO;
import dao.StudentsDAO;
import dto.Assignments;
import dto.AttendanceRecords;
import dto.ClassRoom;
import dto.Grades;
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

		//		HttpSession session = request.getSession(false);
		//		if (session == null || session.getAttribute("loginTeacher") == null) {
		//			response.sendRedirect("LoginServlet");
		//			return;
		//		}

		try {
			int subjectId = 1, fiscalYear = 2025, year = 1, grade = 1, month = 1, studentId = 37;
			// プルダウンの情報
			if (request.getParameter("subjectId") != null) {
				subjectId = Integer.parseInt(request.getParameter("subjectId"));
			}

			if (request.getParameter("year") != null) {
				fiscalYear = Integer.parseInt(request.getParameter("year"));
			}

			if (request.getParameter("grade") != null) {
				grade = Integer.parseInt(request.getParameter("grade"));
			}

			if (request.getParameter("month") != null) {
				month = Integer.parseInt(request.getParameter("month"));
			}

			request.setAttribute("subjectId", subjectId);
			request.setAttribute("year", fiscalYear);
			request.setAttribute("grade", grade);
			request.setAttribute("month", month);

			if (month >= 1 && month < 4) year = fiscalYear + 1; // 年度から年に変換する
			else year = fiscalYear;

			StudentsDAO stuDAO = new StudentsDAO();

			if (request.getParameter("studentId") != null) {
				studentId = Integer.parseInt(request.getParameter("studentId"));
			}

			List<Students> studentsList = stuDAO.select(new Students(studentId, -1, -1, -1, -1, "", "", "", "", ""));
			Students student = new Students();
			if (studentsList.size() > 0) student = studentsList.get(0);
			request.setAttribute("student", student);

			AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
			List<AttendanceRecords> arList = arDAO.select_Fiscal((student.getYear() + grade - 1), studentId, month, subjectId);

			ClassRoomDAO crDAO = new ClassRoomDAO();
			List<ClassRoom> classList = crDAO.select(new ClassRoom(student.getClassId(),student.getGrade(), null));
			ClassRoom classRoom = new ClassRoom();
			if (classList.size() > 0) classRoom = classList.get(0);
			request.setAttribute("className", classRoom.getClassName());

			// 全体の今学期の出席
			int attendedNum = 0, shouldAttendedNum = 0;
			attendedNum = GetAttendedNum(arDAO.select(studentId));
			shouldAttendedNum = GetShouldAttendedNum(arDAO.select(studentId));
			request.setAttribute("attendedRate", GetAttendedRate(attendedNum, shouldAttendedNum));
			request.setAttribute("attendedNum", attendedNum);
			request.setAttribute("shouldAttendNum", shouldAttendedNum);

			// 指定した教科の出席
			int subAttendedNum = 0, subShouldAttendedNum = 0;
			for (int i = 0; i < arList.size(); i++) {
				if (arList.get(i).getStatus().equals("◯") || arList.get(i).getStatus().equals("○")) {
					subAttendedNum++;
				}
				if (!arList.get(i).getStatus().equals("公")) {
					subShouldAttendedNum++;
				}
			}
			request.setAttribute("subjectAttendedRate", GetAttendedRate(subAttendedNum, subShouldAttendedNum));
			request.setAttribute("attendanceRecords", arList);

			// 全教科・今学期の提出物
			AssignmentsDAO asDAO = new AssignmentsDAO();
			List<Assignments> allAsList = asDAO.select(new Assignments(-1, studentId, -1, "", "", -1, -1, null));
			int submittedNum = 0, shouldSubmittedNum = 0;
			submittedNum = GetSubmittedNum(allAsList);
			shouldSubmittedNum = GetShouldSubmittedNum(allAsList);
			request.setAttribute("submittedRate", GetSubmittedRate(submittedNum, shouldSubmittedNum));
			request.setAttribute("submittedNum", submittedNum);
			request.setAttribute("shouldSubmitNum", shouldSubmittedNum);

			// 指定した教科の提出物
			List<Assignments> asList = asDAO.select(new Assignments(-1, studentId, subjectId, "", "", (student.getYear() + grade - 1), month, null));
			List<Assignments> asList2 = asDAO.select(new Assignments(-1, studentId, subjectId, "", "", -1, -1, null));
			int subjectSubmittedNum = 0, subjectShouldSubmittedNum = 0;
			subjectSubmittedNum = GetSubmittedNum(asList2, year, month);
			subjectShouldSubmittedNum = GetShouldSubmittedNum(asList2, year, month);
			request.setAttribute("subjectSubmittedRate", GetSubmittedRate(subjectSubmittedNum, subjectShouldSubmittedNum));
			request.setAttribute("assignmentsList", asList);

			// 成績
			GradesDAO grdDAO = new GradesDAO();
			List<Grades> grdList = grdDAO.select(new Grades(-1, studentId, subjectId, -1, "", (student.getYear() + grade - 1), month));
			request.setAttribute("gradesList", grdList);
			String[] average = new String[grdList.size()];
			for (int i = 0; i < grdList.size(); i++) {
				average[i] = GetAverage(grdList.get(i).getSubjectId(), grdList.get(i).getTestType(), grdList.get(i).getYear(), grdList.get(i).getMonth());
			}
			request.setAttribute("average", average);

			//面談
			InterviewDAO itvDAO = new InterviewDAO();
			List<Interview> itvList = itvDAO.select_thisYear(studentId, (student.getYear() + grade - 1));
			List<Interview> lastItvList = itvDAO.select_lastYear(studentId, (student.getYear() + grade - 1));
			request.setAttribute("interviewList", itvList);
			request.setAttribute("lastInterviewList", lastItvList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
			request.setAttribute("result", "データがありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//		HttpSession session = request.getSession(false);
		//        if (session == null || session.getAttribute("loginTeacher") == null) {
		//            response.sendRedirect("LoginServlet");
		//            return;
		//        }

		try {
			int subjectId = 1, fiscalYear = 2025, year = 1, grade = 1, month = 1, studentId = -1;
			// プルダウンの情報
			if (request.getAttribute("subjectId") != null) {
				subjectId = (int)request.getAttribute("subjectId");
			}

			if (request.getParameter("year") != null) {
				fiscalYear = Integer.parseInt(request.getParameter("year"));
			}

			if (request.getParameter("grade") != null) {
				grade = Integer.parseInt(request.getParameter("grade"));
			}

			if (request.getParameter("month") != null) {
				month = Integer.parseInt(request.getParameter("month"));
			}

			request.setAttribute("subjectId", subjectId);
			request.setAttribute("year", fiscalYear);
			request.setAttribute("grade", grade);
			request.setAttribute("month", month);

			if (month >= 1 && month < 4) year = fiscalYear + 1; // 年度から年に変換する
			else year = fiscalYear;

			StudentsDAO stuDAO = new StudentsDAO();

			if (request.getAttribute("studentId") != null) {
				studentId = (int)request.getAttribute("studentId");
			}

			List<Students> studentsList = stuDAO.select(new Students(studentId, -1, -1, -1, -1, "", "", "", "", ""));
			Students student = new Students();
			if (studentsList.size() > 0) student = studentsList.get(0);
			request.setAttribute("student", student);

			AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
			List<AttendanceRecords> arList = arDAO.select_Fiscal((student.getYear() + grade - 1), studentId, month, subjectId);

			ClassRoomDAO crDAO = new ClassRoomDAO();
			List<ClassRoom> classList = crDAO.select(new ClassRoom(student.getClassId(),student.getGrade(), null));
			ClassRoom classRoom = new ClassRoom();
			if (classList.size() > 0) classRoom = classList.get(0);
			request.setAttribute("className", classRoom.getClassName());

			// 全体の今学期の出席
			int attendedNum = 0, shouldAttendedNum = 0;
			attendedNum = GetAttendedNum(arDAO.select(studentId));
			shouldAttendedNum = GetShouldAttendedNum(arDAO.select(studentId));
			request.setAttribute("attendedRate", GetAttendedRate(attendedNum, shouldAttendedNum));
			request.setAttribute("attendedNum", attendedNum);
			request.setAttribute("shouldAttendNum", shouldAttendedNum);

			// 指定した教科の出席
			int subAttendedNum = 0, subShouldAttendedNum = 0;
			for (int i = 0; i < arList.size(); i++) {
				if (arList.get(i).getStatus().equals("◯") || arList.get(i).getStatus().equals("○")) {
					subAttendedNum++;
				}
				if (!arList.get(i).getStatus().equals("公")) {
					subShouldAttendedNum++;
				}
			}
			request.setAttribute("subjectAttendedRate", GetAttendedRate(subAttendedNum, subShouldAttendedNum));
			request.setAttribute("attendanceRecords", arList);

			// 全教科・今学期の提出物
			AssignmentsDAO asDAO = new AssignmentsDAO();
			List<Assignments> allAsList = asDAO.select(new Assignments(-1, studentId, -1, "", "", -1, -1, null));
			int submittedNum = 0, shouldSubmittedNum = 0;
			submittedNum = GetSubmittedNum(allAsList);
			shouldSubmittedNum = GetShouldSubmittedNum(allAsList);
			request.setAttribute("submittedRate", GetSubmittedRate(submittedNum, shouldSubmittedNum));
			request.setAttribute("submittedNum", submittedNum);
			request.setAttribute("shouldSubmitNum", shouldSubmittedNum);

			// 指定した教科の提出物
			List<Assignments> asList = asDAO.select(new Assignments(-1, studentId, subjectId, "", "", (student.getYear() + grade - 1), month, null));
			List<Assignments> asList2 = asDAO.select(new Assignments(-1, studentId, subjectId, "", "", -1, -1, null));
			int subjectSubmittedNum = 0, subjectShouldSubmittedNum = 0;
			subjectSubmittedNum = GetSubmittedNum(asList2, year, month);
			subjectShouldSubmittedNum = GetShouldSubmittedNum(asList2, year, month);
			request.setAttribute("subjectSubmittedRate", GetSubmittedRate(subjectSubmittedNum, subjectShouldSubmittedNum));
			request.setAttribute("assignmentsList", asList);

			// 成績
			GradesDAO grdDAO = new GradesDAO();
			List<Grades> grdList = grdDAO.select(new Grades(-1, studentId, subjectId, -1, "", (student.getYear() + grade - 1), month));
			request.setAttribute("gradesList", grdList);
			String[] average = new String[grdList.size()];
			for (int i = 0; i < grdList.size(); i++) {
				average[i] = GetAverage(grdList.get(i).getSubjectId(), grdList.get(i).getTestType(), grdList.get(i).getYear(), grdList.get(i).getMonth());
			}
			request.setAttribute("average", average);

			//面談
			InterviewDAO itvDAO = new InterviewDAO();
			List<Interview> itvList = itvDAO.select_thisYear(studentId, (student.getYear() + grade - 1));
			List<Interview> lastItvList = itvDAO.select_lastYear(studentId, (student.getYear() + grade - 1));
			request.setAttribute("interviewList", itvList);
			request.setAttribute("lastInterviewList", lastItvList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
			request.setAttribute("result", "データがありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/info_student.jsp");
			dispatcher.forward(request, response);
		}
	}

	// 引数で指定した生徒の出席数を返す
	public int GetAttendedNum(List<AttendanceRecords> ar) {
		if (ar.size() == 0) return 0;

		int count = 0;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_WEEK);

		for (int i = 0; i < ar.size(); i++) {
			int cYear = ar.get(i).getYear() > 0 ? ar.get(i).getYear() : 2025;
			int cMonth = ar.get(i).getMonth() > 0 && ar.get(i).getMonth() <= 12 ? ar.get(i).getMonth() : 6;

			LocalDate created = LocalDate.of(cYear, cMonth, 1);

			if (isFirstSemester(month, day) ) {
				// 前期
				LocalDate begin = LocalDate.of(year, 3, 31);
				LocalDate end = LocalDate.of(year, 10, 15);
				if (created.isAfter(begin) && created.isBefore(end) && ar.get(i).getPeriod().equals("1")
						&& (ar.get(i).getStatus().equals("◯") || ar.get(i).getStatus().equals("○") || ar.get(i).getStatus().equals("早") || ar.get(i).getStatus().equals("遅"))) {
					count++;
				}
			} else {
				// 年明け後
				if (month + 1 >= 1 && day >= 1) {
					LocalDate begin = LocalDate.of(year-1, 10, 15);
					LocalDate end = LocalDate.of(year, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && ar.get(i).getPeriod().equals("1")
							&& (ar.get(i).getStatus().equals("◯") || ar.get(i).getStatus().equals("○") || ar.get(i).getStatus().equals("早") || ar.get(i).getStatus().equals("遅"))) {
						count++;
					}
				} else {
					LocalDate begin = LocalDate.of(year, 10, 15);
					LocalDate end = LocalDate.of(year+1, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && ar.get(i).getPeriod().equals("1")
							&& (ar.get(i).getStatus().equals("◯") || ar.get(i).getStatus().equals("○") || ar.get(i).getStatus().equals("早") || ar.get(i).getStatus().equals("遅"))) {
						count++;
					}
				}
			}
		}

		// 結果を返す
		return count;
	}

	// 引数で指定した生徒の出席すべき日数を返す
	public int GetShouldAttendedNum(List<AttendanceRecords> ar) {
		if (ar.size() == 0) return 0;

		int count = 0;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_WEEK);

		for (int i = 0; i < ar.size(); i++) {
			int cYear = ar.get(i).getYear() > 0 ? ar.get(i).getYear() : 2025;
			int cMonth = ar.get(i).getMonth() > 0 && ar.get(i).getMonth() <= 12 ? ar.get(i).getMonth() : 6;
			LocalDate created = LocalDate.of(cYear, cMonth, 1);
			if (isFirstSemester(month, day) ) {
				// 前期
				LocalDate begin = LocalDate.of(year, 3, 31);
				LocalDate end = LocalDate.of(year, 10, 15);
				if (created.isAfter(begin) && created.isBefore(end) && ar.get(i).getPeriod().equals("1")) {
					count++;
				}
			} else {
				// 年明け後
				if (month + 1 >= 1 && day >= 1) {
					LocalDate begin = LocalDate.of(year-1, 10, 15);
					LocalDate end = LocalDate.of(year, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && ar.get(i).getPeriod().equals("1")) {
						count++;
					}
				} else {
					LocalDate begin = LocalDate.of(year, 10, 15);
					LocalDate end = LocalDate.of(year+1, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && ar.get(i).getPeriod().equals("1")) {
						count++;
					}
				}
			}
		}

		return count;
	}

	// 出席率を百分率/小数点第二位までのString型で返す
	public String GetAttendedRate(int attendedNum, int shouldAttendedNum) {
		if (attendedNum == 0 || shouldAttendedNum == 0) return "0.0";
		String rate = String.format("%.1f", ((float)attendedNum / (float)shouldAttendedNum * 100.0f));
		return rate;
	}

	// 引数で指定した生徒の課題提出数を返す
	public int GetSubmittedNum(List<Assignments> as) {
		if (as.size() == 0) return 0;

		int count = 0;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_WEEK);

		for (int i = 0; i < as.size(); i++) {
			int cYear = as.get(i).getCreatedYear() > 0 ? as.get(i).getCreatedYear() : 2025;
			int cMonth = as.get(i).getCreatedMonth() > 0 && as.get(i).getCreatedMonth() <= 12 ? as.get(i).getCreatedMonth() : 6;

			LocalDate created = LocalDate.of(cYear, cMonth, 1);

			if (isFirstSemester(month, day) ) {
				// 前期
				LocalDate begin = LocalDate.of(year, 3, 31);
				LocalDate end = LocalDate.of(year, 10, 15);
				if (created.isAfter(begin) && created.isBefore(end) && (as.get(i).getSubmissionStatus().equals("◯") || as.get(i).getSubmissionStatus().equals("○"))) {
					count++;
				}
			} else {
				// 年明け後
				if (month + 1 >= 1 && day >= 1) {
					LocalDate begin = LocalDate.of(year-1, 10, 15);
					LocalDate end = LocalDate.of(year, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && (as.get(i).getSubmissionStatus().equals("◯") || as.get(i).getSubmissionStatus().equals("○"))) {
						count++;
					}
				} else {
					LocalDate begin = LocalDate.of(year, 10, 15);
					LocalDate end = LocalDate.of(year+1, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && (as.get(i).getSubmissionStatus().equals("◯") || as.get(i).getSubmissionStatus().equals("○"))) {
						count++;
					}
				}
			}
		}

		// 結果を返す
		return count;
	}

	public int GetSubmittedNum(List<Assignments> as, int selectYear, int selectMonth) {
		if (as.size() == 0) return 0;

		int count = 0;
		for (int i = 0; i < as.size(); i++) {
			if (as.get(i).getCreatedYear() == selectYear && as.get(i).getCreatedMonth() == selectMonth && 
					(as.get(i).getSubmissionStatus().equals("◯") || as.get(i).getSubmissionStatus().equals("○"))) {
				count++;
			}
		}

		// 結果を返す
		return count;
	}

	// 引数で指定した生徒の提出すべき課題数を返す
	public int GetShouldSubmittedNum(List<Assignments> as) {
		if (as.size() == 0) return 0;

		int count = 0;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_WEEK);

		for (int i = 0; i < as.size(); i++) {
			int cYear = as.get(i).getCreatedYear() > 0 ? as.get(i).getCreatedYear() : 2025;
			int cMonth = as.get(i).getCreatedMonth() > 0 && as.get(i).getCreatedMonth() <= 12 ? as.get(i).getCreatedMonth() : 6;
			LocalDate created = LocalDate.of(cYear, cMonth, 1);
			if (isFirstSemester(month, day) ) {
				// 前期
				LocalDate begin = LocalDate.of(year, 3, 31);
				LocalDate end = LocalDate.of(year, 10, 15);
				if (created.isAfter(begin) && created.isBefore(end)) {
					count++;
				}
			} else {
				// 年明け後
				if (month + 1 >= 1 && day >= 1) {
					LocalDate begin = LocalDate.of(year-1, 10, 15);
					LocalDate end = LocalDate.of(year, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end)) {
						count++;
					}
				} else {
					LocalDate begin = LocalDate.of(year, 10, 15);
					LocalDate end = LocalDate.of(year+1, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end)) {
						count++;
					}
				}
			}
		}

		return count;
	}

	public int GetShouldSubmittedNum(List<Assignments> as, int selectYear, int selectMonth) {
		if (as.size() == 0) return 0;

		int count = 0;
		for (int i = 0; i < as.size(); i++) {
			if (as.get(i).getCreatedYear() == selectYear && as.get(i).getCreatedMonth() == selectMonth) {
				count++;
			}
		}
		return count;
	}

	// 提出率を百分率/小数点第二位までのString型で返す
	public String GetSubmittedRate(int submittedNum, int shouldSubmittedNum) {
		if (submittedNum == 0 || shouldSubmittedNum == 0) return "0.0";

		String rate = String.format("%.1f", ((float)submittedNum / (float)shouldSubmittedNum * 100.0f));
		return rate;
	}

	// 入力した月日が前期かどうか判定する
	public boolean isFirstSemester(int month, int day) {
		if ((month >= 4 && day >= 1 && month < 10) || (month == 10 && day <= 14)) {

			return true;
		} else {
			return false;
		}
	}

	public String GetAverage(int subjectId, String testType, int year, int month) {
		float sum = 0;
		GradesDAO grdDAO = new GradesDAO();
		List<Grades> grdList = grdDAO.select(new Grades(-1, -1, subjectId, -1, testType, year, (month + 1)));
		for (int i = 0; i < grdList.size(); i++) {
			sum += grdList.get(i).getScore();
		}

		return String.format("%.1f", (sum / grdList.size()));
	}
}
