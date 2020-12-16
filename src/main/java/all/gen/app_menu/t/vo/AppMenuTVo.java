package all.gen.app_menu.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_menu 
 * vo类
 */

public class AppMenuTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//APP菜单主键APP_MENU_ID_
	private String appMenuId;
	public void setAppMenuId(String appMenuId) {
		this.appMenuId=appMenuId;
	}
	public String getAppMenuId() {
		return this.appMenuId ;
	}
	
//菜单名称APP_MENU_NAME_
	private String appMenuName;
	public void setAppMenuName(String appMenuName) {
		this.appMenuName=appMenuName;
	}
	public String getAppMenuName() {
		return this.appMenuName ;
	}
	
//菜单编码APP_MENU_CODE_
	private String appMenuCode;
	public void setAppMenuCode(String appMenuCode) {
		this.appMenuCode=appMenuCode;
	}
	public String getAppMenuCode() {
		return this.appMenuCode ;
	}
	
//渠道类型(APP,PC,邮件，短信，微信)CHANNEL_TYPE_
	private String channelType;
	public void setChannelType(String channelType) {
		this.channelType=channelType;
	}
	public String getChannelType() {
		return this.channelType ;
	}
	
//权限码
	private String permissionCode;
	public void setPermissionCode(String permissionCode) {
		this.permissionCode=permissionCode;
	}
	public String getPermissionCode() {
		return this.permissionCode ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}
	
//树上一级
	private String parent;
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
	
//树PATH_
	private String path;
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
	
//树编码
	private String treeCode;
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
	
//管理菜单地址ADMIN_URL_
	private String adminUrl;
	public void setAdminUrl(String adminUrl) {
		this.adminUrl=adminUrl;
	}
	public String getAdminUrl() {
		return this.adminUrl ;
	}
	
//管理菜单图标默认地址ADMIN_PIC_
	private String adminPic;
	public void setAdminPic(String adminPic) {
		this.adminPic=adminPic;
	}
	public String getAdminPic() {
		return this.adminPic ;
	}
	
//管理菜单图标打开地址ADMIN_PIC_OPEN_
	private String adminPicOpen;
	public void setAdminPicOpen(String adminPicOpen) {
		this.adminPicOpen=adminPicOpen;
	}
	public String getAdminPicOpen() {
		return this.adminPicOpen ;
	}
	
//管理菜单图标关闭地址ADMIN_PIC_CLOSE_
	private String adminPicClose;
	public void setAdminPicClose(String adminPicClose) {
		this.adminPicClose=adminPicClose;
	}
	public String getAdminPicClose() {
		return this.adminPicClose ;
	}
	
//PC菜单地址PC_URL_
	private String pcUrl;
	public void setPcUrl(String pcUrl) {
		this.pcUrl=pcUrl;
	}
	public String getPcUrl() {
		return this.pcUrl ;
	}
	
//PC菜单图标默认地址PC_PIC_
	private String pcPic;
	public void setPcPic(String pcPic) {
		this.pcPic=pcPic;
	}
	public String getPcPic() {
		return this.pcPic ;
	}
	
//PC菜单图标打开地址PC_PIC_OPEN_
	private String pcPicOpen;
	public void setPcPicOpen(String pcPicOpen) {
		this.pcPicOpen=pcPicOpen;
	}
	public String getPcPicOpen() {
		return this.pcPicOpen ;
	}
	
//PC菜单图标关闭地址PC_PIC_CLOSE_
	private String pcPicClose;
	public void setPcPicClose(String pcPicClose) {
		this.pcPicClose=pcPicClose;
	}
	public String getPcPicClose() {
		return this.pcPicClose ;
	}
	
//APP菜单地址APP_URL_
	private String appUrl;
	public void setAppUrl(String appUrl) {
		this.appUrl=appUrl;
	}
	public String getAppUrl() {
		return this.appUrl ;
	}
	
//APP菜单图标默认地址APP_PIC_
	private String appPic;
	public void setAppPic(String appPic) {
		this.appPic=appPic;
	}
	public String getAppPic() {
		return this.appPic ;
	}
	
//数据权限等级
	private String permissionGrade;
	public void setPermissionGrade(String permissionGrade) {
		this.permissionGrade=permissionGrade;
	}
	public String getPermissionGrade() {
		return this.permissionGrade ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
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
	
//创建时间数字型CREATE_TIME_LONG_
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
	
//更新时间数字型UPDATE_TIME_LONG_
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//租户编码TENANT_CODE_
	private String tenantCode;
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public String getTenantCode() {
		return this.tenantCode ;
	}
	
//租户编号TENANT_NUMBER_
	private String tenantNumber;
	public void setTenantNumber(String tenantNumber) {
		this.tenantNumber=tenantNumber;
	}
	public String getTenantNumber() {
		return this.tenantNumber ;
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