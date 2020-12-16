package c.x.platform.admin.fav.sys.sys_area_info.vo;
import java.io.Serializable;
/**
 * The vo class for the database table sys_area vo类
 */
public class FunAreaInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 编号
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// 父级编号
	private String parent;
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent() {
		return this.parent;
	}
	// 所有父级编号
	private String parent_ids;
	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}
	public String getParent_ids() {
		return this.parent_ids;
	}
	// 区域编码
	private String code;
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
	// 区域名称
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	// 备注
	private String remarks;
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return this.remarks;
	}
	// 删除标记（0：正常；1：删除）
	private String del_flag;
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getDel_flag() {
		return this.del_flag;
	}
	// path
	private String path;
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return this.path;
	}
	// tree_code
	private String tree_code;
	public void setTree_code(String tree_code) {
		this.tree_code = tree_code;
	}
	public String getTree_code() {
		return this.tree_code;
	}
}