package all.gen.sys_quartz_config.t.entity;

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
 * The persistent class for the database table sys_quartz_config 
 * 定时器配置的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_quartz_config")
public class SysQuartzConfigT implements Serializable {

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
		
			
//SYS_QUARTZ_CONFIG_ID_
@Column(name="SYS_QUARTZ_CONFIG_ID_")
	private String sysQuartzConfigId;
	
	public void setSysQuartzConfigId(String sysQuartzConfigId) {
		this.sysQuartzConfigId=sysQuartzConfigId;
	}
	public String getSysQuartzConfigId() {
		return this.sysQuartzConfigId ;
	}
			
			
//配置名称
@Column(name="QUARTZ_CONFIG_NAME_")
	private String quartzConfigName;
	
	public void setQuartzConfigName(String quartzConfigName) {
		this.quartzConfigName=quartzConfigName;
	}
	public String getQuartzConfigName() {
		return this.quartzConfigName ;
	}
			
			
//是否启动STARTED_
@Column(name="STARTED_")
	private String started;
	
	public void setStarted(String started) {
		this.started=started;
	}
	public String getStarted() {
		return this.started ;
	}
			
			
//是否暂停INSTANDBY_MODE_
@Column(name="INSTANDBY_MODE_")
	private String instandbyMode;
	
	public void setInstandbyMode(String instandbyMode) {
		this.instandbyMode=instandbyMode;
	}
	public String getInstandbyMode() {
		return this.instandbyMode ;
	}
			
			
//是否启动
@Column(name="STARTED_CN_")
	private String startedCn;
	
	public void setStartedCn(String startedCn) {
		this.startedCn=startedCn;
	}
	public String getStartedCn() {
		return this.startedCn ;
	}
			
			
//是否暂停
@Column(name="INSTANDBY_MODE_CN_")
	private String instandbyModeCn;
	
	public void setInstandbyModeCn(String instandbyModeCn) {
		this.instandbyModeCn=instandbyModeCn;
	}
	public String getInstandbyModeCn() {
		return this.instandbyModeCn ;
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