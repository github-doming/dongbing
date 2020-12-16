package com.ibm.follow.servlet.cloud.ibm_log_event.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_log_event 
 * IBM_事件错误日志IBM_LOG_EVENT 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_log_event")
public class IbmLogEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_事件错误日志主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_LOG_EVENT_ID_")
	private String ibmLogEventId;
	public void setIbmLogEventId(String ibmLogEventId) {
		this.ibmLogEventId=ibmLogEventId;
	}
	public void setIbmLogEventId(Object ibmLogEventId) {
		if (ibmLogEventId != null) {
			this.ibmLogEventId = ibmLogEventId.toString();
		}
	}
	public String getIbmLogEventId() {
		return this.ibmLogEventId ;
	}

	/**
	 * 线程名称
	 */
	@Column(name="THREAD_NAME_")
	private String threadName;
	public void setThreadName(String threadName) {
		this.threadName=threadName;
	}
	public void setThreadName(Object threadName) {
		if (threadName != null) {
			this.threadName = threadName.toString();
		}
	}
	public String getThreadName() {
		return this.threadName ;
	}

	/**
	 * 事件编码
	 */
	@Column(name="EVENT_CODE_")
	private String eventCode;
	public void setEventCode(String eventCode) {
		this.eventCode=eventCode;
	}
	public void setEventCode(Object eventCode) {
		if (eventCode != null) {
			this.eventCode = eventCode.toString();
		}
	}
	public String getEventCode() {
		return this.eventCode ;
	}

	/**
	 * 执行类编码
	 */
	@Column(name="EXECUTE_CLASS_CODE_")
	private String executeClassCode;
	public void setExecuteClassCode(String executeClassCode) {
		this.executeClassCode=executeClassCode;
	}
	public void setExecuteClassCode(Object executeClassCode) {
		if (executeClassCode != null) {
			this.executeClassCode = executeClassCode.toString();
		}
	}
	public String getExecuteClassCode() {
		return this.executeClassCode ;
	}

	/**
	 * 错误内容
	 */
	@Column(name="ERROR_CONTEXT_")
	private String errorContext;
	public void setErrorContext(String errorContext) {
		this.errorContext=errorContext;
	}
	public void setErrorContext(Object errorContext) {
		if (errorContext != null) {
			this.errorContext = errorContext.toString();
		}
	}
	public String getErrorContext() {
		return this.errorContext ;
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