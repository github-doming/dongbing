package com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_client_handicap_capacity
 * IBM_中心端客户端盘口容量记录的实体类
 *
 * @author Robot
 */
@AnnotationEntity @AnnotationTable(name = "ibm_client_handicap_capacity") public class IbmClientHandicapCapacity
		implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_中心端客户端盘口主键
	 * 根据底层数据库
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "IBM_CLIENT_HANDICAP_CAPACITY_ID_") private String ibmClientHandicapCapacityId;
	public void setIbmClientHandicapCapacityId(String ibmClientHandicapCapacityId) {
		this.ibmClientHandicapCapacityId = ibmClientHandicapCapacityId;
	}
	public void setIbmClientHandicapCapacityId(Object ibmClientHandicapCapacityId) {
		if (ibmClientHandicapCapacityId != null) {
			this.ibmClientHandicapCapacityId = ibmClientHandicapCapacityId.toString();
		} else {
			this.ibmClientHandicapCapacityId = null;
		}
	}
	public String getIbmClientHandicapCapacityId() {
		return this.ibmClientHandicapCapacityId;
	}

	/**
	 * 中心端客户端容量记录主键
	 */
	@Column(name = "CLIENT_CAPACITY_ID_") private String clientCapacityId;
	public void setClientCapacityId(String clientCapacityId) {
		this.clientCapacityId = clientCapacityId;
	}
	public void setClientCapacityId(Object clientCapacityId) {
		if (clientCapacityId != null) {
			this.clientCapacityId = clientCapacityId.toString();
		} else {
			this.clientCapacityId = null;
		}
	}
	public String getClientCapacityId() {
		return this.clientCapacityId;
	}

	/**
	 * IBM_中心端配置主键
	 */
	@Column(name = "CLIENT_ID_") private String clientId;
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setClientId(Object clientId) {
		if (clientId != null) {
			this.clientId = clientId.toString();
		} else {
			this.clientId = null;
		}
	}
	public String getClientId() {
		return this.clientId;
	}

	/**
	 * 客户端编码
	 */
	@Column(name = "CLIENT_CODE_") private String clientCode;
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public void setClientCode(Object clientCode) {
		if (clientCode != null) {
			this.clientCode = clientCode.toString();
		} else {
			this.clientCode = null;
		}
	}
	public String getClientCode() {
		return this.clientCode;
	}

	/**
	 * 盘口编码
	 */
	@Column(name = "HANDICAP_CODE_") private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode = handicapCode;
	}
	public void setHandicapCode(Object handicapCode) {
		if (handicapCode != null) {
			this.handicapCode = handicapCode.toString();
		} else {
			this.handicapCode = null;
		}
	}
	public String getHandicapCode() {
		return this.handicapCode;
	}

	/**
	 * 盘口最大容量
	 */
	@Column(name = "CAPACITY_HANDICAP_MAX_") private Integer capacityHandicapMax;
	public void setCapacityHandicapMax(Integer capacityHandicapMax) {
		this.capacityHandicapMax = capacityHandicapMax;
	}
	public void setCapacityHandicapMax(Object capacityHandicapMax) {
		if (capacityHandicapMax == null) {
			this.capacityHandicapMax = null;
		} else if (capacityHandicapMax instanceof Integer) {
			this.capacityHandicapMax = (Integer) capacityHandicapMax;
		} else {
			this.capacityHandicapMax = NumberTool.getInteger(capacityHandicapMax);
		}
	}
	public Integer getCapacityHandicapMax() {
		return this.capacityHandicapMax;
	}

	/**
	 * 盘口使用容量
	 */
	@Column(name = "CAPACITY_HANDICAP_") private Integer capacityHandicap;
	public void setCapacityHandicap(Integer capacityHandicap) {
		this.capacityHandicap = capacityHandicap;
	}
	public Integer getCapacityHandicap() {
		return this.capacityHandicap;
	}

	/**
	 * 代理最大容量
	 */
	@Column(name = "CAPACITY_HA_MAX_") private Integer capacityHaMax;
	public void setCapacityHaMax(Integer capacityHaMax) {
		this.capacityHaMax = capacityHaMax;
	}
	public void setCapacityHaMax(Object capacityHaMax) {
		if (capacityHaMax == null) {
			this.capacityHaMax = null;
		} else if (capacityHaMax instanceof Integer) {
			this.capacityHaMax = (Integer) capacityHaMax;
		} else {
			this.capacityHaMax = NumberTool.getInteger(capacityHaMax);
		}
	}
	public Integer getCapacityHaMax() {
		return this.capacityHaMax;
	}

	/**
	 * 代理使用容量
	 */
	@Column(name = "CAPACITY_HA_") private Integer capacityHa;
	public void setCapacityHa(Integer capacityHa) {
		this.capacityHa = capacityHa;
	}
	public Integer getCapacityHa() {
		return this.capacityHa;
	}

	/**
	 * 会员最大容量
	 */
	@Column(name = "CAPACITY_HM_MAX_") private Integer capacityHmMax;
	public void setCapacityHmMax(Integer capacityHmMax) {
		this.capacityHmMax = capacityHmMax;
	}

	public void setCapacityHmMax(Object capacityHmMax) {
		if (capacityHmMax == null) {
			this.capacityHmMax = null;
		} else if (capacityHmMax instanceof Integer) {
			this.capacityHmMax = (Integer) capacityHmMax;
		} else {
			this.capacityHmMax = NumberTool.getInteger(capacityHmMax);
		}
	}
	public Integer getCapacityHmMax() {
		return this.capacityHmMax;
	}

	/**
	 * 会员使用容量
	 */
	@Column(name = "CAPACITY_HM_") private Integer capacityHm;
	public void setCapacityHm(Integer capacityHm) {
		this.capacityHm = capacityHm;
	}
	public Integer getCapacityHm() {
		return this.capacityHm;
	}

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(name = "CREATE_TIME_") private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCreateTime(Object createTime) {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime = (Date) createTime;
			} else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			} else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		} else {
			this.createTime = null;
		}
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME_LONG_") private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong = (Long) createTimeLong;
			} else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		} else {
			this.createTimeLong = null;
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong;
	}

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(name = "UPDATE_TIME_") private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setUpdateTime(Object updateTime) {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime = (Date) updateTime;
			} else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			} else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		} else {
			this.updateTime = null;
		}
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME_LONG_") private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong = updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong = (Long) updateTimeLong;
			} else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		} else {
			this.updateTimeLong = null;
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name = "STATE_") private String state;
	public void setState(String state) {
		this.state = state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		} else {
			this.state = null;
		}
	}
	public String getState() {
		return this.state;
	}

	/**
	 * 描述
	 */
	@Column(name = "DESC_") private String desc;
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		} else {
			this.desc = null;
		}
	}
	public String getDesc() {
		return this.desc;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 *
	 * @return 表名
	 */
	@Transient public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}