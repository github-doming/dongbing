package com.ibm.follow.servlet.cloud.ibm_card_operate_log.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_card_operate_log 
 * IBM_充值卡操作日志 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_card_operate_log")
public class IbmCardOperateLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_充值卡操作日志主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CARD_OPERATE_LOG_ID_")
	private String ibmCardOperateLogId;
	public void setIbmCardOperateLogId(String ibmCardOperateLogId) {
		this.ibmCardOperateLogId=ibmCardOperateLogId;
	}
	public void setIbmCardOperateLogId(Object ibmCardOperateLogId) {
		if (ibmCardOperateLogId != null) {
			this.ibmCardOperateLogId = ibmCardOperateLogId.toString();
		}
	}
	public String getIbmCardOperateLogId() {
		return this.ibmCardOperateLogId ;
	}

	/**
	 * 充值卡主键
	 */
	@Column(name="CARD_ID_")
	private String cardId;
	public void setCardId(String cardId) {
		this.cardId=cardId;
	}
	public void setCardId(Object cardId) {
		if (cardId != null) {
			this.cardId = cardId.toString();
		}
	}
	public String getCardId() {
		return this.cardId ;
	}

	/**
	 * 操作用户主键
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
	 * 操作类型
	 */
	@Column(name="OPERATE_TYPE_")
	private String operateType;
	public void setOperateType(String operateType) {
		this.operateType=operateType;
	}
	public void setOperateType(Object operateType) {
		if (operateType != null) {
			this.operateType = operateType.toString();
		}
	}
	public String getOperateType() {
		return this.operateType ;
	}

	/**
	 * 操作内容
	 */
	@Column(name="OPERATE_CONTECT_")
	private String operateContect;
	public void setOperateContect(String operateContect) {
		this.operateContect=operateContect;
	}
	public void setOperateContect(Object operateContect) {
		if (operateContect != null) {
			this.operateContect = operateContect.toString();
		}
	}
	public String getOperateContect() {
		return this.operateContect ;
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