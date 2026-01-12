package org.joonzis.service;

import java.util.List;

import javax.sound.midi.Sequence;

import org.joonzis.domain.BoardAttachVO;
import org.joonzis.domain.BoardVO;
import org.joonzis.domain.Criteria;
import org.joonzis.mapper.BoardAttachMapper;
import org.joonzis.mapper.BoardMapper;
import org.joonzis.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardMapper boardmapper;
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private BoardAttachMapper attachMapper;
	
	//전체 리스트
	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList...");
		return boardmapper.getList(cri);
	}
	//게시글 수
	@Override
	public int getTotal() {
		log.info("getTotal...");
		return boardmapper.getTotal();
	}
	//데이터 삽입
	@Transactional
	@Override
	public void register(BoardVO vo) {
		log.info("register..."+vo);
		//1. 게시글 등록
		boardmapper.insert(vo);
		
		//2. 등록된 게시글의 번호 가져온다.
		int bno = boardmapper.getNextBno();
		
		//3. 해당 게시글 번호로 첨부 파일 등록
		if(vo.getAttachList() != null &&
			vo.getAttachList().size() > 0) {
			
//			vo.getAttachList().forEach(attach->{
//			attach.setBno(bno);
//			attachMapper.insert(attach);
//			});
			
			for (BoardAttachVO attach : vo.getAttachList()) {
				attach.setBno(bno);
				attachMapper.insert(attach);
				}
			}
			//반복해서 아래 메소드가 실행되면 되겠죠??
			//왜 반복? vo.getAttachList()이 List니까
			//attachMapper.insert(vo);
			//insert 메소드에 전달하는 vo는 BoardAttachVO
			//BoardAttachVO의 구조를 잘 보고
			//잘 전달해서 잘 입력하기
		}
	//조회
	@Override
	public BoardVO get(int bno) {
		log.info("get..."+bno);
		return boardmapper.read(bno);
	}
	//데이터 삭제
	@Transactional
	@Override
	public boolean remove(int bno) {
		log.info("remove..."+bno);
		//-----------트랜젝션 시작
		replyMapper.deleteByBno(bno);// 게시글 삭제, ReplyMapper.java에서 메소드 선언(deleteByBno)
		
		return boardmapper.delete(bno)== 1; //삼항 연산자(1이면 true, 아니면 false)
//		int result =mapper.delete(bno);
//		if(result ==1 ) {
//			return true;
//		}else {
//			return false;
//		}
		
		//----------정상적으로 삭제되면 자동 커밋
	}
	//데이터 수정
	@Override
	public boolean modify(BoardVO vo) {
		log.info("modify..."+vo);
		return boardmapper.update(vo)== 1;
	}
	//첨부파일 리스트
	@Override
	public List<BoardAttachVO> getAttachList(int bno) {
		log.info("getAttachList..."+bno);
		return attachMapper.findByBno(bno);
	}
	
	
	
}
