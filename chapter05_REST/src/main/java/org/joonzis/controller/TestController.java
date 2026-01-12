package org.joonzis.controller;

import org.joonzis.domain.TestVO;
import org.joonzis.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/test")
@RestController
public class TestController {
	
	@GetMapping(value = "/getText",
				produces = "text/plain; charset=utf-8") //produce: 데이터 타입
	public String getText() {
		log.info("Mine Type : "+
				MediaType.TEXT_PLAIN_VALUE);
		
		//기존 jsp의 파일 이름이 아닌 순수 데이터를 전달
		return "안녕하세요";
	}
	@GetMapping(value = "/getObject",
			produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE
			})
	
	public TestVO getObject() {
		return new TestVO(100, "kim");
	}
	
	/*
	 * 메소드를 만들고 URL에 맞게 요청해서 결과를 확인하시오(xml, json)
	 * 1. 요청 URL : /test/check
	 * 2. 파라미터 : 실수형 age
	 * 3. 리턴타입 : TestVO
	 * -vo 객체 생성
	 * -no 필드는 0으로 고정
	 * -전달 받은 age를 문자열로 name 필드에 저장
	 */
	@GetMapping("/check")
	public ResponseEntity<TestVO> check(Double age) { //@RequestParam 필수는 아니지만 사용 권장//쿼리스트링 방식
		TestVO vo = new TestVO();
		vo.setNo(0);
		vo.setName(""+ age);
		
		ResponseEntity<TestVO> result = null;
		
		if(age > 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	@GetMapping("/product/{cat}/{pid}")//경로에 데이터 실어 보냄(예: /food/2)
	 public String[] getPath(
			 		@PathVariable("cat") String cat, //cat을 변수에 저장
			 		@PathVariable("pid") int pid
			 		) {
				return new String[] {
						"category: " + cat + "," +
						"productId : " + pid
						};
		 
	 }
	
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket t) {//@RequestBody: json 데이터를 자바 객체로 변환
		log.info("convert .. ticket : " + t);
		
		String result = new Gson().toJson(t);
		log.info(result);
		
		return t;
	}
	
	@GetMapping("/info")
	public String makeJson() {
		//gson꺼
		JsonObject json = new JsonObject();
		json.addProperty("name", "kim");
		json.addProperty("age", 10);
		json.addProperty("job", "student");
		
		JsonArray ja = new JsonArray();
		for (int i = 0; i < 5; i++) {
			JsonObject j = new JsonObject();
			j.addProperty("user" + i , i);
			ja.add(j);
		}
		json.add("users", ja);
		return json.toString();
	}
}
