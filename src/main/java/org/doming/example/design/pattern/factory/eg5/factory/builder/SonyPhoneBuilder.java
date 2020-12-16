package org.doming.example.design.pattern.factory.eg5.factory.builder;
import org.doming.example.design.pattern.factory.entry.Phone;
import org.doming.example.design.pattern.factory.entry.SonyPhone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SonyPhoneBuilder implements PhoneBuilder {
	private SonyPhone phone = new SonyPhone();
	@Override public void buildBrand() {
		phone.setBrand("Sony");
	}
	@Override public void buildOs() {
		phone.setOs("Android");

	}
	@Override public Phone getResult() {
		return phone;

	}
}
