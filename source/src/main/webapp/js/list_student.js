'use strict'
// チェックボックスのエレメントを取得
let attendanceCheck = document.getElementById('attendanceCheck');
let submissionCheck = document.getElementById('submissionCheck');
let gradesCheck = document.getElementById('gradesCheck');

//　表示・非表示を切り替えたいエレメントを取得
let attendance = document.getElementById('attendance'); // 出欠
let submission = document.getElementById('submission'); // 提出物
let grades = document.getElementById('grades'); // 成績

// 値の変更を捕捉
attendanceCheck.addEventListener('change', {check: attendanceCheck, name: attendance, handleEvent: changeDisplay});
submissionCheck.addEventListener('change', {check: submissionCheck, name: submission, handleEvent: changeDisplay});
gradesCheck.addEventListener('change', {check: gradesCheck, name: grades, handleEvent: changeDisplay});
// addEventListenerで変更を検知。変更したタイミングでchangeDisplayを呼出し（handleEvent: の後ろに関数名）
// checkでチェックボックスのエレメントを、nameで表示切替したい項目の名前を渡すと、関数側で「this.check」「this.name」で取得できる

//　変更時の処理
function changeDisplay(event) {
	let element = attendance;
	// 名前に応じてエレメントを設定
	switch (this.name) {
		case attendance:
		element = attendance;
		break;
		case submission:
		element = submission;
		break;
		case grades:
		element = grades;
		break;
	}
	
	if (this.check.checked) {
		// 表示
		element.style.display = 'block';
	} else {
		// 非表示
		element.style.display = 'none';
	}
};


















	//ここから追加 csv形式でのダウンロード
	document.addEventListener('DOMContentLoaded', function() {
		document.getElementById('downloadBtn').addEventListener('click', function() {
			console.log("ボタンがクリックされました");
	    });
	});    


	//セルのテキストをエスケープする関数
	function escapeCsvValue(value) {
		if (value.includes('"')) {
			value = value.replace(/"/g, '""'); //ダブルクォーテーションを2重にする
		}
		if (value.includes(',') || value.includes('\n')) {
			value = `"${value}"`; //カンマや改行が含まれている場合、値をダブルクォーテーションで囲む
		}
		return value;
	}
	
	





	document.getElementById('downloadBtn').addEventListener('click', function() {
    let textArray = [];

    // (例)1年 1組 2025年 6月 現代文 のタイトルを追加
    let title = `${document.querySelector('[name="grade"]').value}年 ${document.querySelector('[name="className"]').value} ${document.querySelector('[name="year"]').value}年 ${document.querySelector('[name="month"]').value}月 ${document.querySelector('[name="subjectName"]').value}`;
    textArray.push([title]);
    
    //タイトル行の下に1行空行を追加
    textArray.push([]); //空行

    // 出席状況セクション
    textArray.push(['出席状況']); // セクション名
    document.querySelectorAll('#attendance table').forEach(function(table) {
        table.querySelectorAll('tr').forEach(function(row) {
            let rowData = [];
            row.querySelectorAll('td, th').forEach(function(cell) {
                // <input type="submit">タグのvalue属性を取得して氏名を追加
                if (cell.querySelector('input[type="submit"]')) {
                    rowData.push(cell.querySelector('input[type="submit"]').value);
                } else {
                    rowData.push(cell.innerText.trim());   // 通常のセル内容を追加
                }
            });
            textArray.push(rowData);  // 行ごとに配列として追加
        });
    });


	//セクションの下に1行空行を追加
    textArray.push([]); //空行
    
    // 提出物状況セクション
    textArray.push(['提出物状況']); // セクション名
    document.querySelectorAll('#submission table').forEach(function(table) {
        table.querySelectorAll('tr').forEach(function(row) {
            let rowData = [];
            row.querySelectorAll('td, th').forEach(function(cell) {
                if (cell.querySelector('input[type="submit"]')) {
                    rowData.push(cell.querySelector('input[type="submit"]').value);
                } else {
                    rowData.push(cell.innerText.trim()); // 通常のセル内容を追加
                }
            });
            textArray.push(rowData); // 行ごとに配列として追加
        });
    });
    
	//セクションの下に1行空行を追加
    textArray.push([]); //空行    

    // 成績状況セクション
    textArray.push(['成績状況']); // セクション名
    document.querySelectorAll('#grades table').forEach(function(table) {
        table.querySelectorAll('tr').forEach(function(row) {
            let rowData = [];
            row.querySelectorAll('td, th').forEach(function(cell) {
                if (cell.querySelector('input[type="submit"]')) {
                    rowData.push(cell.querySelector('input[type="submit"]').value);
                } else {
                    rowData.push(cell.innerText.trim()); // 通常のセル内容を追加
                }
            });
            textArray.push(rowData); // 行ごとに配列として追加
        });
    });

    // CSVのBOM(Byte Order Mark)を追加してUTF-8エンコーディングで保存
    let bom = '\uFEFF';   // BOMを示す文字
    let csvContent = bom;
    textArray.forEach(function(rowArray){
	
		//行ごとにエスケープ処理を行う
		let escapedRow = rowArray.map(function(cell) {
			return escapeCsvValue(cell); //各セルの値をエスケープする
		}).join(","); //カンマ区切りで行を作成
		csvContent += escapedRow + "\n"; //各行を追加	
		
    });
    
    // Blobを使ってCSVファイルを作成
    let blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    
    // CSVファイルをダウンロードするリンクを作成
    let link = document.createElement("a");
    link.setAttribute("href", URL.createObjectURL(blob));
    link.setAttribute("download", "output.csv");
    
    // ダウンロードリンクをクリック
    document.body.appendChild(link);    // 一時的にリンクをDOMに追加
    link.click();                       // リンクをタウンロードしてダウンロード
    document.body.removeChild(link);   // ダウンロード後、リンクをDOMから削除
    
});



	
	document.getElementById('list_student_form').onsubmit = function (event) {
    	const searchDay = document.getElementById('list_student_form').day.value;
        const searchPeriod = document.getElementById('list_student_form').period.value;
        const searchContent = document.getElementById('list_student_form').content.value;
        const searchTestType = document.getElementById('list_student_form').testType.value;

		if (key == 'attendanceButton') {
            if (searchDay == '出席日') {
                event.preventDefault();
                window.alert('出席日を指定してください。');
            }

            else if (searchPeriod == '時限') {
                event.preventDefault();
                window.alert('時限を指定してください。');
            }
            
            else if (searchDay !== '出席日' && searchPeriod !== '時限') {

            	if (!window.confirm('出欠を登録します。よろしいですか？')) {
                    return false;
                }
                errorMessageObj.textContent = null;

			}
		}

		if (key == 'submissionButton') {
            if (searchContent === '' || searchContent === null) {
                event.preventDefault();
                window.alert('課題内容を指定してください。');
            }
            
            else if (searchContent !== '' && searchContent !== null) {

            	if (!window.confirm('課題内容を登録します。よろしいですか？')) {
                    return false;
                }
                errorMessageObj.textContent = null;
            }
         }
            
            
        if (key == 'gradesButton') {
            if (searchTestType === '' || searchTestType === null) {
                event.preventDefault();
                window.alert('テスト名を指定してください。');
            }
            
            else if (searchTestType !== '' && searchTestType !== null) {

            	if (!window.confirm('テスト名を登録します。よろしいですか？')) {
                    return false;
                }
                errorMessageObj.textContent = null;
            }
		}
          key=null;
    };