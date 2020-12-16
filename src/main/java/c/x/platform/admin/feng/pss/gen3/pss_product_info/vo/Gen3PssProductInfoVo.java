package c.x.platform.admin.feng.pss.gen3.pss_product_info.vo;

import java.io.Serializable;
/**
 * The vo class for the database table pss_product_info vo类
 */

public class Gen3PssProductInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// 商品名称
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

}