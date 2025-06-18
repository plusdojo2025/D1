 // login.js

window.addEventListener("DOMContentLoaded", () => {
  const form      = document.querySelector("form");
  const idField   = document.querySelector("input[name='teacherId']");
  const passField = document.querySelector("input[name='password']");
  const errorBox  = document.getElementById("errorBox");

  // 入力未入力チェック
  form.addEventListener("submit", e => {
    if (!idField.value.trim() || !passField.value.trim()) {
      e.preventDefault();
      showError("IDまたはパスワードが入力されていません。");
    }
  });

  // サーバーからの認証エラー表示（?error=1 の場合）
  const params = new URLSearchParams(location.search);
  if (params.get("error") === "1") {
    showError("IDまたはパスワードが間違っています。");
  }

  function showError(msg) {
    if (errorBox) {
      errorBox.textContent = msg;
      errorBox.style.color = "red";
    } else {
      alert(msg);
    }
  }
});