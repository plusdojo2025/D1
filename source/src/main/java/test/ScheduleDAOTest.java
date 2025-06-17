package test;
import java.time.LocalDateTime;
import java.util.List;

import dao.ScheduleDAO;
import dto.Schedule;

public class ScheduleDAOTest {
	public static void showAllData(List<Schedule> list) {
	    if (list == null || list.isEmpty()) {
	        System.out.println("データはありません。");
	        return;
	    }
	    for (Schedule s : list) {
	    	
	    	System.out.println(
	    		    s.getScheduleId() + "\t" +
	    		    s.getTeacherId() + "\t" +
	    		    s.getClassId() + "\t" +
	    		    s.getDate() + "\t" +
	    		    s.getPeriod() + "\t" +
	    		    s.getContent() + "\t" +
	    		    s.getType() + "\t" +
	    		    s.getYear() + "\t" +
	    		    s.getSemester() + "\t" +
	    		    s.getMemo() + "\t" +
	    		    s.getDay_of_week()
	    		);
	    }
	}

	public static void main(String[] args) {
		ScheduleDAO dao = new ScheduleDAO();
		LocalDateTime dummyDate = LocalDateTime.of(2025, 1, 1, 0, 0);
		LocalDateTime now = LocalDateTime.now();
		// select()のテスト1
		System.out.println("---------- select()のテスト1 ----------");
		List<Schedule> scheduleListSel1 = dao.select(new Schedule(-1,-1,-1,null,"","","",-1, "","",""));
		ScheduleDAOTest.showAllData(scheduleListSel1);

//		// select()のテスト2
//		System.out.println("---------- select()のテスト2 ----------");
//		List<Schedule> scheduleListSel2 = dao.select(new Schedule(0,"","","","","","","","","",""));
//		ScheduleDAOTest.showAllData(scheduleListSel2);
//
		// insert()のテスト
		System.out.println("---------- insert()のテスト ----------");
		Schedule insRec = new Schedule(0,4,2,now,"7限","インサートテスト","class",2025, "前期","メモテスト","月曜日");
		if (dao.insert(insRec)) {
			System.out.println("登録成功！");
			List<Schedule> scheduleListIns = dao.select(new Schedule(-1,-1,-1,null,"","","",-1, "","",""));
			ScheduleDAOTest.showAllData(scheduleListIns);
		} else {
			System.out.println("登録失敗！");
		}
//
//		// update()のテスト
		System.out.println("---------- update()のテスト ----------");

		List<Schedule> scheduleListUp = dao.select(new Schedule(11,-1,-1,null,"","","",-1,"","",""));

		if (!scheduleListUp.isEmpty()) {
		    Schedule upRec = scheduleListUp.get(0);
		    upRec.setContent("更新テストです");  // ← content を更新
		    if (dao.update(upRec)) {
		        System.out.println("更新成功！");
		        List<Schedule> updatedList = dao.select(new Schedule(-1,-1,-1,null,"","","",-1, "","","")); // 全件表示
		        ScheduleDAOTest.showAllData(updatedList);
		    } else {
		        System.out.println("更新失敗！");
		    }
		} else {
		    System.out.println("対象データが見つかりません。");
		}
//
//		// delete()のテスト
		System.out.println("---------- delete()のテスト ----------");
		List<Schedule> scheduleListDel = dao.select(new Schedule(11,-1,-1,null,"","","",-1, "","",""));
		Schedule delRec = scheduleListDel.get(0);
		if (dao.delete(delRec)) {
			System.out.println("削除成功！");
			scheduleListDel = dao.select(new Schedule(-1,-1,-1,null,"","","",-1, "","",""));
			ScheduleDAOTest.showAllData(scheduleListDel);
		} else {
			System.out.println("削除失敗！");
		}
	}

}
