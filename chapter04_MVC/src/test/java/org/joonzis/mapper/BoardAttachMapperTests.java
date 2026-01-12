package org.joonzis.mapper;

import java.util.List;

import org.joonzis.domain.BoardAttachVO;
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
public class BoardAttachMapperTests {
	@Autowired
	private BoardAttachMapper mapper;
	
//	@Test
//	public void testInsert() {
//		BoardAttachVO vo = new BoardAttachVO();
//		vo.setUuid("test");
//		vo.setBno(1);
//		vo.setFileName("testfile");
//		vo.setUploadPath("uploadpath");
//	}
//	@Test
//	public void testDelete() {
//		mapper.delete("uuid"); //void는 "int result = mapper.delete(1);"가 안 됨/리턴 없음
//	}
	@Test
	public void testFindByBno() {
		List<BoardAttachVO> list = mapper.findByBno(1);
		for(BoardAttachVO vo : list) {
			log.info(vo);
		}
	}
}
