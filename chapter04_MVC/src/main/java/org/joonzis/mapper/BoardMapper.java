package org.joonzis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.joonzis.domain.BoardAttachVO;
import org.joonzis.domain.BoardVO;
import org.joonzis.domain.Criteria;

public interface BoardMapper {
	//��ü ����Ʈ
	public List<BoardVO> getList(Criteria cri);
	//
	public int getTotal();
	//게시글 번호
	public int getNextBno();
	//������ ���� insert
	public void insert(BoardVO vo);
	//���� ������(�� ����) read
	public BoardVO read(int bno);
	//������ ���� delete --�⺻Ű�� ����
	public int delete(int bno);
	//������ ���� update -- �⺻Ű ����/ ����, ����, �ۼ���, ���� ��¥ ����
	public int update(BoardVO vo);
	public void updateReplyCnt(@Param("bno") int bno,
								@Param("amount") int amount);
	
	//1. 위 updateReplyCnt 메소드에 해당하는 쿼리 작성
	//2. 댓글 삽입 / 삭제 시 updateReplyCnt 메소드 실행
	// =>트랜잭션 처리
	//3. 게시글 리스트에서 댓글 개수 변경 확인
	
	
}
