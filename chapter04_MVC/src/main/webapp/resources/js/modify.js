// css 파일 추가 > 계속 반복되니까 유틸 js를 만들어서 함수로 사용
// 1. 파일 경로 설정
const CSS_FILE_PATH = "/resources/css/get.css";
// 2. link 태그 생성 및 속성 추가
let linkEle = document.createElement('link');
linkEle.rel = 'stylesheet';
linkEle.type = 'text/css';
linkEle.href = CSS_FILE_PATH;
// 3. head 태그에 link 엘리먼트 추가
document.head.appendChild(linkEle);

let f = document.forms[0];
// 버튼 이벤트 추가
document.querySelectorAll('.panel-body-btns button').forEach(btn => {
    btn.addEventListener('click', (e) => {
        let me = e.currentTarget;
        if (me.id == 'modifyBtn') {
            modify();
        }
        else if (me.id == 'indexBtn') {
        	const {pageNum, amount} = getStorageData();
        	const sendData = `pageNum=${pageNum}&amount=${amount}`;
            location.href = '/board/list';
        }
        else if (me.id == 'removeBtn') {
            remove();
        }
    })
});
function modify() {
    if (!f.title.value || !f.content.value) {
        alert("모든 값을 입력해주세요");
        return;
    }
    f.action = '/board/modify';
    f.submit();
}
function remove() {
    if (!confirm("삭제하시겠습니까?")) {
        return;
    }
    f.action = '/board/remove';
    f.submit();
}
(function(){
	fetch(`/board/getAttachList/${f.bno.value}`)
	.then(response => response.json())
	.then(data =>{
		showUploadedFile(data);
	})
	.catch(err => console.log(err));
})();

//업로드 완료된 목록 보여주는 함수
let uploadResult = document.querySelector('.uploadResult ul')
function showUploadedFile(uploadResultArr){
	let str = ``;
	uploadResultArr.forEach( file =>{
		let fileCallPath = encodeURIComponent(
					file.uploadPath + "/"+
					file.uuid + "_" +
					file.fileName
		);
		console.log(fileCallPath)
		str += `<li path="${file.uploadPath}" uuid="${file.uuid}" fileName="${file.fileName}">`;
//		str += `<a href="/download?fileName=${fileCallPath}">`;
		str += `${file.fileName}`;
//		str += `</a>`;
		str += `<span data-file="${fileCallPath}">X</span>`;
		str += `</li>`;
	});
	uploadResult.innerHTML = str;
}
uploadResult.addEventListener('click', e=>{
	
	if(e.target.tagName === "SPAN"){ //e.target.tagName 시, 태그 내용 대문자 변환됨
		let targetFile = e.target.getAttribute(`data-file`);
	
		
	fetch(`/deleteFile`,
			{
				method : 'post',
				body : targetFile,
				headers : {
					'Content-Type' : 'text/plain'
				}
			})
			.then(response => response.text())
			.then(data => {
				console.log(data);
				
				//해당 태그 삭제
				let targetLi = e.target.closest('li');
				targetLi.remove();
				
				
			})
			.catch(err => console.log(err));
	}
	
})	
	