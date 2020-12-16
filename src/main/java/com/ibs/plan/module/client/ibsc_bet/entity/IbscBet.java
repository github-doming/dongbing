package com.ibs.plan.module.client.ibsc_bet.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsc_bet 
 * IBSC客户端_投注 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsc_bet")
public class IbscBet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSC客户端_投注主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSC_BET_ID_")
	private String ibscBetId;
	public void setIbscBetId(String ibscBetId) {
		this.ibscBetId=ibscBetId;
	}
	public void setIbscBetId(Object ibscBetId) {
		if (ibscBetId != null) {
			this.ibscBetId = ibscBetId.toString();
		}
	}
	public String getIbscBetId() {
		return this.ibscBetId ;
	}

	/**
	 * 已存在盘口会员主键
	 */
	@Column(name="EXIST_HM_ID_")
	private String existHmId;
	public void setExistHmId(String existHmId) {
		this.existHmId=existHmId;
	}
	public void setExistHmId(Object existHmId) {
		if (existHmId != null) {
			this.existHmId = existHmId.toString();
		}
	}
	public String getExistHmId() {
		return this.existHmId ;
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
	 * 类型
	 */
	@Column(name="GAME_DRAW_TYPE_")
	private String gameDrawType;
	public void setGameDrawType(String gameDrawType) {
		this.gameDrawType=gameDrawType;
	}
	public void setGameDrawType(Object gameDrawType) {
		if (gameDrawType != null) {
			this.gameDrawType = gameDrawType.toString();
		}
	}
	public String getGameDrawType() {
		return this.gameDrawType ;
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
	 * 基准期数
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
		}
	}
	public String getPlanGroupDesc() {
		return this.planGroupDesc ;
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
	 * 投注类型
	 */
	@Column(name="BET_TYPE_")
	private String betType;
	public void setBetType(String betType) {
		this.betType=betType;
	}
	public void setBetType(Object betType) {
		if (betType != null) {
			this.betType = betType.toString();
		}
	}
	public String getBetType() {
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
		}
	}
	public String getDrawNumber() {
		return this.drawNumber ;
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
		}
	}
	public Integer getBetContentLen() {
		return this.betContentLen ;
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
		}
	}
	public String getBetContent() {
		return this.betContent ;
	}

	/**
	 * 投注金额
	 */
	@Column(name="BET_FUND_T_")
	private Long betFundT;
	public void setBetFundT(Long betFundT) {
		this.betFundT=betFundT;
	}
	public void setBetFundT(Object betFundT) {
		if (betFundT != null) {
			if (betFundT instanceof Long) {
				this.betFundT= (Long) betFundT;
			}else if (StringTool.isInteger(betFundT.toString())) {
				this.betFundT = Long.parseLong(betFundT.toString());
			}
		}
	}
	public Long getBetFundT() {
		return this.betFundT ;
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
		}
	}
	public Long getProfitT() {
		return this.profitT ;
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
		}
	}
	public String getExecState() {
		return this.execState ;
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