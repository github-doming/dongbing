package com.ibm.follow.servlet.cloud.ibm_log_monitor_servlet.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_log_monitor_servlet 
 * IBM_服务器监控日志 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_log_monitor_servlet")
public class IbmLogMonitorServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_服务器详情日志主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_LOG_MONITOR_SERVLET_ID_")
	private String ibmLogMonitorServletId;
	public void setIbmLogMonitorServletId(String ibmLogMonitorServletId) {
		this.ibmLogMonitorServletId=ibmLogMonitorServletId;
	}
	public void setIbmLogMonitorServletId(Object ibmLogMonitorServletId) {
		if (ibmLogMonitorServletId != null) {
			this.ibmLogMonitorServletId = ibmLogMonitorServletId.toString();
		}
	}
	public String getIbmLogMonitorServletId() {
		return this.ibmLogMonitorServletId ;
	}

	/**
	 * 服务IP
	 */
	@Column(name="SERVER_IP_")
	private String serverIp;
	public void setServerIp(String serverIp) {
		this.serverIp=serverIp;
	}
	public void setServerIp(Object serverIp) {
		if (serverIp != null) {
			this.serverIp = serverIp.toString();
		}
	}
	public String getServerIp() {
		return this.serverIp ;
	}

	/**
	 * 模块编码
	 */
	@Column(name="MODULE_CODE_")
	private String moduleCode;
	public void setModuleCode(String moduleCode) {
		this.moduleCode=moduleCode;
	}
	public void setModuleCode(Object moduleCode) {
		if (moduleCode != null) {
			this.moduleCode = moduleCode.toString();
		}
	}
	public String getModuleCode() {
		return this.moduleCode ;
	}

	/**
	 * 模块类型
	 */
	@Column(name="MODULE_NAME_")
	private String moduleName;
	public void setModuleName(String moduleName) {
		this.moduleName=moduleName;
	}
	public void setModuleName(Object moduleName) {
		if (moduleName != null) {
			this.moduleName = moduleName.toString();
		}
	}
	public String getModuleName() {
		return this.moduleName ;
	}

	/**
	 * CPU使用率
	 */
	@Column(name="CPU_RATE_")
	private String cpuRate;
	public void setCpuRate(String cpuRate) {
		this.cpuRate=cpuRate;
	}
	public void setCpuRate(Object cpuRate) {
		if (cpuRate != null) {
			this.cpuRate = cpuRate.toString();
		}
	}
	public String getCpuRate() {
		return this.cpuRate ;
	}

	/**
	 * JVM总内存
	 */
	@Column(name="JVM_RATE_")
	private String jvmRate;
	public void setJvmRate(String jvmRate) {
		this.jvmRate=jvmRate;
	}
	public void setJvmRate(Object jvmRate) {
		if (jvmRate != null) {
			this.jvmRate = jvmRate.toString();
		}
	}
	public String getJvmRate() {
		return this.jvmRate ;
	}

	/**
	 * JVM总内存
	 */
	@Column(name="JVM_TOTAL_")
	private String jvmTotal;
	public void setJvmTotal(String jvmTotal) {
		this.jvmTotal=jvmTotal;
	}
	public void setJvmTotal(Object jvmTotal) {
		if (jvmTotal != null) {
			this.jvmTotal = jvmTotal.toString();
		}
	}
	public String getJvmTotal() {
		return this.jvmTotal ;
	}

	/**
	 * JVM总内存
	 */
	@Column(name="JVM_MAX_")
	private String jvmMax;
	public void setJvmMax(String jvmMax) {
		this.jvmMax=jvmMax;
	}
	public void setJvmMax(Object jvmMax) {
		if (jvmMax != null) {
			this.jvmMax = jvmMax.toString();
		}
	}
	public String getJvmMax() {
		return this.jvmMax ;
	}

	/**
	 * JVM剩余内存
	 */
	@Column(name="JVM_FREE_")
	private String jvmFree;
	public void setJvmFree(String jvmFree) {
		this.jvmFree=jvmFree;
	}
	public void setJvmFree(Object jvmFree) {
		if (jvmFree != null) {
			this.jvmFree = jvmFree.toString();
		}
	}
	public String getJvmFree() {
		return this.jvmFree ;
	}

	/**
	 * 内存使用率
	 */
	@Column(name="RAM_RATE_")
	private String ramRate;
	public void setRamRate(String ramRate) {
		this.ramRate=ramRate;
	}
	public void setRamRate(Object ramRate) {
		if (ramRate != null) {
			this.ramRate = ramRate.toString();
		}
	}
	public String getRamRate() {
		return this.ramRate ;
	}

	/**
	 * 总内存
	 */
	@Column(name="RAM_TOTAL_")
	private String ramTotal;
	public void setRamTotal(String ramTotal) {
		this.ramTotal=ramTotal;
	}
	public void setRamTotal(Object ramTotal) {
		if (ramTotal != null) {
			this.ramTotal = ramTotal.toString();
		}
	}
	public String getRamTotal() {
		return this.ramTotal ;
	}

	/**
	 * 剩余内存
	 */
	@Column(name="RAM_FREE_")
	private String ramFree;
	public void setRamFree(String ramFree) {
		this.ramFree=ramFree;
	}
	public void setRamFree(Object ramFree) {
		if (ramFree != null) {
			this.ramFree = ramFree.toString();
		}
	}
	public String getRamFree() {
		return this.ramFree ;
	}

	/**
	 * 磁盘1使用率
	 */
	@Column(name="DISK1_RATE_")
	private String disk1Rate;
	public void setDisk1Rate(String disk1Rate) {
		this.disk1Rate=disk1Rate;
	}
	public void setDisk1Rate(Object disk1Rate) {
		if (disk1Rate != null) {
			this.disk1Rate = disk1Rate.toString();
		}
	}
	public String getDisk1Rate() {
		return this.disk1Rate ;
	}

	/**
	 * 总磁盘1
	 */
	@Column(name="DISK1_TOTAL_")
	private String disk1Total;
	public void setDisk1Total(String disk1Total) {
		this.disk1Total=disk1Total;
	}
	public void setDisk1Total(Object disk1Total) {
		if (disk1Total != null) {
			this.disk1Total = disk1Total.toString();
		}
	}
	public String getDisk1Total() {
		return this.disk1Total ;
	}

	/**
	 * 剩余磁盘1
	 */
	@Column(name="DISK1_FREE_")
	private String disk1Free;
	public void setDisk1Free(String disk1Free) {
		this.disk1Free=disk1Free;
	}
	public void setDisk1Free(Object disk1Free) {
		if (disk1Free != null) {
			this.disk1Free = disk1Free.toString();
		}
	}
	public String getDisk1Free() {
		return this.disk1Free ;
	}

	/**
	 * 磁盘2使用率
	 */
	@Column(name="DISK2_RATE_")
	private String disk2Rate;
	public void setDisk2Rate(String disk2Rate) {
		this.disk2Rate=disk2Rate;
	}
	public void setDisk2Rate(Object disk2Rate) {
		if (disk2Rate != null) {
			this.disk2Rate = disk2Rate.toString();
		}
	}
	public String getDisk2Rate() {
		return this.disk2Rate ;
	}

	/**
	 * 总磁盘2
	 */
	@Column(name="DISK2_TOTAL_")
	private String disk2Total;
	public void setDisk2Total(String disk2Total) {
		this.disk2Total=disk2Total;
	}
	public void setDisk2Total(Object disk2Total) {
		if (disk2Total != null) {
			this.disk2Total = disk2Total.toString();
		}
	}
	public String getDisk2Total() {
		return this.disk2Total ;
	}

	/**
	 * 剩余磁盘2
	 */
	@Column(name="DISK2_FREE_")
	private String disk2Free;
	public void setDisk2Free(String disk2Free) {
		this.disk2Free=disk2Free;
	}
	public void setDisk2Free(Object disk2Free) {
		if (disk2Free != null) {
			this.disk2Free = disk2Free.toString();
		}
	}
	public String getDisk2Free() {
		return this.disk2Free ;
	}

	/**
	 * 磁盘3使用率
	 */
	@Column(name="DISK3_RATE_")
	private String disk3Rate;
	public void setDisk3Rate(String disk3Rate) {
		this.disk3Rate=disk3Rate;
	}
	public void setDisk3Rate(Object disk3Rate) {
		if (disk3Rate != null) {
			this.disk3Rate = disk3Rate.toString();
		}
	}
	public String getDisk3Rate() {
		return this.disk3Rate ;
	}

	/**
	 * 总磁盘3
	 */
	@Column(name="DISK3_TOTAL_")
	private String disk3Total;
	public void setDisk3Total(String disk3Total) {
		this.disk3Total=disk3Total;
	}
	public void setDisk3Total(Object disk3Total) {
		if (disk3Total != null) {
			this.disk3Total = disk3Total.toString();
		}
	}
	public String getDisk3Total() {
		return this.disk3Total ;
	}

	/**
	 * 剩余磁盘3
	 */
	@Column(name="DISK3_FREE_")
	private String disk3Free;
	public void setDisk3Free(String disk3Free) {
		this.disk3Free=disk3Free;
	}
	public void setDisk3Free(Object disk3Free) {
		if (disk3Free != null) {
			this.disk3Free = disk3Free.toString();
		}
	}
	public String getDisk3Free() {
		return this.disk3Free ;
	}

	/**
	 * 磁盘4使用率
	 */
	@Column(name="DISK4_RATE_")
	private String disk4Rate;
	public void setDisk4Rate(String disk4Rate) {
		this.disk4Rate=disk4Rate;
	}
	public void setDisk4Rate(Object disk4Rate) {
		if (disk4Rate != null) {
			this.disk4Rate = disk4Rate.toString();
		}
	}
	public String getDisk4Rate() {
		return this.disk4Rate ;
	}

	/**
	 * 总磁盘4
	 */
	@Column(name="DISK4_TOTAL_")
	private String disk4Total;
	public void setDisk4Total(String disk4Total) {
		this.disk4Total=disk4Total;
	}
	public void setDisk4Total(Object disk4Total) {
		if (disk4Total != null) {
			this.disk4Total = disk4Total.toString();
		}
	}
	public String getDisk4Total() {
		return this.disk4Total ;
	}

	/**
	 * 剩余磁盘4
	 */
	@Column(name="DISK4_FREE_")
	private String disk4Free;
	public void setDisk4Free(String disk4Free) {
		this.disk4Free=disk4Free;
	}
	public void setDisk4Free(Object disk4Free) {
		if (disk4Free != null) {
			this.disk4Free = disk4Free.toString();
		}
	}
	public String getDisk4Free() {
		return this.disk4Free ;
	}

	/**
	 * 创建时间CREATE_TIME_
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME_")
	private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public void setCreateTime(Object createTime) throws ParseException {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间数字型CREATE_TIME_LONG_
	 */
	@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong= (Long) createTimeLong;
			}else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}

	/**
	 * 更新时间UPDATE_TIME_
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME_")
	private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTime(Object updateTime) throws ParseException {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间数字型UPDATE_TIME_LONG_
	 */
	@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong= (Long) updateTimeLong;
			}else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name="STATE_")
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		}
	}
	public String getState() {
		return this.state ;
	}

	/**
	 * 描述
	 */
	@Column(name="DESC_")
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 * @return 表名
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}