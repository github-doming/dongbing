package all.task.tms.project_user.app_user.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_user 
 * vo类
 */

public class AppUserProjectUserVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//APP用户主键APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//用户主账号MAIN_APP_ACCOUNT_ID_
	private String mainAppAccountId;
	public void setMainAppAccountId(String mainAppAccountId) {
		this.mainAppAccountId=mainAppAccountId;
	}
	public String getMainAppAccountId() {
		return this.mainAppAccountId ;
	}
	
//用户主键SYS_USER_ID_
	private String sysUserId;
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
	
//APP用户名称APP_USER_NAME_
	private String appUserName;
	public void setAppUserName(String appUserName) {
		this.appUserName=appUserName;
	}
	public String getAppUserName() {
		return this.appUserName ;
	}
	
//APP用户编码APP_USER_CODE_
	private String appUserCode;
	public void setAppUserCode(String appUserCode) {
		this.appUserCode=appUserCode;
	}
	public String getAppUserCode() {
		return this.appUserCode ;
	}
	
//APP用户类型APP_USER_TYPE_
	private String appUserType;
	public void setAppUserType(String appUserType) {
		this.appUserType=appUserType;
	}
	public String getAppUserType() {
		return this.appUserType ;
	}
	
//相当于表名
	private String tableName;
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
	
//头像HEAD_PORTRAIT_
	private String headPortrait;
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait=headPortrait;
	}
	public String getHeadPortrait() {
		return this.headPortrait ;
	}
	
//昵称NICKNAME_
	private String nickname;
	public void setNickname(String nickname) {
		this.nickname=nickname;
	}
	public String getNickname() {
		return this.nickname ;
	}
	
//实名
	private String realname;
	public void setRealname(String realname) {
		this.realname=realname;
	}
	public String getRealname() {
		return this.realname ;
	}
	
//性别
	private String gender;
	public void setGender(String gender) {
		this.gender=gender;
	}
	public String getGender() {
		return this.gender ;
	}
	
//年龄
	private String age;
	public void setAge(String age) {
		this.age=age;
	}
	public String getAge() {
		return this.age ;
	}
	
//数据权限等级
	private String permissionGrade;
	public void setPermissionGrade(String permissionGrade) {
		this.permissionGrade=permissionGrade;
	}
	public String getPermissionGrade() {
		return this.permissionGrade ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
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