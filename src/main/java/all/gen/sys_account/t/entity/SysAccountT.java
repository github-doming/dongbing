package all.gen.sys_account.t.entity;

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
 * The persistent class for the database table sys_account 
 * 后台账号表的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_account")
public class SysAccountT implements Serializable {

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
		
			
//SYS_ACCOUNT_ID_
@Column(name="SYS_ACCOUNT_ID_")
	private String sysAccountId;
	
	public void setSysAccountId(String sysAccountId) {
		this.sysAccountId=sysAccountId;
	}
	public String getSysAccountId() {
		return this.sysAccountId ;
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
			
			
//账号名称SYS_ACCOUNT_NAME_
@Column(name="SYS_ACCOUNT_NAME_")
	private String sysAccountName;
	
	public void setSysAccountName(String sysAccountName) {
		this.sysAccountName=sysAccountName;
	}
	public String getSysAccountName() {
		return this.sysAccountName ;
	}
			
			
//后台账户编码SYS_ACCOUNT_CODE_
@Column(name="SYS_ACCOUNT_CODE_")
	private String sysAccountCode;
	
	public void setSysAccountCode(String sysAccountCode) {
		this.sysAccountCode=sysAccountCode;
	}
	public String getSysAccountCode() {
		return this.sysAccountCode ;
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
			
			
//账户类型ACCOUNT_TYPE_
@Column(name="ACCOUNT_TYPE_")
	private String accountType;
	
	public void setAccountType(String accountType) {
		this.accountType=accountType;
	}
	public String getAccountType() {
		return this.accountType ;
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