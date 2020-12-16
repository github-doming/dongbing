package c.x.platform.admin.feng.pss.gen1.pss_goods_info.vo;

import java.io.Serializable;
/**
 * The vo class for the database table pss_goods_info vo类
 */

public class Gen1PssGoodsInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// name
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	// is_showcase
	private String is_showcase;
	public void setIs_showcase(String is_showcase) {
		this.is_showcase = is_showcase;
	}
	public String getIs_showcase() {
		return this.is_showcase;
	}

	// version
	private String version;
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return this.version;
	}

}