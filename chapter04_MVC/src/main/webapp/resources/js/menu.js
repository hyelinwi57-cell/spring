// .header 하위 a 태그들에 이벤트 부여
// 		1. 기존 이벤트 방지
// 		2. 해당 속성에서 값 꺼내서 분기 태우기

document.querySelectorAll(".header a").forEach(a => a.addEventListener("click", (e) => {
    e.preventDefault();
    const href = a.getAttribute("href");
    if (href == "mainPage") {
        location.href = "/";
    }
    else if (href == "boardList") {
        location.href = "/board/list";
    }
    else console.log("wrong request");

})
);

function setStorageData(pageNum, amount){
	let pageData = {
			pageNum : pageNum,
			amount : amount
	};
	localStorage.setItem(
			'page_data',
			JSON.stringify(pageData)
	);
}
function getStorageData(){
	return JSON.parse(localStorage.getItem('page_data'))
}

//------------principal 객체 가져오기
let principal;
async function getPrincipal(){
	try {
		const response = await fetch(`/api/currentUser.json`);
		const userPrincipal = await response.json();
		principal = userPrincipal.principal;
		console.log(principal);
	} catch (e) {
		console.log(`에러 : ${e}`);
	}
}
getPrincipal();





