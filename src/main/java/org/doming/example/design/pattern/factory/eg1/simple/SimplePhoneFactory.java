package org.doming.example.design.pattern.factory.eg1.simple;
import org.doming.example.design.pattern.factory.entry.ApplePhone;
import org.doming.example.design.pattern.factory.entry.Phone;
import org.doming.example.design.pattern.factory.entry.SonyPhone;
/**
 * @Description: 简单工厂模式
 * @Author: Dongming
 * @Date: 2019-11-27 14:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SimplePhoneFactory {

	public static Phone getPhone(int brand) {
		if (brand < 1000) {
			SonyPhone sonyPhone = new SonyPhone();
			sonyPhone.setBrand("Sony");
			return sonyPhone;
		} else {
			ApplePhone applePhone = new ApplePhone();
			applePhone.setBrand("Apple");
			return applePhone;
		}
	}
}
