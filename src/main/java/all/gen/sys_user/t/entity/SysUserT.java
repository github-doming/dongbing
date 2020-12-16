package all.gen.sys_user.t.entity;

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
 * The persistent class for the database table sys_user 
 * 后台用户表的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_user")
public class SysUserT implements Serializable {

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
		
			
//用户主键SYS_USER_ID_
@Column(name="SYS_USER_ID_")
	private String sysUserId;
	
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
			
			
//用户主账号MAIN_SYS_ACCOUNT_ID_
@Column(name="MAIN_SYS_ACCOUNT_ID_")
	private String mainSysAccountId;
	
	public void setMainSysAccountId(String mainSysAccountId) {
		this.mainSysAccountId=mainSysAccountId;
	}
	public String getMainSysAccountId() {
		return this.mainSysAccountId ;
	}
			
			
//用户名称SYS_USER_NAME_
@Column(name="SYS_USER_NAME_")
	private String sysUserName;
	
	public void setSysUserName(String sysUserName) {
		this.sysUserName=sysUserName;
	}
	public String getSysUserName() {
		return this.sysUserName ;
	}
			
			
//后台用户编码SYS_USER_CODE_
@Column(name="SYS_USER_CODE_")
	private String sysUserCode;
	
	public void setSysUserCode(String sysUserCode) {
		this.sysUserCode=sysUserCode;
	}
	public String getSysUserCode() {
		return this.sysUserCode ;
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
			
			
//树上一级
@Column(name="PARENT_")
	private String parent;
	
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
			
			
//树PATH_
@Column(name="PATH_")
	private String path;
	
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
			
			
//树编码
@Column(name="TREE_CODE_")
	private String treeCode;
	
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
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
			
			
//租户编码TENANT_CODE_
@Column(name="TENANT_CODE_")
	private String tenantCode;
	
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public String getTenantCode() {
		return this.tenantCode ;
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