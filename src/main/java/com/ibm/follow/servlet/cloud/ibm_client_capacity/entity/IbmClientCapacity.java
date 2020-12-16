package com.ibm.follow.servlet.cloud.ibm_client_capacity.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_client_capacity 
 * IBM_中心端客户端容量记录的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_client_capacity")
public class IbmClientCapacity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_中心端客户端容量记录主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CLIENT_CAPACITY_ID_")
	private String ibmClientCapacityId;
	public void setIbmClientCapacityId(String ibmClientCapacityId) {
		this.ibmClientCapacityId=ibmClientCapacityId;
	}
	public void setIbmClientCapacityId(Object ibmClientCapacityId) {
		if (ibmClientCapacityId != null) {
			this.ibmClientCapacityId = ibmClientCapacityId.toString();
		}else{
			this.ibmClientCapacityId = null;
		}
	}
	public String getIbmClientCapacityId() {
		return this.ibmClientCapacityId ;
	}

	/**
	 * 中心端客户端主键
	 */
	@Column(name="CLIENT_ID_")
	private String clientId;
	public void setClientId(String clientId) {
		this.clientId=clientId;
	}
	public void setClientId(Object clientId) {
		if (clientId != null) {
			this.clientId = clientId.toString();
		}else{
			this.clientId = null;
		}
	}
	public String getClientId() {
		return this.clientId ;
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
		}else{
			this.clientCode = null;
		}
	}
	public String getClientCode() {
		return this.clientCode ;
	}

	/**
	 * 客户端最大容量
	 */
	@Column(name="CLIENT_CAPACITY_MAX_")
	private Integer clientCapacityMax;
	public void setClientCapacityMax(Integer clientCapacityMax) {
		this.clientCapacityMax=clientCapacityMax;
	}
	public Integer getClientCapacityMax() {
		return this.clientCapacityMax ;
	}

	/**
	 * 客户端使用容量
	 */
	@Column(name="CLIENT_CAPACITY_")
	private Integer clientCapacity;
	public void setClientCapacity(Integer clientCapacity) {
		this.clientCapacity=clientCapacity;
	}
	public Integer getClientCapacity() {
		return this.clientCapacity ;
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