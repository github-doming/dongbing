package com.ibm.follow.servlet.cloud.ibm_ha_user.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_ha_user 
 * IBM_盘口代理用户的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_ha_user")
public class IbmHaUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口代理用户主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HA_USER_ID_")
	private String ibmHaUserId;
	public void setIbmHaUserId(String ibmHaUserId) {
		this.ibmHaUserId=ibmHaUserId;
	}
	public void setIbmHaUserId(Object ibmHaUserId) {
		if (ibmHaUserId != null) {
			this.ibmHaUserId = ibmHaUserId.toString();
		}else{
			this.ibmHaUserId = null;
		}
	}
	public String getIbmHaUserId() {
		return this.ibmHaUserId ;
	}

	/**
	 * 用户主键
	 */
	@Column(name="APP_USER_ID_")
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public void setAppUserId(Object appUserId) {
		if (appUserId != null) {
			this.appUserId = appUserId.toString();
		}else{
			this.appUserId = null;
		}
	}
	public String getAppUserId() {
		return this.appUserId ;
	}

	/**
	 * 盘口主键
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public void setHandicapId(Object handicapId) {
		if (handicapId != null) {
			this.handicapId = handicapId.toString();
		}else{
			this.handicapId = null;
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
	}

	/**
	 * 盘口编码
	 */
	@Column(name="HANDICAP_CODE_")
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public void setHandicapCode(Object handicapCode) {
		if (handicapCode != null) {
			this.handicapCode = handicapCode.toString();
		}else{
			this.handicapCode = null;
		}
	}
	public String getHandicapCode() {
		return this.handicapCode ;
	}

	/**
	 * 盘口名称
	 */
	@Column(name="HANDICAP_NAME_")
	private String handicapName;
	public void setHandicapName(String handicapName) {
		this.handicapName=handicapName;
	}
	public void setHandicapName(Object handicapName) {
		if (handicapName != null) {
			this.handicapName = handicapName.toString();
		}else{
			this.handicapName = null;
		}
	}
	public String getHandicapName() {
		return this.handicapName ;
	}

	/**
	 * 在线代理主键组
	 */
	@Column(name="ONLINE_AGENTS_IDS_")
	private String onlineAgentsIds;
	public void setOnlineAgentsIds(String onlineAgentsIds) {
		this.onlineAgentsIds=onlineAgentsIds;
	}
	public void setOnlineAgentsIds(Object onlineAgentsIds) {
		if (onlineAgentsIds != null) {
			this.onlineAgentsIds = onlineAgentsIds.toString();
		}else{
			this.onlineAgentsIds = null;
		}
	}
	public String getOnlineAgentsIds() {
		return this.onlineAgentsIds ;
	}

	/**
	 * 在线代理数量
	 */
	@Column(name="ONLINE_AGENTS_COUNT_")
	private Integer onlineAgentsCount;
	public void setOnlineAgentsCount(Integer onlineAgentsCount) {
		this.onlineAgentsCount=onlineAgentsCount;
	}
	public Integer getOnlineAgentsCount() {
		return this.onlineAgentsCount ;
	}

	/**
	 * 最大代理在线数量
	 */
	@Column(name="ONLINE_NUMBER_MAX_")
	private Integer onlineNumberMax;
	public void setOnlineNumberMax(Integer onlineNumberMax) {
		this.onlineNumberMax=onlineNumberMax;
	}
	public Integer getOnlineNumberMax() {
		return this.onlineNumberMax ;
	}

	/**
	 * 使用频次
	 */
	@Column(name="FREQUENCY_")
	private Integer frequency;
	public void setFrequency(Integer frequency) {
		this.frequency=frequency;
	}
	public void setFrequency(Object frequency) {
		if (frequency != null) {
			if (frequency instanceof Integer) {
				this.frequency= (Integer) frequency;
			}else if (StringTool.isInteger(frequency.toString())) {
				this.frequency = Integer.parseInt(frequency.toString());
			}
		}else{
			this.frequency = null;
		}
	}
	public Integer getFrequency() {
		return this.frequency ;
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
	public void setCreateTime(Object createTime) {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}else{
			this.createTime = null;
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
		}else{
			this.createTimeLong = null;
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
	public void setUpdateTime(Object updateTime) {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}else{
			this.updateTime = null;
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
		}else{
			this.updateTimeLong = null;
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
		}else{
			this.state = null;
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
		}else{
			this.desc = null;
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