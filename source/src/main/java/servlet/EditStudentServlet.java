package servlet;

import java.io.IOException;
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try {
			int subjectId = 1, fiscalYear = 1, grade = 1, month = 1, studentId = 1;
			// プルダウンの情報
			// 教科
			if (request.getParameter("subjectId") != null) {
				subjectId = Integer.parseInt(request.getParameter("subjectId"));
			}
			// 年度
			if (request.getParameter("year") != null) {
				fiscalYear = Integer.parseInt(request.getParameter("year"));
			}
			// 学年
			if (request.getParameter("grade") != null) {
				grade = Integer.parseInt(request.getParameter("grade"));
			}
			// 月
			if (request.getParameter("month") != null) {
				month = Integer.parseInt(request.getParameter("month"));
			}

			request.setAttribute("subjectId", subjectId);
			request.setAttribute("year", fiscalYear);
			request.setAttribute("grade", grade);
			request.setAttribute("month", month);

			StudentsDAO stuDAO = new StudentsDAO();

			if (request.getParameter("studentId") != null) {
				studentId = Integer.parseInt(request.getParameter("studentId"));
			}
			List<Students> studentsList = stuDAO.select(new Students(studentId, -1, -1, -1, -1, "", "", "", "", ""));
			Students student = new Students();
			if (studentsList.size() > 0) student = studentsList.get(0);
			request.setAttribute("student", student);

			AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
			List<AttendanceRecords> arList = arDAO.select_Fiscal(student.getYear() + grade, student.getClassId(), month, subjectId);

			ClassRoomDAO crDAO = new ClassRoomDAO();
			List<ClassRoom> classList = crDAO.select(new ClassRoom(student.getClassId(),student.getGrade(),""));
			ClassRoom classRoom = new ClassRoom();
			if (classList.size() > 0) classRoom = classList.get(0);
			request.setAttribute("className", classRoom.getClassName());

			// 全体の出席
			int attendedNum = arDAO.GetAttendedNum(arList);
			int shouldAttendedNum = arDAO.GetShouldAttendedNum(arList);
			request.setAttribute("attendedRate", arDAO.GetAttendedRate(attendedNum, shouldAttendedNum));
			request.setAttribute("attendedNum", attendedNum);
			request.setAttribute("shouldAttendNum", shouldAttendedNum);

			// 全体の提出物
			AssignmentsDAO asDAO = new AssignmentsDAO();
			List<Assignments> allAsList = asDAO.select(new Assignments(-1, studentId, -1, "", "", -1, -1, null));
			int submittedNum = GetSubmittedNum(allAsList);
			int shouldSubmittedNum = GetShouldSubmittedNum(allAsList);
			request.setAttribute("submittedRate", GetSubmittedRate(submittedNum, shouldSubmittedNum));
			request.setAttribute("submittedNum", submittedNum);
			request.setAttribute("shouldSubmitNum", shouldSubmittedNum);

			List<Assignments> asList = asDAO.select(new Assignments(-1, studentId, subjectId, "", "", -1, month, null));
			int subjectSubmittedNum = GetSubmittedNum(asList);
			int subjectShouldSubmittedNum = GetShouldSubmittedNum(asList);
			
			// 指定した教科の出席・提出物
			request.setAttribute("subjectAttendedRate", arDAO.GetAttendedRate(studentId, subjectId));
			request.setAttribute("attendanceRecords", arList);
			request.setAttribute("subjectSubmittedRate", GetSubmittedRate(subjectSubmittedNum, subjectShouldSubmittedNum));
			request.setAttribute("assignmentsList", asList);

			// 成績
			GradesDAO grdDAO = new GradesDAO();
			List<Grades> grdList = grdDAO.select(new Grades(-1, studentId, subjectId, -1, "", -1, month));
			request.setAttribute("gradesList", grdList);
			float[] average = new float[grdList.size()];
			for (int i = 0; i < grdList.size(); i++) {
				average[i] = GetAverage(grdList.get(i).getGradesId());
			}
			request.setAttribute("average", average);

			//面談
			InterviewDAO itvDAO = new InterviewDAO();
			List<Interview> itvList = itvDAO.select_thisYear(studentId);
			List<Interview> lastItvList = itvDAO.select_lastYear(studentId);
			request.setAttribute("interviewList", itvList);
			request.setAttribute("lastInterviewList", lastItvList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_student.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("result", "データがありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_student.jsp");
			dispatcher.forward(request, response);
		}
	}

	// 引数で指定した生徒の課題提出数を返す
		public int GetSubmittedNum(List<Assignments> as) {
			int count = 0;
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_WEEK);

			for (int i = 0; i < as.size(); i++) {
				if (isFirstSemester(month, day) ) {
					// 前期
					if (as.get(i).getSubmissionDate().after(new Date(year, 3, 31)) && as.get(i).getSubmissionDate().before(new Date(year, 10, 15)) && as.get(i).getSubmissionStatus().equals("○")) {
						count++;
					}
				} else {
					// 年明け後
					if (month + 1 >= 1 && day >= 1) {
						if (as.get(i).getSubmissionDate().after(new Date(year-1, 10, 15)) && as.get(i).getSubmissionDate().before(new Date(year, 3, 31)) && as.get(i).getSubmissionStatus().equals("○")) {
							count++;
						}
					} else {
						if (as.get(i).getSubmissionDate().after(new Date(year, 10, 15)) && as.get(i).getSubmissionDate().before(new Date(year+1, 3, 31)) && as.get(i).getSubmissionStatus().equals("○")) {
							count++;
						}
					}
				}
			}

			// 結果を返す
			return count;
		}

		// 引数で指定した生徒の提出すべき課題数を返す
		public int GetShouldSubmittedNum(List<Assignments> as) {
			int count = 0;
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_WEEK);
			for (int i = 0; i < as.size(); i++) {
				if (isFirstSemester(month, day) ) {
					// 前期
					if (as.get(i).getSubmissionDate().after(new Date(year, 3, 31)) && as.get(i).getSubmissionDate().before(new Date(year, 10, 15)) && !as.get(i).getSubmissionStatus().equals("公欠")) {
						count++;
					}
				} else {
					// 年明け後
					if (month + 1 >= 1 && day >= 1) {
						if (as.get(i).getSubmissionDate().after(new Date(year-1, 10, 15)) && as.get(i).getSubmissionDate().before(new Date(year, 3, 31)) && !as.get(i).getSubmissionStatus().equals("公欠")) {
							count++;
						}
					} else {
						if (as.get(i).getSubmissionDate().after(new Date(year, 10, 15)) && as.get(i).getSubmissionDate().before(new Date(year+1, 3, 31)) && !as.get(i).getSubmissionStatus().equals("公欠")) {
							count++;
						}
					}
				}
			}
			return count;
		}

		// 提出率を百分率/小数点第二位までのString型で返す
		public String GetSubmittedRate(int SubmittedNum, int shouldSubmittedNum) {
			String rate = String.format("%.1f", (SubmittedNum / shouldSubmittedNum * 100.0f));
			return rate;
		}

		// 入力した月日が前期かどうか判定する
		public boolean isFirstSemester(int month, int day) {
			if (month + 1 >= 4 && day >= 1 && month + 1 <= 10 && day <= 14) {
				return true;
			} else {
				return false;
			}
		}

		public float GetAverage(int gradesId) {
			float sum = 0;
			GradesDAO grdDAO = new GradesDAO();
			List<Grades> grdList = grdDAO.select(new Grades(gradesId, -1, -1, -1, "", -1, -1));
			for (int i = 0; i < grdList.size(); i++) {
				sum += grdList.get(i).getScore();
			}
			
			return sum / gradesId;
		}
}
