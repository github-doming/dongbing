package com.ibs.plan.module.cloud.ibsp_log_plan.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_log_plan 
 * IBSP_方案用户操作日志 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_log_plan")
public class IbspLogPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_方案用户操作日志主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_LOG_PLAN_ID_")
	private String ibspLogPlanId;
	public void setIbspLogPlanId(String ibspLogPlanId) {
		this.ibspLogPlanId=ibspLogPlanId;
	}
	public void setIbspLogPlanId(Object ibspLogPlanId) {
		if (ibspLogPlanId != null) {
			this.ibspLogPlanId = ibspLogPlanId.toString();
		}
	}
	public String getIbspLogPlanId() {
		return this.ibspLogPlanId ;
	}

	/**
	 * 用户主键APP_USER_ID_
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
	 * 方案主键
	 */
	@Column(name="PLAN_CODE_")
	private String planCode;
	public void setPlanCode(String planCode) {
		this.planCode=planCode;
	}
	public void setPlanCode(Object planCode) {
		if (planCode != null) {
			this.planCode = planCode.toString();
		}
	}
	public String getPlanCode() {
		return this.planCode ;
	}

	/**
	 * 操作类型HANDLE_TYPE_
	 */
	@Column(name="HANDLE_TYPE_")
	private String handleType;
	public void setHandleType(String handleType) {
		this.handleType=handleType;
	}
	public void setHandleType(Object handleType) {
		if (handleType != null) {
			this.handleType = handleType.toString();
		}
	}
	public String getHandleType() {
		return this.handleType ;
	}

	/**
	 * 操作内容HANDLE_CONTENT_
	 */
	@Column(name="HANDLE_CONTENT_")
	private String handleContent;
	public void setHandleContent(String handleContent) {
		this.handleContent=handleContent;
	}
	public void setHandleContent(Object handleContent) {
		if (handleContent != null) {
			this.handleContent = handleContent.toString();
		}
	}
	public String getHandleContent() {
		return this.handleContent ;
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