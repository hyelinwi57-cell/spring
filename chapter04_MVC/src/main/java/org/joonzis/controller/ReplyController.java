package org.joonzis.controller;

import java.util.List;

import org.joonzis.domain.ReplyVO;
import org.joonzis.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	ReplyService service;
	//1. 등록
	@PostMapping(
			value="/new", 
			consumes = "application/json", 		// 수신 데이터 포맷
			produces=MediaType.TEXT_PLAIN_VALUE	// 송신 데이터 포맷
			)
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		log.info("ReplyVO "+vo);
		int result = service.register(vo);
		// 삼항 연산자
		// result가 1이면 상태가 ok인 객체 반환
		// 아니면 내부 서버 오류 상태인 객체 반환
		return result == 1 ? 
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	//2. 목록(페이지)
	// /reply/pages/:bno - get
	@GetMapping(value = "/pages/{bno}",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
			})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("bno") int bno) {
		log.info("getList "+ bno);
		List<ReplyVO> list = service.getList(bno);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//3. 삭제
	//reply/:rno - delete
	@DeleteMapping(value = "{rno}", 
			produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		log.info("remove.."+ rno);
		
		return service.remove(rno)?  
		new ResponseEntity<String>("success",HttpStatus.OK):
		new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//4. 수정
	@RequestMapping(
		value = "/{rno}",
		method = {RequestMethod.PUT, RequestMethod.PATCH},
		consumes = "application/json",
				produces = {
						MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE
				})
	public ResponseEntity<String> modify(@PathVariable("rno") int rno, @RequestBody ReplyVO vo) {
		log.info("rno.."+ rno);
		log.info("ReplyVO.."+ vo);
		vo.setRno(rno);
		boolean modifyCount = service.modify(vo);
		
		log.info("Reply modifyCount : "+ modifyCount);
		
		return modifyCount?
		new ResponseEntity<String>("success",HttpStatus.OK):
		new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	//5. 조회
	   @GetMapping(value = "/{rno}",
	         produces = {MediaType.APPLICATION_ATOM_XML_VALUE,
	         MediaType.APPLICATION_JSON_VALUE})
	   public ResponseEntity<ReplyVO> get(@PathVariable("rno") int rno){
	      log.info("get..." + rno);
	      return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	   }
}
