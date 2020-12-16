package org.doming.example.design.pattern.factory.eg5.factory.builder;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface PhoneBuilder {
	/**
	 * 刻铭牌
	 */
	void buildBrand();

	/**
	 * 安装系统
	 */
	void buildOs();

	Phone getResult();


}
