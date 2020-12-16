package c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.entity;
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
 * The persistent class for the database table bbs_notegroup_note 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "bbs_notegroup_note")
public class BbsNotegroupNote implements Serializable {
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
	// notegroup_id
	@Column(name = "notegroup_id")
	private Long notegroup_id;
	public void setNotegroup_id(Long notegroup_id) {
		this.notegroup_id = notegroup_id;
	}
	public Long getNotegroup_id() {
		return this.notegroup_id;
	}
	// note_id
	@Column(name = "note_id")
	private Long note_id;
	public void setNote_id(Long note_id) {
		this.note_id = note_id;
	}
	public Long getNote_id() {
		return this.note_id;
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