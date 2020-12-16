package all.sys_admin.sys.sys_monitor.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_monitor 
 * vo类
 */

public class SysMonitorTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//后台监控(系统)表主键SYS_MONITOR_ID_
	private String sysMonitorId;
	public void setSysMonitorId(String sysMonitorId) {
		this.sysMonitorId=sysMonitorId;
	}
	public String getSysMonitorId() {
		return this.sysMonitorId ;
	}
	
//操作系统OS_
	private String os;
	public void setOs(String os) {
		this.os=os;
	}
	public String getOs() {
		return this.os ;
	}
	
//PID_
	private String pid;
	public void setPid(String pid) {
		this.pid=pid;
	}
	public String getPid() {
		return this.pid ;
	}
	
//JVM 线程数THREAD_COUNT_
	private String threadCount;
	public void setThreadCount(String threadCount) {
		this.threadCount=threadCount;
	}
	public String getThreadCount() {
		return this.threadCount ;
	}
	
//CPU使用率CPU_RATE_
	private String cpuRate;
	public void setCpuRate(String cpuRate) {
		this.cpuRate=cpuRate;
	}
	public String getCpuRate() {
		return this.cpuRate ;
	}
	
//物理内存总数MEMORY_TOTAL_
	private String memoryTotal;
	public void setMemoryTotal(String memoryTotal) {
		this.memoryTotal=memoryTotal;
	}
	public String getMemoryTotal() {
		return this.memoryTotal ;
	}
	
//JVM内存使用MEMORY_JVM_
	private String memoryJvm;
	public void setMemoryJvm(String memoryJvm) {
		this.memoryJvm=memoryJvm;
	}
	public String getMemoryJvm() {
		return this.memoryJvm ;
	}
	
//内存占用率MEMORY_RATE_
	private String memoryRate;
	public void setMemoryRate(String memoryRate) {
		this.memoryRate=memoryRate;
	}
	public String getMemoryRate() {
		return this.memoryRate ;
	}
	
//上行速度UP_SPEED_
	private String upSpeed;
	public void setUpSpeed(String upSpeed) {
		this.upSpeed=upSpeed;
	}
	public String getUpSpeed() {
		return this.upSpeed ;
	}
	
//下行速度DOWN_SPEED_
	private String downSpeed;
	public void setDownSpeed(String downSpeed) {
		this.downSpeed=downSpeed;
	}
	public String getDownSpeed() {
		return this.downSpeed ;
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