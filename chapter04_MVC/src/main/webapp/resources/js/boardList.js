
// css 파일 추가
// 1. 파일 경로 설정
const CSS_FILE_PATH = [	
	'/resources/css/boardList.css',	
	'/resources/css/page.css'
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

// 2. link 태그 생성 및 속성 추가

// 버튼 이벤트 추가
document.querySelector('#registerBtn').addEventListener('click', () => {
    console.log("registerBtn clicked");
    location.href = '/board/register';
});

// 게시글 상세보기
document.querySelectorAll("td a").forEach(a => a.addEventListener("click", (e) => {
    e.preventDefault();
    const bno = a.getAttribute("href");

    location.href = `/board/get?bno=${bno}`;
}));
//페이지 이동 등에서 사용하기 위한 객체 cri를 저장하는 방법

let pageNum = new URLSearchParams(location.search).get('pageNum');
let amount = new URLSearchParams(location.search).get('amount');
if(!pageNum || !amount){
  pageNum = 1;
  amount = 10;
}
setStorageData(pageNum, amount);

document.querySelectorAll('.page-nation li a').forEach(a=>{
  a.addEventListener('click', (e)=>{
    e.preventDefault();
    
    pageNum = a.getAttribute('href');
    setStorageData(pageNum, amount);
    
    let sendData = `pageNum=${pageNum}&amount=${amount}`;
    location.href = '/board/list?' + sendData;
  });
});