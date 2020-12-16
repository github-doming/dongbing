package com.ibm.follow.servlet.cloud.ibm_card_report.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_card_report 
 * IBM_充值卡报表 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_card_report")
public class IbmCardReport implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_充值卡报表主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CARD_REPORT_ID_")
	private String ibmCardReportId;
	public void setIbmCardReportId(String ibmCardReportId) {
		this.ibmCardReportId=ibmCardReportId;
	}
	public void setIbmCardReportId(Object ibmCardReportId) {
		if (ibmCardReportId != null) {
			this.ibmCardReportId = ibmCardReportId.toString();
		}
	}
	public String getIbmCardReportId() {
		return this.ibmCardReportId ;
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
	 * 管理员用户名
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
	 * 总价值
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
	 * 激活总点数
	 */
	@Column(name="POINT_TOTAL_")
	private Long pointTotal;
	public void setPointTotal(Long pointTotal) {
		this.pointTotal=pointTotal;
	}
	public void setPointTotal(Object pointTotal) {
		if (pointTotal != null) {
			if (pointTotal instanceof Long) {
				this.pointTotal= (Long) pointTotal;
			}else if (StringTool.isInteger(pointTotal.toString())) {
				this.pointTotal = Long.parseLong(pointTotal.toString());
			}
		}
	}
	public Long getPointTotal() {
		return this.pointTotal ;
	}

	/**
	 * 总数量
	 */
	@Column(name="CARD_TOTAL_")
	private Integer cardTotal;
	public void setCardTotal(Integer cardTotal) {
		this.cardTotal=cardTotal;
	}
	public void setCardTotal(Object cardTotal) {
		if (cardTotal != null) {
			if (cardTotal instanceof Integer) {
				this.cardTotal= (Integer) cardTotal;
			}else if (StringTool.isInteger(cardTotal.toString())) {
				this.cardTotal = Integer.parseInt(cardTotal.toString());
			}
		}
	}
	public Integer getCardTotal() {
		return this.cardTotal ;
	}

	/**
	 * 创建张数合计
	 */
	@Column(name="CARD_ACTIVATE_TOTAL_")
	private Integer cardActivateTotal;
	public void setCardActivateTotal(Integer cardActivateTotal) {
		this.cardActivateTotal=cardActivateTotal;
	}
	public void setCardActivateTotal(Object cardActivateTotal) {
		if (cardActivateTotal != null) {
			if (cardActivateTotal instanceof Integer) {
				this.cardActivateTotal= (Integer) cardActivateTotal;
			}else if (StringTool.isInteger(cardActivateTotal.toString())) {
				this.cardActivateTotal = Integer.parseInt(cardActivateTotal.toString());
			}
		}
	}
	public Integer getCardActivateTotal() {
		return this.cardActivateTotal ;
	}

	/**
	 * 记录目标 数据给谁看的 -SELF —PARENT
	 */
	@Column(name="TARGET_")
	private String target;
	public void setTarget(String target) {
		this.target=target;
	}
	public void setTarget(Object target) {
		if (target != null) {
			this.target = target.toString();
		}
	}
	public String getTarget() {
		return this.target ;
	}

	/**
	 * 报表时间-每天记录时间数字型
	 */
	@Column(name="REPORT_TIME_LONG_")
	private Long reportTimeLong;
	public void setReportTimeLong(Long reportTimeLong) {
		this.reportTimeLong=reportTimeLong;
	}
	public void setReportTimeLong(Object reportTimeLong) {
		if (reportTimeLong != null) {
			if (reportTimeLong instanceof Long) {
				this.reportTimeLong= (Long) reportTimeLong;
			}else if (StringTool.isInteger(reportTimeLong.toString())) {
				this.reportTimeLong = Long.parseLong(reportTimeLong.toString());
			}
		}
	}
	public Long getReportTimeLong() {
		return this.reportTimeLong ;
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
	 * 版本号
	 */
	@Column(name="EDITION_")
	private Integer edition;
	public void setEdition(Integer edition) {
		this.edition=edition;
	}
	public void setEdition(Object edition) {
		if (edition != null) {
			if (edition instanceof Integer) {
				this.edition= (Integer) edition;
			}else if (StringTool.isInteger(edition.toString())) {
				this.edition = Integer.parseInt(edition.toString());
			}
		}
	}
	public Integer getEdition() {
		return this.edition ;
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