package test;
import java.util.Date;
import java.util.List;

import dao.AttendanceRecordsDAO;
import dto.AttendanceRecords;

public class AttendanceRecordsDAOTest {

	public static void main(String[] args) {
		AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();

//		System.out.println("--SELECT1--");
//		Date date = new Date();
//		AttendanceRecords ar = new AttendanceRecords(-1, 1, -1, -1, -1, -1, "", -1, "", "");
//		List<AttendanceRecords> arList = arDAO.select(ar);
//		Show(arList);
//
//		System.out.println("--SELECT2--");
//		List<AttendanceRecords> arList2 = arDAO.select(1);
//		Show(arList2);
//		
//		System.out.println("--SELECT3--");
//		List<AttendanceRecords> arList3 = arDAO.select(1, 1);
//		Show(arList3);
		
//		System.out.println("--SELECT4--");
//		List<AttendanceRecords> arList4 = arDAO.select_Fiscal(-1, 1, -1, -1);
//		Show(arList4);
		
		System.out.println("--COUNT--");
		int count = arDAO.count(1, 2025, 4, 1, 2025, 10, 14, null);
		System.out.println(count);

//		System.out.println("--INSERT--");
//		if (arDAO.insert(new AttendanceRecords(0, 1, 1, date, "", 1, "○", "test"))) {
//			System.out.println("INSERT CLEAR");
//		} else {
//			System.out.println("INSERT FAILED");
//		}
//
//		System.out.println("--UPDATE--");
//		if (arDAO.update(new AttendanceRecords(0, 1, 1, date, "", 1, "○", "test_update"))) {
//			System.out.println("UPDATE CLEAR");
//		} else {
//			System.out.println("UPDATE FAILED");
//		}
//		Select();
//		
//		System.out.println("--DELETE--");
//		AttendanceRecords ar2 = new AttendanceRecords(0, 1, 1, date, "", 1, "", "test");
//		if (arDAO.insert(ar2)) {
//			System.out.println("DELETE CLEAR");
//		} else {
//			System.out.println("DELETE FAILED");
//		}
//		Select();
	}

	public static void Select() {	
		AttendanceRecordsDAO arDAO = new AttendanceRecordsDAO();
		Date date = new Date();
		AttendanceRecords ar = new AttendanceRecords(0, 1, 1, date, "", 1, "○", "");
		List<AttendanceRecords> arList = arDAO.select(ar);
		for (int i = 0; i < arList.size(); i++) {
			System.out.println(arList.get(i).getRecordId() + " : "+ arList.get(i).getStudentId() + " " + arList.get(i).getStatus());
		}
	}
	
	public static void Show(List<AttendanceRecords> arList) {
		for (int i = 0; i < arList.size(); i++) {
			AttendanceRecords ar = arList.get(i);
			System.out.println(ar.getRecordId() + " 生徒ID: " + ar.getStudentId() + " クラスID: " + ar.getClassId() + 
					" 日付: " + ar.getDate() + " 時限: " + ar.getPeriod() + " 教科ID: " + ar.getSubjectId() + 
					" 状態: " + ar.getStatus() + " 備考: " + ar.getRemarks());
		}
	}
}
