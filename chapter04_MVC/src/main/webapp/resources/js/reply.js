console.log('reply module...');

const replyService = (function(){
	
	//1. 등록
	function add(reply, callback){
		fetch('/reply/new', 
				{
				method : 'post',
				body : JSON.stringify(reply),
				headers : {
					'Content-type': 'application/json; charset=utf-8' //body,headers는 전달 데이터
				}
			}
		)
		.then(response => response.text())//문자열(String)로 받음(json으로 받지 않음)
		.then(data =>{
			callback(data);
		})
		.catch(err => console.log(err));
	}
	
	//2. 목록(페이지)
	function getList(bno, callback){
		fetch('/reply/pages/'+ bno +'.json')
		.then(response => response.json())
		.then(data =>{
			callback(data);
		})
		.catch(err => console.log(err));
	}
	
	//3. 삭제
	function remove(rno, callback){
		fetch('/reply/'+ rno,{
			method : 'delete',
			headers : {
				'Content-type': 'application/json; charset=utf-8'
			}
		})
		.then(response => response.text())
		.then(data =>{
			callback(data);
		})
		.catch(err => console.log(err));
	}
	
	//4. 수정
	function update(rno, reply, callback){
		fetch('/reply/'+ rno+'.json',{
			method : 'put',
			body : JSON.stringify(reply),
			headers : {
				'Content-type': 'application/json; charset=utf-8'
			}
		})
		.then(response => response.text())
		.then(data =>{
			callback(data);
		})
		.catch(err => console.log(err));
		
	}
	
	//5. 조회
	function get(rno, callback){
		fetch('/reply/'+ rno+'.json')
		.then(response => response.json())
		.then(data =>{
			callback(data);
		})
		.catch(err => console.log(err));
	}
	
	
	return{ // replyService의 반환 값 -> 함수를 모아 놓은 객체
		add : add,
		getList : getList,
		remove : remove,
		update : update,
		get : get
	};
	
	
})();

	
