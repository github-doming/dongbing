package org.doming.example.design.pattern.factory.eg4.abs.factory;
import org.doming.example.design.pattern.factory.entry.SonyHeadset;
import org.doming.example.design.pattern.factory.entry.SonyPhone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SonyFactory implements Factory{
	@Override public SonyPhone getPhone() {
		SonyPhone sonyPhone = new SonyPhone();
		sonyPhone.setBrand("Sony");
		return sonyPhone;

	}
	@Override public SonyHeadset getHeadset() {
		SonyHeadset sonyHeadset = new SonyHeadset();
		sonyHeadset.setBrand("Sony");
		return sonyHeadset;

	}
}
