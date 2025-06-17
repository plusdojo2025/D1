package test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import dao.MemoDAO;
import dto.Memo;

public class MemoDAOTest {

    // 一覧表示用メソッド
    public static void showAllData(List<Memo> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("データはありません。");
            return;
        }
        for (Memo m : list) {
            System.out.println(
                m.getMemoId() + "\t" +
                m.getTeacherId() + "\t" +
                m.getClassId() + "\t" +
                m.getContent() + "\t" +
                m.getDate() + "\t" +
                m.getPeriod()
            );
        }
    }

    public static void main(String[] args) {
        MemoDAO dao = new MemoDAO();

        // SELECT ALL テスト
        System.out.println("---------- selectAll() のテスト ----------");
        List<Memo> memoListAll = dao.selectAll();
        showAllData(memoListAll);

        // INSERT テスト
        System.out.println("---------- insert() のテスト ----------");
        Memo insertMemo = new Memo(0, 2, 3, "新しいメモテスト", Date.valueOf(LocalDate.now()), "3限");
        if (dao.insert(insertMemo)) {
            System.out.println("登録成功！");
            showAllData(dao.selectAll());
        } else {
            System.out.println("登録失敗！");
        }

        // SELECT 条件付き（content部分一致）
        System.out.println("---------- select()（部分一致） のテスト ----------");
        Memo searchMemo = new Memo(0, 0, 0, "メモ", null, null);
        List<Memo> searchResults = dao.select(searchMemo);
        showAllData(searchResults);

        // UPDATE テスト
        System.out.println("---------- update() のテスト ----------");
        if (!searchResults.isEmpty()) {
            Memo target = searchResults.get(0);
            target.setContent("更新されたメモ");
            target.setPeriod("5限");
            if (dao.update(target)) {
                System.out.println("更新成功！");
                showAllData(dao.selectAll());
            } else {
                System.out.println("更新失敗！");
            }
        } else {
            System.out.println("更新対象が見つかりません。");
        }

        // DELETE テスト
        System.out.println("---------- delete() のテスト ----------");
        if (!searchResults.isEmpty()) {
            Memo target = searchResults.get(0);
            if (dao.delete(target.getMemoId())) {
                System.out.println("削除成功！");
                showAllData(dao.selectAll());
            } else {
                System.out.println("削除失敗！");
            }
        } else {
            System.out.println("削除対象が見つかりません。");
        }
    }
}
