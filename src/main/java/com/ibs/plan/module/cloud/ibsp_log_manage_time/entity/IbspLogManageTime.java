package com.ibs.plan.module.cloud.ibsp_log_manage_time.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_log_manage_time 
 * IBSP_可用时长情况 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_log_manage_time")
public class IbspLogManageTime implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_可用时长情况主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_LOG_MANAGE_TIME_ID_")
	private String ibspLogManageTimeId;
	public void setIbspLogManageTimeId(String ibspLogManageTimeId) {
		this.ibspLogManageTimeId=ibspLogManageTimeId;
	}
	public void setIbspLogManageTimeId(Object ibspLogManageTimeId) {
		if (ibspLogManageTimeId != null) {
			this.ibspLogManageTimeId = ibspLogManageTimeId.toString();
		}
	}
	public String getIbspLogManageTimeId() {
		return this.ibspLogManageTimeId ;
	}

	/**
	 * 报表点数主键
	 */
	@Column(name="LOG_POINT_ID_")
	private String logPointId;
	public void setLogPointId(String logPointId) {
		this.logPointId=logPointId;
	}
	public void setLogPointId(Object logPointId) {
		if (logPointId != null) {
			this.logPointId = logPointId.toString();
		}
	}
	public String getLogPointId() {
		return this.logPointId ;
	}

	/**
	 * 上一次主键
	 */
	@Column(name="PRE_ID_")
	private String preId;
	public void setPreId(String preId) {
		this.preId=preId;
	}
	public void setPreId(Object preId) {
		if (preId != null) {
			this.preId = preId.toString();
		}
	}
	public String getPreId() {
		return this.preId ;
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
	 * 本次消耗积分
	 */
	@Column(name="USED_POINT_T_")
	private Long usedPointT;
	public void setUsedPointT(Long usedPointT) {
		this.usedPointT=usedPointT;
	}
	public void setUsedPointT(Object usedPointT) {
		if (usedPointT != null) {
			if (usedPointT instanceof Long) {
				this.usedPointT= (Long) usedPointT;
			}else if (StringTool.isInteger(usedPointT.toString())) {
				this.usedPointT = Long.parseLong(usedPointT.toString());
			}
		}
	}
	public Long getUsedPointT() {
		return this.usedPointT ;
	}

	/**
	 * 增加时长数字型
	 */
	@Column(name="ADD_TIME_LONG_")
	private Integer addTimeLong;
	public void setAddTimeLong(Integer addTimeLong) {
		this.addTimeLong=addTimeLong;
	}
	public void setAddTimeLong(Object addTimeLong) {
		if (addTimeLong != null) {
			if (addTimeLong instanceof Integer) {
				this.addTimeLong= (Integer) addTimeLong;
			}else if (StringTool.isInteger(addTimeLong.toString())) {
				this.addTimeLong = Integer.parseInt(addTimeLong.toString());
			}
		}
	}
	public Integer getAddTimeLong() {
		return this.addTimeLong ;
	}

	/**
	 * 开始时间START_TIME_
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="START_TIME_")
	private Date startTime;
	public void setStartTime(Date startTime) {
		this.startTime=startTime;
	}
	public void setStartTime(Object startTime) throws ParseException {
		if (startTime != null) {
			if (startTime instanceof Date) {
				this.startTime= (Date) startTime;
			}else if (StringTool.isInteger(startTime.toString())) {
				this.startTime = new Date(Long.parseLong(startTime.toString()));
			}else {
				this.startTime = DateTool.getTime(startTime.toString());
			}
		}
	}
	public Date getStartTime() {
		return this.startTime ;
	}

	/**
	 * 开始时间数字型
	 */
	@Column(name="START_TIME_LONG_")
	private Long startTimeLong;
	public void setStartTimeLong(Long startTimeLong) {
		this.startTimeLong=startTimeLong;
	}
	public void setStartTimeLong(Object startTimeLong) {
		if (startTimeLong != null) {
			if (startTimeLong instanceof Long) {
				this.startTimeLong= (Long) startTimeLong;
			}else if (StringTool.isInteger(startTimeLong.toString())) {
				this.startTimeLong = Long.parseLong(startTimeLong.toString());
			}
		}
	}
	public Long getStartTimeLong() {
		return this.startTimeLong ;
	}

	/**
	 * 结束时间END_TIME_
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="END_TIME_")
	private Date endTime;
	public void setEndTime(Date endTime) {
		this.endTime=endTime;
	}
	public void setEndTime(Object endTime) throws ParseException {
		if (endTime != null) {
			if (endTime instanceof Date) {
				this.endTime= (Date) endTime;
			}else if (StringTool.isInteger(endTime.toString())) {
				this.endTime = new Date(Long.parseLong(endTime.toString()));
			}else {
				this.endTime = DateTool.getTime(endTime.toString());
			}
		}
	}
	public Date getEndTime() {
		return this.endTime ;
	}

	/**
	 * 结束时间数字型
	 */
	@Column(name="END_TIME_LONG_")
	private Long endTimeLong;
	public void setEndTimeLong(Long endTimeLong) {
		this.endTimeLong=endTimeLong;
	}
	public void setEndTimeLong(Object endTimeLong) {
		if (endTimeLong != null) {
			if (endTimeLong instanceof Long) {
				this.endTimeLong= (Long) endTimeLong;
			}else if (StringTool.isInteger(endTimeLong.toString())) {
				this.endTimeLong = Long.parseLong(endTimeLong.toString());
			}
		}
	}
	public Long getEndTimeLong() {
		return this.endTimeLong ;
	}

	/**
	 * 原结束时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="REP_END_TIME_")
	private Date repEndTime;
	public void setRepEndTime(Date repEndTime) {
		this.repEndTime=repEndTime;
	}
	public void setRepEndTime(Object repEndTime) throws ParseException {
		if (repEndTime != null) {
			if (repEndTime instanceof Date) {
				this.repEndTime= (Date) repEndTime;
			}else if (StringTool.isInteger(repEndTime.toString())) {
				this.repEndTime = new Date(Long.parseLong(repEndTime.toString()));
			}else {
				this.repEndTime = DateTool.getTime(repEndTime.toString());
			}
		}
	}
	public Date getRepEndTime() {
		return this.repEndTime ;
	}

	/**
	 * 原结束时间数字型
	 */
	@Column(name="REP_END_TIME_LONG_")
	private Long repEndTimeLong;
	public void setRepEndTimeLong(Long repEndTimeLong) {
		this.repEndTimeLong=repEndTimeLong;
	}
	public void setRepEndTimeLong(Object repEndTimeLong) {
		if (repEndTimeLong != null) {
			if (repEndTimeLong instanceof Long) {
				this.repEndTimeLong= (Long) repEndTimeLong;
			}else if (StringTool.isInteger(repEndTimeLong.toString())) {
				this.repEndTimeLong = Long.parseLong(repEndTimeLong.toString());
			}
		}
	}
	public Long getRepEndTimeLong() {
		return this.repEndTimeLong ;
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