package org.doming.example.design.pattern.factory.eg5.factory.builder;
import org.doming.example.design.pattern.factory.entry.ApplePhone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ApplePhoneFactory implements PhoneFactory {
	private PhoneDirector director = new PhoneDirector();

	@Override public ApplePhone getPhone() {
		ApplePhoneBuilder builder = new ApplePhoneBuilder();
		return (ApplePhone) director.construct(builder);
	}
}
