package com.ibm.follow.servlet.cloud.ibm_card_tree.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_card_tree 
 * IBM_充值卡卡类 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_card_tree")
public class IbmCardTree implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_充值卡卡类主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CARD_TREE_ID_")
	private String ibmCardTreeId;
	public void setIbmCardTreeId(String ibmCardTreeId) {
		this.ibmCardTreeId=ibmCardTreeId;
	}
	public void setIbmCardTreeId(Object ibmCardTreeId) {
		if (ibmCardTreeId != null) {
			this.ibmCardTreeId = ibmCardTreeId.toString();
		}
	}
	public String getIbmCardTreeId() {
		return this.ibmCardTreeId ;
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
	 * 卡类类型
	 */
	@Column(name="CARD_TREE_TYPE_")
	private String cardTreeType;
	public void setCardTreeType(String cardTreeType) {
		this.cardTreeType=cardTreeType;
	}
	public void setCardTreeType(Object cardTreeType) {
		if (cardTreeType != null) {
			this.cardTreeType = cardTreeType.toString();
		}
	}
	public String getCardTreeType() {
		return this.cardTreeType ;
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
	private Long cardPriceT;
	public void setCardPriceT(Long cardPriceT) {
		this.cardPriceT=cardPriceT;
	}
	public void setCardPriceT(Object cardPriceT) {
		if (cardPriceT != null) {
			if (cardPriceT instanceof Long) {
				this.cardPriceT= (Long) cardPriceT;
			}else if (StringTool.isInteger(cardPriceT.toString())) {
				this.cardPriceT = Long.parseLong(cardPriceT.toString());
			}
		}
	}
	public Long getCardPriceT() {
		return this.cardPriceT ;
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
	@Column(name="CREATER_NAME_")
	private String createrName;
	public void setCreaterName(String createrName) {
		this.createrName=createrName;
	}
	public void setCreaterName(Object createrName) {
		if (createrName != null) {
			this.createrName = createrName.toString();
		}
	}
	public String getCreaterName() {
		return this.createrName ;
	}


	/**
	 * 排序
	 */
	@Column(name="SN_")
	private int sn;
	public void setSn(int sn) {
		this.sn=sn;
	}
	public int getSn() {
		return this.sn ;
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