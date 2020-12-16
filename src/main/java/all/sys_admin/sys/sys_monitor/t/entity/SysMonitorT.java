package all.sys_admin.sys.sys_monitor.t.entity;

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
 * The persistent class for the database table sys_monitor 
 * 后台监控(系统)表SYS_MONITOR的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_monitor")
public class SysMonitorT implements Serializable {

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
		
			
//后台监控(系统)表主键SYS_MONITOR_ID_
@Column(name="SYS_MONITOR_ID_")
	private String sysMonitorId;
	
	public void setSysMonitorId(String sysMonitorId) {
		this.sysMonitorId=sysMonitorId;
	}
	public String getSysMonitorId() {
		return this.sysMonitorId ;
	}
			
			
//操作系统OS_
@Column(name="OS_")
	private String os;
	
	public void setOs(String os) {
		this.os=os;
	}
	public String getOs() {
		return this.os ;
	}
			
			
//PID_
@Column(name="PID_")
	private String pid;
	
	public void setPid(String pid) {
		this.pid=pid;
	}
	public String getPid() {
		return this.pid ;
	}
			
			
//JVM 线程数THREAD_COUNT_
@Column(name="THREAD_COUNT_")
	private String threadCount;
	
	public void setThreadCount(String threadCount) {
		this.threadCount=threadCount;
	}
	public String getThreadCount() {
		return this.threadCount ;
	}
			
			
//CPU使用率CPU_RATE_
@Column(name="CPU_RATE_")
	private String cpuRate;
	
	public void setCpuRate(String cpuRate) {
		this.cpuRate=cpuRate;
	}
	public String getCpuRate() {
		return this.cpuRate ;
	}
			
			
//物理内存总数MEMORY_TOTAL_
@Column(name="MEMORY_TOTAL_")
	private String memoryTotal;
	
	public void setMemoryTotal(String memoryTotal) {
		this.memoryTotal=memoryTotal;
	}
	public String getMemoryTotal() {
		return this.memoryTotal ;
	}
			
			
//JVM内存使用MEMORY_JVM_
@Column(name="MEMORY_JVM_")
	private String memoryJvm;
	
	public void setMemoryJvm(String memoryJvm) {
		this.memoryJvm=memoryJvm;
	}
	public String getMemoryJvm() {
		return this.memoryJvm ;
	}
			
			
//内存占用率MEMORY_RATE_
@Column(name="MEMORY_RATE_")
	private String memoryRate;
	
	public void setMemoryRate(String memoryRate) {
		this.memoryRate=memoryRate;
	}
	public String getMemoryRate() {
		return this.memoryRate ;
	}
			
			
//上行速度UP_SPEED_
@Column(name="UP_SPEED_")
	private String upSpeed;
	
	public void setUpSpeed(String upSpeed) {
		this.upSpeed=upSpeed;
	}
	public String getUpSpeed() {
		return this.upSpeed ;
	}
			
			
//下行速度DOWN_SPEED_
@Column(name="DOWN_SPEED_")
	private String downSpeed;
	
	public void setDownSpeed(String downSpeed) {
		this.downSpeed=downSpeed;
	}
	public String getDownSpeed() {
		return this.downSpeed ;
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