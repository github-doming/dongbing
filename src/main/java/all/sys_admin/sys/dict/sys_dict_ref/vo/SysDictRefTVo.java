package all.sys_admin.sys.dict.sys_dict_ref.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_dict_ref 
 * vo类
 */

public class SysDictRefTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//后台数据字典关联表主键SYS_DICT_REF_ID_
	private String sysDictRefId;
	public void setSysDictRefId(String sysDictRefId) {
		this.sysDictRefId=sysDictRefId;
	}
	public String getSysDictRefId() {
		return this.sysDictRefId ;
	}
	
//后台数据字典分类表主键SYS_DICT_TREE_ID_
	private String sysDictTreeId;
	public void setSysDictTreeId(String sysDictTreeId) {
		this.sysDictTreeId=sysDictTreeId;
	}
	public String getSysDictTreeId() {
		return this.sysDictTreeId ;
	}
	
//后台数据字典主键SYS_DICT_ID_
	private String sysDictId;
	public void setSysDictId(String sysDictId) {
		this.sysDictId=sysDictId;
	}
	public String getSysDictId() {
		return this.sysDictId ;
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