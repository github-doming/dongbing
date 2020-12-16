package all.gen.sys_exception.t.entity;

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
 * The persistent class for the database table sys_exception 
 * 后台异常表的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_exception")
public class SysExceptionT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//主键
@Column(name="ID_")
	private Long id;
	
	public void setId(Long id) {
		this.id=id;
	}
	public Long getId() {
		return this.id ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//SYS_EXCEPTION_ID_
@Column(name="SYS_EXCEPTION_ID_")
	private String sysExceptionId;
	
	public void setSysExceptionId(String sysExceptionId) {
		this.sysExceptionId=sysExceptionId;
	}
	public String getSysExceptionId() {
		return this.sysExceptionId ;
	}
			
			
//名称
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
			
//EXCEPTION_CLASS_
@Column(name="EXCEPTION_CLASS_")
	private String exceptionClass;
	
	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass=exceptionClass;
	}
	public String getExceptionClass() {
		return this.exceptionClass ;
	}
			
			
//BIZ_CLASS_
@Column(name="BIZ_CLASS_")
	private String bizClass;
	
	public void setBizClass(String bizClass) {
		this.bizClass=bizClass;
	}
	public String getBizClass() {
		return this.bizClass ;
	}
			
			
//METHOD_NAME_
@Column(name="METHOD_NAME_")
	private String methodName;
	
	public void setMethodName(String methodName) {
		this.methodName=methodName;
	}
	public String getMethodName() {
		return this.methodName ;
	}
			
			
//LINE_NUMBER_
@Column(name="LINE_NUMBER_")
	private Integer lineNumber;
	
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber=lineNumber;
	}
	public Integer getLineNumber() {
		return this.lineNumber ;
	}
			
			
//MSG_
@Column(name="MSG_")
	private String msg;
	
	public void setMsg(String msg) {
		this.msg=msg;
	}
	public String getMsg() {
		return this.msg ;
	}
			
			
//SERVLET_PATH_
@Column(name="SERVLET_PATH_")
	private String servletPath;
	
	public void setServletPath(String servletPath) {
		this.servletPath=servletPath;
	}
	public String getServletPath() {
		return this.servletPath ;
	}
			
			
//SYS_USER_ID_
@Column(name="SYS_USER_ID_")
	private String sysUserId;
	
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
			
			
//SYS_USER_NAME_
@Column(name="SYS_USER_NAME_")
	private String sysUserName;
	
	public void setSysUserName(String sysUserName) {
		this.sysUserName=sysUserName;
	}
	public String getSysUserName() {
		return this.sysUserName ;
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
			
			
//创建时间
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
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
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
			
			
//编码CODE_
@Column(name="CODE_")
	private String code;
	
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}
			
			
//名称缩写AB_
@Column(name="AB_")
	private String ab;
	
	public void setAb(String ab) {
		this.ab=ab;
	}
	public String getAb() {
		return this.ab ;
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