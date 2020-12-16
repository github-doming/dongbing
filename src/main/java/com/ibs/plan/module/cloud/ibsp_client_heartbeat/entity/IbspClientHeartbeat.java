package com.ibs.plan.module.cloud.ibsp_client_heartbeat.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_client_heartbeat 
 * IBSP_客户端心跳 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_client_heartbeat")
public class IbspClientHeartbeat implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_客户端心跳主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_CLIENT_HEARTBEAT_ID_")
	private String ibspClientHeartbeatId;
	public void setIbspClientHeartbeatId(String ibspClientHeartbeatId) {
		this.ibspClientHeartbeatId=ibspClientHeartbeatId;
	}
	public void setIbspClientHeartbeatId(Object ibspClientHeartbeatId) {
		if (ibspClientHeartbeatId != null) {
			this.ibspClientHeartbeatId = ibspClientHeartbeatId.toString();
		}
	}
	public String getIbspClientHeartbeatId() {
		return this.ibspClientHeartbeatId ;
	}

	/**
	 * 客户端编码
	 */
	@Column(name="CLIENT_CODE_")
	private String clientCode;
	public void setClientCode(String clientCode) {
		this.clientCode=clientCode;
	}
	public void setClientCode(Object clientCode) {
		if (clientCode != null) {
			this.clientCode = clientCode.toString();
		}
	}
	public String getClientCode() {
		return this.clientCode ;
	}

	/**
	 * 心跳结果
	 */
	@Column(name="HEARTBEAT_RESULT_")
	private String heartbeatResult;
	public void setHeartbeatResult(String heartbeatResult) {
		this.heartbeatResult=heartbeatResult;
	}
	public void setHeartbeatResult(Object heartbeatResult) {
		if (heartbeatResult != null) {
			this.heartbeatResult = heartbeatResult.toString();
		}
	}
	public String getHeartbeatResult() {
		return this.heartbeatResult ;
	}

	/**
	 * 心跳状态
	 */
	@Column(name="HEARTBEAT_STATUS_")
	private String heartbeatStatus;
	public void setHeartbeatStatus(String heartbeatStatus) {
		this.heartbeatStatus=heartbeatStatus;
	}
	public void setHeartbeatStatus(Object heartbeatStatus) {
		if (heartbeatStatus != null) {
			this.heartbeatStatus = heartbeatStatus.toString();
		}
	}
	public String getHeartbeatStatus() {
		return this.heartbeatStatus ;
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