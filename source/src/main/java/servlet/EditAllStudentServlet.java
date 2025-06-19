package servlet;

import java.io.IOException;
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
import dao.SubjectDAO;
import dto.Assignments;
import dto.AttendanceRecords;
import dto.ClassRoom;
import dto.Grades;
import dto.Subject;

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
		
		/*
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		*/
		
		request.setCharacterEncoding("UTF-8");
		
		int year = Integer.parseInt(request.getParameter("year"));          //年
		int month = Integer.parseInt(request.getParameter("month"));        //月
		int grade = Integer.parseInt(request.getParameter("grade"));        //学年
		String className =request.getParameter("className");                //クラス
		String subjectName = request.getParameter("subjectName");           //教科


		//classIDを取得
		ClassRoomDAO classDao = new ClassRoomDAO();
		List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
		request.setAttribute("classList", classList);
		int classId = classList.get(0).getClassId();        //クラス
		request.setAttribute("classId", classId);

		
		//subjectIDを取得
		SubjectDAO subjectDao = new SubjectDAO();
		List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));
		request.setAttribute("subjectList", subjectList);
		int subjectId = subjectList.get(0).getSubjectId();  //教科Id
		request.setAttribute("subjectId", subjectId);


		//出欠情報を取得
		AttendanceRecordsDAO attendanceRecordsDao = new AttendanceRecordsDAO();
		if (attendanceRecordsDao.update(new AttendanceRecords(0, studentId, classId, date, period, 
				subjectId, status, ""))) { // 更新成功
			System.out.println("更新成功");
		} else { // 更新失敗
			System.out.println("出席更新失敗");
		}

		//提出物状況を取得
		AssignmentsDAO assignmentsDao = new AssignmentsDAO();
		if (assignmentsDao.update(new Assignments(assignmentId, studentId, subjectId, 
				submissionStatus, content, year, month, submissionDate))) { // 更新成功
			System.out.println("更新成功");
		} else { // 更新失敗
			System.out.println("提出物更新失敗");
		}

		//成績情報を取得
		GradesDAO gradesDao = new GradesDAO();
		if (gradesDao.update(new Grades(gradesId, studentId, subjectId, score, testType, year, month))) { // 更新成功
			System.out.println("更新成功");
		} else { // 更新失敗
			System.out.println("テスト更新失敗");
		}

		request.setAttribute("grade", grade);
		request.setAttribute("className", className);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("subjectName", subjectName);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/InfoStudentServlet");
        dispatcher.forward(request, response);


		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
