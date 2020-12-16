package com.ibm.follow.servlet.cloud.ibm_sys_periods.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_periods 
 * IBM_期数管理 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_periods")
public class IbmSysPeriods implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_期数管理主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_PERIODS_ID_")
	private String ibmSysPeriodsId;
	public void setIbmSysPeriodsId(String ibmSysPeriodsId) {
		this.ibmSysPeriodsId=ibmSysPeriodsId;
	}
	public void setIbmSysPeriodsId(Object ibmSysPeriodsId) {
		if (ibmSysPeriodsId != null) {
			this.ibmSysPeriodsId = ibmSysPeriodsId.toString();
		}
	}
	public String getIbmSysPeriodsId() {
		return this.ibmSysPeriodsId ;
	}

	/**
	 * 游戏主键
	 */
	@Column(name="GAME_ID_")
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public void setGameId(Object gameId) {
		if (gameId != null) {
			this.gameId = gameId.toString();
		}
	}
	public String getGameId() {
		return this.gameId ;
	}

	/**
	 * 游戏编码
	 */
	@Column(name="GAME_CODE_")
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public void setGameCode(Object gameCode) {
		if (gameCode != null) {
			this.gameCode = gameCode.toString();
		}
	}
	public String getGameCode() {
		return this.gameCode ;
	}

	/**
	 * 暂停开始时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="PAUSE_START_TIME_")
	private Date pauseStartTime;
	public void setPauseStartTime(Date pauseStartTime) {
		this.pauseStartTime=pauseStartTime;
	}
	public void setPauseStartTime(Object pauseStartTime) throws ParseException {
		if (pauseStartTime != null) {
			if (pauseStartTime instanceof Date) {
				this.pauseStartTime= (Date) pauseStartTime;
			}else if (StringTool.isInteger(pauseStartTime.toString())) {
				this.pauseStartTime = new Date(Long.parseLong(pauseStartTime.toString()));
			}else {
				this.pauseStartTime = DateTool.getTime(pauseStartTime.toString());
			}
		}
	}
	public Date getPauseStartTime() {
		return this.pauseStartTime ;
	}

	/**
	 * 暂停开始时间数字型
	 */
	@Column(name="PAUSE_START_TIME_LONG_")
	private Long pauseStartTimeLong;
	public void setPauseStartTimeLong(Long pauseStartTimeLong) {
		this.pauseStartTimeLong=pauseStartTimeLong;
	}
	public void setPauseStartTimeLong(Object pauseStartTimeLong) {
		if (pauseStartTimeLong != null) {
			if (pauseStartTimeLong instanceof Long) {
				this.pauseStartTimeLong= (Long) pauseStartTimeLong;
			}else if (StringTool.isInteger(pauseStartTimeLong.toString())) {
				this.pauseStartTimeLong = Long.parseLong(pauseStartTimeLong.toString());
			}
		}
	}
	public Long getPauseStartTimeLong() {
		return this.pauseStartTimeLong ;
	}

	/**
	 * 暂停结束时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="PAUSE_END_TIME_")
	private Date pauseEndTime;
	public void setPauseEndTime(Date pauseEndTime) {
		this.pauseEndTime=pauseEndTime;
	}
	public void setPauseEndTime(Object pauseEndTime) throws ParseException {
		if (pauseEndTime != null) {
			if (pauseEndTime instanceof Date) {
				this.pauseEndTime= (Date) pauseEndTime;
			}else if (StringTool.isInteger(pauseEndTime.toString())) {
				this.pauseEndTime = new Date(Long.parseLong(pauseEndTime.toString()));
			}else {
				this.pauseEndTime = DateTool.getTime(pauseEndTime.toString());
			}
		}
	}
	public Date getPauseEndTime() {
		return this.pauseEndTime ;
	}

	/**
	 * 暂停结束时间数字型
	 */
	@Column(name="PAUSE_END_TIME_LONG_")
	private Long pauseEndTimeLong;
	public void setPauseEndTimeLong(Long pauseEndTimeLong) {
		this.pauseEndTimeLong=pauseEndTimeLong;
	}
	public void setPauseEndTimeLong(Object pauseEndTimeLong) {
		if (pauseEndTimeLong != null) {
			if (pauseEndTimeLong instanceof Long) {
				this.pauseEndTimeLong= (Long) pauseEndTimeLong;
			}else if (StringTool.isInteger(pauseEndTimeLong.toString())) {
				this.pauseEndTimeLong = Long.parseLong(pauseEndTimeLong.toString());
			}
		}
	}
	public Long getPauseEndTimeLong() {
		return this.pauseEndTimeLong ;
	}

	/**
	 * 创建时间
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
	 * 创建时间
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
	 * 更新时间
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
	 * 更新时间
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