package all.gen.sys_account.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_account 
 * vo类
 */

public class SysAccountTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_ACCOUNT_ID_
	private String sysAccountId;
	public void setSysAccountId(String sysAccountId) {
		this.sysAccountId=sysAccountId;
	}
	public String getSysAccountId() {
		return this.sysAccountId ;
	}
	
//SYS_USER_ID_
	private String sysUserId;
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
	
//账号名称SYS_ACCOUNT_NAME_
	private String sysAccountName;
	public void setSysAccountName(String sysAccountName) {
		this.sysAccountName=sysAccountName;
	}
	public String getSysAccountName() {
		return this.sysAccountName ;
	}
	
//后台账户编码SYS_ACCOUNT_CODE_
	private String sysAccountCode;
	public void setSysAccountCode(String sysAccountCode) {
		this.sysAccountCode=sysAccountCode;
	}
	public String getSysAccountCode() {
		return this.sysAccountCode ;
	}
	
//密码
	private String password;
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return this.password ;
	}
	
//账户类型ACCOUNT_TYPE_
	private String accountType;
	public void setAccountType(String accountType) {
		this.accountType=accountType;
	}
	public String getAccountType() {
		return this.accountType ;
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
	
//创建时间
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
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
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