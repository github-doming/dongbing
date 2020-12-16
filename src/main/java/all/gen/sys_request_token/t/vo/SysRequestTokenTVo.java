package all.gen.sys_request_token.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_request_token 
 * vo类
 */

public class SysRequestTokenTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//主键
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
	}
	
//SYS_REQUEST_TOKEN_ID_
	private String sysRequestTokenId;
	public void setSysRequestTokenId(String sysRequestTokenId) {
		this.sysRequestTokenId=sysRequestTokenId;
	}
	public String getSysRequestTokenId() {
		return this.sysRequestTokenId ;
	}
	
//令牌
	private String token;
	public void setToken(String token) {
		this.token=token;
	}
	public String getToken() {
		return this.token ;
	}
	
//版本
	private String version;
	public void setVersion(String version) {
		this.version=version;
	}
	public String getVersion() {
		return this.version ;
	}
	
//SERVLET_PATH_
	private String servletPath;
	public void setServletPath(String servletPath) {
		this.servletPath=servletPath;
	}
	public String getServletPath() {
		return this.servletPath ;
	}
	
//失效时间
	private String expiredTime;
	public void setExpiredTime(String expiredTime) {
		this.expiredTime=expiredTime;
	}
	public String getExpiredTime() {
		return this.expiredTime ;
	}
	
//失效时间
	private String expiredTimeLong;
	public void setExpiredTimeLong(String expiredTimeLong) {
		this.expiredTimeLong=expiredTimeLong;
	}
	public String getExpiredTimeLong() {
		return this.expiredTimeLong ;
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