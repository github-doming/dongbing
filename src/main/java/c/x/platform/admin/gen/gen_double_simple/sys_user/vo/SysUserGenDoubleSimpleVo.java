package c.x.platform.admin.gen.gen_double_simple.sys_user.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_user 
 * vo类
 */

public class SysUserGenDoubleSimpleVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//ID_
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
	}
	
//主键
	private String sysUserId;
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
	
//SYS_PERSON_ID_
	private String sysPersonId;
	public void setSysPersonId(String sysPersonId) {
		this.sysPersonId=sysPersonId;
	}
	public String getSysPersonId() {
		return this.sysPersonId ;
	}
	
//用户名
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//数据权限等级
	private String permissionGrade;
	public void setPermissionGrade(String permissionGrade) {
		this.permissionGrade=permissionGrade;
	}
	public String getPermissionGrade() {
		return this.permissionGrade ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	
//CREATE_TIME_
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//CREATE_TIME_LONG_
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//UPDATE_TIME_
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//UPDATE_TIME_LONG_
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}


}