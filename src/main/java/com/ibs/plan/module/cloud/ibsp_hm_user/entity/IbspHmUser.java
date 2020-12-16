package com.ibs.plan.module.cloud.ibsp_hm_user.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_hm_user 
 * IBSP_盘口会员用户 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_hm_user")
public class IbspHmUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_盘口会员用户主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_HM_USER_ID_")
	private String ibspHmUserId;
	public void setIbspHmUserId(String ibspHmUserId) {
		this.ibspHmUserId=ibspHmUserId;
	}
	public void setIbspHmUserId(Object ibspHmUserId) {
		if (ibspHmUserId != null) {
			this.ibspHmUserId = ibspHmUserId.toString();
		}
	}
	public String getIbspHmUserId() {
		return this.ibspHmUserId ;
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
		}
	}
	public String getHandicapName() {
		return this.handicapName ;
	}

	/**
	 * 在线会员主键组
	 */
	@Column(name="ONLINE_MEMBERS_IDS_")
	private String onlineMembersIds;
	public void setOnlineMembersIds(String onlineMembersIds) {
		this.onlineMembersIds=onlineMembersIds;
	}
	public void setOnlineMembersIds(Object onlineMembersIds) {
		if (onlineMembersIds != null) {
			this.onlineMembersIds = onlineMembersIds.toString();
		}
	}
	public String getOnlineMembersIds() {
		return this.onlineMembersIds ;
	}

	/**
	 * 在线会员数量
	 */
	@Column(name="ONLINE_MEMBERS_COUNT_")
	private Integer onlineMembersCount;
	public void setOnlineMembersCount(Integer onlineMembersCount) {
		this.onlineMembersCount=onlineMembersCount;
	}
	public void setOnlineMembersCount(Object onlineMembersCount) {
		if (onlineMembersCount != null) {
			if (onlineMembersCount instanceof Integer) {
				this.onlineMembersCount= (Integer) onlineMembersCount;
			}else if (StringTool.isInteger(onlineMembersCount.toString())) {
				this.onlineMembersCount = Integer.parseInt(onlineMembersCount.toString());
			}
		}
	}
	public Integer getOnlineMembersCount() {
		return this.onlineMembersCount ;
	}

	/**
	 * 最大会员在线数量
	 */
	@Column(name="ONLINE_NUMBER_MAX_")
	private Integer onlineNumberMax;
	public void setOnlineNumberMax(Integer onlineNumberMax) {
		this.onlineNumberMax=onlineNumberMax;
	}
	public void setOnlineNumberMax(Object onlineNumberMax) {
		if (onlineNumberMax != null) {
			if (onlineNumberMax instanceof Integer) {
				this.onlineNumberMax= (Integer) onlineNumberMax;
			}else if (StringTool.isInteger(onlineNumberMax.toString())) {
				this.onlineNumberMax = Integer.parseInt(onlineNumberMax.toString());
			}
		}
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