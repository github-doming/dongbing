package c.x.platform.admin.feng.bbs.buy.bbs_post_info.entity;

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
 * The persistent class for the database table bbs_post_info 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "bbs_post_info")
public class BbsPostInfo implements Serializable {

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

	// 内容
	@Column(name = "content")
	private String content;

	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return this.content;
	}

	// 标题
	@Column(name = "title")
	private String title;

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
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