package com.ibm.old.v1.cloud.ibm_exec_bet_item.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_exec_bet_item 
 * IBM_执行投注项的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_exec_bet_item")
public class IbmExecBetItemT implements Serializable {

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
	 * IBM_执行投注项主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_EXEC_BET_ITEM_ID_")
	private String ibmExecBetItemId;
	public void setIbmExecBetItemId(String ibmExecBetItemId) {
		this.ibmExecBetItemId=ibmExecBetItemId;
	}
	public void setIbmExecBetItemId(Object ibmExecBetItemId) {
		if (ibmExecBetItemId != null) {
			this.ibmExecBetItemId = ibmExecBetItemId.toString();
		}else{
			this.ibmExecBetItemId = null;
		}
	}
	public String getIbmExecBetItemId() {
		return this.ibmExecBetItemId ;
	}

	/**
	 * IBM_执行盘口会员方案组主键
	 */
	@Column(name="EXEC_PLAN_GROUP_ID_")
	private String execPlanGroupId;
	public void setExecPlanGroupId(String execPlanGroupId) {
		this.execPlanGroupId=execPlanGroupId;
	}
	public void setExecPlanGroupId(Object execPlanGroupId) {
		if (execPlanGroupId != null) {
			this.execPlanGroupId = execPlanGroupId.toString();
		}else{
			this.execPlanGroupId = null;
		}
	}
	public String getExecPlanGroupId() {
		return this.execPlanGroupId ;
	}

	/**
	 * 盘口主键
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public void setHandicapId(Object handicapId) {
		if (handicapId != null) {
			this.handicapId = handicapId.toString();
		}else{
			this.handicapId = null;
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
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
	 * 盘口会员主键
	 */
	@Column(name="HANDICAP_MEMBER_ID_")
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public void setHandicapMemberId(Object handicapMemberId) {
		if (handicapMemberId != null) {
			this.handicapMemberId = handicapMemberId.toString();
		}else{
			this.handicapMemberId = null;
		}
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}

	/**
	 * 期数
	 */
	@Column(name="PERIOD_")
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public void setPeriod(Object period) {
		if (period != null) {
			this.period = period.toString();
		}else{
			this.period = null;
		}
	}
	public String getPeriod() {
		return this.period ;
	}

