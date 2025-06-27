package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
		
		Date date=null; ;
		Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        
        
		int yearNow = calendar.get(Calendar.YEAR); 
		request.setAttribute("yearNow", yearNow);
        
        
		int grade = 1;
		String className = "1組";
		String subjectName = "現代文";
		int studentId = 0;
		
		// 検索処理を行う
		//classIDを取得
		ClassRoomDAO classDao = new ClassRoomDAO();
		List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
		request.setAttribute("classList", classList);
		
		//subjectIDを取得
		SubjectDAO subjectDao = new SubjectDAO();
		List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));
		request.setAttribute("subjectList", subjectList);
		
		int classId = classList.get(0).getClassId();        //クラス
		request.setAttribute("classId", classId);
		
		int subjectId = subjectList.get(0).getSubjectId();  //教科Id
		request.setAttribute("subjectId", subjectId);
		
		//生徒情報を取得
		StudentsDAO studentDao = new StudentsDAO();
		List<Students> studentList = studentDao.select(new Students(0,0,0,classId,0, "", "", "", "", ""));
		request.setAttribute("studentList", studentList);
		
		
		List<Integer> studentIdList = new ArrayList<>();
		for(int i=0;studentList.size()>i;i++) {
			studentIdList.add(studentList.get(i).getStudentId());
		}
		int studentOne = studentIdList.get(0);

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
		
		for(int i=0;studentIdList.size()>i;i++) {
			studentId = studentIdList.get(i);
			for(int j=0;attendanceDateSortEditList.size()>j;j++) {
				attendanceList.addAll(attendanceRecordsDao.select(new AttendanceRecords
						(0,studentId, classId, year, month,attendanceDateSortEditList.get(j),"",subjectId,"","")));
			}
			assignmentsList.addAll(assignmentsDao.select(new Assignments
					(0,studentId,subjectId,"","",year,month,date)));

			gradesList.addAll(gradesDao.select(new Grades(0,studentId,subjectId,-1,"",year,month)));

		}
		
		/*
		for(int i=0;studentIdList.size()>i;i++) {
			//studentId.add(studentList.get(i).getStudentId());
			studentId = studentIdList.get(i);
		
			attendanceList.addAll(attendanceRecordsDao.select(new AttendanceRecords
					(0,studentId, classId, year, month,0,"",subjectId,"","")));
			
			assignmentsList.addAll(assignmentsDao.select(new Assignments
					(0,studentId,subjectId,"","",year,month,date)));
			
			gradesList.addAll(gradesDao.select(new Grades(0,studentId,subjectId,-1,"",year,month)));
		}	
		*/
		
		ArrayList<Double> attendanceRate = new ArrayList<>();
		double attendanceSum = 0;
		double sum = 0;
		for(int i=0;studentIdList.size()>i;i++) { //表示する生徒の分繰り返す
			sum=0;
			attendanceSum=0;
			for(int j=0;attendanceList.size()>j;j++) {  //あるクラスの出席状況の文繰り返す
				if(studentIdList.get(i) ==  attendanceList.get(j).getStudentId()) {  //ある生徒の該当する出席状況の個所を割り出す
					String attendanceStatus = attendanceList.get(j).getStatus();
					if (attendanceStatus.equals("◯")||attendanceStatus.equals("公")) {
						attendanceSum+=1;
					}
					sum+=1;
				}
			}
			double attend = (Math.floor(attendanceSum * 100 / sum * 10)) / 10;
			attendanceRate.add(attend);
		}
		
		ArrayList<Double> submissionRate = new ArrayList<>();
		double submissionSum = 0;
		double sumS = 0;
		for(int k=0;studentIdList.size()>k;k++) { //表示する生徒の分繰り返す
			sumS=0;
			submissionSum=0;
			
			for(int l=0;assignmentsList.size()>l;l++) {  //あるクラスの出席状況の文繰り返す
				if(studentIdList.get(k) ==  assignmentsList.get(l).getStudentId()) {  //ある生徒の該当する出席状況の個所を割り出す
					String assignmentsSubmissionStatus = assignmentsList.get(l).getSubmissionStatus();
					if (assignmentsSubmissionStatus.equals("◯")) {
						submissionSum+=1;
					}
					sumS+=1;
				}
			}
			double sub = (Math.floor(submissionSum * 100 / sumS * 10)) / 10;
			submissionRate.add(sub);
			
		}
		
		
		
		request.setAttribute("attendanceList", attendanceList);
		request.setAttribute("assignmentsList", assignmentsList);
		request.setAttribute("gradesList", gradesList);
		
		request.setAttribute("attendanceDateList", attendanceDateList);
		request.setAttribute("attendanceDateSortList", attendanceDateSortList);
		
		request.setAttribute("contentList", contentList);
		request.setAttribute("testTypeList", testTypeList);
		request.setAttribute("testTypeListSize", testTypeListSize);
		
		request.setAttribute("attendanceRate", attendanceRate);
		request.setAttribute("submissionRate", submissionRate);
		
		request.setAttribute("grade", grade);
		request.setAttribute("className", className);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("subjectName", subjectName);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list_student.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// もしもログインしていなかったらログインサーブレットにリダイレクトする

		request.setCharacterEncoding("utf-8");

		/*
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		 */
		 //nameで指定しvalueを参照
		if(request.getParameter("edit") !=null && request.getParameter("edit").equals("編集")){
				
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
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("EditAllStudentServlet");
				//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/EditAllStudentServlet");
				dispatcher.forward(request, response);

		}else if(request.getAttribute("add") !="aaa" && request.getParameter("add") !=null && 
				(request.getParameter("add").equals("提出物追加") || request.getParameter("add").equals("テスト追加")
						|| request.getParameter("add").equals("出席日追加"))){
			try {
			String content =null;                    //内容
			String testType =null;                    //内容
			int day = 0;
			String period = null;
			String errorMessage = null;
			String add = "aaa";
			
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
			
			//classIDを取得
			ClassRoomDAO classDao = new ClassRoomDAO();
			List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));
			request.setAttribute("classList", classList);
			
			//subjectIDを取得
			SubjectDAO subjectDao = new SubjectDAO();
			List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));
			request.setAttribute("subjectList", subjectList);
			
			int classId = classList.get(0).getClassId();        //クラス
			int subjectId = subjectList.get(0).getSubjectId();  //教科Id
			
			
			if(request.getParameter("add").equals("出席日追加")) {
				day =Integer.parseInt(request.getParameter("day"));            //出席日
				period =request.getParameter("period");                        //内容
				
				AttendanceRecordsDAO attendanceRecordsDao = new AttendanceRecordsDAO();
				List<AttendanceRecords> attendanceDateList = attendanceRecordsDao.select(new AttendanceRecords
						(0,-1, classId, year, month,day,period,subjectId,"",""));
				System.out.println(attendanceDateList);
				if(!attendanceDateList.isEmpty()) {
					errorMessage="入力された日・時限は、既に登録されています。";
					request.setAttribute("add", add);
					request.setAttribute("errorMessage", errorMessage);
					System.out.println(1);
					RequestDispatcher dispatcher = request.getRequestDispatcher("ListStudentServlet");
					dispatcher.forward(request, response);
					return;
				}
				
				request.setAttribute("day", day);
				request.setAttribute("period", period);
			}else if(request.getParameter("add").equals("提出物追加")) {
				content =request.getParameter("content");                      //提出物内容
				request.setAttribute("content", content);
			}else{
				testType =request.getParameter("testType");                    //テスト種別
				request.setAttribute("testType", testType);
			}
		
			RequestDispatcher dispatcher = request.getRequestDispatcher("EditAllStudentServlet");
			//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/ListStudentServlet");
			dispatcher.forward(request, response);
			}catch (Exception e) {
				System.out.println(e);
				request.setAttribute("errorMessage", "エラーです。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit_student.jsp");
				dispatcher.forward(request, response);
			}

		}else if(request.getParameter("studentNum") ==null){
			// リクエストパラメータを取得する
			Date date=new Date(); 
			
			Calendar cal = Calendar.getInstance();
			int yearNow = cal.get(Calendar.YEAR); 
			request.setAttribute("yearNow", yearNow);
			

			int year = Integer.parseInt(request.getParameter("year"));          //年
			if(year < 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}

			int month = Integer.parseInt(request.getParameter("month"));          //月
			if(month < 0) {
				month = Calendar.getInstance().get(Calendar.MONTH);;
			}

			int grade = Integer.parseInt(request.getParameter("grade"));          //学年
			if(grade < 0) {
				grade = 1;
			}

			String className =(String)request.getAttribute("className");                   //クラス
			if(className == null) {
				className =(String)request.getParameter("className");                   //クラス
			}
			
			if(className == null || className.isEmpty()) {
				className = "1組";
			}

			String subjectName = request.getParameter("subjectName");              //教科
			if(subjectName == null || subjectName.isEmpty()) {
				subjectName = "現代文";
			}

			// 検索処理を行う
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


			List<Integer> studentIdList = new ArrayList<>();
			for(int i=0;studentList.size()>i;i++) {
				studentIdList.add(studentList.get(i).getStudentId());
			}
			int studentOne = studentIdList.get(0);

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

			ArrayList<Double> attendanceRate = new ArrayList<>();
			double attendanceSum = 0;
			double sum = 0;
			for(int i=0;studentIdList.size()>i;i++) { //表示する生徒の分繰り返す
				sum=0;
				attendanceSum=0;
				for(int j=0;attendanceList.size()>j;j++) {  //あるクラスの出席状況の文繰り返す
					if(studentIdList.get(i) ==  attendanceList.get(j).getStudentId()) {  //ある生徒の該当する出席状況の個所を割り出す
						String attendanceStatus = attendanceList.get(j).getStatus();
						if (attendanceStatus.equals("◯")||attendanceStatus.equals("公")) {
							attendanceSum+=1;
						}
						sum+=1;
					}
				}
				double attend = (Math.floor(attendanceSum * 100 / sum * 10)) / 10;
				attendanceRate.add(attend);
			}
			
			ArrayList<Double> submissionRate = new ArrayList<>();
			double submissionSum = 0;
			double sumS = 0;
			for(int k=0;studentIdList.size()>k;k++) { //表示する生徒の分繰り返す
				sumS=0;
				submissionSum=0;
				
				for(int l=0;assignmentsList.size()>l;l++) {  //あるクラスの出席状況の文繰り返す
					if(studentIdList.get(k) ==  assignmentsList.get(l).getStudentId()) {  //ある生徒の該当する出席状況の個所を割り出す
						String assignmentsSubmissionStatus = assignmentsList.get(l).getSubmissionStatus();
						if (assignmentsSubmissionStatus.equals("◯")) {
							submissionSum+=1;
						}
						sumS+=1;
					}
				}
				double sub = (Math.floor(submissionSum * 100 / sumS * 10)) / 10;
				submissionRate.add(sub);
				
			}
			
			request.setAttribute("attendanceList", attendanceList);
			request.setAttribute("assignmentsList", assignmentsList);
			request.setAttribute("gradesList", gradesList);

			request.setAttribute("attendanceDateList", attendanceDateList);
			request.setAttribute("attendanceDateSortList", attendanceDateSortList);
			request.setAttribute("contentList", contentList);
			request.setAttribute("testTypeList", testTypeList);
			request.setAttribute("testTypeListSize", testTypeListSize);
			
			request.setAttribute("attendanceRate", attendanceRate);
			request.setAttribute("submissionRate", submissionRate);

			request.setAttribute("grade", grade);
			request.setAttribute("className", className);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("subjectName", subjectName);
			request.setAttribute("classId", classId);
			request.setAttribute("subjectId", subjectId);

			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list_student.jsp");
			dispatcher.forward(request, response);

		}else if(request.getParameter("studentNum") != null){
			
			
			//個別閲覧へ

			//studentID subjectID year month
			int grade = Integer.parseInt(request.getParameter("grade")); 
			String className =request.getParameter("className");
			String name =request.getParameter("studentNum");

			//classIDを取得
			ClassRoomDAO classDao = new ClassRoomDAO();
			List<ClassRoom> classList = classDao.select(new ClassRoom(-1,grade,className));

			int classId = classList.get(0).getClassId();        //クラス
			request.setAttribute("classId", classId);

			//生徒情報を取得
			StudentsDAO studentDao = new StudentsDAO();
			List<Students> studentList = studentDao.select(new Students(0,0,grade,classId,0,name, "", "", "", ""));

			int studentId = studentList.get(0).getStudentId();
			request.setAttribute("studentId", studentId);


			String subjectName = request.getParameter("subjectName");
			SubjectDAO subjectDao = new SubjectDAO();
			List<Subject> subjectList = subjectDao.select(new Subject(-1,subjectName));
			
			int subjectId = subjectList.get(0).getSubjectId();
			request.setAttribute("subjectId", subjectId);

			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));

			request.setAttribute("year", year);
			request.setAttribute("month", month);

			RequestDispatcher dispatcher = request.getRequestDispatcher("InfoStudentServlet");
			//RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/InfoStudentServlet");
			dispatcher.forward(request, response);
		}

	}

}
