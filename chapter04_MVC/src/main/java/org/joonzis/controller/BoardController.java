package org.joonzis.controller;


import java.util.List;

import org.joonzis.domain.BoardAttachVO;
import org.joonzis.domain.BoardVO;
import org.joonzis.domain.Criteria;
import org.joonzis.domain.MemberVO;
import org.joonzis.domain.PageDTO;
import org.joonzis.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("board/*") 		// 
								// board/list , board/register 등 모두 이 컨트롤러
public class BoardController {
	@Autowired
	private BoardService service;
	
	
	
	//전체 데이터
	@GetMapping("/list") //get 방식, cmd는 list
	public String list(Criteria cri, Model model) { 	/* ps) 만일 리다이렉트를 받는 곳 rttr의 파라미터를 받을 거라면? 
										 	   @ModelAttribute 어노테이션을 이용해서 매개변수로 받을 수 있음 */
		log.info("list..." + cri);
		
		if(cri.getPageNum() == 0 || cri.getAmount() == 0) {
			cri.setPageNum(1);
			cri.setAmount(10);
		}
		//해당 페이지에 보여줄 데이터
		model.addAttribute("list", service.getList(cri));
		// 도착 url(jsp)과 요청 경로(컨트롤러)가 같은 경우 생략하고, 반환타입을 void로 가능 
		
		int total = service.getTotal();
		log.info("total..."+total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		
		return "/board/list";
	}
	
	// 등록
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(BoardVO vo , RedirectAttributes rttr) { // 자동 매핑?
		log.info("BoardController::register");
		log.info("BoardController::BoardVO is " + vo);
		service.register(vo);
		rttr.addFlashAttribute("result","success"); // 리다이렉트 시 1회성 param 또는 데이터를 사용하기 위해(jsp에서)
		return "redirect:/board/list"; 	//포워딩이 아닌 리다이렉트 방식으로 이동할 때
										//또는 매개변수에 RedirectAttributes rttr를 받아서 위의 주석과 같이 사용
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public void registerPage() {}
	
	
	// 조회
	@GetMapping("/get")
	public String read(@RequestParam("bno") int bno,
						Model model) {
		log.info("BoardController::get, bno="+bno);
		model.addAttribute("vo", service.get(bno));
		return "/board/get";
	}
	// 삭제
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") int bno) {
		log.info("BoardController::remove, bno="+bno);
		service.remove(bno);
		return "redirect:/board/list";
	}
	
	
	// 수정
	@PostMapping("/modify")
	public String modify (BoardVO vo) {
		log.info("BoardController::modify, vo="+vo);
		boolean result = service.modify(vo);
		return "redirect:/board/list";
	}
	@GetMapping("/modify") // get 방식의 register와 비슷한 구조라서 합쳐도 된다
							// @GetMapping({"/modify","/register"}) 이런 식으로 anno를 수정해도 됨
	public String modifyPage(@RequestParam("bno") int bno, Model model) {
		log.info("Get, modifyPage::" + bno);
		model.addAttribute("vo",service.get(bno));
		return "/board/modify";
	}
	//첨부파일 리스트 가져오기
		@ResponseBody
		@GetMapping(value = "/getAttachList/{bno}",
					produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		public ResponseEntity<List<BoardAttachVO>> getAttachList(
									@PathVariable("bno")int bno){
		log.info("getAttachList :" + bno);
		return new ResponseEntity<List<BoardAttachVO>>(service.getAttachList(bno),HttpStatus.OK);
		}
		
	
}
