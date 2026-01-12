package org.joonzis.service;

import org.joonzis.mapper.Sample1Mapper;
import org.joonzis.mapper.Sample2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService {
	@Autowired
	private Sample1Mapper mapper1;
	@Autowired
	private Sample2Mapper mapper2;
	@Transactional //중간에 하나라도 실패하면 전부 취소(rollback) 성공 시 전부 반영(commit)
	@Override
	public void addData(String value) {
		log.info("mapper1....");
		mapper1.insertCol1(value);
		
		log.info("mapper2....");
		mapper2.insertCol2(value);
		
		log.info("End!!!");
	}
	
	
}
