package com.ibm.old.v1.cloud.ibm_sys_handicap.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_handicap 
 * IBM_系统盘口IBM_SYS_HANDICAP的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_handicap")
public class IbmSysHandicapT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public void setIdx(Object idx) {
		if (idx != null) {
			if (idx instanceof Long) {
				this.idx= (Long) idx;
			}else if (StringTool.isInteger(idx.toString())) {
				this.idx = Long.parseLong(idx.toString());
			}
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_中心端盘口会员日志主键IBM_CLOUD_LOG_HM_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_HANDICAP_ID_")
	private String ibmSysHandicapId;
	public void setIbmSysHandicapId(String ibmSysHandicapId) {
		this.ibmSysHandicapId=ibmSysHandicapId;
	}
	public void setIbmSysHandicapId(Object ibmSysHandicapId) {
		if (ibmSysHandicapId != null) {
			this.ibmSysHandicapId = ibmSysHandicapId.toString();
		}
	}
	public String getIbmSysHandicapId() {
		return this.ibmSysHandicapId ;
	}

	/**
	 * 盘口主键HANDICAP_ID_
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public void setHandicapId(Object handicapId) {
		if (handicapId != null) {
			this.handicapId = handicapId.toString();
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
	/**
	 * 盘口编码HANDICAP_CODE_
	 */
	@Column(name="HANDICAP_CODE_")
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public void setHandicapCode(Object handicapCode) {
		if (handicapCode != null) {
			this.handicapCode = handicapCode.toString();
		}
	}
	public String getHandicapCode() {
		return this.handicapCode ;
	}
	/**
	 * 盘口检测时间HANDICAP_DETECTION_TIME_
	 */
	@Column(name="HANDICAP_DETECTION_TIME_")
	private Integer handicapDetectionTime;
	public void setHandicapDetectionTime(Integer handicapDetectionTime) {
		this.handicapDetectionTime=handicapDetectionTime;
	}
	public void setHandicapDetectionTime(Object handicapDetectionTime) {
		if (handicapDetectionTime != null) {
			if (handicapDetectionTime instanceof Integer) {
				this.handicapDetectionTime= (Integer) handicapDetectionTime;
			}else if (StringTool.isInteger(handicapDetectionTime.toString())) {
				this.handicapDetectionTime = Integer.parseInt(handicapDetectionTime.toString());
			}
		}
	}
	public Integer getHandicapDetectionTime() {
		return this.handicapDetectionTime ;
	}

	/**
	 * 上次校验时间LAST_CHECK_TIME_
	 */
	@Column(name="LAST_CHECK_TIME_")
	private Long lastCheckTime;
	public void setLastCheckTime(Long lastCheckTime) {
		this.lastCheckTime=lastCheckTime;
	}
	public void setLastCheckTime(Object lastCheckTime) {
		if (lastCheckTime != null) {
			if (lastCheckTime instanceof Long) {
				this.lastCheckTime= (Long) lastCheckTime;
			}else if (StringTool.isInteger(lastCheckTime.toString())) {
				this.lastCheckTime = Long.parseLong(lastCheckTime.toString());
			}
		}
	}
	public Long getLastCheckTime() {
		return this.lastCheckTime ;
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