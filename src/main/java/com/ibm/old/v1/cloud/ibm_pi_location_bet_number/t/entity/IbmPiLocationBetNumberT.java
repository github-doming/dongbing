package com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_pi_location_bet_number 
 * 位置投注_号码IBM_PI_LOCATION_BET_NUMBER的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_pi_location_bet_number")
public class IbmPiLocationBetNumberT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * 位置投注_号码主键IBM_PI_LOCATION_BET_NUMBER_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_PI_LOCATION_BET_NUMBER_ID_")
	private String ibmPiLocationBetNumberId;
	public void setIbmPiLocationBetNumberId(String ibmPiLocationBetNumberId) {
		this.ibmPiLocationBetNumberId=ibmPiLocationBetNumberId;
	}
	public String getIbmPiLocationBetNumberId() {
		return this.ibmPiLocationBetNumberId ;
	}

	/**
	 * 方案主键
	 */
	@Column(name="PLAN_ID_")
	private String planId;
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public String getPlanId() {
		return this.planId ;
	}

	/**
	 * 用户主键USER_ID_
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public String getUserId() {
		return this.userId ;
	}

	/**
	 * 止盈上限
	 */
	@Column(name="PROFIT_LIMIT_MAX_T_")
	private Long profitLimitMaxT;
	public void setProfitLimitMaxT(Long profitLimitMaxT) {
		this.profitLimitMaxT=profitLimitMaxT;
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
	public Long getLossLimitMinT() {
		return this.lossLimitMinT ;
	}

	/**
	 * 资金列表
	 */
	@Column(name="FUNDS_LIST_")
	private String fundsList;
	public void setFundsList(String fundsList) {
		this.fundsList=fundsList;
	}
	public String getFundsList() {
		return this.fundsList ;
	}

	/**
	 * 资金方案ID
	 */
	@Column(name="FUNDS_LIST_ID_")
	private String fundsListId;
	public void setFundsListId(String fundsListId) {
		this.fundsListId=fundsListId;
	}
	public String getFundsListId() {
		return this.fundsListId ;
	}

	/**
	 * 跟进期数
	 */
	@Column(name="FOLLOW_PERIOD_")
	private Integer followPeriod;
	public void setFollowPeriod(Integer followPeriod) {
		this.followPeriod=followPeriod;
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
	public String getUnIncrease() {
		return this.unIncrease ;
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