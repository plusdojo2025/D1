 // memo.js

window.addEventListener("DOMContentLoaded", function () {
    // すべての削除ボタンを取得（value="削除"）
    const deleteButtons = document.querySelectorAll('form button, form input[type="submit"]');

    deleteButtons.forEach(button => {
        // name="action" value="delete" のときだけ処理
        const form = button.closest("form");
        if (form && form.querySelector('input[name="action"][value="delete"]')) {
            button.addEventListener("click", function (event) {
                const confirmed = confirm("削除してよろしいですか？");
                if (!confirmed) {
                    event.preventDefault(); // 実行キャンセル
                }
            });
        }
    });
});