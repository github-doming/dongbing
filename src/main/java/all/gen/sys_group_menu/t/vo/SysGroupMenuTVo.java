package all.gen.sys_group_menu.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_group_menu 
 * vo类
 */

public class SysGroupMenuTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_GROUP_MENU_ID_
	private String sysGroupMenuId;
	public void setSysGroupMenuId(String sysGroupMenuId) {
		this.sysGroupMenuId=sysGroupMenuId;
	}
	public String getSysGroupMenuId() {
		return this.sysGroupMenuId ;
	}
	
//用户组ID
	private String sysGroupId;
	public void setSysGroupId(String sysGroupId) {
		this.sysGroupId=sysGroupId;
	}
	public String getSysGroupId() {
		return this.sysGroupId ;
	}
	
//菜单ID
	private String sysMenuId;
	public void setSysMenuId(String sysMenuId) {
		this.sysMenuId=sysMenuId;
	}
	public String getSysMenuId() {
		return this.sysMenuId ;
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