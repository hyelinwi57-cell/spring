package org.joonzis.DI_2;

public class SamsungTV implements TV{
	public SamsungTV() {
		System.out.println("SamsungTV 객체 생성");
	}
	@Override
	public void powerOn() {
		System.out.println("SamsungTV 전원 켜짐");
	}
	@Override
	public void powerOff() {
		System.out.println("SamsungTV 전원 꺼짐");
	}
	@Override
	public void volumeUp() {
		System.out.println("SamsungTV 볼륨 업");
	}
	@Override
	public void volumeDown() {
		System.out.println("SamsungTV 볼륨 다운");
	}
}
