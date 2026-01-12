package org.joonzis.service;

import java.util.List;

import org.joonzis.domain.ReplyVO;
import org.joonzis.mapper.BoardMapper;
import org.joonzis.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public ReplyVO get(int rno) {
		ReplyVO vo = replyMapper.read(rno);
		return vo;
	}
	
	@Override
	public List<ReplyVO> getList(int bno) {
		List<ReplyVO> list = replyMapper.getList(bno);
		return list;
	}
	
	
	@Override
	public boolean modify(ReplyVO vo) {
		int result = replyMapper.update(vo);
		if(result > 0) return true;
		else return false;
	}
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		
		boardMapper.updateReplyCnt(vo.getBno(), 1);//vo의 Bno를 가져옴
		return replyMapper.insert(vo);
		
	}
	@Transactional
	@Override
	public boolean remove(int rno) {
		
		ReplyVO vo = replyMapper.read(rno);
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		
		int result = replyMapper.delete(rno);
		if(result > 0 ) return true;
		else return false;
	}
}
