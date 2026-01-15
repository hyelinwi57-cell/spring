console.log('upload.js 실행.....');

const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
const MAX_SIZE = 5242880;  //5MB

//파일의 크기와 종류 체크
function checkExtension(fileName, fileSize){
	if(fileSize >= MAX_SIZE){
		alert("파일 사이즈 초과");
		return;
	}
	if(regex.test(fileName)){
		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		return;
	}
	return true;
}

//비어있는 요소 복사해두기
let uploadDiv = document.querySelector(".uploadDiv");
//하위 노드까지 복사
let cloneObj = uploadDiv.firstElementChild.cloneNode(true);



//input에서 파일 선택 후 바로 서버에 업로드 되는 이벤트를 거는 코드
document.querySelector('input[type="file"]').addEventListener('change',()=>{
	const inputFile =
		document.querySelector('input[type="file"]');
	const files = inputFile.files;
	const formData = new FormData();
	
	for (let i = 0; i < files.length; i++) {
		
		if(!checkExtension(files[i].name, files[i].size)){
			return false;
		}
		formData.append('uploadFile', files[i]);
	}
	//실제 파일 업로드
	fetch(`/uploadAsyncAction`,
		{
			method : 'post',
			body : formData
		})
		.then(response => response.json())
		.then(data => {
			console.log(data);
			
			inputFile.value = '';
			showUploadedFile(data);
		})
		.catch(err => console.log(err));
});


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
	//str += `<a href="/download?fileName=${fileCallPath}">`;
		str += `${file.fileName}`;
	//str += `</a>`;
		str += `<span data-file="${fileCallPath}">X</span>`;
		str += `</li>`;
	});
	uploadResult.innerHTML = str;
	
	//위에서 생성된 X에 클릭이벤트 부여
	//클릭 시 '/deleteFile' 경로로 fetch
	//*method : post
	//*body : data-file의 값 == fileCallPath
	//*headers : {'Content-Type' : 'text/plain'}
	//*api에서는 deleted라는 문자열을 리턴할 예정
	
	
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
	
	
}