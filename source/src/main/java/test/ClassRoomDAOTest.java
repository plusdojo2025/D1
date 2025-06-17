package test;

import java.util.List;

import dao.ClassRoomDAO;
import dto.ClassRoom;

public class ClassRoomDAOTest {
	public static void showAllData(List<ClassRoom> crList) {
		for (ClassRoom cr : crList) {
			System.out.println("クラスID：" + cr.getClassId());
			System.out.println("学年：" + cr.getGrade());
			System.out.println("クラス名：" + cr.getClassName());			
		}
	}
	
	public static void main(String[] args) {
		ClassRoomDAO dao = new ClassRoomDAO();	
		
		// select()のテスト1
		System.out.println("---------- select()のテスト1 ----------");
		List<ClassRoom> crListSel1 = dao.select(new ClassRoom(1, 3, "1組"));
		ClassRoomDAOTest.showAllData(crListSel1);
		
		// insert()のテスト
		System.out.println("---------- insert()のテスト ----------");
		ClassRoom insRec = new ClassRoom(99, 25, "3組");
		if (dao.insert(insRec)) {
			System.out.println("登録成功！");
			List<ClassRoom> crListIns = dao.select(new ClassRoom(99, 25, ""));
			ClassRoomDAOTest.showAllData(crListIns);
		} else {
			System.out.println("登録失敗！");
		}
		// update()のテスト
		System.out.println("---------- update()のテスト ----------");
		List<ClassRoom> crListUp = dao.select(new ClassRoom(99, 25, "3組"));
		//変更箇所消すかもしれない
		if (crListUp != null && !crListUp.isEmpty()) {
		ClassRoom upRec = crListUp.get(0);
		upRec.setClassName("6組");
		
		if (dao.update(upRec)) {
			System.out.println("更新成功！");
			crListUp = dao.select(new ClassRoom(99, 25, "6組"));
			ClassRoomDAOTest.showAllData(crListUp);
		} else {
			System.out.println("更新失敗！");
		}
		
		} else {
		    System.out.println("更新対象のデータが見つかりませんでした。");
		}

		// delete()のテスト
		System.out.println("---------- delete()のテスト ----------");
		List<ClassRoom> crListDel = dao.select(new ClassRoom(99, 25, "6組"));
		ClassRoom delRec = crListDel.get(0);
		if (dao.delete(delRec)) {
			System.out.println("削除成功！");
			crListDel = dao.select(new ClassRoom(99, 25, "6組"));
			ClassRoomDAOTest.showAllData(crListDel);
		} else {
			System.out.println("削除失敗！");
		}
		}		
}
