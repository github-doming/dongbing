package com.ibm.follow.servlet.cloud.ibm_sys_card_price.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_card_price 
 * IBM_点卡价格 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_card_price")
public class IbmSysCardPrice implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_点卡价格主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_CARD_PRICE_ID_")
	private String ibmSysCardPriceId;
	public void setIbmSysCardPriceId(String ibmSysCardPriceId) {
		this.ibmSysCardPriceId=ibmSysCardPriceId;
	}
	public void setIbmSysCardPriceId(Object ibmSysCardPriceId) {
		if (ibmSysCardPriceId != null) {
			this.ibmSysCardPriceId = ibmSysCardPriceId.toString();
		}
	}
	public String getIbmSysCardPriceId() {
		return this.ibmSysCardPriceId ;
	}

	/**
	 * 名称
	 */
	@Column(name="PRICE_NAME_")
	private String priceName;
	public void setPriceName(String priceName) {
		this.priceName=priceName;
	}
	public void setPriceName(Object priceName) {
		if (priceName != null) {
			this.priceName = priceName.toString();
		}
	}
	public String getPriceName() {
		return this.priceName ;
	}

	/**
	 * 使用时长
	 */
	@Column(name="USE_TIME_")
	private Integer useTime;
	public void setUseTime(Integer useTime) {
		this.useTime=useTime;
	}
	public void setUseTime(Object useTime) {
		if (useTime != null) {
			if (useTime instanceof Integer) {
				this.useTime= (Integer) useTime;
			}else if (StringTool.isInteger(useTime.toString())) {
				this.useTime = Integer.parseInt(useTime.toString());
			}
		}
	}
	public Integer getUseTime() {
		return this.useTime ;
	}

	/**
	 * 原价
	 */
	@Column(name="ORIGINAL_PRICE_")
	private Integer originalPrice;
	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice=originalPrice;
	}
	public void setOriginalPrice(Object originalPrice) {
		if (originalPrice != null) {
			if (originalPrice instanceof Integer) {
				this.originalPrice= (Integer) originalPrice;
			}else if (StringTool.isInteger(originalPrice.toString())) {
				this.originalPrice = Integer.parseInt(originalPrice.toString());
			}
		}
	}
	public Integer getOriginalPrice() {
		return this.originalPrice ;
	}

	/**
	 * 现价
	 */
	@Column(name="CURRENT_PRICE_")
	private Integer currentPrice;
	public void setCurrentPrice(Integer currentPrice) {
		this.currentPrice=currentPrice;
	}
	public void setCurrentPrice(Object currentPrice) {
		if (currentPrice != null) {
			if (currentPrice instanceof Integer) {
				this.currentPrice= (Integer) currentPrice;
			}else if (StringTool.isInteger(currentPrice.toString())) {
				this.currentPrice = Integer.parseInt(currentPrice.toString());
			}
		}
	}
	public Integer getCurrentPrice() {
		return this.currentPrice ;
	}

	/**
	 * 折扣
	 */
	@Column(name="DISCOUNT_")
	private Integer discount;
	public void setDiscount(Integer discount) {
		this.discount=discount;
	}
	public void setDiscount(Object discount) {
		if (discount != null) {
			if (discount instanceof Integer) {
				this.discount= (Integer) discount;
			}else if (StringTool.isInteger(discount.toString())) {
				this.discount = Integer.parseInt(discount.toString());
			}
		}
	}
	public Integer getDiscount() {
		return this.discount ;
	}

	/**
	 * 次序
	 */
	@Column(name="SN_")
	private Integer sn;
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public void setSn(Object sn) {
		if (sn != null) {
			if (sn instanceof Integer) {
				this.sn= (Integer) sn;
			}else if (StringTool.isInteger(sn.toString())) {
				this.sn = Integer.parseInt(sn.toString());
			}
		}
	}
	public Integer getSn() {
		return this.sn ;
	}

	/**
	 * 创建人
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}
	}
	public String getCreateUser() {
		return this.createUser ;
	}

	/**
	 * 创建时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME_")
	private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public void setCreateTime(Object createTime) throws ParseException {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间
	 */
	@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong= (Long) createTimeLong;
			}else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}

	/**
	 * 更新时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME_")
	private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTime(Object updateTime) throws ParseException {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间
	 */
	@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong= (Long) updateTimeLong;
			}else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name="STATE_")
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		}
	}
	public String getState() {
		return this.state ;
	}

	/**
	 * 描述
	 */
	@Column(name="DESC_")
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 * @return 表名
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}