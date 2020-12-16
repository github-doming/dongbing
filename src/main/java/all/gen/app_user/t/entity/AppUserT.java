package all.gen.app_user.t.entity;

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
 * The persistent class for the database table app_user 
 * APP用户表的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_user")
public class AppUserT implements Serializable {

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
		
			
//APP用户主键APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//用户主账号MAIN_APP_ACCOUNT_ID_
@Column(name="MAIN_APP_ACCOUNT_ID_")
	private String mainAppAccountId;
	
	public void setMainAppAccountId(String mainAppAccountId) {
		this.mainAppAccountId=mainAppAccountId;
	}
	public String getMainAppAccountId() {
		return this.mainAppAccountId ;
	}
			
			
//用户主键SYS_USER_ID_
@Column(name="SYS_USER_ID_")
	private String sysUserId;
	
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
			
			
//APP用户名称APP_USER_NAME_
@Column(name="APP_USER_NAME_")
	private String appUserName;
	
	public void setAppUserName(String appUserName) {
		this.appUserName=appUserName;
	}
	public String getAppUserName() {
		return this.appUserName ;
	}
			
			
//APP用户编码APP_USER_CODE_
@Column(name="APP_USER_CODE_")
	private String appUserCode;
	
	public void setAppUserCode(String appUserCode) {
		this.appUserCode=appUserCode;
	}
	public String getAppUserCode() {
		return this.appUserCode ;
	}
			
			
//APP用户类型APP_USER_TYPE_
@Column(name="APP_USER_TYPE_")
	private String appUserType;
	
	public void setAppUserType(String appUserType) {
		this.appUserType=appUserType;
	}
	public String getAppUserType() {
		return this.appUserType ;
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
			
			
//头像HEAD_PORTRAIT_
@Column(name="HEAD_PORTRAIT_")
	private String headPortrait;
	
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait=headPortrait;
	}
	public String getHeadPortrait() {
		return this.headPortrait ;
	}
			
			
//昵称NICKNAME_
@Column(name="NICKNAME_")
	private String nickname;
	
	public void setNickname(String nickname) {
		this.nickname=nickname;
	}
	public String getNickname() {
		return this.nickname ;
	}
			
			
//实名
@Column(name="REALNAME_")
	private String realname;
	
	public void setRealname(String realname) {
		this.realname=realname;
	}
	public String getRealname() {
		return this.realname ;
	}
			
			
//性别
@Column(name="GENDER_")
	private String gender;
	
	public void setGender(String gender) {
		this.gender=gender;
	}
	public String getGender() {
		return this.gender ;
	}
			
			
//年龄
@Column(name="AGE_")
	private Integer age;
	
	public void setAge(Integer age) {
		this.age=age;
	}
	public Integer getAge() {
		return this.age ;
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
			
			
//次序
@Column(name="SN_")
	private Integer sn;
	
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
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