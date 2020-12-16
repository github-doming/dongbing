package all.gen.sys_menu.t.entity;

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
 * The persistent class for the database table sys_menu 
 * 后台菜单的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_menu")
public class SysMenuT implements Serializable {

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
		
			
//SYS_MENU_ID_
@Column(name="SYS_MENU_ID_")
	private String sysMenuId;
	
	public void setSysMenuId(String sysMenuId) {
		this.sysMenuId=sysMenuId;
	}
	public String getSysMenuId() {
		return this.sysMenuId ;
	}
			
			
//后台菜单名称SYS_MENU_NAME_
@Column(name="SYS_MENU_NAME_")
	private String sysMenuName;
	
	public void setSysMenuName(String sysMenuName) {
		this.sysMenuName=sysMenuName;
	}
	public String getSysMenuName() {
		return this.sysMenuName ;
	}
			
			
//后台菜单编码SYS_MENU_CODE_
@Column(name="SYS_MENU_CODE_")
	private String sysMenuCode;
	
	public void setSysMenuCode(String sysMenuCode) {
		this.sysMenuCode=sysMenuCode;
	}
	public String getSysMenuCode() {
		return this.sysMenuCode ;
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
			
			
//权限码
@Column(name="PERMISSION_CODE_")
	private String permissionCode;
	
	public void setPermissionCode(String permissionCode) {
		this.permissionCode=permissionCode;
	}
	public String getPermissionCode() {
		return this.permissionCode ;
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
			
			
//地址URL_
@Column(name="URL_")
	private String url;
	
	public void setUrl(String url) {
		this.url=url;
	}
	public String getUrl() {
		return this.url ;
	}
			
			
//图标默认地址PIC_
@Column(name="PIC_")
	private String pic;
	
	public void setPic(String pic) {
		this.pic=pic;
	}
	public String getPic() {
		return this.pic ;
	}
			
			
//图标打开地址PIC_OPEN_
@Column(name="PIC_OPEN_")
	private String picOpen;
	
	public void setPicOpen(String picOpen) {
		this.picOpen=picOpen;
	}
	public String getPicOpen() {
		return this.picOpen ;
	}
			
			
//图标关闭地址PIC_CLOSE_
@Column(name="PIC_CLOSE_")
	private String picClose;
	
	public void setPicClose(String picClose) {
		this.picClose=picClose;
	}
	public String getPicClose() {
		return this.picClose ;
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