	/**
	 * 金额
	 */
	@Column(name="FUND_T_")
	private Long fundT;
	public void setFundT(Long fundT) {
		this.fundT=fundT;
	}
	public void setFundT(Object fundT) {
		if (fundT != null) {
			if (fundT instanceof Long) {
				this.fundT= (Long) fundT;
			}else if (StringTool.isInteger(fundT.toString())) {
				this.fundT = Long.parseLong(fundT.toString());
			}
		}else{
			this.fundT = null;
		}
	}
	public Long getFundT() {
		return this.fundT ;
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
		}
	}
	public String getPlanItemTableId() {
		return this.planItemTableId ;
	}
	/**
	 * 方案组key
	 */
	@Column(name="PLAN_GROUP_KEY_")
	private String planGroupKey;
	public void setPlanGroupKey(String planGroupKey) {
		this.planGroupKey=planGroupKey;
	}
	public void setPlanGroupKey(Object planGroupKey) {
		if (planGroupKey != null) {
			this.planGroupKey = planGroupKey.toString();
		}
	}
	public String getPlanGroupKey() {
		return this.planGroupKey ;
	}
	/**
	 * 方案组说明
	 */
	@Column(name="PLAN_GROUP_DESC_")
	private String planGroupDesc;
	public void setPlanGroupDesc(String planGroupDesc) {
		this.planGroupDesc=planGroupDesc;
	}
	public void setPlanGroupDesc(Object planGroupDesc) {
		if (planGroupDesc != null) {
			this.planGroupDesc = planGroupDesc.toString();
		}else{
			this.planGroupDesc = null;
		}
	}
	public String getPlanGroupDesc() {
		return this.planGroupDesc ;
	}

	/**
	 * 投注内容
	 */
	@Column(name="BET_CONTENT_")
	private String betContent;
	public void setBetContent(String betContent) {
		this.betContent=betContent;
	}
	public void setBetContent(Object betContent) {
		if (betContent != null) {
			this.betContent = betContent.toString();
		}else{
			this.betContent = null;
		}
	}
	public String getBetContent() {
		return this.betContent ;
	}

	/**
	 * 投注内容编码
	 */
	@Column(name="BET_CONTENT_CODE_")
	private String betContentCode;
	public void setBetContentCode(String betContentCode) {
		this.betContentCode=betContentCode;
	}
	public void setBetContentCode(Object betContentCode) {
		if (betContentCode != null) {
			this.betContentCode = betContentCode.toString();
		}else{
			this.betContentCode = null;
		}
	}
	public String getBetContentCode() {
		return this.betContentCode ;
	}

	/**
	 * 投注内容长度
	 */
	@Column(name="BET_CONTENT_LEN_")
	private Integer betContentLen;
	public void setBetContentLen(Integer betContentLen) {
		this.betContentLen=betContentLen;
	}
	public void setBetContentLen(Object betContentLen) {
		if (betContentLen != null) {
			if (betContentLen instanceof Integer) {
				this.betContentLen= (Integer) betContentLen;
			}else if (StringTool.isInteger(betContentLen.toString())) {
				this.betContentLen = Integer.parseInt(betContentLen.toString());
			}
		}else{
			this.betContentLen = null;
		}
	}
	public Integer getBetContentLen() {
		return this.betContentLen ;
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
	 * 投注类型
	 */
	@Column(name="BET_TYPE_")
	private Integer betType;
	public void setBetType(Integer betType) {
		this.betType=betType;
	}
	public Integer getBetType() {
		return this.betType ;
	}

	/**
	 * 开奖号码
	 */
	@Column(name="DRAW_NUMBER_")
	private String drawNumber;
	public void setDrawNumber(String drawNumber) {
		this.drawNumber=drawNumber;
	}
	public void setDrawNumber(Object drawNumber) {
		if (drawNumber != null) {
			this.drawNumber = drawNumber.toString();
		}else{
			this.drawNumber = null;
		}
	}
	public String getDrawNumber() {
		return this.drawNumber ;
	}

	/**
	 * 资金轮次
	 */
	@Column(name="FUNDS_INDEX_")
	private String fundsIndex;
	public void setFundsIndex(String fundsIndex) {
		this.fundsIndex=fundsIndex;
	}
	public void setFundsIndex(Object fundsIndex) {
		if (fundsIndex != null) {
			this.fundsIndex = fundsIndex.toString();
		}else{
			this.fundsIndex = null;
		}
	}
	public String getFundsIndex() {
		return this.fundsIndex ;
	}

	/**
	 * 执行状态
	 */
	@Column(name="EXEC_STATE_")
	private String execState;
	public void setExecState(String execState) {
		this.execState=execState;
	}
	public void setExecState(Object execState) {
		if (execState != null) {
			this.execState = execState.toString();
		}else{
			this.execState = null;
		}
	}
	public String getExecState() {
		return this.execState ;
	}

	/**
	 * 盈亏
	 */
	@Column(name="PROFIT_T_")
	private Long profitT;
	public void setProfitT(Long profitT) {
		this.profitT=profitT;
	}
	public void setProfitT(Object profitT) {
		if (profitT != null) {
			if (profitT instanceof Long) {
				this.profitT= (Long) profitT;
			}else if (StringTool.isInteger(profitT.toString())) {
				this.profitT = Long.parseLong(profitT.toString());
			}
		}else{
			this.profitT = null;
		}
	}
	public Long getProfitT() {
		return this.profitT ;
	}

	/**
	 * 赔率
	 */
	@Column(name="ODDS_")
	private String odds;
	public void setOdds(String odds) {
		this.odds=odds;
	}
	public void setOdds(Object odds) {
		if (odds != null) {
			this.odds = odds.toString();
		}else{
			this.odds = null;
		}
	}
	public String getOdds() {
		return this.odds ;
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