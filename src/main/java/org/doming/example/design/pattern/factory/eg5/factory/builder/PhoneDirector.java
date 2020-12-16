package org.doming.example.design.pattern.factory.eg5.factory.builder;
import org.doming.example.design.pattern.factory.entry.Phone;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PhoneDirector {
	public Phone construct(PhoneBuilder builder){
		builder.buildBrand();
		builder.buildOs();
		return builder.getResult();
	}
}
