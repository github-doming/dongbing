package com.ibm.follow.servlet.cloud.ibm_exp_user.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_exp_user 
 * IBM_用户 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_exp_user")
public class IbmExpUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_用户盘口信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_EXP_USER_ID_")
	private String ibmExpUserId;
	public void setIbmExpUserId(String ibmExpUserId) {
		this.ibmExpUserId=ibmExpUserId;
	}
	public void setIbmExpUserId(Object ibmExpUserId) {
		if (ibmExpUserId != null) {
			this.ibmExpUserId = ibmExpUserId.toString();
		}
	}
	public String getIbmExpUserId() {
		return this.ibmExpUserId ;
	}

	/**
	 * 用户主键
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
	 * IBM_角色主键
	 */
	@Column(name="EXP_ROLE_ID_")
	private String expRoleId;
	public void setExpRoleId(String expRoleId) {
		this.expRoleId=expRoleId;
	}
	public void setExpRoleId(Object expRoleId) {
		if (expRoleId != null) {
			this.expRoleId = expRoleId.toString();
		}
	}
	public String getExpRoleId() {
		return this.expRoleId ;
	}

	/**
	 * 可用游戏
	 */
	@Column(name="AVAILABLE_GAME_")
	private String availableGame;
	public void setAvailableGame(String availableGame) {
		this.availableGame=availableGame;
	}
	public void setAvailableGame(Object availableGame) {
		if (availableGame != null) {
			this.availableGame = availableGame.toString();
		}
	}
	public String getAvailableGame() {
		return this.availableGame ;
	}

	/**
	 * 代理可用盘口
	 */
	@Column(name="AGENT_AVAILABLE_HANDICAP_")
	private String agentAvailableHandicap;
	public void setAgentAvailableHandicap(String agentAvailableHandicap) {
		this.agentAvailableHandicap=agentAvailableHandicap;
	}
	public void setAgentAvailableHandicap(Object agentAvailableHandicap) {
		if (agentAvailableHandicap != null) {
			this.agentAvailableHandicap = agentAvailableHandicap.toString();
		}
	}
	public String getAgentAvailableHandicap() {
		return this.agentAvailableHandicap ;
	}

	/**
	 * 代理最大在线数量
	 */
	@Column(name="AGENT_ONLINE_MAX_")
	private Integer agentOnlineMax;
	public void setAgentOnlineMax(Integer agentOnlineMax) {
		this.agentOnlineMax=agentOnlineMax;
	}
	public Integer getAgentOnlineMax() {
		return this.agentOnlineMax ;
	}

	/**
	 * 代理在线数量
	 */
	@Column(name="AGENT_ONLINE_")
	private Integer agentOnline;
	public void setAgentOnline(Integer agentOnline) {
		this.agentOnline=agentOnline;
	}
	public Integer getAgentOnline() {
		return this.agentOnline ;
	}

	/**
	 * 会员可用盘口
	 */
	@Column(name="MEMBER_AVAILABLE_HANDICAP_")
	private String memberAvailableHandicap;
	public void setMemberAvailableHandicap(String memberAvailableHandicap) {
		this.memberAvailableHandicap=memberAvailableHandicap;
	}
	public void setMemberAvailableHandicap(Object memberAvailableHandicap) {
		if (memberAvailableHandicap != null) {
			this.memberAvailableHandicap = memberAvailableHandicap.toString();
		}
	}
	public String getMemberAvailableHandicap() {
		return this.memberAvailableHandicap ;
	}

	/**
	 * 会员最大在线数量
	 */
	@Column(name="MEMBER_ONLINE_MAX_")
	private Integer memberOnlineMax;
	public void setMemberOnlineMax(Integer memberOnlineMax) {
		this.memberOnlineMax=memberOnlineMax;
	}
	public Integer getMemberOnlineMax() {
		return this.memberOnlineMax ;
	}

	/**
	 * 会员在线数量
	 */
	@Column(name="MEMBER_ONLINE_")
	private Integer memberOnline;
	public void setMemberOnline(Integer memberOnline) {
		this.memberOnline=memberOnline;
	}
	public Integer getMemberOnline() {
		return this.memberOnline ;
	}

	/**
	 * 创建者
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}
	}
	public String getCreateUser() {
		return this.createUser ;
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
	 * 更新者
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}
	}
	public String getUpdateUser() {
		return this.updateUser ;
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