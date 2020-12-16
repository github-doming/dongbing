package com.cloud.recharge.card_recharge_daily.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table card_recharge_daily 
 * 充值卡报表 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "card_recharge_daily")
public class CardRechargeDaily implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 充值卡报表主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CARD_RECHARGE_DAILY_ID_")
	private String cardRechargeDailyId;
	public void setCardRechargeDailyId(String cardRechargeDailyId) {
		this.cardRechargeDailyId=cardRechargeDailyId;
	}
	public void setCardRechargeDailyId(Object cardRechargeDailyId) {
		if (cardRechargeDailyId != null) {
			this.cardRechargeDailyId = cardRechargeDailyId.toString();
		}
	}
	public String getCardRechargeDailyId() {
		return this.cardRechargeDailyId ;
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
	 * 用户名称
	 */
	@Column(name="USER_NAME_")
	private String userName;
	public void setUserName(String userName) {
		this.userName=userName;
	}
	public void setUserName(Object userName) {
		if (userName != null) {
			this.userName = userName.toString();
		}
	}
	public String getUserName() {
		return this.userName ;
	}

	/**
	 * 卡类名称
	 */
	@Column(name="CARD_TREE_NAME_")
	private String cardTreeName;
	public void setCardTreeName(String cardTreeName) {
		this.cardTreeName=cardTreeName;
	}
	public void setCardTreeName(Object cardTreeName) {
		if (cardTreeName != null) {
			this.cardTreeName = cardTreeName.toString();
		}
	}
	public String getCardTreeName() {
		return this.cardTreeName ;
	}
	/**
	 * 总数量
	 */
	@Column(name="CREATE_TOTAL_")
	private Integer createTotal;
	public void setCreateTotal(Integer createTotal) {
		this.createTotal=createTotal;
	}
	public void setCreateTotal(Object createTotal) {
		if (createTotal != null) {
			if (createTotal instanceof Integer) {
				this.createTotal= (Integer) createTotal;
			}else if (StringTool.isInteger(createTotal.toString())) {
				this.createTotal = Integer.parseInt(createTotal.toString());
			}
		}
	}
	public Integer getCreateTotal() {
		return this.createTotal ;
	}

	/**
	 * 激活数量合计
	 */
	@Column(name="ACTIVATE_TOTAL_")
	private Integer activateTotal;
	public void setActivateTotal(Integer activateTotal) {
		this.activateTotal=activateTotal;
	}
	public void setActivateTotal(Object activateTotal) {
		if (activateTotal != null) {
			if (activateTotal instanceof Integer) {
				this.activateTotal= (Integer) activateTotal;
			}else if (StringTool.isInteger(activateTotal.toString())) {
				this.activateTotal = Integer.parseInt(activateTotal.toString());
			}
		}
	}
	public Integer getActivateTotal() {
		return this.activateTotal ;
	}

	/**
	 * 激活总点数
	 */
	@Column(name="POINT_TOTAL_")
	private Long pointTotalT;
	public void setPointTotalT(Long pointTotalT) {
		this.pointTotalT=pointTotalT;
	}
	public void setPointTotalT(Object pointTotalT) {
		if (pointTotalT != null) {
			if (pointTotalT instanceof Long) {
				this.pointTotalT= (Long) pointTotalT;
			}else if (StringTool.isInteger(pointTotalT.toString())) {
				this.pointTotalT = Long.parseLong(pointTotalT.toString());
			}
		}
	}
	public Long getPointTotalT() {
		return this.pointTotalT ;
	}

	/**
	 * 开卡上缴金额
	 */
	@Column(name="PRICE_TOTAL_T_")
	private Long priceTotalT;
	public void setPriceTotalT(Long priceTotalT) {
		this.priceTotalT=priceTotalT;
	}
	public void setPriceTotalT(Object priceTotalT) {
		if (priceTotalT != null) {
			if (priceTotalT instanceof Long) {
				this.priceTotalT= (Long) priceTotalT;
			}else if (StringTool.isInteger(priceTotalT.toString())) {
				this.priceTotalT = Long.parseLong(priceTotalT.toString());
			}
		}
	}
	public Long getPriceTotalT() {
		return this.priceTotalT ;
	}

	/**
	 * 特殊开卡金额
	 */
	@Column(name="PRICE_SPECIAL_T_")
	private String priceSpecialT;
	public void setPriceSpecialT(String priceSpecialT) {
		this.priceSpecialT=priceSpecialT;
	}
	public void setPriceSpecialT(Object priceSpecialT) {
		if (priceSpecialT != null) {
			this.priceSpecialT = priceSpecialT.toString();
		}
	}
	public String getPriceSpecialT() {
		return this.priceSpecialT ;
	}

	/**
	 * DAILY_TIME_
	 */
	@Column(name="DAILY_TIME_")
	private String dailyTime;
	public void setDailyTime(String dailyTime) {
		this.dailyTime=dailyTime;
	}
	public void setDailyTime(Object dailyTime) {
		if (dailyTime != null) {
			this.dailyTime = dailyTime.toString();
		}
	}
	public String getDailyTime() {
		return this.dailyTime ;
	}

	/**
	 * 创建时间数字型
	 */
	@Column(name="DAILY_TIME_LONG_")
	private Long dailyTimeLong;
	public void setDailyTimeLong(Long dailyTimeLong) {
		this.dailyTimeLong=dailyTimeLong;
	}
	public void setDailyTimeLong(Object dailyTimeLong) {
		if (dailyTimeLong != null) {
			if (dailyTimeLong instanceof Long) {
				this.dailyTimeLong= (Long) dailyTimeLong;
			}else if (StringTool.isInteger(dailyTimeLong.toString())) {
				this.dailyTimeLong = Long.parseLong(dailyTimeLong.toString());
			}
		}
	}
	public Long getDailyTimeLong() {
		return this.dailyTimeLong ;
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