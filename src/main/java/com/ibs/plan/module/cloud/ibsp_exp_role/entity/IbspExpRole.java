package com.ibs.plan.module.cloud.ibsp_exp_role.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_exp_role 
 * IBSP_角色 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_exp_role")
public class IbspExpRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_角色主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_EXP_ROLE_ID_")
	private String ibspExpRoleId;
	public void setIbspExpRoleId(String ibspExpRoleId) {
		this.ibspExpRoleId=ibspExpRoleId;
	}
	public void setIbspExpRoleId(Object ibspExpRoleId) {
		if (ibspExpRoleId != null) {
			this.ibspExpRoleId = ibspExpRoleId.toString();
		}
	}
	public String getIbspExpRoleId() {
		return this.ibspExpRoleId ;
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
	 * 角色编码
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
	 * 授权方案
	 */
	@Column(name="PLAN_CODES_")
	private String planCodes;
	public void setPlanCodes(String planCodes) {
		this.planCodes=planCodes;
	}
	public void setPlanCodes(Object planCodes) {
		if (planCodes != null) {
			this.planCodes = planCodes.toString();
		}
	}
	public String getPlanCodes() {
		return this.planCodes ;
	}

	/**
	 * 授权盘口
	 */
	@Column(name="HANDICAP_CODES_")
	private String handicapCodes;
	public void setHandicapCodes(String handicapCodes) {
		this.handicapCodes=handicapCodes;
	}
	public void setHandicapCodes(Object handicapCodes) {
		if (handicapCodes != null) {
			this.handicapCodes = handicapCodes.toString();
		}
	}
	public String getHandicapCodes() {
		return this.handicapCodes ;
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