package com.ibm.follow.servlet.cloud.ibm_sys_monitor_client.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_monitor_client 
 * IBM_客户端监控信息 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_monitor_client")
public class IbmSysMonitorClient implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_客户端监控信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_MONITOR_CLIENT_ID_")
	private String ibmSysMonitorClientId;
	public void setIbmSysMonitorClientId(String ibmSysMonitorClientId) {
		this.ibmSysMonitorClientId=ibmSysMonitorClientId;
	}
	public void setIbmSysMonitorClientId(Object ibmSysMonitorClientId) {
		if (ibmSysMonitorClientId != null) {
			this.ibmSysMonitorClientId = ibmSysMonitorClientId.toString();
		}
	}
	public String getIbmSysMonitorClientId() {
		return this.ibmSysMonitorClientId ;
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
	 * 客户端编码
	 */
	@Column(name="CLIENT_CODE_")
	private String clientCode;
	public void setClientCode(String clientCode) {
		this.clientCode=clientCode;
	}
	public void setClientCode(Object clientCode) {
		if (clientCode != null) {
			this.clientCode = clientCode.toString();
		}
	}
	public String getClientCode() {
		return this.clientCode ;
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
	 * 更新者
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}
	}
	public String getUpdateUser() {
		return this.updateUser ;
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