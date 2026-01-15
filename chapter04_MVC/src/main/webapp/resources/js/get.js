// css 파일 추가
// 1. 파일 경로 설정
const CSS_FILE_PATH = [
	'/resources/css/get.css',
	'/resources/css/modal.css'
	];
cssBinding(CSS_FILE_PATH);
function cssBinding(cssFiles){
	cssFiles.forEach(css => {
		let linkEle = document.createElement('link');
		linkEle.rel = 'stylesheet';
		linkEle.type = 'text/css';
		linkEle.href = css;
// 3. head 태그에 link 엘리먼트 추가
		document.head.appendChild(linkEle);
	});
}

let f = document.forms[0];
// 버튼 이벤트 추가
document.querySelectorAll('.panel-body-btns button, #replyBtn, #addReplyBtn, #modifyReplyBtn, #removeReplyBtn').forEach(btn => {
    btn.addEventListener('click', (e) => {
    	let me = e.currentTarget;
        if (me.id == 'modifyBtn') {
            modify();
        }else if (me.id == 'indexBtn') {
        	const {pageNum, amount} = getStorageData();
        	const sendData = `pageNum=${pageNum}&amount=${amount}`;
            location.href = '/board/list';
        }else if(me.id == 'replyBtn'){
        	registerModalPage();
        }else if(me.id == 'addReplyBtn'){
        	//진짜 댓글 등록 실행 버튼
        	registerReply();
        }else if(me.id == 'closeModalBtn'){
        	closeModal();
        }else if (me.id == 'addReplyBtn') {
            registerReply(); // 진짜 댓글 등록 실행버튼
        }
        else if (me.id == 'modifyReplyBtn') {
            modifyReply();
        }
        else if (me.id == 'removeReplyBtn') {
            removeReply();
        }

    })
});

function modify() {
    let bno = f.bno.value;
    location.href = '/board/modify?bno=' + bno;
}

//-----------------댓글 관련 스크립트---------------
const rs = replyService;		//reply.js에서 CRUD 담당 객체
function showList(){
	let bno = f.bno.value;
	let replyUL = document.querySelector('.chat');
	
	rs.getList(bno, jsonArray =>{ //배열
		
		let msg = '';
		
		jsonArray.forEach(reply =>{ //댓글 forEach문(배열)
			if(!reply){
				replyUL.innerHTML = '';
				return;
			}
			
			msg += `<li data-rno="${reply.rno}" onclick="modifyModalPage(this)">`; //this는 li임
			msg += `<div>`;
			msg += `<div class="chat-header">`;
			msg += `<strong>${reply.replyer}</strong>`;
			msg += `<small class="pull-right">${displayTime(reply.replydate)}</small>`;
			msg += `</div>`;
			msg += `<p>${reply.reply}</p>`;
			msg += `</div>`;
			msg += `</li>`; 
		});
		replyUL.innerHTML = msg;
	});
	
}
showList();

function displayTime(unixTimeStamp){
	//초 일 때 
	let myDate = new Date(unixTimeStamp);
	//밀리초일 때
// let myDate = new Date(unixTimeStamp/1000);
	
	let y = myDate.getFullYear();
	let m = String(myDate.getMonth()+1).padStart(2, '0');
	let d = String(myDate.getDate()).padStart(2, '0');
	
	let date = y + '-' + m + '-' + d;
	return date;
}


//----------------모달 관련 스크립트-----------------
const modal = document.querySelector('#modal');
const inputReply = 
	document.querySelector('input[name="reply"]');
const inputReplyer = 
	document.querySelector('input[name="replyer"]');
const inputReplydate = 
	document.querySelector('input[name="replydate"]');
const addReplyBtn = document.querySelector('#addReplyBtn');
const modifyReplyBtn = document.querySelector('#modifyReplyBtn');
const removeReplyBtn = document.querySelector('#removeReplyBtn');
const closeModalBtn = document.querySelector('#closeModalBtn');
function openModal(){
	modal.style.display = "block";
//document.body.style.overflow = "hidden";
}
function closeModal(){
	modal.style.display ="none";
//document.body.style.overflow = "auto";
}



