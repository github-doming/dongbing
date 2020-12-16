package com.ibs.plan.module.cloud.ibsp_plan_user.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_plan_user 
 * IBSP_用户与方案 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_plan_user")
public class IbspPlanUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_用户与方案主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_PLAN_USER_ID_")
	private String ibspPlanUserId;
	public void setIbspPlanUserId(String ibspPlanUserId) {
		this.ibspPlanUserId=ibspPlanUserId;
	}
	public void setIbspPlanUserId(Object ibspPlanUserId) {
		if (ibspPlanUserId != null) {
			this.ibspPlanUserId = ibspPlanUserId.toString();
		}
	}
	public String getIbspPlanUserId() {
		return this.ibspPlanUserId ;
	}

	/**
	 * 用户主键
	 */
	@Column(name="APP_USER_ID_")
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public void setAppUserId(Object appUserId) {
		if (appUserId != null) {
			this.appUserId = appUserId.toString();
		}
	}
	public String getAppUserId() {
		return this.appUserId ;
	}

	/**
	 * 方案主键
	 */
	@Column(name="PLAN_ID_")
	private String planId;
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public void setPlanId(Object planId) {
		if (planId != null) {
			this.planId = planId.toString();
		}
	}
	public String getPlanId() {
		return this.planId ;
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
	 * 方案详情表名
	 */
	@Column(name="PLAN_ITEM_TABLE_NAME_")
	private String planItemTableName;
	public void setPlanItemTableName(String planItemTableName) {
		this.planItemTableName=planItemTableName;
	}
	public void setPlanItemTableName(Object planItemTableName) {
		if (planItemTableName != null) {
			this.planItemTableName = planItemTableName.toString();
		}
	}
	public String getPlanItemTableName() {
		return this.planItemTableName ;
	}

	/**
	 * 方案详情主键
	 */
	@Column(name="PLAN_ITEM_TABLE_ID_")
	private String planItemTableId;
	public void setPlanItemTableId(String planItemTableId) {
		this.planItemTableId=planItemTableId;
	}
	public void setPlanItemTableId(Object planItemTableId) {
		if (planItemTableId != null) {
			this.planItemTableId = planItemTableId.toString();
		}
	}
	public String getPlanItemTableId() {
		return this.planItemTableId ;
	}

	/**
	 * 方案编码
	 */
	@Column(name="PLAN_CODE_")
	private String planCode;
	public void setPlanCode(String planCode) {
		this.planCode=planCode;
	}
	public void setPlanCode(Object planCode) {
		if (planCode != null) {
			this.planCode = planCode.toString();
		}
	}
	public String getPlanCode() {
		return this.planCode ;
	}

	/**
	 * 方案名称
	 */
	@Column(name="PLAN_NAME_")
	private String planName;
	public void setPlanName(String planName) {
		this.planName=planName;
	}
	public void setPlanName(Object planName) {
		if (planName != null) {
			this.planName = planName.toString();
		}
	}
	public String getPlanName() {
		return this.planName ;
	}

	/**
	 * 投注模式
	 */
	@Column(name="BET_MODE_")
	private String betMode;
	public void setBetMode(String betMode) {
		this.betMode=betMode;
	}
	public void setBetMode(Object betMode) {
		if (betMode != null) {
			this.betMode = betMode.toString();
		}
	}
	public String getBetMode() {
		return this.betMode ;
	}

	/**
	 * 监控次数
	 */
	@Column(name="MONITOR_PERIOD_")
	private Integer monitorPeriod;
	public void setMonitorPeriod(Integer monitorPeriod) {
		this.monitorPeriod=monitorPeriod;
	}
	public void setMonitorPeriod(Object monitorPeriod) {
		if (monitorPeriod != null) {
			if (monitorPeriod instanceof Integer) {
				this.monitorPeriod= (Integer) monitorPeriod;
			}else if (StringTool.isInteger(monitorPeriod.toString())) {
				this.monitorPeriod = Integer.parseInt(monitorPeriod.toString());
			}
		}
	}
	public Integer getMonitorPeriod() {
		return this.monitorPeriod ;
	}

	/**
	 * 方案状态
	 */
	@Column(name="PLAN_STATE_")
	private String planState;
	public void setPlanState(String planState) {
		this.planState=planState;
	}
	public void setPlanState(Object planState) {
		if (planState != null) {
			this.planState = planState.toString();
		}
	}
	public String getPlanState() {
		return this.planState ;
	}

	/**
	 * 次序
	 */
	@Column(name="SN_")
	private Integer sn;
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public void setSn(Object sn) {
		if (sn != null) {
			if (sn instanceof Integer) {
				this.sn= (Integer) sn;
			}else if (StringTool.isInteger(sn.toString())) {
				this.sn = Integer.parseInt(sn.toString());
			}
		}
	}
	public Integer getSn() {
		return this.sn ;
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