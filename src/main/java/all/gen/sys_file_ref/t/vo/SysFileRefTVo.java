package all.gen.sys_file_ref.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_file_ref 
 * vo类
 */

public class SysFileRefTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//后台文件关联表主键SYS_FILE_REF_ID_
	private String sysFileRefId;
	public void setSysFileRefId(String sysFileRefId) {
		this.sysFileRefId=sysFileRefId;
	}
	public String getSysFileRefId() {
		return this.sysFileRefId ;
	}
	
//后台文件分类表主健SYS_TEMPLATE_TREE_ID_
	private String sysFileTreeId;
	public void setSysFileTreeId(String sysFileTreeId) {
		this.sysFileTreeId=sysFileTreeId;
	}
	public String getSysFileTreeId() {
		return this.sysFileTreeId ;
	}
	
//后台文件表主键SYS_FILE_ID_
	private String sysFileId;
	public void setSysFileId(String sysFileId) {
		this.sysFileId=sysFileId;
	}
	public String getSysFileId() {
		return this.sysFileId ;
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