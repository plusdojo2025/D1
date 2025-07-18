package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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

		if(request.getParameter("editCompleted") !=null && request.getParameter("editCompleted").equals("編集完了")){
 
			Date date= new Date();
			Date dateNull= null;
			int year = Integer.parseInt(request.getParameter("year"));                 //年
			int month = Integer.parseInt(request.getParameter("month"));               //月
			int grade = Integer.parseInt(request.getParameter("grade"));               //学年
			String className =request.getParameter("className");                       //クラス
			String subjectName = request.getParameter("subjectName");                  //教科
			
			
			//classIDを取得
			ClassRoomDAO classDao = new ClassRoomDAO();
			List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
			int classId = classList.get(0).getClassId();        //クラス
			
			//subjectIDを取得
			SubjectDAO subjectDao = new SubjectDAO();
			List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));
			int subjectId = subjectList.get(0).getSubjectId();  //教科Id
			
			//生徒IDを取得
			StudentsDAO studentDao = new StudentsDAO();
			List<Students> studentList = studentDao.select(new Students(0,0,0,classId,0, "", "", "", "", ""));
			List<Integer> studentIdList = new ArrayList<>();
			for(int i=0;studentList.size()>i;i++) {
				studentIdList.add(studentList.get(i).getStudentId());
			}
			
			//recordIdのリストを取得
			AttendanceRecordsDAO attendanceRecordsDao = new AttendanceRecordsDAO();
			List<AttendanceRecords> attendanceList = new ArrayList<AttendanceRecords>();
			for(int i=0;studentIdList.size()>i;i++) {
				attendanceList.addAll(attendanceRecordsDao.select(new AttendanceRecords
						(0,studentIdList.get(i), classId, year, month,0,"",subjectId,"","")));
			}

			List<Integer> attendanceIdList = new ArrayList<>();
			for(int i=0;attendanceList.size()>i;i++) {
				attendanceIdList.add(attendanceList.get(i).getRecordId());
			}
			
			//System.out.println("attendanceIdList"+attendanceIdList);
			
			int recordId = 0;
			String status = null;             //出欠情報
			for(int i=0;attendanceIdList.size()>i;i++) {
				recordId = attendanceIdList.get(i);
				//System.out.println("recordId"+recordId);
				String searchattendance =Integer.toString(recordId).concat("record");
				status = request.getParameter(searchattendance);         //出欠ID

				//出欠情報を取得
				if (attendanceRecordsDao.update(new AttendanceRecords(recordId, 0, 0, null, "", 0, status, ""))) {
					System.out.println("更新成功");
				} else { // 更新失敗
					System.out.println("出席更新失敗");
				}

			}
			
			
			
			//Assignmentのリストを取得
			AssignmentsDAO assignmentsDao = new AssignmentsDAO();
			List<Assignments> assignmentsList = new ArrayList<Assignments>();
			for(int i=0;studentIdList.size()>i;i++) {
				assignmentsList.addAll(assignmentsDao.select(new Assignments
						(0,studentIdList.get(i),subjectId,"","",year,month,date)));
			}

			List<Integer> assignmentsIdList = new ArrayList<>();
			for(int i=0;assignmentsList.size()>i;i++) {
				assignmentsIdList.add(assignmentsList.get(i).getAssignmentId());
			}
			
			int assignmentId = 0;
			String submissionStatus = null;             //課題情報
			for(int i=0;assignmentsIdList.size()>i;i++) {
				assignmentId = assignmentsIdList.get(i);
				
				List<Assignments> contentList = assignmentsDao.select(new Assignments
						(assignmentId,-1,-1,"","",-1,-1,dateNull));
				String content = contentList.get(0).getContent();
		
				
				String searchassignment =Integer.toString(assignmentId).concat("assign");
				submissionStatus = request.getParameter(searchassignment);         

				//提出物情報を取得
				if (assignmentsDao.update(new Assignments(assignmentId, 0, 0, submissionStatus, content, date))) { // 更新成功
					System.out.println("提出物更新成功");
				} else { // 更新失敗
					System.out.println("提出物更新失敗");
				}

			}
			
			//gradesIdのリストを取得
			GradesDAO gradesDao = new GradesDAO();
			List<Grades> gradesList = new ArrayList<Grades>();
			for(int i=0;studentIdList.size()>i;i++) {
				gradesList.addAll(gradesDao.select(new Grades(0,studentIdList.get(i),subjectId,-1,"",year,month)));
			}

			List<Integer> gradesIdList = new ArrayList<>();
			for(int i=0;gradesList.size()>i;i++) {
				gradesIdList.add(gradesList.get(i).getGradesId());
			}
			
			int gradesId = 0;
			int score = 0;             //テストの点
			for(int i=0;gradesIdList.size()>i;i++) {
				gradesId = gradesIdList.get(i);
				String searchgrades =Integer.toString(gradesId).concat("grades");
				score = Integer.parseInt(request.getParameter(searchgrades));         //成績ID
				
				
				List<Grades> testTypeList = gradesDao.select(
						new Grades(gradesId,-1,-1,-1,"",-1,-1));
				String testType = testTypeList.get(0).getTestType();
	

				//出欠情報を取得
				if (gradesDao.update(new Grades(gradesId, 0, 0, score, testType, 0, 0))) { // 更新成功
					System.out.println("更新成功");
				} else { // 更新失敗
					System.out.println("テスト更新失敗");
				}

			}
			request.setAttribute("grade", grade);
			request.setAttribute("className", className);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("subjectName", subjectName);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListStudentServlet");
			//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/ListStudentServlet");
			dispatcher.forward(request, response);

		}else if(request.getParameter("cancel") !=null && request.getParameter("cancel").equals("キャンセル")){
			//キャンセルボタン押したときの処理
			int year = Integer.parseInt(request.getParameter("year"));          //年
			int month = Integer.parseInt(request.getParameter("month"));        //月
			int grade = Integer.parseInt(request.getParameter("grade"));        //学年
			String className =request.getParameter("className");                //クラス
			String subjectName = request.getParameter("subjectName");           //教科
			
			request.setAttribute("grade", grade);
			request.setAttribute("className", className);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("subjectName", subjectName);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListStudentServlet");
			//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/ListStudentServlet");
			dispatcher.forward(request, response);
			
		}else if(request.getParameter("period") !=null && request.getParameter("period") !="" && 
				!request.getParameter("day").equals("出席日") ){

			int year = Integer.parseInt(request.getParameter("year"));          //年
			int month = Integer.parseInt(request.getParameter("month"));        //月
			int day = Integer.parseInt(request.getParameter("day"));            //日
			int grade = Integer.parseInt(request.getParameter("grade"));        //学年
			String className =request.getParameter("className");                //クラス
			String subjectName = request.getParameter("subjectName");           //教科
			String period =request.getParameter("period");                      //時限


			//classIDを取得
			ClassRoomDAO classDao = new ClassRoomDAO();
			List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
			request.setAttribute("classList", classList);

			int classId = classList.get(0).getClassId();        //クラス


			//subjectIDを取得
			SubjectDAO subjectDao = new SubjectDAO();
			List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));

			int subjectId = subjectList.get(0).getSubjectId();  //教科Id


			//生徒情報を取得
			StudentsDAO studentDao = new StudentsDAO();
			List<Students> studentList = studentDao.select(new Students(0,0,0,classId,0, "", "", "", "", ""));
			request.setAttribute("studentList", studentList);


			List<Integer> studentIdList = new ArrayList<>();
			for(int i=0;studentList.size()>i;i++) {
				studentIdList.add(studentList.get(i).getStudentId());
			}

			for (int i=0;studentIdList.size()>i;i++) {
				AttendanceRecordsDAO attendanceRecordsDao = new AttendanceRecordsDAO();
				if (attendanceRecordsDao.insert(new AttendanceRecords(0, studentIdList.get(i), classId, year, month, 
						day,period,subjectId, "◯", ""))) { // 登録成功
					System.out.println("出席追加成功");

				} else { // 登録失敗
					System.out.println("出席追加失敗");
				}
			}

			request.setAttribute("grade", grade);
			request.setAttribute("className", className);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("subjectName", subjectName);
			
			request.setAttribute("add", "aaa");
			request.setAttribute("period", null);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListStudentServlet");
			//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/ListStudentServlet");
			dispatcher.forward(request, response);

		}else if(request.getParameter("content") !=null && request.getParameter("content") !="" && request.getParameter("edit")==null){

			Date date=new Date(); 

			int year = Integer.parseInt(request.getParameter("year"));          //年
			int month = Integer.parseInt(request.getParameter("month"));        //月
			int grade = Integer.parseInt(request.getParameter("grade"));        //学年
			String className =request.getParameter("className");                //クラス
			String subjectName = request.getParameter("subjectName");           //教科
			String content =request.getParameter("content");           //内容
			


			//classIDを取得
			ClassRoomDAO classDao = new ClassRoomDAO();
			List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
			request.setAttribute("classList", classList);

			int classId = classList.get(0).getClassId();        //クラス


			//subjectIDを取得
			SubjectDAO subjectDao = new SubjectDAO();
			List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));

			int subjectId = subjectList.get(0).getSubjectId();  //教科Id


			//生徒情報を取得
			StudentsDAO studentDao = new StudentsDAO();
			List<Students> studentList = studentDao.select(new Students(0,0,0,classId,0, "", "", "", "", ""));
			request.setAttribute("studentList", studentList);


			List<Integer> studentIdList = new ArrayList<>();
			for(int i=0;studentList.size()>i;i++) {
				studentIdList.add(studentList.get(i).getStudentId());
			}

			for (int i=0;studentIdList.size()>i;i++) {
				AssignmentsDAO assignmentsDao = new AssignmentsDAO();
				if (assignmentsDao.insert(new Assignments(0, studentIdList.get(i), subjectId, "✕", content, 
						year, month, date))) { // 登録成功
					System.out.println("更新成功");

				} else { // 登録失敗
					System.out.println("提出物更新失敗");
				}
			}

			request.setAttribute("grade", grade);
			request.setAttribute("className", className);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("subjectName", subjectName);
			
			request.setAttribute("add", "aaa");
			request.setAttribute("content", null);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListStudentServlet");
			//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/ListStudentServlet");
			dispatcher.forward(request, response);

		}else if(request.getParameter("testType") !=null && request.getParameter("testType") !="" && request.getParameter("edit")==null){

			int year = Integer.parseInt(request.getParameter("year"));          //年
			int month = Integer.parseInt(request.getParameter("month"));        //月
			int grade = Integer.parseInt(request.getParameter("grade"));        //学年
			String className =request.getParameter("className");                //クラス
			String subjectName = request.getParameter("subjectName");           //教科
			String testType =request.getParameter("testType");           //内容


			//classIDを取得
			ClassRoomDAO classDao = new ClassRoomDAO();
			List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
			request.setAttribute("classList", classList);

			int classId = classList.get(0).getClassId();        //クラス


			//subjectIDを取得
			SubjectDAO subjectDao = new SubjectDAO();
			List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));

			int subjectId = subjectList.get(0).getSubjectId();  //教科Id


			//生徒情報を取得
			StudentsDAO studentDao = new StudentsDAO();
			List<Students> studentList = studentDao.select(new Students(0,0,0,classId,0, "", "", "", "", ""));
			request.setAttribute("studentList", studentList);


			List<Integer> studentIdList = new ArrayList<>();
			for(int i=0;studentList.size()>i;i++) {
				studentIdList.add(studentList.get(i).getStudentId());
			}

			for (int i=0;studentIdList.size()>i;i++) {
				GradesDAO gradesDao = new GradesDAO();
				if (gradesDao.insert(new Grades(0, studentIdList.get(i), subjectId, 0, testType, 
						year, month))) { // 登録成功
					System.out.println("更新成功");

				} else { // 登録失敗
					System.out.println("提出物更新失敗");
				}
			}

			request.setAttribute("grade", grade);
			request.setAttribute("className", className);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("subjectName", subjectName);
			
			request.setAttribute("add", "aaa");
			request.setAttribute("testType", null);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ListStudentServlet");
			//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/ListStudentServlet");
			dispatcher.forward(request, response);

		}else{ //画面遷移後の初期表示用

			Date date= null;
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


			//生徒情報を取得
			StudentsDAO studentDao = new StudentsDAO();
			List<Students> studentList = studentDao.select(new Students(0,0,0,classId,0, "", "", "", "", ""));
			request.setAttribute("studentList", studentList);

			//studentIdを取得
			List<Integer> studentIdList = new ArrayList<>();
			for(int i=0;studentList.size()>i;i++) {
				studentIdList.add(studentList.get(i).getStudentId());
			}
			int studentOne = studentIdList.get(1);

			AttendanceRecordsDAO attendanceRecordsDao = new AttendanceRecordsDAO();
			List<AttendanceRecords> attendanceList = new ArrayList<AttendanceRecords>();
			AssignmentsDAO assignmentsDao = new AssignmentsDAO();
			List<Assignments> assignmentsList = new ArrayList<Assignments>();
			GradesDAO gradesDao = new GradesDAO();
			List<Grades> gradesList = new ArrayList<Grades>();


			List<AttendanceRecords> attendanceDateList = attendanceRecordsDao.select(new AttendanceRecords
					(0,studentOne, classId, year, month,0,"",subjectId,"",""));
			List<Assignments> contentList = assignmentsDao.select(new Assignments
					(0,studentOne,subjectId,"","",year,month,date));
			List<Grades> testTypeList = gradesDao.select(new Grades(0,studentOne,subjectId,-1,"",year,month));
			int testTypeListSize = testTypeList.size();


			//attendanceDateListを日付順にソート
			List<Integer> attendanceDateSortList = new ArrayList<>();
			for(int i=0;attendanceDateList.size()>i;i++) {
				attendanceDateSortList.add(attendanceDateList.get(i).getDay());	
			}
			Collections.sort(attendanceDateSortList);
			List<Integer> attendanceDateSortEditList =  new ArrayList<Integer>(new HashSet<>(attendanceDateSortList));
			
			System.out.println("attendanceDateList"+attendanceDateList);
			System.out.println("attendanceDateSortList"+attendanceDateSortList);
			System.out.println("attendanceDateSortEditList"+attendanceDateSortEditList);
			
			
			for(int i=0;studentIdList.size()>i;i++) {
				int studentId = studentIdList.get(i);
				for(int j=0;attendanceDateSortEditList.size()>j;j++) {
					attendanceList.addAll(attendanceRecordsDao.select(new AttendanceRecords
							(0,studentId, classId, year, month,attendanceDateSortEditList.get(j),"",subjectId,"","")));
				}
				assignmentsList.addAll(assignmentsDao.select(new Assignments
						(0,studentId,subjectId,"","",year,month,date)));

				gradesList.addAll(gradesDao.select(new Grades(0,studentId,subjectId,-1,"",year,month)));

			}


			List<Integer> recordIdList = new ArrayList<>();
			for(int j = 0; attendanceList.size()>j ; j++) {
				recordIdList.add(attendanceList.get(j).getRecordId());
			}

			List<Integer> assignmentIdList = new ArrayList<>();
			for(int j = 0; assignmentsList.size()>j ; j++) {
				assignmentIdList.add(assignmentsList.get(j).getAssignmentId());
			}

			List<Integer> gradesIdList = new ArrayList<>();
			for(int j = 0; gradesList.size()>j ; j++) {
				gradesIdList.add(gradesList.get(j).getGradesId());
			}

			request.setAttribute("attendanceList", attendanceList);
			request.setAttribute("assignmentsList", assignmentsList);
			request.setAttribute("gradesList", gradesList);

			request.setAttribute("attendanceDateList", attendanceDateList);
			request.setAttribute("attendanceDateSortList", attendanceDateSortList);
			request.setAttribute("contentList", contentList);
			request.setAttribute("testTypeList", testTypeList);
			request.setAttribute("testTypeListSize", testTypeListSize);

			request.setAttribute("recordIdList", recordIdList);
			request.setAttribute("assignmentIdList", assignmentIdList);
			request.setAttribute("gradesIdList", gradesIdList);

			request.setAttribute("grade", grade);
			request.setAttribute("className", className);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("subjectName", subjectName);
			request.setAttribute("classId", classId);
			request.setAttribute("subjectId", subjectId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_allStudent.jsp");
			dispatcher.forward(request, response);
			
		}
	}

}
