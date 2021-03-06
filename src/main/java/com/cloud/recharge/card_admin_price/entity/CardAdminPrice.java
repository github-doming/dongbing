package com.cloud.recharge.card_admin_price.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table card_admin_price 
 * 管理员卡种价格 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "card_admin_price")
public class CardAdminPrice implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 管理员卡种价格主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CARD_ADMIN_PRICE_ID_")
	private String cardAdminPriceId;
	public void setCardAdminPriceId(String cardAdminPriceId) {
		this.cardAdminPriceId=cardAdminPriceId;
	}
	public void setCardAdminPriceId(Object cardAdminPriceId) {
		if (cardAdminPriceId != null) {
			this.cardAdminPriceId = cardAdminPriceId.toString();
		}
	}
	public String getCardAdminPriceId() {
		return this.cardAdminPriceId ;
	}

	/**
	 * 充值卡卡类主键
	 */
	@Column(name="CARD_TREE_ID_")
	private String cardTreeId;
	public void setCardTreeId(String cardTreeId) {
		this.cardTreeId=cardTreeId;
	}
	public void setCardTreeId(Object cardTreeId) {
		if (cardTreeId != null) {
			this.cardTreeId = cardTreeId.toString();
		}
	}
	public String getCardTreeId() {
		return this.cardTreeId ;
	}

	/**
	 * 充值卡管理用户主键
	 */
	@Column(name="CARD_ADMIN_ID_")
	private String cardAdminId;
	public void setCardAdminId(String cardAdminId) {
		this.cardAdminId=cardAdminId;
	}
	public void setCardAdminId(Object cardAdminId) {
		if (cardAdminId != null) {
			this.cardAdminId = cardAdminId.toString();
		}
	}
	public String getCardAdminId() {
		return this.cardAdminId ;
	}

	/**
	 * 管理员主键
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			this.userId = userId.toString();
		}
	}
	public String getUserId() {
		return this.userId ;
	}

	/**
	 * 下级管理员主键
	 */
	@Column(name="SUB_USER_ID_")
	private String subUserId;
	public void setSubUserId(String subUserId) {
		this.subUserId=subUserId;
	}
	public void setSubUserId(Object subUserId) {
		if (subUserId != null) {
			this.subUserId = subUserId.toString();
		}
	}
	public String getSubUserId() {
		return this.subUserId ;
	}

	/**
	 * 卡类价格
	 */
	@Column(name="CARD_PRICE_T_")
	private Integer cardPriceT;
	public void setCardPriceT(Integer cardPriceT) {
		this.cardPriceT=cardPriceT;
	}
	public void setCardPriceT(Object cardPriceT) {
		if (cardPriceT != null) {
			if (cardPriceT instanceof Integer) {
				this.cardPriceT= (Integer) cardPriceT;
			}else if (StringTool.isInteger(cardPriceT.toString())) {
				this.cardPriceT = Integer.parseInt(cardPriceT.toString());
			}
		}
	}
	public Integer getCardPriceT() {
		return this.cardPriceT ;
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
	 * 创建时间数字型
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
	 * 更新时间数字型
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