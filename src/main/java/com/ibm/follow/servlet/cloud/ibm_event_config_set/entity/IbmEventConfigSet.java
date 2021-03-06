package com.ibm.follow.servlet.cloud.ibm_event_config_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_event_config_set 
 * IBM_客户设置事件的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_event_config_set")
public class IbmEventConfigSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_客户设置事件主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_EVENT_CONFIG_SET_ID_")
	private String ibmEventConfigSetId;
	public void setIbmEventConfigSetId(String ibmEventConfigSetId) {
		this.ibmEventConfigSetId=ibmEventConfigSetId;
	}
	public void setIbmEventConfigSetId(Object ibmEventConfigSetId) {
		if (ibmEventConfigSetId != null) {
			this.ibmEventConfigSetId = ibmEventConfigSetId.toString();
		}else{
			this.ibmEventConfigSetId = null;
		}
	}
	public String getIbmEventConfigSetId() {
		return this.ibmEventConfigSetId ;
	}

	/**
	 * 客户类型
	 */
	@Column(name="CUSTOMER_TYPE_")
	private String customerType;
	public void setCustomerType(String customerType) {
		this.customerType=customerType;
	}
	public void setCustomerType(Object customerType) {
		if (customerType != null) {
			this.customerType = customerType.toString();
		}else{
			this.customerType = null;
		}
	}
	public String getCustomerType() {
		return this.customerType ;
	}

	/**
	 * 事件内容
	 */
	@Column(name="EVENT_CONTENT_")
	private String eventContent;
	public void setEventContent(String eventContent) {
		this.eventContent=eventContent;
	}
	public void setEventContent(Object eventContent) {
		if (eventContent != null) {
			this.eventContent = eventContent.toString();
		}else{
			this.eventContent = null;
		}
	}
	public String getEventContent() {
		return this.eventContent ;
	}

	/**
	 * 事件状态
	 */
	@Column(name="EVENT_STATE_")
	private String eventState;
	public void setEventState(String eventState) {
		this.eventState=eventState;
	}
	public void setEventState(Object eventState) {
		if (eventState != null) {
			this.eventState = eventState.toString();
		}else{
			this.eventState = null;
		}
	}
	public String getEventState() {
		return this.eventState ;
	}

	/**
	 * 事件结果
	 */
	@Column(name="EVENT_RESULT_")
	private String eventResult;
	public void setEventResult(String eventResult) {
		this.eventResult=eventResult;
	}
	public void setEventResult(Object eventResult) {
		if (eventResult != null) {
			this.eventResult = eventResult.toString();
		}else{
			this.eventResult = null;
		}
	}
	public String getEventResult() {
		return this.eventResult ;
	}

	/**
	 * 执行次数
	 */
	@Column(name="EXEC_NUMBER_")
	private Integer execNumber;
	public void setExecNumber(Integer execNumber) {
		this.execNumber=execNumber;
	}
	public void setExecNumber(Object execNumber) {
		if (execNumber != null) {
			if (execNumber instanceof Integer) {
				this.execNumber= (Integer) execNumber;
			}else if (StringTool.isInteger(execNumber.toString())) {
				this.execNumber = Integer.parseInt(execNumber.toString());
			}
		}else{
			this.execNumber = null;
		}
	}
	public Integer getExecNumber() {
		return this.execNumber ;
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
	public void setCreateTime(Object createTime) {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}else{
			this.createTime = null;
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间数字型
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
		}else{
			this.createTimeLong = null;
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
	public void setUpdateTime(Object updateTime) {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}else{
			this.updateTime = null;
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间数字型
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
		}else{
			this.updateTimeLong = null;
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
		}else{
			this.state = null;
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
		}else{
			this.desc = null;
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