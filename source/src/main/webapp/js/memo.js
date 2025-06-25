 // memo.js

window.addEventListener("DOMContentLoaded", function () {
  let pendingForm = null;

  function showModal(type, content) {
    const modal = document.getElementById("confirm-modal");
    const messageBox = document.getElementById("confirm-message");
    const contentBox = document.getElementById("confirm-content");

    if (type === "update") {
      messageBox.textContent = "以下の入力した内容で更新してもよろしいでしょうか？";
      contentBox.textContent = "・" + content;
    } else if (type === "delete") {
      messageBox.textContent = "本当に削除してよろしいですか？";
      contentBox.textContent = "";
    }

    modal.dataset.type = type;
    modal.style.display = "block";
  }

  document.getElementById("confirm-yes").addEventListener("click", function () {
    if (pendingForm) {
      pendingForm.submit();
    }
  });

  document.getElementById("confirm-no").addEventListener("click", function () {
    document.getElementById("confirm-modal").style.display = "none";
    pendingForm = null;
  });

  // 更新ボタン（form の textarea の内容を取得）
  const updateButtons = document.querySelectorAll('button[name="action"][value="update"]');
  updateButtons.forEach(button => {
    button.addEventListener("click", function (event) {
      event.preventDefault();
      pendingForm = button.closest("form");
      const content = pendingForm.querySelector("textarea[name='content']");
      showModal("update", content ? content.value.trim() : "");
    });
  });

  // 削除ボタン
  const deleteButtons = document.querySelectorAll('button[name="action"][value="delete"], input[name="action"][value="delete"]');
  deleteButtons.forEach(button => {
    button.addEventListener("click", function (event) {
      event.preventDefault();
      pendingForm = button.closest("form");
      showModal("delete");
    });
  });
});