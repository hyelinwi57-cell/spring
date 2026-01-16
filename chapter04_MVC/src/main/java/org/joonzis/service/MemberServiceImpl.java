package org.joonzis.service;

import org.joonzis.domain.MemberVO;
import org.joonzis.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberMapper memberMapper;
	@Override
	public void join(MemberVO vo) {
		// TODO Auto-generated method stub
		
	}
}
