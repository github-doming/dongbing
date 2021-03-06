package c.x.platform.admin.feng.bbs.buy.bbs_forum_info.entity;

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
 * The persistent class for the database table bbs_forum_info 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "bbs_forum_info")
public class BbsForumInfo implements Serializable {

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

	// 名称
	@Column(name = "name")
	private String name;

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	// path
	@Column(name = "path")
	private String path;

	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return this.path;
	}

	// tree_code
	@Column(name = "tree_code")
	private String tree_code;

	public void setTree_code(String tree_code) {
		this.tree_code = tree_code;
	}
	public String getTree_code() {
		return this.tree_code;
	}

	// parent
	@Column(name = "parent")
	private String parent;

	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent() {
		return this.parent;
	}

	// 排序
	@Column(name = "sequence")
	private Integer sequence;

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getSequence() {
		return this.sequence;
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