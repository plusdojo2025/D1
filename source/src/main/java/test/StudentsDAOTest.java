package test;

import java.util.List;

import dao.StudentsDAO;
import dto.Students;

public class StudentsDAOTest {
	public static void showAllData(List<Students> stList) {
//		if (stList == null || stList.isEmpty()) {
//			System.out.println("データが見つかりませんでした。");
//			return;		
//		}
		
		
		
		
		
		for (Students st : stList) {
			System.out.println("学生ID：" + st.getStudentId());
			System.out.println("学年：" + st.getGrade());
			System.out.println("クラスID：" + st.getClassId());
            System.out.println("出席番号：" + st.getStudentNum());
            System.out.println("氏名：" + st.getName());
            System.out.println("氏名ふりがな：" + st.getNameRuby());
            System.out.println("在籍状態：" + st.getEnrollmentStatus());
            System.out.println("授業外活動：" + st.getExtracurricularActivities());
            System.out.println("授業態度：" + st.getAttitude());
			System.out.println();
		}
	}

	public static void main(String[] args) {
		StudentsDAO dao = new StudentsDAO();

		// select()のテスト1
		System.out.println("---------- select()のテスト1 ----------");
		List<Students> stListSel1 = dao.select(new Students(-1, -1, -1, -1, "栃木", "", "", "", ""));
		StudentsDAOTest.showAllData(stListSel1);

		// select()のテスト2
		//System.out.println("---------- select()のテスト2 ----------");
		//List<Students> stListSel2 = dao.select(new Students(  , 3, 1,  , "", "", "", "", ""));
		//StudentsDAOTest.showAllData(stListSel2);

		// select()のテスト3
		//System.out.println("---------- select()のテスト3 ----------");
		//List<Students> stListSel3 = dao.select(new Students("", "2", "3", "", "", "", "", "", ""));
		//StudentsDAOTest.showAllData(stListSel3);

		// select()のテスト4
		//System.out.println("---------- select()のテスト4 ----------");
		//List<Students> stListSel4 = dao.select(new Students("", "", "", "30", "", "", "", "", ""));
		//StudentsDAOTest.showAllData(stListSel4);
		
		// insert()のテスト
		System.out.println("---------- insert()のテスト ----------");
		Students insRec = new Students(70, 1, 6, 5, "栃木　三郎", "とちき　さぶろう", "", "", "");
		if (dao.insert(insRec)) {
			System.out.println("登録成功！");
			List<Students> stListIns = dao.select(new Students(70, 1, 6, 5, "栃木　三郎", "とちき　さぶろう", "", "", ""));
			StudentsDAOTest.showAllData(stListIns);
		} else {
			System.out.println("登録失敗！");
		}
	

		// update()のテスト
		System.out.println("---------- update()のテスト ----------");
		List<Students> stListUp = dao.select(new Students(70, 1, 6, 4, "栃木　三郎", "とちき　さぶろう", "", "", ""));
		//変更箇所消すかもしれない
		if (stListUp != null && !stListUp.isEmpty()) {
		Students upRec = stListUp.get(0);
		upRec.setName("日光　五郎");
		
		if (dao.update(upRec)) {
			System.out.println("更新成功！");
			stListUp = dao.select(new Students(70, 1, 6, 4, "日光　五郎", "にっこう　ごろう", "", "", ""));
			StudentsDAOTest.showAllData(stListUp);
		} else {
			System.out.println("更新失敗！");
		}
		
		} else {
		    System.out.println("更新対象のデータが見つかりませんでした。");
		}

		// delete()のテスト
		System.out.println("---------- delete()のテスト ----------");
		List<Students> stListDel = dao.select(new Students(55, 0, 0, 0, "", "", "", "", ""));
		Students delRec = stListDel.get(0);
		if (dao.delete(delRec)) {
			System.out.println("削除成功！");
			stListDel = dao.select(new Students(55, 0, 0, 0, "", "", "", "", ""));
			StudentsDAOTest.showAllData(stListDel);
		} else {
			System.out.println("削除失敗！");
		}
		}
	
}		


