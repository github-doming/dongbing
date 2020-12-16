package com.ibm.follow.servlet.client.ibmc_handicap_agent.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibmc_handicap_agent 
 * IBMC_客户端盘口代理的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibmc_handicap_agent")
public class IbmcHandicapAgent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC_客户端盘口代理主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBMC_HANDICAP_AGENT_ID_")
	private String ibmcHandicapAgentId;
	public void setIbmcHandicapAgentId(String ibmcHandicapAgentId) {
		this.ibmcHandicapAgentId=ibmcHandicapAgentId;
	}
	public void setIbmcHandicapAgentId(Object ibmcHandicapAgentId) {
		if (ibmcHandicapAgentId != null) {
			this.ibmcHandicapAgentId = ibmcHandicapAgentId.toString();
		}else{
			this.ibmcHandicapAgentId = null;
		}
	}
	public String getIbmcHandicapAgentId() {
		return this.ibmcHandicapAgentId ;
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
	 * 盘口代理主键
	 */
	@Column(name="HANDICAP_AGENT_ID_")
	private String handicapAgentId;
	public void setHandicapAgentId(String handicapAgentId) {
		this.handicapAgentId=handicapAgentId;
	}
	public void setHandicapAgentId(Object handicapAgentId) {
		if (handicapAgentId != null) {
			this.handicapAgentId = handicapAgentId.toString();
		}else{
			this.handicapAgentId = null;
		}
	}
	public String getHandicapAgentId() {
		return this.handicapAgentId ;
	}

	/**
	 * 代理账户
	 */
	@Column(name="AGENT_ACCOUNT_")
	private String agentAccount;
	public void setAgentAccount(String agentAccount) {
		this.agentAccount=agentAccount;
	}
	public void setAgentAccount(Object agentAccount) {
		if (agentAccount != null) {
			this.agentAccount = agentAccount.toString();
		}else{
			this.agentAccount = null;
		}
	}
	public String getAgentAccount() {
		return this.agentAccount ;
	}

	/**
	 * 代理密码
	 */
	@Column(name="AGENT_PASSWORD_")
	private String agentPassword;
	public void setAgentPassword(String agentPassword) {
		this.agentPassword=agentPassword;
	}
	public void setAgentPassword(Object agentPassword) {
		if (agentPassword != null) {
			this.agentPassword = agentPassword.toString();
		}else{
			this.agentPassword = null;
		}
	}
	public String getAgentPassword() {
		return this.agentPassword ;
	}

	/**
	 * 盘口地址
	 */
	@Column(name="HANDICAP_URL_")
	private String handicapUrl;
	public void setHandicapUrl(String handicapUrl) {
		this.handicapUrl=handicapUrl;
	}
	public void setHandicapUrl(Object handicapUrl) {
		if (handicapUrl != null) {
			this.handicapUrl = handicapUrl.toString();
		}else{
			this.handicapUrl = null;
		}
	}
	public String getHandicapUrl() {
		return this.handicapUrl ;
	}

	/**
	 * 盘口校验码
	 */
	@Column(name="HANDICAP_CAPTCHA_")
	private String handicapCaptcha;
	public void setHandicapCaptcha(String handicapCaptcha) {
		this.handicapCaptcha=handicapCaptcha;
	}
	public void setHandicapCaptcha(Object handicapCaptcha) {
		if (handicapCaptcha != null) {
			this.handicapCaptcha = handicapCaptcha.toString();
		}else{
			this.handicapCaptcha = null;
		}
	}
	public String getHandicapCaptcha() {
		return this.handicapCaptcha ;
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