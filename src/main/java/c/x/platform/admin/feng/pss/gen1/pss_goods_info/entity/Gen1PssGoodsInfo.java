package c.x.platform.admin.feng.pss.gen1.pss_goods_info.entity;

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
 * The persistent class for the database table pss_goods_info 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "pss_goods_info")
public class Gen1PssGoodsInfo implements Serializable {

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

	// is_showcase
	@Column(name = "is_showcase")
	private Integer is_showcase;

	public void setIs_showcase(Integer is_showcase) {
		this.is_showcase = is_showcase;
	}
	public Integer getIs_showcase() {
		return this.is_showcase;
	}

	// version
	@Column(name = "version")
	private Long version;

	public void setVersion(Long version) {
		this.version = version;
	}
	public Long getVersion() {
		return this.version;
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