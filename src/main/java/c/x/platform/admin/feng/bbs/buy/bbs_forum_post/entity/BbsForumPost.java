package c.x.platform.admin.feng.bbs.buy.bbs_forum_post.entity;

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
 * The persistent class for the database table bbs_forum_post 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "bbs_forum_post")
public class BbsForumPost implements Serializable {

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

	// forum_id
	@Column(name = "forum_id")
	private Long forum_id;

	public void setForum_id(Long forum_id) {
		this.forum_id = forum_id;
	}
	public Long getForum_id() {
		return this.forum_id;
	}

	// post_id
	@Column(name = "post_id")
	private Long post_id;

	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}
	public Long getPost_id() {
		return this.post_id;
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