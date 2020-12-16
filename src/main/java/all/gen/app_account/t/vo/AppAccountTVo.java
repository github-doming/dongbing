package all.gen.app_account.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_account 
 * vo类
 */

public class AppAccountTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//APP_ACCOUNT_ID_
	private String appAccountId;
	public void setAppAccountId(String appAccountId) {
		this.appAccountId=appAccountId;
	}
	public String getAppAccountId() {
		return this.appAccountId ;
	}
	
//APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//账号名称ACCOUNT_NAME_
	private String accountName;
	public void setAccountName(String accountName) {
		this.accountName=accountName;
	}
	public String getAccountName() {
		return this.accountName ;
	}
	
//账号编码ACCOUNT_CODE_
	private String accountCode;
	public void setAccountCode(String accountCode) {
		this.accountCode=accountCode;
	}
	public String getAccountCode() {
		return this.accountCode ;
	}
	
//账户类型ACCOUNT_TYPE_
	private String accountType;
	public void setAccountType(String accountType) {
		this.accountType=accountType;
	}
	public String getAccountType() {
		return this.accountType ;
	}
	
//相当于表名
	private String tableName;
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
	
//密码
	private String password;
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return this.password ;
	}
	
//注册类型或来源(APP或PC或管理员或程序)REGISTER_TYPE_
	private String registerType;
	public void setRegisterType(String registerType) {
		this.registerType=registerType;
	}
	public String getRegisterType() {
		return this.registerType ;
	}
	
//渠道类型(APP,PC,邮件，短信，微信)CHANNEL_TYPE_
	private String channelType;
	public void setChannelType(String channelType) {
		this.channelType=channelType;
	}
	public String getChannelType() {
		return this.channelType ;
	}
	
//数据权限等级
	private String permissionGrade;
	public void setPermissionGrade(String permissionGrade) {
		this.permissionGrade=permissionGrade;
	}
	public String getPermissionGrade() {
		return this.permissionGrade ;
	}
	
//登录时间LOGIN_TIME_
	private String loginTime;
	public void setLoginTime(String loginTime) {
		this.loginTime=loginTime;
	}
	public String getLoginTime() {
		return this.loginTime ;
	}
	
//登录时间数字型LOGIN_TIME_LONG_
	private String loginTimeLong;
	public void setLoginTimeLong(String loginTimeLong) {
		this.loginTimeLong=loginTimeLong;
	}
	public String getLoginTimeLong() {
		return this.loginTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//创建者CREATE_USER_
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间数字型CREATE_TIME_LONG_
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新者UPDATE_USER_
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间数字型UPDATE_TIME_LONG_
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//租户编码TENANT_CODE_
	private String tenantCode;
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public String getTenantCode() {
		return this.tenantCode ;
	}
	
//租户编号TENANT_NUMBER_
	private String tenantNumber;
	public void setTenantNumber(String tenantNumber) {
		this.tenantNumber=tenantNumber;
	}
	public String getTenantNumber() {
		return this.tenantNumber ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}


}