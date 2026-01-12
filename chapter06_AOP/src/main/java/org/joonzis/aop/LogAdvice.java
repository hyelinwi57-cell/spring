package org.joonzis.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
@Aspect
public class LogAdvice {
	/*
	 * @Aspect => 해당 클래스의 객체에서 Aspect를 구현한 것
	 * @Component =>AOP와는 관계 없지만 빈으로 인식 시키기 위함
	 * @Before => BeforeAdvice 구현한 메소드
	 * @After, @AfterReturning, @AfterThrowing 등 동일한 방식
	 * 
	 * Advice와 관련된 어노테이션들은 내부적으로 pointcut을 지정
	 * pointout은 별도의 @Pointcut으로 지정해서 사용 가능
	 * execution 문자열은  AspectJ의 표현식으로
	 * 접근 제한자와 특정 클래스의 메소드를 지정할 수 있다.
	 * */
	@Before(
	"execution(* org.joonzis.service.SampleService*.*(..))") //*: 모든 접근 제한자
	public void logBefore() {
		log.info("=================");
	}
	@Before(
	"execution(* org.joonzis.service.SampleService*.doAdd(String, String)) "
	+ "&& args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	//일부러 애러 발생시킴
	@AfterThrowing(
		pointcut = "execution(* org.joonzis.service.SampleService*.*(..))",
		throwing = "exception"
	)
	public void logException(Exception exception) {
		log.info("Exception...!!!!");
		log.info("exception : " + exception);
	}
	
	
}
