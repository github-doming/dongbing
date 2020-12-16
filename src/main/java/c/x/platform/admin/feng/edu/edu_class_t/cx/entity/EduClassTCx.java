package c.x.platform.admin.feng.edu.edu_class_t.cx.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table edu_class_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "edu_class_t")
public class EduClassTCx implements Serializable {

	private static final long serialVersionUID = 1L;
	// 自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// id
	@Column(name = "id")
	private String id;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// name
	@Column(name = "name")
	private String name;

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	// 驾证类型
	@Column(name = "license_type")
	private Long licenseType;

	public void setLicenseType(Long licenseType) {
		this.licenseType = licenseType;
	}
	public Long getLicenseType() {
		return this.licenseType;
	}

	// 价格以分为单位
	@Column(name = "price")
	private Long price;

	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getPrice() {
		return this.price;
	}

	// 学习周期类型
	@Column(name = "time_type")
	private Long timeType;

	public void setTimeType(Long timeType) {
		this.timeType = timeType;
	}
	public Long getTimeType() {
		return this.timeType;
	}

	private String table_name;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

}