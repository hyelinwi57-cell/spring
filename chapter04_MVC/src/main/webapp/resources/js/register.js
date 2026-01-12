// css 파일 추가
// 1. 파일 경로 설정
const CSS_FILE_PATH = "/resources/css/register.css";
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
        if (me.id == 'registerBtn') {
            register();
        }
        else if (me.id == 'resetBtn') {
            f.reset();
        }
        else if (me.id == 'indexBtn') {
        	const {pageNum, amount} = getStorageData();
        	const sendData = `pageNum=${pageNum}&amount=${amount}`;
            location.href = '/board/list' + sendData;
        }
    })
});

function register() {
    // 검증
    let { title, writer, content } = f;
    if (!title.value || !writer.value || !content.value) {
        alert("모든 값을 입력해주세요");
        return;
    }
    
    //데이터 담기
    let str = ``;
    let liElements = document.querySelectorAll(`.uploadResult ul li`);
    liElements.forEach((li, index) =>{ //for문 도는 index
    	let path = li.getAttribute('path');
    	let uuid = li.getAttribute('uuid');
    	let fileName = li.getAttribute('fileName');
    	
    	str += `<input type="hidden" name="attachList[${index}].uploadPath" value="${path}"/>`;
    	str += `<input type="hidden" name="attachList[${index}].uuid" value="${uuid}"/>`;
    	str += `<input type="hidden" name="attachList[${index}].fileName" value="${fileName}"/>`;
    	
    });
    
    //f.innerHTML += str;
    f.insertAdjacentHTML('beforeend', str);

    // 폼 내용을 post로 전송
    f.action = '/board/register';
    f.submit();
}
