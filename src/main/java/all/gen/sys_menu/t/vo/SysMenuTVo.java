package all.gen.sys_menu.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_menu 
 * vo类
 */

public class SysMenuTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_MENU_ID_
	private String sysMenuId;
	public void setSysMenuId(String sysMenuId) {
		this.sysMenuId=sysMenuId;
	}
	public String getSysMenuId() {
		return this.sysMenuId ;
	}
	
//后台菜单名称SYS_MENU_NAME_
	private String sysMenuName;
	public void setSysMenuName(String sysMenuName) {
		this.sysMenuName=sysMenuName;
	}
	public String getSysMenuName() {
		return this.sysMenuName ;
	}
	
//后台菜单编码SYS_MENU_CODE_
	private String sysMenuCode;
	public void setSysMenuCode(String sysMenuCode) {
		this.sysMenuCode=sysMenuCode;
	}
	public String getSysMenuCode() {
		return this.sysMenuCode ;
	}
	
//数据权限等级
	private String permissionGrade;
	public void setPermissionGrade(String permissionGrade) {
		this.permissionGrade=permissionGrade;
	}
	public String getPermissionGrade() {
		return this.permissionGrade ;
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
	
//地址URL_
	private String url;
	public void setUrl(String url) {
		this.url=url;
	}
	public String getUrl() {
		return this.url ;
	}
	
//图标默认地址PIC_
	private String pic;
	public void setPic(String pic) {
		this.pic=pic;
	}
	public String getPic() {
		return this.pic ;
	}
	
//图标打开地址PIC_OPEN_
	private String picOpen;
	public void setPicOpen(String picOpen) {
		this.picOpen=picOpen;
	}
	public String getPicOpen() {
		return this.picOpen ;
	}
	
//图标关闭地址PIC_CLOSE_
	private String picClose;
	public void setPicClose(String picClose) {
		this.picClose=picClose;
	}
	public String getPicClose() {
		return this.picClose ;
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
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}


}