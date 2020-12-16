package all.gen.app_account.t.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table app_account 
 * APP账号表的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_account")
public class AppAccountT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//APP_ACCOUNT_ID_
@Column(name="APP_ACCOUNT_ID_")
	private String appAccountId;
	
	public void setAppAccountId(String appAccountId) {
		this.appAccountId=appAccountId;
	}
	public String getAppAccountId() {
		return this.appAccountId ;
	}
			
			
//APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//账号名称ACCOUNT_NAME_
@Column(name="ACCOUNT_NAME_")
	private String accountName;
	
	public void setAccountName(String accountName) {
		this.accountName=accountName;
	}
	public String getAccountName() {
		return this.accountName ;
	}
			
			
//账号编码ACCOUNT_CODE_
@Column(name="ACCOUNT_CODE_")
	private String accountCode;
	
	public void setAccountCode(String accountCode) {
		this.accountCode=accountCode;
	}
	public String getAccountCode() {
		return this.accountCode ;
	}
			
			
//账户类型ACCOUNT_TYPE_
@Column(name="ACCOUNT_TYPE_")
	private String accountType;
	
	public void setAccountType(String accountType) {
		this.accountType=accountType;
	}
	public String getAccountType() {
		return this.accountType ;
	}
			
			
//相当于表名
@Column(name="TABLE_NAME_")
	private String tableName;
	
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
			
			
//密码
@Column(name="PASSWORD_")
	private String password;
	
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return this.password ;
	}
			
			
//注册类型或来源(APP或PC或管理员或程序)REGISTER_TYPE_
@Column(name="REGISTER_TYPE_")
	private String registerType;
	
	public void setRegisterType(String registerType) {
		this.registerType=registerType;
	}
	public String getRegisterType() {
		return this.registerType ;
	}
			
			
//渠道类型(APP,PC,邮件，短信，微信)CHANNEL_TYPE_
@Column(name="CHANNEL_TYPE_")
	private String channelType;
	
	public void setChannelType(String channelType) {
		this.channelType=channelType;
	}
	public String getChannelType() {
		return this.channelType ;
	}
			
			
//数据权限等级
@Column(name="PERMISSION_GRADE_")
	private Integer permissionGrade;
	
	public void setPermissionGrade(Integer permissionGrade) {
		this.permissionGrade=permissionGrade;
	}
	public Integer getPermissionGrade() {
		return this.permissionGrade ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//登录时间LOGIN_TIME_
@Column(name="LOGIN_TIME_")
	private Date loginTime;
	
	public void setLoginTime(Date loginTime) {
		this.loginTime=loginTime;
	}
	public Date getLoginTime() {
		return this.loginTime ;
	}
			
			
//登录时间数字型LOGIN_TIME_LONG_
@Column(name="LOGIN_TIME_LONG_")
	private Long loginTimeLong;
	
	public void setLoginTimeLong(Long loginTimeLong) {
		this.loginTimeLong=loginTimeLong;
	}
	public Long getLoginTimeLong() {
		return this.loginTimeLong ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//创建者CREATE_USER_
@Column(name="CREATE_USER_")
	private String createUser;
	
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间数字型CREATE_TIME_LONG_
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
			
//更新者UPDATE_USER_
@Column(name="UPDATE_USER_")
	private String updateUser;
	
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间数字型UPDATE_TIME_LONG_
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//租户编码TENANT_CODE_
@Column(name="TENANT_CODE_")
	private String tenantCode;
	
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public String getTenantCode() {
		return this.tenantCode ;
	}
			
			
//租户编号TENANT_NUMBER_
@Column(name="TENANT_NUMBER_")
	private Integer tenantNumber;
	
	public void setTenantNumber(Integer tenantNumber) {
		this.tenantNumber=tenantNumber;
	}
	public Integer getTenantNumber() {
		return this.tenantNumber ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}