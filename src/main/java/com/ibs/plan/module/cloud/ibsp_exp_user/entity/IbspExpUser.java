package com.ibs.plan.module.cloud.ibsp_exp_user.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_exp_user 
 * IBSP_用户 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_exp_user")
public class IbspExpUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_用户盘口信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_EXP_USER_ID_")
	private String ibspExpUserId;
	public void setIbspExpUserId(String ibspExpUserId) {
		this.ibspExpUserId=ibspExpUserId;
	}
	public void setIbspExpUserId(Object ibspExpUserId) {
		if (ibspExpUserId != null) {
			this.ibspExpUserId = ibspExpUserId.toString();
		}
	}
	public String getIbspExpUserId() {
		return this.ibspExpUserId ;
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
	 * 角色主键
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
	 * 可用方案
	 */
	@Column(name="AVAILABLE_PLAN_")
	private String availablePlan;
	public void setAvailablePlan(String availablePlan) {
		this.availablePlan=availablePlan;
	}
	public void setAvailablePlan(Object availablePlan) {
		if (availablePlan != null) {
			this.availablePlan = availablePlan.toString();
		}
	}
	public String getAvailablePlan() {
		return this.availablePlan ;
	}

	/**
	 * 可用盘口
	 */
	@Column(name="AVAILABLE_HANDICAP_")
	private String availableHandicap;
	public void setAvailableHandicap(String availableHandicap) {
		this.availableHandicap=availableHandicap;
	}
	public void setAvailableHandicap(Object availableHandicap) {
		if (availableHandicap != null) {
			this.availableHandicap = availableHandicap.toString();
		}
	}
	public String getAvailableHandicap() {
		return this.availableHandicap ;
	}

	/**
	 * 最大在线数量
	 */
	@Column(name="ONLINE_MAX_")
	private Integer onlineMax;
	public void setOnlineMax(Integer onlineMax) {
		this.onlineMax=onlineMax;
	}
	public void setOnlineMax(Object onlineMax) {
		if (onlineMax != null) {
			if (onlineMax instanceof Integer) {
				this.onlineMax= (Integer) onlineMax;
			}else if (StringTool.isInteger(onlineMax.toString())) {
				this.onlineMax = Integer.parseInt(onlineMax.toString());
			}
		}
	}
	public Integer getOnlineMax() {
		return this.onlineMax ;
	}

	/**
	 * 在线数量
	 */
	@Column(name="ONLINE_")
	private Integer online;
	public void setOnline(Integer online) {
		this.online=online;
	}
	public void setOnline(Object online) {
		if (online != null) {
			if (online instanceof Integer) {
				this.online= (Integer) online;
			}else if (StringTool.isInteger(online.toString())) {
				this.online = Integer.parseInt(online.toString());
			}
		}
	}
	public Integer getOnline() {
		return this.online ;
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