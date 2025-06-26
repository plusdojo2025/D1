 document.addEventListener("DOMContentLoaded", () => {

  // ① 更新ボタン（新規 or 編集）
  const updateForm = document.querySelector('form[action="MemoServlet"]');
  if (updateForm) {
    updateForm.addEventListener("submit", (e) => {
      const memo1 = updateForm.querySelector('input[name="memo1"]').value.trim();
      const memo2 = updateForm.querySelector('input[name="memo2"]').value.trim();
      const memo3 = updateForm.querySelector('input[name="memo3"]').value.trim();
      const content = updateForm.querySelector('input[name="content"]')?.value.trim();

      const text = content || [memo1, memo2, memo3].filter(Boolean).join("\n");

      const confirmed = window.confirm(`以下の入力内容で更新してもよろしいですか？\n\n・${text}`);
      if (!confirmed) e.preventDefault();
    });
  }

  // ② 削除ボタン
  const deleteForms = document.querySelectorAll('form[action="MemoServlet"] input[name="action"][value="delete"]');
  deleteForms.forEach(input => {
    input.closest('form').addEventListener("submit", (e) => {
      const confirmed = window.confirm("本当に削除してよろしいですか？");
      if (!confirmed) e.preventDefault();
    });
  });

});