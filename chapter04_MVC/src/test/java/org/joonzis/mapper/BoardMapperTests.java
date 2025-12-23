package org.joonzis.mapper;

import java.util.List;

import org.joonzis.domain.BoardVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		"file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {
	@Autowired
	private BoardMapper mapper;
	
//	@Test
//	public void testGetList() {
	
//		List<BoardVO> list = mapper.getList();
							//bdao.getList();
	
//		for(BoardVO vo :list) {
//			log.info(vo);
//			
//		}
	
//	@Test
//	public void testInsert() {
//		//  bdao.getInsertBBS(bvo);
//		BoardVO vo= new BoardVO();
//		vo.setTitle("title");
//		vo.setContent("content");
//		vo.setWriter("writer");
	
//		mapper.insert(vo);
//	}
//	@Test
//	public void read() {
//		
//		int result = mapper.read(1);
//		
//	}
//	@Test
//	public void delete(){
//		
//		int result = mapper.delete(1);
//		}
	@Test
	public void testUpdate() {
		BoardVO vo = new BoardVO();
		
		vo.setBno(6);
		vo.setTitle("UpdateTest");
		vo.setContent("UpdateContent");
		vo.setWriter("UpdateWriter");
		
		int result = mapper.update(vo);
		
	}
}
