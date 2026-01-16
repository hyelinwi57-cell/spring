package org.joonzis.controller;


import java.util.List;

import org.joonzis.domain.BoardAttachVO;
import org.joonzis.domain.BoardVO;
import org.joonzis.domain.Criteria;
import org.joonzis.domain.MemberVO;
import org.joonzis.domain.PageDTO;
import org.joonzis.service.BoardService;
import org.joonzis.service.MemberService;
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
public class MemberController {
	@Autowired
	private MemberService service;
	
	
	//목록으로 이동
	@GetMapping("/join")
	public String joinForm() {
		return "board/join";
	}
	//회원가입
	@PostMapping("/join")
	public String joinProcess(MemberVO vo) {
		MemberService.join(vo);
		return "redirect:/board/list";
	}
	
	
	
}
