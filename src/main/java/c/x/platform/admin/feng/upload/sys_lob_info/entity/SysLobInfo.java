package c.x.platform.admin.feng.upload.sys_lob_info.entity;
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
 * The persistent class for the database table sys_lob_info 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_lob_info")
public class SysLobInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// 主键
	@Column(name = "id")
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// 文件名
	@Column(name = "file_name")
	private String file_name;
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_name() {
		return this.file_name;
	}
	// 文件
	@Column(name = "file")
	private byte[] file;
	public void setFile(byte[] file) {
		this.file = file;
	}
	public byte[] getFile() {
		return this.file;
	}
	// 用户ID
	@Column(name = "user_id")
	private Long user_id;
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getUser_id() {
		return this.user_id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	// create_time
	@Column(name = "create_time")
	private Date create_time;
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getCreate_time() {
		return this.create_time;
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