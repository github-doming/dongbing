package c.x.platform.admin.fav.sys.sys_area_info.entity;
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
 * The persistent class for the database table sys_area 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_area_info")
public class FunAreaInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// 编号
	@Column(name = "id")
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// 父级编号
	@Column(name = "parent")
	private String parent;
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent() {
		return this.parent;
	}
	// 所有父级编号
	@Column(name = "parent_ids")
	private String parent_ids;
	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}
	public String getParent_ids() {
		return this.parent_ids;
	}
	// 区域编码
	@Column(name = "code")
	private String code;
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
	// 区域名称
	@Column(name = "name")
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	// 备注
	@Column(name = "remarks")
	private String remarks;
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return this.remarks;
	}
	// 删除标记（0：正常；1：删除）
	@Column(name = "del_flag")
	private String del_flag;
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getDel_flag() {
		return this.del_flag;
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