//댓글 등록창 함수
function registerModalPage(){
	//보여질 목록 수정
	regReplyModalStyle();
	//입력 내용 초기화& 불러오기
	inputReply.value = '';
	//inputReplyer.value = '';
	
	openModal();
}
//댓글 달기 창 스타일 변경 함수
function regReplyModalStyle(){
	modifyReplyBtn.classList.add('hide'); //classList: 클래스 관리(hide, remove, add)
	removeReplyBtn.classList.add('hide');
	inputReplydate.closest('div').classList.add('hide');
	document.getElementById('closeModalBtn').onclick = closeModal;
	
}

//진짜 댓글 등록 함수
function registerReply(){
	if(!inputReply.value || !inputReplyer.value){
		alert("모든 내용을 입력하세요");
		return;
	}
	
	const sendData = {
		reply : inputReply.value,
		replyer : inputReplyer.value,
		bno : f.bno.value
	};
	 rs.add(sendData, result =>{
		 console.log(result);
		 closeModal();
		 showList();
	 });
}
//댓글 클릭 함수
let rno;
function modifyModalPage(li){
	rno = li.dataset.rno;
	//보여질 목록 수정
	modReplyModalStyle();
	
	//입력 내용 초기화 & 불러오기
	const replyData = li.querySelector('p').innerText;
	const replyerData = li.querySelector('strong').innerText;
	const replydateData = li.querySelector('small').innerText;
	
	inputReply.value = replyData;
	inputReplyer.value = replyerData;
	inputReplydate.value = replydateData;
	
	openModal();
}
function modReplyModalStyle(){ 
	addReplyBtn.classList.add('hide');
	modifyReplyBtn.classList.remove('hide');
	removeReplyBtn.classList.remove('hide');
	inputReplydate.closest('div').classList.remove('hide');
	document.getElementById('closeModalBtn').onclick = closeModal;
	
	inputReplyer.readOnly = true;
	inputReplydate.readOnly = true;
}
//수정
function modifyReply() {
    if (!inputReply.value || !inputReplyer.value) {
        alert("댓글 내용과 댓글 작성자 이름을 입력해주세요.");
        return;
    }
    let replyObj = {
        reply: inputReply.value,
        replyer: inputReplyer.value
    }
    //여기서 사용한 rno는 전역변수
    rs.update(rno, replyObj, result => {
    	console.log(result);
        if (result === 'success') {
            closeModal();
            showList();
        }
        else {
            alert('수정 실패');
        }
    })
}

// 삭제
function removeReply() {

	    if (!confirm('삭제하시겠습니까?')) {
        return;
    }
    rs.remove(rno, result => {
        if (result === 'success') {
            closeModal();
            showList();
        }
        else {
            alert('삭제 실패');
        }
    })
}



//rs.add(
//	{
//		bno : f.bno.value,
//		reply : 'JS TEST',
//		replyer : 'tester'
//	}, 
//	function(result){
//		console.log(result);
//		}
//	);
//rs.getList(f.bno.value, function(result){
//	console.log(result);
//});
//rs.remove(41,
//	function (result){
//		console.log(result);
//}	
//);
//rs.update(42,'asdwrqwr',
//	function (result){
//		console.log(result);
//}
//);
//rs.get(42,
//	function (result){
//		console.log(result);
//}
//)


//--------------------첨부 파일 관련 스크립트


//파일 리스트 콘솔에 출력
(function(){
	fetch(`/board/getAttachList/${f.bno.value}`)
	.then(response => response.json())
	.then(data =>{
		showUploadedFile(data);
	})
	.catch(err => console.log(err));
})();

//업로드 완료된 목록 보여주는 함수
//1. 다운로드 기능 추가
//2. 삭제 기능 방지

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
		str += `<a href="/download?fileName=${fileCallPath}">`; //다운로드 기능 추가
		str += `${file.fileName}`;
		str += `</a>`;
		// str += `<span data-file="${fileCallPath}">X</span>`;   //삭제 기능 방지
		str += `</li>`;
	});
	uploadResult.innerHTML = str;

}
	