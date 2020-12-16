package com.ibm.old.v1.cloud.ibm_plan_user.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_plan_user 
 * IBM_玩家与方案IBM_PLAN_USER的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_plan_user")
public class IbmPlanUserT implements Serializable {

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
		}else{
			this.idx = null;
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_方案与玩家主键IBM_PLAN_USER_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_PLAN_USER_ID_")
	private String ibmPlanUserId;
	public void setIbmPlanUserId(String ibmPlanUserId) {
		this.ibmPlanUserId=ibmPlanUserId;
	}
	public void setIbmPlanUserId(Object ibmPlanUserId) {
		if (ibmPlanUserId != null) {
			this.ibmPlanUserId = ibmPlanUserId.toString();
		}else{
			this.ibmPlanUserId = null;
		}
	}
	public String getIbmPlanUserId() {
		return this.ibmPlanUserId ;
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
		}else{
			this.planId = null;
		}
	}
	public String getPlanId() {
		return this.planId ;
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
		}else{
			this.planItemTableName = null;
		}
	}
	public String getPlanItemTableName() {
		return this.planItemTableName ;
	}

	/**
	 * 方案详情表主键
	 */
	@Column(name="PLAN_ITEM_TABLE_ID_")
	private String planItemTableId;
	public void setPlanItemTableId(String planItemTableId) {
		this.planItemTableId=planItemTableId;
	}
	public void setPlanItemTableId(Object planItemTableId) {
		if (planItemTableId != null) {
			this.planItemTableId = planItemTableId.toString();
		}else{
			this.planItemTableId = null;
		}
	}
	public String getPlanItemTableId() {
		return this.planItemTableId ;
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
		}else{
			this.appUserId = null;
		}
	}
	public String getAppUserId() {
		return this.appUserId ;
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
		}else{
			this.gameId = null;
		}
	}
	public String getGameId() {
		return this.gameId ;
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
		}else{
			this.planName = null;
		}
	}
	public String getPlanName() {
		return this.planName ;
	}

	/**
	 * 方案图标
	 */
	@Column(name="PLAN_ICON_")
	private String planIcon;
	public void setPlanIcon(String planIcon) {
		this.planIcon=planIcon;
	}
	public void setPlanIcon(Object planIcon) {
		if (planIcon != null) {
			this.planIcon = planIcon.toString();
		}else{
			this.planIcon = null;
		}
	}
	public String getPlanIcon() {
		return this.planIcon ;
	}

	/**
	 * 止盈上限
	 */
	@Column(name="PROFIT_LIMIT_MAX_T_")
	private Long profitLimitMaxT;
	public void setProfitLimitMaxT(Long profitLimitMaxT) {
		this.profitLimitMaxT=profitLimitMaxT;
	}
	public void setProfitLimitMaxT(Object profitLimitMaxT) {
		if (profitLimitMaxT != null) {
			if (profitLimitMaxT instanceof Long) {
				this.profitLimitMaxT= (Long) profitLimitMaxT;
			}else if (StringTool.isInteger(profitLimitMaxT.toString())) {
				this.profitLimitMaxT = Long.parseLong(profitLimitMaxT.toString());
			}
		}else{
			this.profitLimitMaxT = null;
		}
	}
	public Long getProfitLimitMaxT() {
		return this.profitLimitMaxT ;
	}

	/**
	 * 止损下限
	 */
	@Column(name="LOSS_LIMIT_MIN_T_")
	private Long lossLimitMinT;
	public void setLossLimitMinT(Long lossLimitMinT) {
		this.lossLimitMinT=lossLimitMinT;
	}
	public void setLossLimitMinT(Object lossLimitMinT) {
		if (lossLimitMinT != null) {
			if (lossLimitMinT instanceof Long) {
				this.lossLimitMinT= (Long) lossLimitMinT;
			}else if (StringTool.isInteger(lossLimitMinT.toString())) {
				this.lossLimitMinT = Long.parseLong(lossLimitMinT.toString());
			}
		}else{
			this.lossLimitMinT = null;
		}
	}
	public Long getLossLimitMinT() {
		return this.lossLimitMinT ;
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
		}else{
			this.monitorPeriod = null;
		}
	}
	public Integer getMonitorPeriod() {
		return this.monitorPeriod ;
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
		}else{
			this.betMode = null;
		}
	}
	public String getBetMode() {
		return this.betMode ;
	}

	/**
	 * 操作频率
	 */
	@Column(name="OPERATE_FREQUENCY_")
	private Integer operateFrequency;
	public void setOperateFrequency(Integer operateFrequency) {
		this.operateFrequency=operateFrequency;
	}
	public void setOperateFrequency(Object operateFrequency) {
		if (operateFrequency != null) {
			if (operateFrequency instanceof Integer) {
				this.operateFrequency= (Integer) operateFrequency;
			}else if (StringTool.isInteger(operateFrequency.toString())) {
				this.operateFrequency = Integer.parseInt(operateFrequency.toString());
			}
		}else{
			this.operateFrequency = null;
		}
	}
	public Integer getOperateFrequency() {
		return this.operateFrequency ;
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
		}else{
			this.createTime = null;
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
		}else{
			this.createTimeLong = null;
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
		}else{
			this.updateTime = null;
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
		}else{
			this.updateTimeLong = null;
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
		}else{
			this.state = null;
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
		}else{
			this.desc = null;
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