package all.gen.app_menu.t.entity;

import java.util.List;
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
 * The persistent class for the database table app_menu 
 * APP菜单APP_MENU的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_menu")
public class AppMenuT implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<AppMenuT > children;
	public List<AppMenuT> getChildren() {
		return children;
	}
	public void setChildren(List<AppMenuT> children) {
		this.children = children;
	}
				
			
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
		
			
//APP菜单主键APP_MENU_ID_
@Column(name="APP_MENU_ID_")
	private String appMenuId;
	
	public void setAppMenuId(String appMenuId) {
		this.appMenuId=appMenuId;
	}
	public String getAppMenuId() {
		return this.appMenuId ;
	}
			
			
//菜单名称APP_MENU_NAME_
@Column(name="APP_MENU_NAME_")
	private String appMenuName;
	
	public void setAppMenuName(String appMenuName) {
		this.appMenuName=appMenuName;
	}
	public String getAppMenuName() {
		return this.appMenuName ;
	}
			
			
//菜单编码APP_MENU_CODE_
@Column(name="APP_MENU_CODE_")
	private String appMenuCode;
	
	public void setAppMenuCode(String appMenuCode) {
		this.appMenuCode=appMenuCode;
	}
	public String getAppMenuCode() {
		return this.appMenuCode ;
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
			
			
//管理菜单地址ADMIN_URL_
@Column(name="ADMIN_URL_")
	private String adminUrl;
	
	public void setAdminUrl(String adminUrl) {
		this.adminUrl=adminUrl;
	}
	public String getAdminUrl() {
		return this.adminUrl ;
	}
			
			
//管理菜单图标默认地址ADMIN_PIC_
@Column(name="ADMIN_PIC_")
	private String adminPic;
	
	public void setAdminPic(String adminPic) {
		this.adminPic=adminPic;
	}
	public String getAdminPic() {
		return this.adminPic ;
	}
			
			
//管理菜单图标打开地址ADMIN_PIC_OPEN_
@Column(name="ADMIN_PIC_OPEN_")
	private String adminPicOpen;
	
	public void setAdminPicOpen(String adminPicOpen) {
		this.adminPicOpen=adminPicOpen;
	}
	public String getAdminPicOpen() {
		return this.adminPicOpen ;
	}
			
			
//管理菜单图标关闭地址ADMIN_PIC_CLOSE_
@Column(name="ADMIN_PIC_CLOSE_")
	private String adminPicClose;
	
	public void setAdminPicClose(String adminPicClose) {
		this.adminPicClose=adminPicClose;
	}
	public String getAdminPicClose() {
		return this.adminPicClose ;
	}
			
			
//PC菜单地址PC_URL_
@Column(name="PC_URL_")
	private String pcUrl;
	
	public void setPcUrl(String pcUrl) {
		this.pcUrl=pcUrl;
	}
	public String getPcUrl() {
		return this.pcUrl ;
	}
			
			
//PC菜单图标默认地址PC_PIC_
@Column(name="PC_PIC_")
	private String pcPic;
	
	public void setPcPic(String pcPic) {
		this.pcPic=pcPic;
	}
	public String getPcPic() {
		return this.pcPic ;
	}
			
			
//PC菜单图标打开地址PC_PIC_OPEN_
@Column(name="PC_PIC_OPEN_")
	private String pcPicOpen;
	
	public void setPcPicOpen(String pcPicOpen) {
		this.pcPicOpen=pcPicOpen;
	}
	public String getPcPicOpen() {
		return this.pcPicOpen ;
	}
			
			
//PC菜单图标关闭地址PC_PIC_CLOSE_
@Column(name="PC_PIC_CLOSE_")
	private String pcPicClose;
	
	public void setPcPicClose(String pcPicClose) {
		this.pcPicClose=pcPicClose;
	}
	public String getPcPicClose() {
		return this.pcPicClose ;
	}
			
			
//APP菜单地址APP_URL_
@Column(name="APP_URL_")
	private String appUrl;
	
	public void setAppUrl(String appUrl) {
		this.appUrl=appUrl;
	}
	public String getAppUrl() {
		return this.appUrl ;
	}
			
			
//APP菜单图标默认地址APP_PIC_
@Column(name="APP_PIC_")
	private String appPic;
	
	public void setAppPic(String appPic) {
		this.appPic=appPic;
	}
	public String getAppPic() {
		return this.appPic ;
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