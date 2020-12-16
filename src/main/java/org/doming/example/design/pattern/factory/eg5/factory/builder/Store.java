package org.doming.example.design.pattern.factory.eg5.factory.builder;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Store {
	PhoneFactory factory;
	public Store(PhoneFactory factory) {
		this.factory = factory;
	}
	public void supplyPhone(){
		Phone phone = factory.getPhone();
		// 补充手机逻辑...
		System.out.println("补充" + phone.getBrand() + "手机完成");
	}

	public static void main(String[] args) {
		Store store = new Store(new ApplePhoneFactory());
		store.supplyPhone();
	}
}
