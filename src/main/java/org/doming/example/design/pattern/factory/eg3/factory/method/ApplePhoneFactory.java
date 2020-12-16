package org.doming.example.design.pattern.factory.eg3.factory.method;
import org.doming.example.design.pattern.factory.entry.ApplePhone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 14:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ApplePhoneFactory implements PhoneFactory {
	@Override public ApplePhone getPhone() {
		ApplePhone applePhone = new ApplePhone();
		applePhone.setBrand("Apple");
		return applePhone;
	}
}
