package com.ibm.follow.servlet.cloud.ibm_sys_quartz_simple.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_quartz_simple 
 * IBM_定时器运行设置 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_quartz_simple")
public class IbmSysQuartzSimple implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_定时器CRON主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_QUARTZ_SIMPLE_ID_")
	private String ibmSysQuartzSimpleId;
	public void setIbmSysQuartzSimpleId(String ibmSysQuartzSimpleId) {
		this.ibmSysQuartzSimpleId=ibmSysQuartzSimpleId;
	}
	public void setIbmSysQuartzSimpleId(Object ibmSysQuartzSimpleId) {
		if (ibmSysQuartzSimpleId != null) {
			this.ibmSysQuartzSimpleId = ibmSysQuartzSimpleId.toString();
		}
	}
	public String getIbmSysQuartzSimpleId() {
		return this.ibmSysQuartzSimpleId ;
	}

	/**
	 * 定时器任务主键
	 */
	@Column(name="QUARTZ_TASK_ID_")
	private String quartzTaskId;
	public void setQuartzTaskId(String quartzTaskId) {
		this.quartzTaskId=quartzTaskId;
	}
	public void setQuartzTaskId(Object quartzTaskId) {
		if (quartzTaskId != null) {
			this.quartzTaskId = quartzTaskId.toString();
		}
	}
	public String getQuartzTaskId() {
		return this.quartzTaskId ;
	}

	/**
	 * 启动时间
	 */
	@Column(name="START_TIME_")
	private Long startTime;
	public void setStartTime(Long startTime) {
		this.startTime=startTime;
	}
	public void setStartTime(Object startTime) {
		if (startTime != null) {
			if (startTime instanceof Long) {
				this.startTime= (Long) startTime;
			}else if (StringTool.isInteger(startTime.toString())) {
				this.startTime = Long.parseLong(startTime.toString());
			}
		}
	}
	public Long getStartTime() {
		return this.startTime ;
	}

	/**
	 * 间隔时间
	 */
	@Column(name="INTERVAL_TIME_")
	private Integer intervalTime;
	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime=intervalTime;
	}
	public void setIntervalTime(Object intervalTime) {
		if (intervalTime != null) {
			if (intervalTime instanceof Integer) {
				this.intervalTime= (Integer) intervalTime;
			}else if (StringTool.isInteger(intervalTime.toString())) {
				this.intervalTime = Integer.parseInt(intervalTime.toString());
			}
		}
	}
	public Integer getIntervalTime() {
		return this.intervalTime ;
	}

	/**
	 * 循环次数
	 */
	@Column(name="REPEAT_COUNT_")
	private Integer repeatCount;
	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount=repeatCount;
	}
	public void setRepeatCount(Object repeatCount) {
		if (repeatCount != null) {
			if (repeatCount instanceof Integer) {
				this.repeatCount= (Integer) repeatCount;
			}else if (StringTool.isInteger(repeatCount.toString())) {
				this.repeatCount = Integer.parseInt(repeatCount.toString());
			}
		}
	}
	public Integer getRepeatCount() {
		return this.repeatCount ;
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