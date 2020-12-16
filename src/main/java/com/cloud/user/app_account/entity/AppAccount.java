package com.cloud.user.app_account.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table app_account 
 * APP账号表 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "app_account")
public class AppAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * APP_ACCOUNT_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="APP_ACCOUNT_ID_")
	private String appAccountId;
	public void setAppAccountId(String appAccountId) {
		this.appAccountId=appAccountId;
	}
	public void setAppAccountId(Object appAccountId) {
		if (appAccountId != null) {
			this.appAccountId = appAccountId.toString();
		}
	}
	public String getAppAccountId() {
		return this.appAccountId ;
	}

	/**
	 * APP用户主键
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
	 * 账号名称ACCOUNT_NAME_
	 */
	@Column(name="ACCOUNT_NAME_")
	private String accountName;
	public void setAccountName(String accountName) {
		this.accountName=accountName;
	}
	public void setAccountName(Object accountName) {
		if (accountName != null) {
			this.accountName = accountName.toString();
		}
	}
	public String getAccountName() {
		return this.accountName ;
	}

	/**
	 * 账号编码ACCOUNT_CODE_
	 */
	@Column(name="ACCOUNT_CODE_")
	private String accountCode;
	public void setAccountCode(String accountCode) {
		this.accountCode=accountCode;
	}
	public void setAccountCode(Object accountCode) {
		if (accountCode != null) {
			this.accountCode = accountCode.toString();
		}
	}
	public String getAccountCode() {
		return this.accountCode ;
	}

	/**
	 * 账户类型ACCOUNT_TYPE_
	 */
	@Column(name="ACCOUNT_TYPE_")
	private String accountType;
	public void setAccountType(String accountType) {
		this.accountType=accountType;
	}
	public void setAccountType(Object accountType) {
		if (accountType != null) {
			this.accountType = accountType.toString();
		}
	}
	public String getAccountType() {
		return this.accountType ;
	}

	/**
	 * 相当于表名
	 */
	@Column(name="TABLE_NAME_")
	private String tableName;
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public void setTableName(Object tableName) {
		if (tableName != null) {
			this.tableName = tableName.toString();
		}
	}
	public String getTableName() {
		return this.tableName ;
	}

	/**
	 * 密码
	 */
	@Column(name="PASSWORD_")
	private String password;
	public void setPassword(String password) {
		this.password=password;
	}
	public void setPassword(Object password) {
		if (password != null) {
			this.password = password.toString();
		}
	}
	public String getPassword() {
		return this.password ;
	}

	/**
	 * 注册类型或来源(APP或PC或管理员或程序)
	 */
	@Column(name="REGISTER_TYPE_")
	private String registerType;
	public void setRegisterType(String registerType) {
		this.registerType=registerType;
	}
	public void setRegisterType(Object registerType) {
		if (registerType != null) {
			this.registerType = registerType.toString();
		}
	}
	public String getRegisterType() {
		return this.registerType ;
	}

	/**
	 * 渠道类型(APP,PC,邮件，短信，微信)
	 */
	@Column(name="CHANNEL_TYPE_")
	private String channelType;
	public void setChannelType(String channelType) {
		this.channelType=channelType;
	}
	public void setChannelType(Object channelType) {
		if (channelType != null) {
			this.channelType = channelType.toString();
		}
	}
	public String getChannelType() {
		return this.channelType ;
	}

	/**
	 * 数据权限等级
	 */
	@Column(name="PERMISSION_GRADE_")
	private Integer permissionGrade;
	public void setPermissionGrade(Integer permissionGrade) {
		this.permissionGrade=permissionGrade;
	}
	public void setPermissionGrade(Object permissionGrade) {
		if (permissionGrade != null) {
			if (permissionGrade instanceof Integer) {
				this.permissionGrade= (Integer) permissionGrade;
			}else if (StringTool.isInteger(permissionGrade.toString())) {
				this.permissionGrade = Integer.parseInt(permissionGrade.toString());
			}
		}
	}
	public Integer getPermissionGrade() {
		return this.permissionGrade ;
	}

	/**
	 * 登录时间LOGIN_TIME_
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
	 * 登录时间数字型LOGIN_TIME_LONG_
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
	 * 租户编码TENANT_CODE_
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
	 * 租户编号TENANT_NUMBER_
	 */
	@Column(name="TENANT_NUMBER_")
	private Integer tenantNumber;
	public void setTenantNumber(Integer tenantNumber) {
		this.tenantNumber=tenantNumber;
	}
	public void setTenantNumber(Object tenantNumber) {
		if (tenantNumber != null) {
			if (tenantNumber instanceof Integer) {
				this.tenantNumber= (Integer) tenantNumber;
			}else if (StringTool.isInteger(tenantNumber.toString())) {
				this.tenantNumber = Integer.parseInt(tenantNumber.toString());
			}
		}
	}
	public Integer getTenantNumber() {
		return this.tenantNumber ;
	}

	/**
	 * 创建者CREATE_USER_
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
	 * 更新者UPDATE_USER_
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