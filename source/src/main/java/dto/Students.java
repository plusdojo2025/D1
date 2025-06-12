package dto;

public class Students {
	private int studentId;                     /*学生ID*/
	private int grade;                         /*学年*/       
	private int classId;                       /*クラスID*/
	private int studentNum;                    /*出席番号*/
	private String name;                       /*氏名*/
	private String nameRuby;                   /*氏名ふりがな*/
	private String enrollmentStatus;           /*在籍状態*/
	private String extracurricularActivities;  /*授業外活動*/ 
	private String attitude;                   /*授業態度*/

	public Students(int studentId, int grade, int classId, int studentNum, 
			String name, String nameRuby, String enrollmentStatus, String extracurricularActivities, String attitude) {
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
	
	public Students() {
		this.studentId = -1;
		this.grade = -1;
		this.classId = -1;
		this.studentNum = -1;
		this.name = "";
		this.nameRuby = "";
		this.enrollmentStatus = "";
        this.extracurricularActivities = "";
        this.attitude = "";
	}

    public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameRuby() {
		return nameRuby;
	}

	public void setNameRuby(String nameRuby) {
		this.nameRuby = nameRuby;
	}

	public String getEnrollmentStatus() {
		return enrollmentStatus;
	}

	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	public String getExtracurricularActivities() {
		return extracurricularActivities;
	}

	public void setExtracurricularActivities(String extracurricularActivities) {
		this.extracurricularActivities = extracurricularActivities;
	}

	public String getAttitude() {
		return attitude;
	}

	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
}



