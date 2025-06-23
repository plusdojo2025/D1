
document.addEventListener("DOMContentLoaded", () => {
  const classMap = {
    "1": {"1":13,"2":14,"3":15,"4":16,"5":17,"6":18},
    "2": {"1":7,"2":8,"3":9,"4":10,"5":11,"6":12},
    "3": {"1":1,"2":2,"3":3,"4":4,"5":5,"6":6}
  };

  const gradeSelect = document.getElementById('grade-select');
  const classSelect = document.getElementById('classId-select');
  const classIdInput = document.getElementById('classId');
  const formObj = document.getElementById('regist_form');
  const errorMessageObj = document.getElementById('error_message');

  function updateClassOptions(gradeValue, selectedClassNum = "") {
    classSelect.innerHTML = '<option value="">-- クラスを選択 --</option>';
    if (classMap[gradeValue]) {
      Object.entries(classMap[gradeValue]).forEach(([num, id]) => {
        const opt = document.createElement('option');
        opt.value = num;
        opt.text = num + "組";
        classSelect.appendChild(opt);
      });
    }
    classSelect.value = selectedClassNum;
    classIdInput.value = (classMap[gradeValue] && classMap[gradeValue][selectedClassNum]) ? classMap[gradeValue][selectedClassNum] : "";
  }

  // 学年は初期値「1年」だけセット、クラスは空（未選択）
  const initialGrade = gradeSelect.value || "1";
  gradeSelect.value = initialGrade;
  updateClassOptions(initialGrade, ""); // クラス未選択

  gradeSelect.addEventListener('change', () => {
    updateClassOptions(gradeSelect.value, "");
  });

  classSelect.addEventListener('change', () => {
    const g = gradeSelect.value;
    const c = classSelect.value;
    classIdInput.value = (classMap[g] && classMap[g][c]) ? classMap[g][c] : "";
  });

  formObj.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
      e.preventDefault();
      formObj.requestSubmit();
    }
  });

  formObj.addEventListener('submit', e => {
    e.preventDefault();
    const fullWidth = '　';
    const name = formObj.name.value.trim();
    const ruby = formObj.nameRuby.value.trim();
    let msg = '';

    if (!formObj.year.value) msg = '入学年を入力してください';
    else if (!formObj.grade.value) msg = '学年を選択してください';
    else if (!formObj.classId.value) msg = 'クラスを選択してください';
    else if (!formObj.studentNum.value) msg = '出席番号を入力してください';
    else if (!formObj.name.value) msg = '氏名を入力してください';
    else if (!name.includes(fullWidth)) msg = ' 姓 と 名 の間に全角スペースを入力してください';
    else if (!formObj.nameRuby.value) msg = 'ふりがなを入力してください';
    else if (!ruby.includes(fullWidth)) msg = ' せい と めい の間に全角スペースを入力してください';

    if (msg) {
      errorMessageObj.textContent = msg;
      return;
    }

    formObj.submit();
  });
});