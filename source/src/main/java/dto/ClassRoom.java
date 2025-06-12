package dto;

public class ClassRoom {
	private int classId;                 /*クラスID*/
	private int grade;                   /*学年*/       
	private String className;            /*クラス名*/
	
	
	public ClassRoom(int classId, int grade, String className) {
		this.classId = classId;
		this.grade = grade;
		this.className = className;
		
		
	}
	
	public ClassRoom() {
		this.classId = -1;
		this.grade = -1;
		this.className = "";
		
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
	

}