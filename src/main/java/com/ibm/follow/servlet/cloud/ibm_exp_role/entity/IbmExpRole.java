package com.ibm.follow.servlet.cloud.ibm_exp_role.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_exp_role 
 * IBM_角色 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_exp_role")
public class IbmExpRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_角色主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_EXP_ROLE_ID_")
	private String ibmExpRoleId;
	public void setIbmExpRoleId(String ibmExpRoleId) {
		this.ibmExpRoleId=ibmExpRoleId;
	}
	public void setIbmExpRoleId(Object ibmExpRoleId) {
		if (ibmExpRoleId != null) {
			this.ibmExpRoleId = ibmExpRoleId.toString();
		}
	}
	public String getIbmExpRoleId() {
		return this.ibmExpRoleId ;
	}

	/**
	 * 角色名
	 */
	@Column(name="ROLE_NAME_")
	private String roleName;
	public void setRoleName(String roleName) {
		this.roleName=roleName;
	}
	public void setRoleName(Object roleName) {
		if (roleName != null) {
			this.roleName = roleName.toString();
		}
	}
	public String getRoleName() {
		return this.roleName ;
	}

	/**
	 * 角色CODE
	 */
	@Column(name="ROLE_CODE_")
	private String roleCode;
	public void setRoleCode(String roleCode) {
		this.roleCode=roleCode;
	}
	public void setRoleCode(Object roleCode) {
		if (roleCode != null) {
			this.roleCode = roleCode.toString();
		}
	}
	public String getRoleCode() {
		return this.roleCode ;
	}

	/**
	 * 角色等级
	 */
	@Column(name="ROLE_LEVEL_")
	private Integer roleLevel;
	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel=roleLevel;
	}
	public void setRoleLevel(Object roleLevel) {
		if (roleLevel != null) {
			if (roleLevel instanceof Integer) {
				this.roleLevel= (Integer) roleLevel;
			}else if (StringTool.isInteger(roleLevel.toString())) {
				this.roleLevel = Integer.parseInt(roleLevel.toString());
			}
		}
	}
	public Integer getRoleLevel() {
		return this.roleLevel ;
	}

	/**
	 * 授权游戏
	 */
	@Column(name="GAME_CODES_")
	private String gameCodes;
	public void setGameCodes(String gameCodes) {
		this.gameCodes=gameCodes;
	}
	public void setGameCodes(Object gameCodes) {
		if (gameCodes != null) {
			this.gameCodes = gameCodes.toString();
		}
	}
	public String getGameCodes() {
		return this.gameCodes ;
	}

	/**
	 * 会员授权盘口
	 */
	@Column(name="MEMBER_HANDICAP_CODES_")
	private String memberHandicapCodes;
	public void setMemberHandicapCodes(String memberHandicapCodes) {
		this.memberHandicapCodes=memberHandicapCodes;
	}
	public void setMemberHandicapCodes(Object memberHandicapCodes) {
		if (memberHandicapCodes != null) {
			this.memberHandicapCodes = memberHandicapCodes.toString();
		}
	}
	public String getMemberHandicapCodes() {
		return this.memberHandicapCodes ;
	}

	/**
	 * 代理授权盘口
	 */
	@Column(name="AGENT_HANDICAP_CODES_")
	private String agentHandicapCodes;
	public void setAgentHandicapCodes(String agentHandicapCodes) {
		this.agentHandicapCodes=agentHandicapCodes;
	}
	public void setAgentHandicapCodes(Object agentHandicapCodes) {
		if (agentHandicapCodes != null) {
			this.agentHandicapCodes = agentHandicapCodes.toString();
		}
	}
	public String getAgentHandicapCodes() {
		return this.agentHandicapCodes ;
	}

	/**
	 * 会员最大在线数量
	 */
	@Column(name="MEMBER_ONLINE_MAX_")
	private Integer memberOnlineMax;
	public void setMemberOnlineMax(Integer memberOnlineMax) {
		this.memberOnlineMax=memberOnlineMax;
	}
	public void setMemberOnlineMax(Object memberOnlineMax) {
		if (memberOnlineMax != null) {
			if (memberOnlineMax instanceof Integer) {
				this.memberOnlineMax= (Integer) memberOnlineMax;
			}else if (StringTool.isInteger(memberOnlineMax.toString())) {
				this.memberOnlineMax = Integer.parseInt(memberOnlineMax.toString());
			}
		}
	}
	public Integer getMemberOnlineMax() {
		return this.memberOnlineMax ;
	}

	/**
	 * 代理最大在线数量
	 */
	@Column(name="AGENT_ONLINE_MAX_")
	private Integer agentOnlineMax;
	public void setAgentOnlineMax(Integer agentOnlineMax) {
		this.agentOnlineMax=agentOnlineMax;
	}
	public void setAgentOnlineMax(Object agentOnlineMax) {
		if (agentOnlineMax != null) {
			if (agentOnlineMax instanceof Integer) {
				this.agentOnlineMax= (Integer) agentOnlineMax;
			}else if (StringTool.isInteger(agentOnlineMax.toString())) {
				this.agentOnlineMax = Integer.parseInt(agentOnlineMax.toString());
			}
		}
	}
	public Integer getAgentOnlineMax() {
		return this.agentOnlineMax ;
	}

	/**
	 * 最大盘口会员在线数
	 */
	@Column(name="HM_ONLINE_MAX_")
	private Integer hmOnlineMax;
	public void setHmOnlineMax(Integer hmOnlineMax) {
		this.hmOnlineMax=hmOnlineMax;
	}
	public void setHmOnlineMax(Object hmOnlineMax) {
		if (hmOnlineMax != null) {
			if (hmOnlineMax instanceof Integer) {
				this.hmOnlineMax= (Integer) hmOnlineMax;
			}else if (StringTool.isInteger(hmOnlineMax.toString())) {
				this.hmOnlineMax = Integer.parseInt(hmOnlineMax.toString());
			}
		}
	}
	public Integer getHmOnlineMax() {
		return this.hmOnlineMax ;
	}

	/**
	 * 最大盘口代理在线数
	 */
	@Column(name="HA_ONLINE_MAX_")
	private Integer haOnlineMax;
	public void setHaOnlineMax(Integer haOnlineMax) {
		this.haOnlineMax=haOnlineMax;
	}
	public void setHaOnlineMax(Object haOnlineMax) {
		if (haOnlineMax != null) {
			if (haOnlineMax instanceof Integer) {
				this.haOnlineMax= (Integer) haOnlineMax;
			}else if (StringTool.isInteger(haOnlineMax.toString())) {
				this.haOnlineMax = Integer.parseInt(haOnlineMax.toString());
			}
		}
	}
	public Integer getHaOnlineMax() {
		return this.haOnlineMax ;
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