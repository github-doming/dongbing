package org.doming.example.design.pattern.factory.entry;
/**
 * @Description: 手机
 * @Author: Dongming
 * @Date: 2019-11-27 14:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class Phone {
	/**
	 * 品牌
	 */
	protected String brand;

	/**
	 * 操作系统
	 */
	protected String os;

	/**
	 * 充电
	 */
	public abstract void charge();

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
}
