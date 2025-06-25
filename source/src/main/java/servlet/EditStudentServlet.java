package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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
import dao.InterviewDAO;
import dao.StudentsDAO;
import dto.Assignments;
import dto.AttendanceRecords;
import dto.ClassRoom;
import dto.Grades;
import dto.Interview;
import dto.Students;

/**
 * Servlet implementation class EditStudentServlet
 */
@WebServlet("/EditStudentServlet")
public class EditStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//		HttpSession session = request.getSession(false);
		//      if (session == null || session.getAttribute("loginTeacher") == null) {
		//          response.sendRedirect("LoginServlet");
		//          return;
		//      }

		try {
			int subjectId = 1, fiscalYear = 2025, year = 1, grade = 1, month = 1, studentId = 1;
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
			int attendedNum = arDAO.GetAttendedNum(studentId, -1);
			int shouldAttendedNum = arDAO.GetShouldAttendedNum(studentId, -1);
			request.setAttribute("attendedRate", arDAO.GetAttendedRate(attendedNum, shouldAttendedNum));
			request.setAttribute("attendedNum", attendedNum);
			request.setAttribute("shouldAttendNum", shouldAttendedNum);

			// 指定した教科の出席
			int subAttendedNum = 0, subShouldAttendedNum = 0;
			for (int i = 0; i < arList.size(); i++) {
				if (arList.get(i).getStatus().equals("○")) {
					subAttendedNum++;
				}
				if (!arList.get(i).getStatus().equals("公")) {
					subShouldAttendedNum++;
				}
			}
			request.setAttribute("subjectAttendedRate", arDAO.GetAttendedRate(subAttendedNum, subShouldAttendedNum));
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
				average[i] = GetAverage(grdList.get(i).getGradesId());
			}
			request.setAttribute("average", average);

			//面談
			InterviewDAO itvDAO = new InterviewDAO();
			List<Interview> itvList = itvDAO.select_thisYear(studentId, (student.getYear() + grade - 1));
			List<Interview> lastItvList = itvDAO.select_lastYear(studentId, (student.getYear() + grade - 1));
			request.setAttribute("interviewList", itvList);
			request.setAttribute("lastInterviewList", lastItvList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_student.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
			request.setAttribute("result", "データがありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_student.jsp");
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		cal.setTime(today);

		try {
			int subjectId = 1, fiscalYear = cal.get(Calendar.YEAR), year = 1, grade = 1, month = 1, studentId = 1;
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
			studentId = Integer.parseInt(request.getParameter("studentId"));
			Students student = stuDAO.select(new Students(studentId, -1, -1, -1, -1, null, null, null, null, null)).get(0);
			String name = request.getParameter("name");
			String nameRuby = request.getParameter("nameRuby");
			String enrollmentStatus = request.getParameter("enrollmentStatus");
			String extraActivities = request.getParameter("extracurricularActivities");
			String attitude = request.getParameter("attitude");
			stuDAO.update(new Students(studentId, student.getYear(), student.getGrade(), student.getClassId(), student.getStudentNum(), name, nameRuby, enrollmentStatus, extraActivities, attitude));

			// 出欠記録の更新
			AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
			int recordAmount = Integer.parseInt(request.getParameter("recordAmount"));
			for (int i = 0; i < recordAmount; i++) {
				int id = Integer.parseInt(request.getParameter("recordId" + i));
				String status = request.getParameter("attendedStatus" + i);
				String remarks = request.getParameter("attendanceRemarks" + i);

				if (arDAO.update(new AttendanceRecords(id, -1, -1, "", -1, status, remarks))) {
					System.out.println("出欠記録 更新成功 " + id + " / " + status + " / " + remarks);
				} else {
					System.out.println("出欠記録 更新失敗 " + id + " / " + status + " / " + remarks);
				}
			}

			// 提出記録の更新
			AssignmentsDAO asDAO = new AssignmentsDAO();
			System.out.println("test");
			System.out.println(request.getParameter("submissionAmount"));
			int submissionAmount = Integer.parseInt(request.getParameter("submissionAmount"));
			for (int i = 0; i < submissionAmount; i++) {
				int id = Integer.parseInt(request.getParameter("assignmentId" + i));
				String content = request.getParameter("assignmentContent" + i);
				String status = request.getParameter("submittionStatus" + i);
				Date date = sdf.parse(request.getParameter("submittedDate" + i));

				if (asDAO.update(new Assignments(id, -1, -1, status, content, date))) {
					System.out.println("提出物記録 更新成功 " + id + " / " + status + " / " + content);
				} else {
					System.out.println("提出物記録 更新失敗 " + id + " / " + status + " / " + content);
				}
			}
			
			// 課題の追加
			int addSubmissionAmount = Integer.parseInt(request.getParameter("addSubmittionAmount"));
			for (int i = 0; i < addSubmissionAmount; i++) {
				String content = request.getParameter("addAssignmentContent" + i);

				if (!content.equals("") && content != null) {
					String status = request.getParameter("addSubmittionStatus" + i);
					Date date = sdf.parse(request.getParameter("addSubmittedDate" + i));

					if (asDAO.insert(new Assignments(-1, studentId, subjectId, status, content, year, month, date))) {
						System.out.println("提出物記録 追加成功 / " + status + " / " + content);
					} else {
						System.out.println("提出物記録 追加失敗 / " + status + " / " + content);
					}
				} else {
					System.out.println("提出物記録 追加失敗 / 内容未入力");
				}
			}
			
			// 成績の変更
			GradesDAO grdDAO = new GradesDAO();
			int gradesAmount = Integer.parseInt(request.getParameter("gradesAmount"));
			for (int i = 0; i < gradesAmount; i++) {
				int id = Integer.parseInt(request.getParameter("gradeId" + i));
				String testType = request.getParameter("gradeTestType" + i);
				int score = Integer.parseInt(request.getParameter("gradeScore" + i));

				if (grdDAO.update(new Grades(id, -1, -1, score, testType))) {
					System.out.println("成績記録 更新成功 / " + testType + " / " + score);
				} else {
					System.out.println("成績記録 更新失敗 / " + testType + " / " + score);
				}
			}
			
			// 成績の追加
			int addGradesAmount = Integer.parseInt(request.getParameter("addGradesAmount"));
			for (int i = 0; i < addGradesAmount; i++) {
				String testType = request.getParameter("addGradeTestType" + i);

				if (!testType.equals("") && testType != null) {
					int score = Integer.parseInt(request.getParameter("addGradeScore" + i));

					if (grdDAO.insert(new Grades(0, studentId, subjectId, score, testType, year, month))) {
						System.out.println("成績記録 追加成功 / " + testType + " / " + score);
					} else {
						System.out.println("成績記録 追加失敗 / " + testType + " / " + score);
					}
				} else {
					System.out.println("成績記録 追加失敗 / 内容未入力");
				}
			}
			
			// 面談の更新
			InterviewDAO itvDAO = new InterviewDAO();
			int interviewAmount = Integer.parseInt(request.getParameter("interviewAmount"));
			for (int i = 0; i < interviewAmount; i++) {
				int id = Integer.parseInt(request.getParameter("interviewId" + i));
				Date date = sdf.parse(request.getParameter("interviewDate" + i));
				String contents = request.getParameter("interviewContents" + i);
				String remarks = request.getParameter("interviewRemarks" + i);

				if (itvDAO.update(new Interview(id, -1, date, studentId, contents, remarks, subjectId))) {
					System.out.println("面談記録 更新成功 / " + id + " / " + date + " / " + contents + " / " + remarks);
				} else {
					System.out.println("面談記録 更新失敗 / " + id + " / " + date + " / " + contents + " / " + remarks);
				}
			}
			
			// 面談の追加
			int addInterviewAmount = Integer.parseInt(request.getParameter("addInterviewAmount"));
			for (int i = 0; i < addInterviewAmount; i++) {
				String contents = request.getParameter("addInterviewContents" + i);

				if (!contents.equals("") && contents != null) {
					String remarks = request.getParameter("addInterviewRemarks" + i);
					Date date = sdf.parse(request.getParameter("addInterviewDate" + i));

					if (itvDAO.insert(new Interview(0, -1, date, studentId, contents, remarks, subjectId))) {
						System.out.println("面談記録 追加成功 / " + date + " / " + contents + " / " + remarks);
					} else {
						System.out.println("面談記録 追加失敗 / " + date + " / " + contents + " / " + remarks);
					}
				} else {
					System.out.println("面談記録 追加失敗 / 内容未入力");
				}
			}
			
			// 前年度の面談の更新
			int lastInterviewAmount = Integer.parseInt(request.getParameter("lastInterviewAmount"));
			for (int i = 0; i < lastInterviewAmount; i++) {
				int id = Integer.parseInt(request.getParameter("lastInterviewId" + i));
				Date date = sdf.parse(request.getParameter("lastInterviewDate" + i));
				String contents = request.getParameter("lastInterviewContents" + i);
				String remarks = request.getParameter("lastInterviewRemarks" + i);

				if (itvDAO.update(new Interview(id, -1, date, studentId, contents, remarks, subjectId))) {
					System.out.println("前年度面談記録 更新成功 / " + id + " / " + date + " / " + contents + " / " + remarks);
				} else {
					System.out.println("前年度面談記録 更新失敗 / " + id + " / " + date + " / " + contents + " / " + remarks);
				}
			}
			
			// 前年度の面談の追加
			int addLastInterviewAmount = Integer.parseInt(request.getParameter("addLastInterviewAmount"));
			for (int i = 0; i < addLastInterviewAmount; i++) {
				String contents = request.getParameter("addLastInterviewContents" + i);

				if (!contents.equals("") && contents != null) {
					Date date = sdf.parse(request.getParameter("addLastInterviewDate" + i));
					String remarks = request.getParameter("addLastInterviewRemarks" + i);

					if (itvDAO.insert(new Interview(0, -1, date, studentId, contents, remarks, subjectId))) {
						System.out.println("前年度面談記録 追加成功 / " + date + " / " + contents + " / " + remarks);
					} else {
						System.out.println("前年度面談記録 追加失敗 / " + date + " / " + contents + " / " + remarks);
					}
				} else {
					System.out.println("前年度面談記録 追加失敗 / 内容未入力");
				}
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list_student.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
			request.setAttribute("result", "データがありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list_student.jsp");
			dispatcher.forward(request, response);
		}
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
				if (created.isAfter(begin) && created.isBefore(end) && as.get(i).getSubmissionStatus().equals("◯")) {
					count++;
				}
			} else {
				// 年明け後
				if (month + 1 >= 1 && day >= 1) {
					LocalDate begin = LocalDate.of(year-1, 10, 15);
					LocalDate end = LocalDate.of(year, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && as.get(i).getSubmissionStatus().equals("◯")) {
						count++;
					}
				} else {
					LocalDate begin = LocalDate.of(year, 10, 15);
					LocalDate end = LocalDate.of(year+1, 3, 31);
					if (created.isAfter(begin) && created.isBefore(end) && as.get(i).getSubmissionStatus().equals("◯")) {
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
			if (as.get(i).getCreatedYear() == selectYear && as.get(i).getCreatedMonth() == selectMonth && as.get(i).getSubmissionStatus().equals("◯")) {
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

		String rate = String.format("%.1f", (submittedNum / shouldSubmittedNum * 100.0f));
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

	public String GetAverage(int gradesId) {
		float sum = 0;
		GradesDAO grdDAO = new GradesDAO();
		List<Grades> grdList = grdDAO.select(new Grades(gradesId, -1, -1, -1, "", -1, -1));
		for (int i = 0; i < grdList.size(); i++) {
			sum += grdList.get(i).getScore();
		}

		return String.format("%.1f", (sum / grdList.size()));
	}
}
