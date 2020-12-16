package com.ibs.plan.module.client.ibsc_plan_group_result.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsc_plan_group_result
 * IBSC客户端_执行结果 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsc_plan_group_result")
public class IbscPlanGroupResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSC客户端_执行结果主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSC_PLAN_GROUP_RESULT_ID_")
	private String ibscPlanGroupResultId;
	public void setIbscPlanGroupResultId(String ibscPlanGroupResultId) {
		this.ibscPlanGroupResultId=ibscPlanGroupResultId;
	}
	public void setIbscPlanGroupResultId(Object ibscPlanGroupResultId) {
		if (ibscPlanGroupResultId != null) {
			this.ibscPlanGroupResultId = ibscPlanGroupResultId.toString();
		}
	}
	public String getIbscPlanGroupResultId() {
		return this.ibscPlanGroupResultId ;
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
	 * 最后期数
	 */
	@Column(name="LAST_PERIOD_")
	private String lastPeriod;
	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod=lastPeriod;
	}
	public void setLastPeriod(Object lastPeriod) {
		if (lastPeriod != null) {
			this.lastPeriod = lastPeriod.toString();
		}
	}
	public String getLastPeriod() {
		return this.lastPeriod ;
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
	 * 投注项
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