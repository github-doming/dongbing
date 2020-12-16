package org.doming.example.design.pattern.factory.eg2.factory.method;
import org.doming.example.design.pattern.factory.entry.SonyPhone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 14:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SonyPhoneFactory implements PhoneFactory {
	@Override public SonyPhone getPhone() {
		SonyPhone sonyPhone = new SonyPhone();
		sonyPhone.setBrand("Sony");
		return sonyPhone;

	}
}
