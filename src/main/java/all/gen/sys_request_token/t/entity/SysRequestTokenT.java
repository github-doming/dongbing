package all.gen.sys_request_token.t.entity;

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
 * The persistent class for the database table sys_request_token 
 * 后台请求令牌SYS_REQUEST_TOKEN的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_request_token")
public class SysRequestTokenT implements Serializable {

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
		
			
//SYS_REQUEST_TOKEN_ID_
@Column(name="SYS_REQUEST_TOKEN_ID_")
	private String sysRequestTokenId;
	
	public void setSysRequestTokenId(String sysRequestTokenId) {
		this.sysRequestTokenId=sysRequestTokenId;
	}
	public String getSysRequestTokenId() {
		return this.sysRequestTokenId ;
	}
			
			
//令牌
@Column(name="TOKEN_")
	private String token;
	
	public void setToken(String token) {
		this.token=token;
	}
	public String getToken() {
		return this.token ;
	}
			
			
//版本
@Column(name="VERSION_")
	private Integer version;
	
	public void setVersion(Integer version) {
		this.version=version;
	}
	public Integer getVersion() {
		return this.version ;
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
			
		@Temporal( TemporalType.TIMESTAMP)
		
//失效时间
@Column(name="EXPIRED_TIME_")
	private Date expiredTime;
	
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime=expiredTime;
	}
	public Date getExpiredTime() {
		return this.expiredTime ;
	}
			
			
//失效时间
@Column(name="EXPIRED_TIME_LONG_")
	private Long expiredTimeLong;
	
	public void setExpiredTimeLong(Long expiredTimeLong) {
		this.expiredTimeLong=expiredTimeLong;
	}
	public Long getExpiredTimeLong() {
		return this.expiredTimeLong ;
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