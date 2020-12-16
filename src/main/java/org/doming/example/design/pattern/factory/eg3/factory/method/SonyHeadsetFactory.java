package org.doming.example.design.pattern.factory.eg3.factory.method;
import org.doming.example.design.pattern.factory.entry.SonyHeadset;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SonyHeadsetFactory  implements HeadsetFactory {
	@Override public SonyHeadset getHeadset() {
		SonyHeadset sonyHeadset = new SonyHeadset();
		sonyHeadset.setBrand("Sony");
		return sonyHeadset;

	}
}
