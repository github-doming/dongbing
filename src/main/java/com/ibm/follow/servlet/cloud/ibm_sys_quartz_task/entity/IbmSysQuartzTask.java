package com.ibm.follow.servlet.cloud.ibm_sys_quartz_task.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_quartz_task 
 * IBM_定时器任务 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_quartz_task")
public class IbmSysQuartzTask implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_定时器任务主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_QUARTZ_TASK_ID_")
	private String ibmSysQuartzTaskId;
	public void setIbmSysQuartzTaskId(String ibmSysQuartzTaskId) {
		this.ibmSysQuartzTaskId=ibmSysQuartzTaskId;
	}
	public void setIbmSysQuartzTaskId(Object ibmSysQuartzTaskId) {
		if (ibmSysQuartzTaskId != null) {
			this.ibmSysQuartzTaskId = ibmSysQuartzTaskId.toString();
		}
	}
	public String getIbmSysQuartzTaskId() {
		return this.ibmSysQuartzTaskId ;
	}

	/**
	 * 任务名称
	 */
	@Column(name="TASK_NAME_")
	private String taskName;
	public void setTaskName(String taskName) {
		this.taskName=taskName;
	}
	public void setTaskName(Object taskName) {
		if (taskName != null) {
			this.taskName = taskName.toString();
		}
	}
	public String getTaskName() {
		return this.taskName ;
	}

	/**
	 * 任务组
	 */
	@Column(name="TASK_GROUP_")
	private String taskGroup;
	public void setTaskGroup(String taskGroup) {
		this.taskGroup=taskGroup;
	}
	public void setTaskGroup(Object taskGroup) {
		if (taskGroup != null) {
			this.taskGroup = taskGroup.toString();
		}
	}
	public String getTaskGroup() {
		return this.taskGroup ;
	}

	/**
	 * 任务描述
	 */
	@Column(name="TASK_DESCRIPTION_")
	private String taskDescription;
	public void setTaskDescription(String taskDescription) {
		this.taskDescription=taskDescription;
	}
	public void setTaskDescription(Object taskDescription) {
		if (taskDescription != null) {
			this.taskDescription = taskDescription.toString();
		}
	}
	public String getTaskDescription() {
		return this.taskDescription ;
	}

	/**
	 * 任务执行者
	 */
	@Column(name="TASK_CLASS_")
	private String taskClass;
	public void setTaskClass(String taskClass) {
		this.taskClass=taskClass;
	}
	public void setTaskClass(Object taskClass) {
		if (taskClass != null) {
			this.taskClass = taskClass.toString();
		}
	}
	public String getTaskClass() {
		return this.taskClass ;
	}

	/**
	 * 任务数据
	 */
	@Column(name="TASK_CONTENT_")
	private String taskContent;
	public void setTaskContent(String taskContent) {
		this.taskContent=taskContent;
	}
	public void setTaskContent(Object taskContent) {
		if (taskContent != null) {
			this.taskContent = taskContent.toString();
		}
	}
	public String getTaskContent() {
		return this.taskContent ;
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