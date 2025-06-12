package dto;

public class ClassRoom {
	private int classId;                 /*クラスID*/
	private int grade;                   /*学年*/       
	private String className;            /*クラス名*/
	private int teacherId;               /*教員ID*/
	
	public ClassRoom(int classId, int grade, String className, int teacherId) {
		this.classId = classId;
		this.grade = grade;
		this.className = className;
		this.teacherId = teacherId;
		
	}
	
	public ClassRoom() {
		this.classId = -1;
		this.grade = -1;
		this.className = "";
		this.teacherId = -1;
	}

    public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public int getTeacherId() {
		return teacherId;
	}
	
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
		
	}

}