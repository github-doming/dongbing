package org.doming.example.design.pattern.factory.eg5.factory.builder;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SonyPhoneFactory implements PhoneFactory {
	PhoneDirector director = new PhoneDirector();
	@Override public Phone getPhone() {
		SonyPhoneBuilder builder = new SonyPhoneBuilder();
		return director.construct(builder);
	}
}
