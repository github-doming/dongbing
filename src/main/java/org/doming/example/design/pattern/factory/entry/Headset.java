package org.doming.example.design.pattern.factory.entry;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 15:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class Headset {
	/**
	 * 品牌
	 */
	protected String brand;

	abstract void play();

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}
