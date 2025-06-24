package dto;

import java.io.Serializable;

public class Teacher implements Serializable {
	private int teacherId;		//教員ID
	private String name;		//教員氏名
	private String userId;      //ログインID
	private String password;	//パスワード 
	
	
	
	public Teacher(int teacherId, String name, String userId, String password) {
		this.teacherId = teacherId;
		this.name = name;
		this.userId = userId;
		this.password = password;
	}

	public Teacher(String userId, String password) {
		this.teacherId = -1;
		this.name = "";
		this.userId = userId;
		this.password = password;
	}
	
	public Teacher() {
		this.teacherId = -1;
		this.name = "";
		this.password = "";
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
