package com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_pi_member_plan_item 
 * VR会员方案详情 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_pi_member_plan_item")
public class VrPiMemberPlanItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR位置投注_号码主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_PI_MEMBER_PLAN_ITEM_ID_")
	private String vrPiMemberPlanItemId;
	public void setVrPiMemberPlanItemId(String vrPiMemberPlanItemId) {
		this.vrPiMemberPlanItemId=vrPiMemberPlanItemId;
	}
	public void setVrPiMemberPlanItemId(Object vrPiMemberPlanItemId) {
		if (vrPiMemberPlanItemId != null) {
			this.vrPiMemberPlanItemId = vrPiMemberPlanItemId.toString();
		}
	}
	public String getVrPiMemberPlanItemId() {
		return this.vrPiMemberPlanItemId ;
	}

	/**
	 * 虚拟会员主键
	 */
	@Column(name="VR_MEMBER_ID_")
	private String vrMemberId;
	public void setVrMemberId(String vrMemberId) {
		this.vrMemberId=vrMemberId;
	}
	public void setVrMemberId(Object vrMemberId) {
		if (vrMemberId != null) {
			this.vrMemberId = vrMemberId.toString();
		}
	}
	public String getVrMemberId() {
		return this.vrMemberId ;
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
	 * 止盈上限
	 */
	@Column(name="PROFIT_LIMIT_MAX_")
	private Integer profitLimitMax;
	public void setProfitLimitMax(Integer profitLimitMax) {
		this.profitLimitMax=profitLimitMax;
	}
	public void setProfitLimitMax(Object profitLimitMax) {
		if (profitLimitMax != null) {
			if (profitLimitMax instanceof Integer) {
				this.profitLimitMax= (Integer) profitLimitMax;
			}else if (StringTool.isInteger(profitLimitMax.toString())) {
				this.profitLimitMax = Integer.parseInt(profitLimitMax.toString());
			}
		}
	}
	public Integer getProfitLimitMax() {
		return this.profitLimitMax ;
	}

	/**
	 * 止损下限
	 */
	@Column(name="LOSS_LIMIT_MIN_")
	private Integer lossLimitMin;
	public void setLossLimitMin(Integer lossLimitMin) {
		this.lossLimitMin=lossLimitMin;
	}
	public void setLossLimitMin(Object lossLimitMin) {
		if (lossLimitMin != null) {
			if (lossLimitMin instanceof Integer) {
				this.lossLimitMin= (Integer) lossLimitMin;
			}else if (StringTool.isInteger(lossLimitMin.toString())) {
				this.lossLimitMin = Integer.parseInt(lossLimitMin.toString());
			}
		}
	}
	public Integer getLossLimitMin() {
		return this.lossLimitMin ;
	}

	/**
	 * 资金列表
	 */
	@Column(name="FUNDS_LIST_")
	private String fundsList;
	public void setFundsList(String fundsList) {
		this.fundsList=fundsList;
	}
	public void setFundsList(Object fundsList) {
		if (fundsList != null) {
			this.fundsList = fundsList.toString();
		}
	}
	public String getFundsList() {
		return this.fundsList ;
	}

	/**
	 * 跟进期数
	 */
	@Column(name="FOLLOW_PERIOD_")
	private Integer followPeriod;
	public void setFollowPeriod(Integer followPeriod) {
		this.followPeriod=followPeriod;
	}
	public void setFollowPeriod(Object followPeriod) {
		if (followPeriod != null) {
			if (followPeriod instanceof Integer) {
				this.followPeriod= (Integer) followPeriod;
			}else if (StringTool.isInteger(followPeriod.toString())) {
				this.followPeriod = Integer.parseInt(followPeriod.toString());
			}
		}
	}
	public Integer getFollowPeriod() {
		return this.followPeriod ;
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
	 * 资金切换方式
	 */
	@Column(name="FUND_SWAP_MODE_")
	private String fundSwapMode;
	public void setFundSwapMode(String fundSwapMode) {
		this.fundSwapMode=fundSwapMode;
	}
	public void setFundSwapMode(Object fundSwapMode) {
		if (fundSwapMode != null) {
			this.fundSwapMode = fundSwapMode.toString();
		}
	}
	public String getFundSwapMode() {
		return this.fundSwapMode ;
	}

	/**
	 * 期期滚选项
	 */
	@Column(name="PERIOD_ROLL_MODE_")
	private String periodRollMode;
	public void setPeriodRollMode(String periodRollMode) {
		this.periodRollMode=periodRollMode;
	}
	public void setPeriodRollMode(Object periodRollMode) {
		if (periodRollMode != null) {
			this.periodRollMode = periodRollMode.toString();
		}
	}
	public String getPeriodRollMode() {
		return this.periodRollMode ;
	}

	/**
	 * 方案组数据
	 */
	@Column(name="PLAN_GROUP_DATA_")
	private String planGroupData;
	public void setPlanGroupData(String planGroupData) {
		this.planGroupData=planGroupData;
	}
	public void setPlanGroupData(Object planGroupData) {
		if (planGroupData != null) {
			this.planGroupData = planGroupData.toString();
		}
	}
	public String getPlanGroupData() {
		return this.planGroupData ;
	}

	/**
	 * 开启方案组key
	 */
	@Column(name="PLAN_GROUP_ACTIVE_KEY_")
	private String planGroupActiveKey;
	public void setPlanGroupActiveKey(String planGroupActiveKey) {
		this.planGroupActiveKey=planGroupActiveKey;
	}
	public void setPlanGroupActiveKey(Object planGroupActiveKey) {
		if (planGroupActiveKey != null) {
			this.planGroupActiveKey = planGroupActiveKey.toString();
		}
	}
	public String getPlanGroupActiveKey() {
		return this.planGroupActiveKey ;
	}

	/**
	 * 不中禁止新增
	 */
	@Column(name="UN_INCREASE_")
	private String unIncrease;
	public void setUnIncrease(String unIncrease) {
		this.unIncrease=unIncrease;
	}
	public void setUnIncrease(Object unIncrease) {
		if (unIncrease != null) {
			this.unIncrease = unIncrease.toString();
		}
	}
	public String getUnIncrease() {
		return this.unIncrease ;
	}

	/**
	 * 扩展信息
	 */
	@Column(name="EXPAND_INFO_")
	private String expandInfo;
	public void setExpandInfo(String expandInfo) {
		this.expandInfo=expandInfo;
	}
	public void setExpandInfo(Object expandInfo) {
		if (expandInfo != null) {
			this.expandInfo = expandInfo.toString();
		}
	}
	public String getExpandInfo() {
		return this.expandInfo ;
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