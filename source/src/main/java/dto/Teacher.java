package dto;

import java.io.Serializable;

public class Teacher implements Serializable {
	private int teacherId;		//教員ID
	private String name;		//教員氏名
	private String password;	//パスワード 
	
	public Teacher() {}
	
	public Teacher(int teacherId, String name, String password) {
	
		this.teacherId = teacherId;
		this.name = name;
		this.password = password;
	}

	public int getTeacherId() {
		return teacherId;
	}
	
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
	public String getName() {
		return name;
	}
	
	public void  setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void  setPassword(String password) {
		this.password = password;
	}
}
