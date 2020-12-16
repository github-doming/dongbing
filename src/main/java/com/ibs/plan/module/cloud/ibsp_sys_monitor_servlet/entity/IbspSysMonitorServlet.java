package com.ibs.plan.module.cloud.ibsp_sys_monitor_servlet.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_sys_monitor_servlet 
 * IBS_服务器监控信息 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_sys_monitor_servlet")
public class IbspSysMonitorServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBS_服务器监控信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_SYS_MONITOR_SERVLET_ID_")
	private String ibspSysMonitorServletId;
	public void setIbspSysMonitorServletId(String ibspSysMonitorServletId) {
		this.ibspSysMonitorServletId=ibspSysMonitorServletId;
	}
	public void setIbspSysMonitorServletId(Object ibspSysMonitorServletId) {
		if (ibspSysMonitorServletId != null) {
			this.ibspSysMonitorServletId = ibspSysMonitorServletId.toString();
		}
	}
	public String getIbspSysMonitorServletId() {
		return this.ibspSysMonitorServletId ;
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
	 * 模块名称
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
	 * 磁盘使用率
	 */
	@Column(name="DISK_RATE_")
	private String diskRate;
	public void setDiskRate(String diskRate) {
		this.diskRate=diskRate;
	}
	public void setDiskRate(Object diskRate) {
		if (diskRate != null) {
			this.diskRate = diskRate.toString();
		}
	}
	public String getDiskRate() {
		return this.diskRate ;
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