package c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.entity;

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
 * The persistent class for the database table pss_productgroup_product 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "pss_productgroup_product")
public class Gen3PssProductgroupProduct implements Serializable {

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

	// productgroup_id
	@Column(name = "productgroup_id")
	private String productgroup_id;

	public void setProductgroup_id(String productgroup_id) {
		this.productgroup_id = productgroup_id;
	}
	public String getProductgroup_id() {
		return this.productgroup_id;
	}

	// product_id
	@Column(name = "product_id")
	private String product_id;

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_id() {
		return this.product_id;
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