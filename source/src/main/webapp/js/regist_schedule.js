function validateForm() {
    const form = document.forms["scheduleForm"];
    const year = form["year"].value.trim();
    const semester = form["semester"].value.trim();
    const type = form["type"].value;
    const day = form["day_of_week"].value;
    const period = form["period"].value;
    const content = form["content"].value.trim();

    if (!year || !semester || !type || !day || !period || !content) {
        const msgEl = document.getElementById("jsErrorMessage");
        msgEl.textContent = "必須項目を入力してください";
        msgEl.style.display = "block";
        return false;
    }

    // 入力が正しい場合は青エラーを非表示
    document.getElementById("jsErrorMessage").style.display = "none";
    return true;
}
