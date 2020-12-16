package com.ibs.plan.module.cloud.ibsp_sys_thread_pool.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_thread_pool 
 * IBM_线程池信息 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_sys_thread_pool")
public class IbspSysThreadPool implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_线程池信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_SYS_THREAD_POOL_ID_")
	private String ibspSysThreadPoolId;
	public void setIbspSysThreadPoolId(String ibspSysThreadPoolId) {
		this.ibspSysThreadPoolId=ibspSysThreadPoolId;
	}
	public void setIbspSysThreadPoolId(Object ibspSysThreadPoolId) {
		if (ibspSysThreadPoolId != null) {
			this.ibspSysThreadPoolId = ibspSysThreadPoolId.toString();
		}
	}
	public String getIbspSysThreadPoolId() {
		return this.ibspSysThreadPoolId ;
	}

	/**
	 * 服务IP
	 */
	@Column(name="SERVER_IP_")
	private String serverIp;
	public void setServerIp(String serverIp) {
		this.serverIp=serverIp;
	}
	public void setServerIp(Object serverIp) {
		if (serverIp != null) {
			this.serverIp = serverIp.toString();
		}
	}
	public String getServerIp() {
		return this.serverIp ;
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
	 * 模块名称
	 */
	@Column(name="MODULE_NAME_")
	private String moduleName;
	public void setModuleName(String moduleName) {
		this.moduleName=moduleName;
	}
	public void setModuleName(Object moduleName) {
		if (moduleName != null) {
			this.moduleName = moduleName.toString();
		}
	}
	public String getModuleName() {
		return this.moduleName ;
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
	 * 当前线程数
	 */
	@Column(name="POOL_SIZE_")
	private String poolSize;
	public void setPoolSize(String poolSize) {
		this.poolSize=poolSize;
	}
	public void setPoolSize(Object poolSize) {
		if (poolSize != null) {
			this.poolSize = poolSize.toString();
		}
	}
	public String getPoolSize() {
		return this.poolSize ;
	}

	/**
	 * 核心线程池容量
	 */
	@Column(name="CORE_SIZE_")
	private String coreSize;
	public void setCoreSize(String coreSize) {
		this.coreSize=coreSize;
	}
	public void setCoreSize(Object coreSize) {
		if (coreSize != null) {
			this.coreSize = coreSize.toString();
		}
	}
	public String getCoreSize() {
		return this.coreSize ;
	}

	/**
	 * MAX线程池容量
	 */
	@Column(name="MAX_SIZE_")
	private String maxSize;
	public void setMaxSize(String maxSize) {
		this.maxSize=maxSize;
	}
	public void setMaxSize(Object maxSize) {
		if (maxSize != null) {
			this.maxSize = maxSize.toString();
		}
	}
	public String getMaxSize() {
		return this.maxSize ;
	}

	/**
	 * 队列容量
	 */
	@Column(name="QUEUE_SIZE_")
	private String queueSize;
	public void setQueueSize(String queueSize) {
		this.queueSize=queueSize;
	}
	public void setQueueSize(Object queueSize) {
		if (queueSize != null) {
			this.queueSize = queueSize.toString();
		}
	}
	public String getQueueSize() {
		return this.queueSize ;
	}

	/**
	 * 线程活跃数
	 */
	@Column(name="ACTIVE_COUNT_")
	private String activeCount;
	public void setActiveCount(String activeCount) {
		this.activeCount=activeCount;
	}
	public void setActiveCount(Object activeCount) {
		if (activeCount != null) {
			this.activeCount = activeCount.toString();
		}
	}
	public String getActiveCount() {
		return this.activeCount ;
	}

	/**
	 * 总任务数
	 */
	@Column(name="TASK_COUNT_")
	private String taskCount;
	public void setTaskCount(String taskCount) {
		this.taskCount=taskCount;
	}
	public void setTaskCount(Object taskCount) {
		if (taskCount != null) {
			this.taskCount = taskCount.toString();
		}
	}
	public String getTaskCount() {
		return this.taskCount ;
	}

	/**
	 * 已完成任务数
	 */
	@Column(name="COMPLETED_TASK_COUNT_")
	private String completedTaskCount;
	public void setCompletedTaskCount(String completedTaskCount) {
		this.completedTaskCount=completedTaskCount;
	}
	public void setCompletedTaskCount(Object completedTaskCount) {
		if (completedTaskCount != null) {
			this.completedTaskCount = completedTaskCount.toString();
		}
	}
	public String getCompletedTaskCount() {
		return this.completedTaskCount ;
	}

	/**
	 * 线程活跃时间
	 */
	@Column(name="KEEP_ALIVE_TIME_")
	private String keepAliveTime;
	public void setKeepAliveTime(String keepAliveTime) {
		this.keepAliveTime=keepAliveTime;
	}
	public void setKeepAliveTime(Object keepAliveTime) {
		if (keepAliveTime != null) {
			this.keepAliveTime = keepAliveTime.toString();
		}
	}
	public String getKeepAliveTime() {
		return this.keepAliveTime ;
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