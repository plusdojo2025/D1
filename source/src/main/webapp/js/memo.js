 // memo.js などで読み込んでください
document.addEventListener("DOMContentLoaded", () => {
  /* =================================================
     ① 更新ボタン（新規登録／編集）用の確認ダイアログ
     ------------------------------------------------- */
  // 「action=update」を持つ hidden がある form を 1 つ取得
  const updateForm = document.querySelector(
    'form[action="MemoServlet"] input[name="action"][value="update"]'
  )?.closest("form");

  if (updateForm) {
    updateForm.addEventListener("submit", (e) => {
      // 各入力を取得（存在しない場合は空文字）
      const memo1   = updateForm.querySelector('[name="memo1"]')?.value.trim()   || "";
      const memo2   = updateForm.querySelector('[name="memo2"]')?.value.trim()   || "";
      const memo3   = updateForm.querySelector('[name="memo3"]')?.value.trim()   || "";
      const content = updateForm.querySelector('[name="content"]')?.value.trim() || "";

      // プレビュー用テキスト（空を除外し改行結合）
      const preview = [content, memo1, memo2, memo3].filter(Boolean).join("\n");

      // 確認ダイアログ
      if (!window.confirm(`以下の内容で更新しますか？\n\n${preview}`)) {
        e.preventDefault(); // 送信キャンセル
      }
    });
  }

  /* =================================================
     ② 削除ボタン用の確認ダイアログ
     ------------------------------------------------- */
  // 「action=delete」を持つ hidden 要素をすべて取得
  document
    .querySelectorAll('form[action="MemoServlet"] input[name="action"][value="delete"]')
    .forEach((hidden) => {
      const delForm = hidden.closest("form");
      if (delForm) {
        delForm.addEventListener("submit", (e) => {
          if (!window.confirm("本当に削除してよろしいですか？")) {
            e.preventDefault(); // 削除キャンセル
          }
        });
      }
    });
});