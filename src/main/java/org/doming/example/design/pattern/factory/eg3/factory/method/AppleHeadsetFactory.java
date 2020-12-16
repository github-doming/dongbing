package org.doming.example.design.pattern.factory.eg3.factory.method;
import org.doming.example.design.pattern.factory.entry.AppleHeadset;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AppleHeadsetFactory implements HeadsetFactory {
	@Override public AppleHeadset getHeadset() {
		AppleHeadset appleHeadset = new AppleHeadset();
		appleHeadset.setBrand("Apple");
		return appleHeadset;

	}
}
