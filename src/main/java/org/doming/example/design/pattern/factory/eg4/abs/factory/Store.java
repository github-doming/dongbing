package org.doming.example.design.pattern.factory.eg4.abs.factory;
import org.doming.example.design.pattern.factory.entry.Headset;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Store {
	private Factory factory;
	public Store(Factory factory) {
		this.factory = factory;
	}

	public void supplyPhone(){
		Phone phone = factory.getPhone();
		// 补充手机逻辑...
		System.out.println("补充" + phone.getBrand() + "手机完成");
	}
	public void supplyHeadset(){
		Headset headset = factory.getHeadset();
		// 补充耳机逻辑...
		System.out.println("补充" + headset.getBrand() + "耳机完成");
	}

	public static void main(String[] args) {
		Store store = new Store(new SonyFactory());
		store.supplyHeadset();
		store.supplyPhone();
	}
}
