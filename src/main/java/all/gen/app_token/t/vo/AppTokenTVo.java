package all.gen.app_token.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_token 
 * vo类
 */

public class AppTokenTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//APP_TOKEN_ID_
	private String appTokenId;
	public void setAppTokenId(String appTokenId) {
		this.appTokenId=appTokenId;
	}
	public String getAppTokenId() {
		return this.appTokenId ;
	}
	
//APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//KEY_
	private String key;
	public void setKey(String key) {
		this.key=key;
	}
	public String getKey() {
		return this.key ;
	}
	
//描述
	private String value;
	public void setValue(String value) {
		this.value=value;
	}
	public String getValue() {
		return this.value ;
	}
	
//SESSION_ID_
	private String sessionId;
	public void setSessionId(String sessionId) {
		this.sessionId=sessionId;
	}
	public String getSessionId() {
		return this.sessionId ;
	}
	
//用户类型
	private String userType;
	public void setUserType(String userType) {
		this.userType=userType;
	}
	public String getUserType() {
		return this.userType ;
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
	
//有效时间
	private String periodTime;
	public void setPeriodTime(String periodTime) {
		this.periodTime=periodTime;
	}
	public String getPeriodTime() {
		return this.periodTime ;
	}
	
//有效时间数字型
	private String periodTimeLong;
	public void setPeriodTimeLong(String periodTimeLong) {
		this.periodTimeLong=periodTimeLong;
	}
	public String getPeriodTimeLong() {
		return this.periodTimeLong ;
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