function validateForm() {
  const year = document.getElementById('year');
  const semester = document.getElementById('semester');
  const type = document.querySelector('input[name="type"]:checked');
  const day = document.getElementById('day_of_week');
  const period = document.getElementById('period');
  const classId = document.getElementById('classId');
  const content = document.getElementById('content');
  
  const errorMessage = document.getElementById('jsErrorMessage');

  if (!year.value || !semester.value || !type || !day.value || !period.value || !classId.value || content.value.trim() === '') {
    errorMessage.textContent = '必須項目を入力してください';
    errorMessage.style.display = 'block';

    // スクロールしてエラー位置に移動
    errorMessage.scrollIntoView({ behavior: 'smooth', block: 'center' });

    return false;
  } else {
    errorMessage.textContent = '';
    errorMessage.style.display = 'none';
    return true;
  }
}
