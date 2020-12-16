package org.doming.example.design.pattern.factory.eg5.factory.builder;
import org.doming.example.design.pattern.factory.entry.ApplePhone;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ApplePhoneBuilder implements PhoneBuilder {
	private ApplePhone phone = new ApplePhone();
	@Override public void buildBrand() {
		phone.setBrand("Apple");
	}
	@Override public void buildOs() {
		phone.setOs("IOS");
	}
	@Override public Phone getResult() {
		return phone;
	}
}
