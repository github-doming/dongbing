package com.ibm.old.v1.cloud.ibm_pi_rank_hot_and_cold.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_pi_rank_hot_and_cold 
 * IBM_冷热排名的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_pi_rank_hot_and_cold")
public class IbmPiRankHotAndColdT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_冷热排名主键IBM_PI_RANK_HOT_AND_COLD_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_PI_RANK_HOT_AND_COLD_ID_")
	private String ibmPiRankHotAndColdId;
	public void setIbmPiRankHotAndColdId(String ibmPiRankHotAndColdId) {
		this.ibmPiRankHotAndColdId=ibmPiRankHotAndColdId;
	}
	public void setIbmPiRankHotAndColdId(Object ibmPiRankHotAndColdId) {
		if (ibmPiRankHotAndColdId != null) {
			this.ibmPiRankHotAndColdId = ibmPiRankHotAndColdId.toString();
		}else{
			this.ibmPiRankHotAndColdId = null;
		}
	}
	public String getIbmPiRankHotAndColdId() {
		return this.ibmPiRankHotAndColdId ;
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
	 * 用户主键USER_ID_
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			this.userId = userId.toString();
		}else{
			this.userId = null;
		}
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
		}else{
			this.fundsList = null;
		}
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
	public void setFundsListId(Object fundsListId) {
		if (fundsListId != null) {
			this.fundsListId = fundsListId.toString();
		}else{
			this.fundsListId = null;
		}
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
	public void setFollowPeriod(Object followPeriod) {
		if (followPeriod != null) {
			if (followPeriod instanceof Integer) {
				this.followPeriod= (Integer) followPeriod;
			}else if (StringTool.isInteger(followPeriod.toString())) {
				this.followPeriod = Integer.parseInt(followPeriod.toString());
			}
		}else{
			this.followPeriod = null;
		}
	}
	public Integer getFollowPeriod() {
		return this.followPeriod ;
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
		}else{
			this.fundSwapMode = null;
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
		}else{
			this.periodRollMode = null;
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
		}else{
			this.planGroupData = null;
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
		}else{
			this.planGroupActiveKey = null;
		}
	}
	public String getPlanGroupActiveKey() {
		return this.planGroupActiveKey ;
	}

	/**
	 * 不管号码重复
	 */
	@Column(name="ALLOW_REPEAT_NUM_")
	private String allowRepeatNum;
	public void setAllowRepeatNum(String allowRepeatNum) {
		this.allowRepeatNum=allowRepeatNum;
	}
	public void setAllowRepeatNum(Object allowRepeatNum) {
		if (allowRepeatNum != null) {
			this.allowRepeatNum = allowRepeatNum.toString();
		}else{
			this.allowRepeatNum = null;
		}
	}
	public String getAllowRepeatNum() {
		return this.allowRepeatNum ;
	}

	/**
	 * 反投
	 */
	@Column(name="COUNTERATTACK_")
	private String counterattack;
	public void setCounterattack(String counterattack) {
		this.counterattack=counterattack;
	}
	public void setCounterattack(Object counterattack) {
		if (counterattack != null) {
			this.counterattack = counterattack.toString();
		}else{
			this.counterattack = null;
		}
	}
	public String getCounterattack() {
		return this.counterattack ;
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
	public void setCreateTime(Object createTime) {
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
	public void setUpdateTime(Object updateTime) {
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