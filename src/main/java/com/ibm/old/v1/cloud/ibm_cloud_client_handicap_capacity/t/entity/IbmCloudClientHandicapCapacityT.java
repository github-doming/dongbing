package com.ibm.old.v1.cloud.ibm_cloud_client_handicap_capacity.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_cloud_client_handicap_capacity 
 * IBM_中心端客户端盘口容量记录的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_cloud_client_handicap_capacity")
public class IbmCloudClientHandicapCapacityT implements Serializable {

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
	 * IBM_中心端客户端盘口主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_")
	private String ibmCloudClientHandicapCapacityId;
	public void setIbmCloudClientHandicapCapacityId(String ibmCloudClientHandicapCapacityId) {
		this.ibmCloudClientHandicapCapacityId=ibmCloudClientHandicapCapacityId;
	}
	public void setIbmCloudClientHandicapCapacityId(Object ibmCloudClientHandicapCapacityId) {
		if (ibmCloudClientHandicapCapacityId != null) {
			this.ibmCloudClientHandicapCapacityId = ibmCloudClientHandicapCapacityId.toString();
		}
	}
	public String getIbmCloudClientHandicapCapacityId() {
		return this.ibmCloudClientHandicapCapacityId ;
	}

	/**
	 * 中心端客户端容量记录主键
	 */
	@Column(name="CLOUD_CLIENT_CAPACITY_ID_")
	private String cloudClientCapacityId;
	public void setCloudClientCapacityId(String cloudClientCapacityId) {
		this.cloudClientCapacityId=cloudClientCapacityId;
	}
	public void setCloudClientCapacityId(Object cloudClientCapacityId) {
		if (cloudClientCapacityId != null) {
			this.cloudClientCapacityId = cloudClientCapacityId.toString();
		}
	}
	public String getCloudClientCapacityId() {
		return this.cloudClientCapacityId ;
	}

	/**
	 * IBM_中心端配置主键
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
	 * 盘口主键
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public void setHandicapId(Object handicapId) {
		if (handicapId != null) {
			this.handicapId = handicapId.toString();
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
	}

	/**
	 * 盘口编码
	 */
	@Column(name="HANDICAP_CODE_")
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public void setHandicapCode(Object handicapCode) {
		if (handicapCode != null) {
			this.handicapCode = handicapCode.toString();
		}
	}
	public String getHandicapCode() {
		return this.handicapCode ;
	}

	/**
	 * 最大容量
	 */
	@Column(name="CAPACITY_MAX_")
	private String capacityMax;
	public void setCapacityMax(String capacityMax) {
		this.capacityMax=capacityMax;
	}
	public void setCapacityMax(Object capacityMax) {
		if (capacityMax != null) {
			this.capacityMax = capacityMax.toString();
		}
	}
	public String getCapacityMax() {
		return this.capacityMax ;
	}

	/**
	 * 使用容量
	 */
	@Column(name="CAPACITY_")
	private String capacity;
	public void setCapacity(String capacity) {
		this.capacity=capacity;
	}
	public void setCapacity(Object capacity) {
		if (capacity != null) {
			this.capacity = capacity.toString();
		}
	}
	public String getCapacity() {
		return this.capacity ;
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