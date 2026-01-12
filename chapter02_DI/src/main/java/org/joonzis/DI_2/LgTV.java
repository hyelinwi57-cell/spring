package org.joonzis.DI_2;

public class LgTV implements TV {
	public LgTV() {
		System.out.println("LgTv 객체 생성");
	}
	@Override
	public void powerOn() {
		System.out.println("LgTv 전원 켜짐");
	}
	@Override
	public void powerOff() {
		System.out.println("LgTv 전원 꺼짐");
	}
	@Override
	public void volumeUp() {
		System.out.println("LgTv 볼륨 업");
	}
	@Override
	public void volumeDown() {
		System.out.println("LgTv 볼륨 다운");
	}
}
