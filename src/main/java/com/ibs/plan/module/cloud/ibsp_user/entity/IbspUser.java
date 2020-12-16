package com.ibs.plan.module.cloud.ibsp_user.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_user 
 * 投注用户表 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_user")
public class IbspUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_USER_ID_
	 */
	@Column(name="IBSP_USER_ID_")
	private Integer ibspUserId;
	public void setIbspUserId(Integer ibspUserId) {
		this.ibspUserId=ibspUserId;
	}

	public Integer getIbspUserId() {
		return this.ibspUserId ;
	}

	/**
	 * APP用户主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	 * 昵称
	 */
	@Column(name="NICKNAME_")
	private String nickname;
	public void setNickname(String nickname) {
		this.nickname=nickname;
	}
	public void setNickname(Object nickname) {
		if (nickname != null) {
			this.nickname = nickname.toString();
		}
	}
	public String getNickname() {
		return this.nickname ;
	}

	/**
	 * 用户类型
	 */
	@Column(name="USER_TYPE_")
	private String userType;
	public void setUserType(String userType) {
		this.userType=userType;
	}
	public void setUserType(Object userType) {
		if (userType != null) {
			this.userType = userType.toString();
		}
	}
	public String getUserType() {
		return this.userType ;
	}

	/**
	 * 租户编码
	 */
	@Column(name="TENANT_CODE_")
	private String tenantCode;
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public void setTenantCode(Object tenantCode) {
		if (tenantCode != null) {
			this.tenantCode = tenantCode.toString();
		}
	}
	public String getTenantCode() {
		return this.tenantCode ;
	}

	/**
	 * 登录时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="LOGIN_TIME_")
	private Date loginTime;
	public void setLoginTime(Date loginTime) {
		this.loginTime=loginTime;
	}
	public void setLoginTime(Object loginTime) throws ParseException {
		if (loginTime != null) {
			if (loginTime instanceof Date) {
				this.loginTime= (Date) loginTime;
			}else if (StringTool.isInteger(loginTime.toString())) {
				this.loginTime = new Date(Long.parseLong(loginTime.toString()));
			}else {
				this.loginTime = DateTool.getTime(loginTime.toString());
			}
		}
	}
	public Date getLoginTime() {
		return this.loginTime ;
	}

	/**
	 * 登录时间数字型
	 */
	@Column(name="LOGIN_TIME_LONG_")
	private Long loginTimeLong;
	public void setLoginTimeLong(Long loginTimeLong) {
		this.loginTimeLong=loginTimeLong;
	}
	public void setLoginTimeLong(Object loginTimeLong) {
		if (loginTimeLong != null) {
			if (loginTimeLong instanceof Long) {
				this.loginTimeLong= (Long) loginTimeLong;
			}else if (StringTool.isInteger(loginTimeLong.toString())) {
				this.loginTimeLong = Long.parseLong(loginTimeLong.toString());
			}
		}
	}
	public Long getLoginTimeLong() {
		return this.loginTimeLong ;
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