package org.joonzis.DI_5_component;

import org.springframework.stereotype.Component;


/*
 *  @Component ?€ @Configuration/@Bean ?€ ê¸°ëŠ¥??ë¹„ìŠ·
 * @Component???´ëž˜???¨ìœ„
 * @Configuration/@Bean?€ ë©”ì†Œ???¨ìœ„
 * 
 * 
 * */
@Component("tv")
public class LgTV implements TV {
	public LgTV() {
		System.out.println("LgTv ï¿½ï¿½Ã¼ ï¿½ï¿½ï¿½ï¿½");
	}
	@Override
	public void powerOn() {
		System.out.println("LgTv ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½");
	}
	@Override
	public void powerOff() {
		System.out.println("LgTv ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½");
	}
	@Override
	public void volumeUp() {
		System.out.println("LgTv ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½");
	}
	@Override
	public void volumeDown() {
		System.out.println("LgTv ï¿½ï¿½ï¿½ï¿½ ï¿½Ù¿ï¿½");
	}
}
