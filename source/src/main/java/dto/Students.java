package dto;

public class Students {
	private int studentId;                 /*学生ID*/
	private int grade;             /*学年*/       
	private int classId;          /*クラスID*/
	private int studentNum;            /*出席番号*/
	private String name;                /*氏名*/
	private String nameRuby;             /*氏名ふりがな*/
	private String enrollmentStatus;             /*在籍状態*/
	private String extracurricularActivities;               /*授業外活動*/ 
	private String attitude;                 /*授業態度*/

	public Students(int studentId, int grade, int classId, int studentNum, String name, String nameRuby, String enrollmentStatus, String extracurricularActivities, String attitude) {
		this.studentId = studentId;
		this.grade = grade;
		this.classId = classId;
		this.studentNum = studentNum;
		this.name = name;
		this.nameRuby = nameRuby;
		this.enrollmentStatus = enrollmentStatus;
		this.extracurricularActivities = extracurricularActivities;
        this.attitude = attitude;
	}
	

}
