package c.x.platform.admin.fav.sys.sys_org_info.entity;
import java.io.Serializable;
import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
/**
 * The persistent class for the database table sys_org 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_org_info")
public class SysOrgInfo implements Serializable {
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
	// 上一级
	@Column(name = "parent")
	private Integer parent;
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public Integer getParent() {
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
}