package all.gen.sys_exception.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_exception 
 * vo类
 */

public class SysExceptionTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//主键
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
	}
	
//SYS_EXCEPTION_ID_
	private String sysExceptionId;
	public void setSysExceptionId(String sysExceptionId) {
		this.sysExceptionId=sysExceptionId;
	}
	public String getSysExceptionId() {
		return this.sysExceptionId ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//EXCEPTION_CLASS_
	private String exceptionClass;
	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass=exceptionClass;
	}
	public String getExceptionClass() {
		return this.exceptionClass ;
	}
	
//BIZ_CLASS_
	private String bizClass;
	public void setBizClass(String bizClass) {
		this.bizClass=bizClass;
	}
	public String getBizClass() {
		return this.bizClass ;
	}
	
//METHOD_NAME_
	private String methodName;
	public void setMethodName(String methodName) {
		this.methodName=methodName;
	}
	public String getMethodName() {
		return this.methodName ;
	}
	
//LINE_NUMBER_
	private String lineNumber;
	public void setLineNumber(String lineNumber) {
		this.lineNumber=lineNumber;
	}
	public String getLineNumber() {
		return this.lineNumber ;
	}
	
//MSG_
	private String msg;
	public void setMsg(String msg) {
		this.msg=msg;
	}
	public String getMsg() {
		return this.msg ;
	}
	
//SERVLET_PATH_
	private String servletPath;
	public void setServletPath(String servletPath) {
		this.servletPath=servletPath;
	}
	public String getServletPath() {
		return this.servletPath ;
	}
	
//SYS_USER_ID_
	private String sysUserId;
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
	
//SYS_USER_NAME_
	private String sysUserName;
	public void setSysUserName(String sysUserName) {
		this.sysUserName=sysUserName;
	}
	public String getSysUserName() {
		return this.sysUserName ;
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
	
//编码CODE_
	private String code;
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}
	
//名称缩写AB_
	private String ab;
	public void setAb(String ab) {
		this.ab=ab;
	}
	public String getAb() {
		return this.ab ;
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