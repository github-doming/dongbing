package com.ibm.follow.servlet.client.ibmc_ha_info.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibmc_ha_info 
 * IBMC_客户端盘口代理信息的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibmc_ha_info")
public class IbmcHaInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC_客户端盘口代理信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBMC_HA_INFO_ID_")
	private String ibmcHaInfoId;
	public void setIbmcHaInfoId(String ibmcHaInfoId) {
		this.ibmcHaInfoId=ibmcHaInfoId;
	}
	public void setIbmcHaInfoId(Object ibmcHaInfoId) {
		if (ibmcHaInfoId != null) {
			this.ibmcHaInfoId = ibmcHaInfoId.toString();
		}else{
			this.ibmcHaInfoId = null;
		}
	}
	public String getIbmcHaInfoId() {
		return this.ibmcHaInfoId ;
	}

	/**
	 * 已存在盘口代理主键
	 */
	@Column(name="EXIST_HA_ID_")
	private String existHaId;
	public void setExistHaId(String existHaId) {
		this.existHaId=existHaId;
	}
	public void setExistHaId(Object existHaId) {
		if (existHaId != null) {
			this.existHaId = existHaId.toString();
		}else{
			this.existHaId = null;
		}
	}
	public String getExistHaId() {
		return this.existHaId ;
	}

	/**
	 * 会员列表信息
	 */
	@Column(name="MEMBER_LIST_INFO_")
	private String memberListInfo;
	public void setMemberListInfo(String memberListInfo) {
		this.memberListInfo=memberListInfo;
	}
	public void setMemberListInfo(Object memberListInfo) {
		if (memberListInfo != null) {
			this.memberListInfo = memberListInfo.toString();
		}else{
			this.memberListInfo = null;
		}
	}
	public String getMemberListInfo() {
		return this.memberListInfo ;
	}

	/**
	 * 代理信息
	 */
	@Column(name="AGENT_INFO_")
	private String agentInfo;
	public void setAgentInfo(String agentInfo) {
		this.agentInfo=agentInfo;
	}
	public void setAgentInfo(Object agentInfo) {
		if (agentInfo != null) {
			this.agentInfo = agentInfo.toString();
		}else{
			this.agentInfo = null;
		}
	}
	public String getAgentInfo() {
		return this.agentInfo ;
	}

	/**
	 * 代理信息码
	 */
	@Column(name="AGENT_INFO_CODE_")
	private String agentInfoCode;
	public void setAgentInfoCode(String agentInfoCode) {
		this.agentInfoCode=agentInfoCode;
	}
	public void setAgentInfoCode(Object agentInfoCode) {
		if (agentInfoCode != null) {
			this.agentInfoCode = agentInfoCode.toString();
		}else{
			this.agentInfoCode = null;
		}
	}
	public String getAgentInfoCode() {
		return this.agentInfoCode ;
	}

	/**
	 * 检验信息
	 */
	@Column(name="CHECK_INFO_")
	private String checkInfo;
	public void setCheckInfo(String checkInfo) {
		this.checkInfo=checkInfo;
	}
	public void setCheckInfo(Object checkInfo) {
		if (checkInfo != null) {
			this.checkInfo = checkInfo.toString();
		}else{
			this.checkInfo = null;
		}
	}
	public String getCheckInfo() {
		return this.checkInfo ;
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
		}else{
			this.createTimeLong = null;
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