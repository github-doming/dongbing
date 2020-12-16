package com.ibm.follow.servlet.cloud.ibm_manage_time.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_manage_time 
 * IBM_用户时长的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_manage_time")
public class IbmManageTime implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_用户时长主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_MANAGE_TIME_ID_")
	private String ibmManageTimeId;
	public void setIbmManageTimeId(String ibmManageTimeId) {
		this.ibmManageTimeId=ibmManageTimeId;
	}
	public void setIbmManageTimeId(Object ibmManageTimeId) {
		if (ibmManageTimeId != null) {
			this.ibmManageTimeId = ibmManageTimeId.toString();
		}
	}
	public String getIbmManageTimeId() {
		return this.ibmManageTimeId ;
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
	 * 末次报表主键
	 */
	@Column(name="REP_TIME_ID_")
	private String repTimeId;
	public void setRepTimeId(String repTimeId) {
		this.repTimeId=repTimeId;
	}
	public void setRepTimeId(Object repTimeId) {
		if (repTimeId != null) {
			this.repTimeId = repTimeId.toString();
		}
	}
	public String getRepTimeId() {
		return this.repTimeId ;
	}

	/**
	 * 开始时间
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
	 * 结束时间
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
	 * 创建者CREATE_USER_
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
	 * 更新者UPDATE_USER_
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}
	}
	public String getUpdateUser() {
		return this.updateUser ;
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