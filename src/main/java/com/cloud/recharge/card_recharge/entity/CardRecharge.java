package com.cloud.recharge.card_recharge.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table card_recharge 
 * 充值卡 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "card_recharge")
public class CardRecharge implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBS_充值卡主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CARD_RECHARGE_ID_")
	private String cardRechargeId;
	public void setCardRechargeId(String cardRechargeId) {
		this.cardRechargeId=cardRechargeId;
	}
	public void setCardRechargeId(Object cardRechargeId) {
		if (cardRechargeId != null) {
			this.cardRechargeId = cardRechargeId.toString();
		}
	}
	public String getCardRechargeId() {
		return this.cardRechargeId ;
	}

	/**
	 * IBM_充值卡卡类主键
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
	 * 所属代理AGENT_USER_ID_
	 */
	@Column(name="OWNER_USER_ID_")
	private String owneUserId;
	public void setOwneUserId(String owneUserId) {
		this.owneUserId=owneUserId;
	}
	public void setOwneUserId(Object owneUserId) {
		if (owneUserId != null) {
			this.owneUserId = owneUserId.toString();
		}
	}
	public String getOwneUserId() {
		return this.owneUserId ;
	}

	/**
	 * 拥有者名
	 */
	@Column(name="OWNER_NAME_")
	private String ownerName;
	public void setOwnerName(String ownerName) {
		this.ownerName=ownerName;
	}
	public void setOwnerName(Object ownerName) {
		if (ownerName != null) {
			this.ownerName = ownerName.toString();
		}
	}
	public String getOwnerName() {
		return this.ownerName ;
	}

	/**
	 * 使用用户
	 */
	@Column(name="USE_USER_ID_")
	private String useUserId;
	public void setUseUserId(String useUserId) {
		this.useUserId=useUserId;
	}
	public void setUseUserId(Object useUserId) {
		if (useUserId != null) {
			this.useUserId = useUserId.toString();
		}
	}
	public String getUseUserId() {
		return this.useUserId ;
	}

	/**
	 * 使用者名
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
	 * 充值卡密
	 */
	@Column(name="CARD_PASSWORD_")
	private String cardPassword;
	public void setCardPassword(String cardPassword) {
		this.cardPassword=cardPassword;
	}
	public void setCardPassword(Object cardPassword) {
		if (cardPassword != null) {
			this.cardPassword = cardPassword.toString();
		}
	}
	public String getCardPassword() {
		return this.cardPassword ;
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
	 * 卡类点数
	 */
	@Column(name="CARD_TREE_POINT_")
	private Integer cardTreePoint;
	public void setCardTreePoint(Integer cardTreePoint) {
		this.cardTreePoint=cardTreePoint;
	}
	public void setCardTreePoint(Object cardTreePoint) {
		if (cardTreePoint != null) {
			if (cardTreePoint instanceof Integer) {
				this.cardTreePoint= (Integer) cardTreePoint;
			}else if (StringTool.isInteger(cardTreePoint.toString())) {
				this.cardTreePoint = Integer.parseInt(cardTreePoint.toString());
			}
		}
	}
	public Integer getCardTreePoint() {
		return this.cardTreePoint ;
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
	 * 充值卡状态
	 */
	@Column(name="CARD_RECHARGE_STATE_")
	private String cardRechargeState;
	public void setCardRechargeState(String cardRechargeState) {
		this.cardRechargeState=cardRechargeState;
	}
	public void setCardRechargeState(Object cardRechargeState) {
		if (cardRechargeState != null) {
			this.cardRechargeState = cardRechargeState.toString();
		}
	}
	public String getCardRechargeState() {
		return this.cardRechargeState ;
	}

	/**
	 * 创建用户主键
	 */
	@Column(name="CREATE_USER_ID_")
	private String createUserId;
	public void setCreateUserId(String createUserId) {
		this.createUserId=createUserId;
	}
	public void setCreateUserId(Object createUserId) {
		if (createUserId != null) {
			this.createUserId = createUserId.toString();
		}
	}
	public String getCreateUserId() {
		return this.createUserId ;
	}

	/**
	 * 创建者名
	 */
	@Column(name="CREATOR_NAME_")
	private String creatorName;
	public void setCreatorName(String creatorName) {
		this.creatorName=creatorName;
	}
	public void setCreatorName(Object creatorName) {
		if (creatorName != null) {
			this.creatorName = creatorName.toString();
		}
	}
	public String getCreatorName() {
		return this.creatorName ;
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