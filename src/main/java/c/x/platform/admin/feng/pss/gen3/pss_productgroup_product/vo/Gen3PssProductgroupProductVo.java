package c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.vo;

import java.io.Serializable;
/**
 * The vo class for the database table pss_productgroup_product vo类
 */

public class Gen3PssProductgroupProductVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// productgroup_id
	private String productgroup_id;
	public void setProductgroup_id(String productgroup_id) {
		this.productgroup_id = productgroup_id;
	}
	public String getProductgroup_id() {
		return this.productgroup_id;
	}

	// product_id
	private String product_id;
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_id() {
		return this.product_id;
	}

}