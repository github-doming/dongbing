package all.gen.sys_group.t.entity;

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
 * The persistent class for the database table sys_group 
 * 后台用户组的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_group")
public class SysGroupT implements Serializable {

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
		
			
//SYS_GROUP_ID_
@Column(name="SYS_GROUP_ID_")
	private String sysGroupId;
	
	public void setSysGroupId(String sysGroupId) {
		this.sysGroupId=sysGroupId;
	}
	public String getSysGroupId() {
		return this.sysGroupId ;
	}
			
			
//后台用户组名称SYS_GROUP_NAME_
@Column(name="SYS_GROUP_NAME_")
	private String sysGroupName;
	
	public void setSysGroupName(String sysGroupName) {
		this.sysGroupName=sysGroupName;
	}
	public String getSysGroupName() {
		return this.sysGroupName ;
	}
			
			
//后台用户组类型SYS_GROUP_TYPE_
@Column(name="SYS_GROUP_TYPE_")
	private String sysGroupType;
	
	public void setSysGroupType(String sysGroupType) {
		this.sysGroupType=sysGroupType;
	}
	public String getSysGroupType() {
		return this.sysGroupType ;
	}
			
			
//后台用户组编码SYS_GROUP_CODE_
@Column(name="SYS_GROUP_CODE_")
	private String sysGroupCode;
	
	public void setSysGroupCode(String sysGroupCode) {
		this.sysGroupCode=sysGroupCode;
	}
	public String getSysGroupCode() {
		return this.sysGroupCode ;
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