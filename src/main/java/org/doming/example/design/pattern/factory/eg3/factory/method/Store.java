package org.doming.example.design.pattern.factory.eg3.factory.method;
import org.doming.example.design.pattern.factory.entry.Headset;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Store {
	private PhoneFactory phoneFactory;
	private HeadsetFactory headsetFactory;
	public Store(PhoneFactory phoneFactory, HeadsetFactory headsetFactory) {
		super();
		this.phoneFactory = phoneFactory;
		this.headsetFactory = headsetFactory;
	}
	/**
	 * 补充手机
	 */
	public void supplyPhone() {
		Phone phone = phoneFactory.getPhone();
		// 补充手机逻辑...
		System.out.println("补充" + phone.getBrand() + "手机完成");
	}

	/**
	 * 补充耳机
	 */
	public void supplyHeadset() {
		Headset headset = headsetFactory.getHeadset();
		// 补充耳机逻辑...
		System.out.println("补充" + headset.getBrand() + "耳机完成");
	}


	public static void main(String[] args) {
		Store store = new Store(new SonyPhoneFactory(), new SonyHeadsetFactory());
		store.supplyPhone();
		store.supplyHeadset();
	}



}
