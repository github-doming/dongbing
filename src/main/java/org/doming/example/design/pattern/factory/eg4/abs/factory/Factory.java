package org.doming.example.design.pattern.factory.eg4.abs.factory;
import org.doming.example.design.pattern.factory.entry.Headset;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface Factory {
	Phone getPhone();

	Headset getHeadset();

}
