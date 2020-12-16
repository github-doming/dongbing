package c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.vo;

import java.io.Serializable;
/**
 * The vo class for the database table pss_productgroup_info vo类
 */

public class Gen2PssProductgroupInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// 商品分类名称
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
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

	// parent
	private String parent;
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent() {
		return this.parent;
	}

	// 排序
	private String sequence;
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getSequence() {
		return this.sequence;
	}

}