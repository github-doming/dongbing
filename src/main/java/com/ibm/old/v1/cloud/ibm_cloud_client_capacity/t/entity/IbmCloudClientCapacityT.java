package com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_cloud_client_capacity 
 * IBM_中心端客户端容量记录的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_cloud_client_capacity")
public class IbmCloudClientCapacityT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public void setIdx(Object idx) {
		if (idx != null) {
			if (idx instanceof Long) {
				this.idx= (Long) idx;
			}else if (StringTool.isInteger(idx.toString())) {
				this.idx = Long.parseLong(idx.toString());
			}
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_中心端客户端容量记录主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CLOUD_CLIENT_CAPACITY_ID_")
	private String ibmCloudClientCapacityId;
	public void setIbmCloudClientCapacityId(String ibmCloudClientCapacityId) {
		this.ibmCloudClientCapacityId=ibmCloudClientCapacityId;
	}
	public void setIbmCloudClientCapacityId(Object ibmCloudClientCapacityId) {
		if (ibmCloudClientCapacityId != null) {
			this.ibmCloudClientCapacityId = ibmCloudClientCapacityId.toString();
		}
	}
	public String getIbmCloudClientCapacityId() {
		return this.ibmCloudClientCapacityId ;
	}

	/**
	 * 中心端客户端主键
	 */
	@Column(name="CLOUD_CLIENT_ID_")
	private String cloudClientId;
	public void setCloudClientId(String cloudClientId) {
		this.cloudClientId=cloudClientId;
	}
	public void setCloudClientId(Object cloudClientId) {
		if (cloudClientId != null) {
			this.cloudClientId = cloudClientId.toString();
		}
	}
	public String getCloudClientId() {
		return this.cloudClientId ;
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
	 * 客户端最大容量
	 */
	@Column(name="CLIENT_CAPACITY_MAX_")
	private String clientCapacityMax;
	public void setClientCapacityMax(String clientCapacityMax) {
		this.clientCapacityMax=clientCapacityMax;
	}
	public void setClientCapacityMax(Object clientCapacityMax) {
		if (clientCapacityMax != null) {
			this.clientCapacityMax = clientCapacityMax.toString();
		}
	}
	public String getClientCapacityMax() {
		return this.clientCapacityMax ;
	}

	/**
	 * 客户端使用容量
	 */
	@Column(name="CLIENT_CAPACITY_")
	private String clientCapacity;
	public void setClientCapacity(String clientCapacity) {
		this.clientCapacity=clientCapacity;
	}
	public void setClientCapacity(Object clientCapacity) {
		if (clientCapacity != null) {
			this.clientCapacity = clientCapacity.toString();
		}
	}
	public String getClientCapacity() {
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