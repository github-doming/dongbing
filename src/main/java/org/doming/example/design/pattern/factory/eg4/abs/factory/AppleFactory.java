package org.doming.example.design.pattern.factory.eg4.abs.factory;
import org.doming.example.design.pattern.factory.entry.AppleHeadset;
import org.doming.example.design.pattern.factory.entry.ApplePhone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AppleFactory implements Factory {
	@Override public ApplePhone getPhone() {
		ApplePhone applePhone = new ApplePhone();
		applePhone.setBrand("Apple");
		return applePhone;

	}
	@Override public AppleHeadset getHeadset() {
		AppleHeadset appleHeadset = new AppleHeadset();
		appleHeadset.setBrand("Apple");
		return appleHeadset;

	}
}
