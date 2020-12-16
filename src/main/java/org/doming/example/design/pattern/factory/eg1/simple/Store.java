package org.doming.example.design.pattern.factory.eg1.simple;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 14:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Store {
	private int brand;

	public Store(int brand) {
		super();
		this.brand = brand;
	}

	public void supplyPhone(){
		Phone phone = SimplePhoneFactory.getPhone(brand);
		// 补充手机逻辑...
		System.out.println("补充" + phone.getBrand() + "手机完成");
	}

	public static void main(String[] args) {
		Store store = new Store(1001);
		store.supplyPhone();
	}
}
