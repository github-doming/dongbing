package all.gen.sys_quartz_config.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_quartz_config 
 * vo类
 */

public class SysQuartzConfigTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_QUARTZ_CONFIG_ID_
	private String sysQuartzConfigId;
	public void setSysQuartzConfigId(String sysQuartzConfigId) {
		this.sysQuartzConfigId=sysQuartzConfigId;
	}
	public String getSysQuartzConfigId() {
		return this.sysQuartzConfigId ;
	}
	
//配置名称
	private String quartzConfigName;
	public void setQuartzConfigName(String quartzConfigName) {
		this.quartzConfigName=quartzConfigName;
	}
	public String getQuartzConfigName() {
		return this.quartzConfigName ;
	}
	
//是否启动STARTED_
	private String started;
	public void setStarted(String started) {
		this.started=started;
	}
	public String getStarted() {
		return this.started ;
	}
	
//是否暂停INSTANDBY_MODE_
	private String instandbyMode;
	public void setInstandbyMode(String instandbyMode) {
		this.instandbyMode=instandbyMode;
	}
	public String getInstandbyMode() {
		return this.instandbyMode ;
	}
	
//是否启动
	private String startedCn;
	public void setStartedCn(String startedCn) {
		this.startedCn=startedCn;
	}
	public String getStartedCn() {
		return this.startedCn ;
	}
	
//是否暂停
	private String instandbyModeCn;
	public void setInstandbyModeCn(String instandbyModeCn) {
		this.instandbyModeCn=instandbyModeCn;
	}
	public String getInstandbyModeCn() {
		return this.instandbyModeCn ;
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