package c.x.platform.admin.feng.edu.edu_class_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_class_t vo类
 */

public class EduClassTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// name
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	// 驾证类型
	private String licenseType;
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public String getLicenseType() {
		return this.licenseType;
	}

	// 价格以分为单位
	private String price;
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPrice() {
		return this.price;
	}

	// 学习周期类型
	private String timeType;
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getTimeType() {
		return this.timeType;
	}

}