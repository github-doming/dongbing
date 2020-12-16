package com.ibm.old.v1.cloud.ibm_exec_plan_group.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_exec_plan_group 
 * 




的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_exec_plan_group")
public class IbmExecPlanGroupT implements Serializable {

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
	 * IBM_执行盘口会员方案组主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_EXEC_PLAN_GROUP_ID_")
	private String ibmExecPlanGroupId;
	public void setIbmExecPlanGroupId(String ibmExecPlanGroupId) {
		this.ibmExecPlanGroupId=ibmExecPlanGroupId;
	}
	public void setIbmExecPlanGroupId(Object ibmExecPlanGroupId) {
		if (ibmExecPlanGroupId != null) {
			this.ibmExecPlanGroupId = ibmExecPlanGroupId.toString();
		}
	}
	public String getIbmExecPlanGroupId() {
		return this.ibmExecPlanGroupId ;
	}

	/**
	 * 父执行盘口会员方案组主键
	 */
	@Column(name="REP_EXEC_PLAN_GROUP_ID_")
	private String repExecPlanGroupId;
	public void setRepExecPlanGroupId(String repExecPlanGroupId) {
		this.repExecPlanGroupId=repExecPlanGroupId;
	}
	public void setRepExecPlanGroupId(Object repExecPlanGroupId) {
		if (repExecPlanGroupId != null) {
			this.repExecPlanGroupId = repExecPlanGroupId.toString();
		}
	}
	public String getRepExecPlanGroupId() {
		return this.repExecPlanGroupId ;
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
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
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
		}
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
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
		}
	}
	public String getPeriod() {
		return this.period ;
	}

	/**
	 * 跟随期数
	 */
	@Column(name="BASE_PERIOD_")
	private String basePeriod;
	public void setBasePeriod(String basePeriod) {
		this.basePeriod=basePeriod;
	}
	public void setBasePeriod(Object basePeriod) {
		if (basePeriod != null) {
			this.basePeriod = basePeriod.toString();
		}
	}
	public String getBasePeriod() {
		return this.basePeriod ;
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
	 * 资金组key
	 */
	@Column(name="FUND_GROUP_KEY_")
	private String fundGroupKey;
	public void setFundGroupKey(String fundGroupKey) {
		this.fundGroupKey=fundGroupKey;
	}
	public void setFundGroupKey(Object fundGroupKey) {
		if (fundGroupKey != null) {
			this.fundGroupKey = fundGroupKey.toString();
		}
	}
	public String getFundGroupKey() {
		return this.fundGroupKey ;
	}

	/**
	 * 方案组详情
	 */
	@Column(name="PLAN_GROUP_VALUE_")
	private String planGroupValue;
	public void setPlanGroupValue(String planGroupValue) {
		this.planGroupValue=planGroupValue;
	}
	public void setPlanGroupValue(Object planGroupValue) {
		if (planGroupValue != null) {
			this.planGroupValue = planGroupValue.toString();
		}
	}
	public String getPlanGroupValue() {
		return this.planGroupValue ;
	}

	/**
	 * 资金组详情
	 */
	@Column(name="FUND_GROUP_VALUE_")
	private String fundGroupValue;
	public void setFundGroupValue(String fundGroupValue) {
		this.fundGroupValue=fundGroupValue;
	}
	public void setFundGroupValue(Object fundGroupValue) {
		if (fundGroupValue != null) {
			this.fundGroupValue = fundGroupValue.toString();
		}
	}
	public String getFundGroupValue() {
		return this.fundGroupValue ;
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
		}
	}
	public String getFundsListId() {
		return this.fundsListId ;
	}

	/**
	 * 执行结果
	 */
	@Column(name="EXEC_RESULT_")
	private String execResult;
	public void setExecResult(String execResult) {
		this.execResult=execResult;
	}
	public void setExecResult(Object execResult) {
		if (execResult != null) {
			this.execResult = execResult.toString();
		}
	}
	public String getExecResult() {
		return this.execResult ;
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