package test;
import java.util.Date;
import java.util.List;

import dao.AttendanceRecordsDAO;
import dto.AttendanceRecords;

public class AttendanceRecordsDAOTest {

	public static void main(String[] args) {
		AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
		
		System.out.println("--SELECT1--");
		Date date = new Date();
		AttendanceRecords ar = new AttendanceRecords(0, 1, 1, date, "", 1, "○", "");
		List<AttendanceRecords> arList = arDAO.select(ar);
		for (int i = 0; i < arList.size(); i++) {
			System.out.println(arList.get(i).getRecordId() + " : " + arList.get(i).getStatus());
		}
		
		System.out.println("--SELECT2--");
		List<AttendanceRecords> arList2 = arDAO.select(1);
		for (int i = 0; i < arList2.size(); i++) {
			System.out.println(arList2.get(i).getRecordId() + " : " + arList2.get(i).getStatus());
		}
	}

}
