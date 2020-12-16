package com.ibm.follow.servlet.cloud.ibm_log_thread_item.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_log_thread_item 
 * IBM_线程池详情日志 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_log_thread_item")
public class IbmLogThreadItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_线程池详情日志主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_LOG_THREAD_ITEM_ID_")
	private String ibmLogThreadItemId;
	public void setIbmLogThreadItemId(String ibmLogThreadItemId) {
		this.ibmLogThreadItemId=ibmLogThreadItemId;
	}
	public void setIbmLogThreadItemId(Object ibmLogThreadItemId) {
		if (ibmLogThreadItemId != null) {
			this.ibmLogThreadItemId = ibmLogThreadItemId.toString();
		}
	}
	public String getIbmLogThreadItemId() {
		return this.ibmLogThreadItemId ;
	}

	/**
	 * 模块编码
	 */
	@Column(name="MODULE_CODE_")
	private String moduleCode;
	public void setModuleCode(String moduleCode) {
		this.moduleCode=moduleCode;
	}
	public void setModuleCode(Object moduleCode) {
		if (moduleCode != null) {
			this.moduleCode = moduleCode.toString();
		}
	}
	public String getModuleCode() {
		return this.moduleCode ;
	}

	/**
	 * 模块类型
	 */
	@Column(name="MODULE_TYPE_")
	private String moduleType;
	public void setModuleType(String moduleType) {
		this.moduleType=moduleType;
	}
	public void setModuleType(Object moduleType) {
		if (moduleType != null) {
			this.moduleType = moduleType.toString();
		}
	}
	public String getModuleType() {
		return this.moduleType ;
	}

	/**
	 * 线程池编码
	 */
	@Column(name="THREAD_CODE_")
	private String threadCode;
	public void setThreadCode(String threadCode) {
		this.threadCode=threadCode;
	}
	public void setThreadCode(Object threadCode) {
		if (threadCode != null) {
			this.threadCode = threadCode.toString();
		}
	}
	public String getThreadCode() {
		return this.threadCode ;
	}

	/**
	 * 线程活跃数
	 */
	@Column(name="THREAD_ACTIVE_COUNT_")
	private String threadActiveCount;
	public void setThreadActiveCount(String threadActiveCount) {
		this.threadActiveCount=threadActiveCount;
	}
	public void setThreadActiveCount(Object threadActiveCount) {
		if (threadActiveCount != null) {
			this.threadActiveCount = threadActiveCount.toString();
		}
	}
	public String getThreadActiveCount() {
		return this.threadActiveCount ;
	}

	/**
	 * 总任务数
	 */
	@Column(name="THREAD_TASK_COUNT_")
	private String threadTaskCount;
	public void setThreadTaskCount(String threadTaskCount) {
		this.threadTaskCount=threadTaskCount;
	}
	public void setThreadTaskCount(Object threadTaskCount) {
		if (threadTaskCount != null) {
			this.threadTaskCount = threadTaskCount.toString();
		}
	}
	public String getThreadTaskCount() {
		return this.threadTaskCount ;
	}

	/**
	 * 已完成任务数
	 */
	@Column(name="THREAD_COMPLETED_TASK_COUNT_")
	private String threadCompletedTaskCount;
	public void setThreadCompletedTaskCount(String threadCompletedTaskCount) {
		this.threadCompletedTaskCount=threadCompletedTaskCount;
	}
	public void setThreadCompletedTaskCount(Object threadCompletedTaskCount) {
		if (threadCompletedTaskCount != null) {
			this.threadCompletedTaskCount = threadCompletedTaskCount.toString();
		}
	}
	public String getThreadCompletedTaskCount() {
		return this.threadCompletedTaskCount ;
	}

	/**
	 * 线程活跃时间
	 */
	@Column(name="THREAD_KEEP_ALIVE_TIME_")
	private String threadKeepAliveTime;
	public void setThreadKeepAliveTime(String threadKeepAliveTime) {
		this.threadKeepAliveTime=threadKeepAliveTime;
	}
	public void setThreadKeepAliveTime(Object threadKeepAliveTime) {
		if (threadKeepAliveTime != null) {
			this.threadKeepAliveTime = threadKeepAliveTime.toString();
		}
	}
	public String getThreadKeepAliveTime() {
		return this.threadKeepAliveTime ;
	}

	/**
	 * 创建时间CREATE_TIME_
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
	 * 创建时间数字型CREATE_TIME_LONG_
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
	 * 更新者
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
	 * 更新时间UPDATE_TIME_
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
	 * 更新时间数字型UPDATE_TIME_LONG_
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