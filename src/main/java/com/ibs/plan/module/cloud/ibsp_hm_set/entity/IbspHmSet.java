package com.ibs.plan.module.cloud.ibsp_hm_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_hm_set 
 * IBSP_盘口会员设置 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_hm_set")
public class IbspHmSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_盘口会员设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_HM_SET_ID_")
	private String ibspHmSetId;
	public void setIbspHmSetId(String ibspHmSetId) {
		this.ibspHmSetId=ibspHmSetId;
	}
	public void setIbspHmSetId(Object ibspHmSetId) {
		if (ibspHmSetId != null) {
			this.ibspHmSetId = ibspHmSetId.toString();
		}
	}
	public String getIbspHmSetId() {
		return this.ibspHmSetId ;
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
	 * 投注比例
	 */
	@Column(name="BET_RATE_T_")
	private Integer betRateT;
	public void setBetRateT(Integer betRateT) {
		this.betRateT=betRateT;
	}
	public void setBetRateT(Object betRateT) {
		if (betRateT != null) {
			if (betRateT instanceof Integer) {
				this.betRateT= (Integer) betRateT;
			}else if (StringTool.isInteger(betRateT.toString())) {
				this.betRateT = Integer.parseInt(betRateT.toString());
			}
		}
	}
	public Integer getBetRateT() {
		return this.betRateT ;
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
	 * 止盈下限
	 */
	@Column(name="PROFIT_LIMIT_MIN_")
	private Integer profitLimitMin;
	public void setProfitLimitMin(Integer profitLimitMin) {
		this.profitLimitMin=profitLimitMin;
	}
	public void setProfitLimitMin(Object profitLimitMin) {
		if (profitLimitMin != null) {
			if (profitLimitMin instanceof Integer) {
				this.profitLimitMin= (Integer) profitLimitMin;
			}else if (StringTool.isInteger(profitLimitMin.toString())) {
				this.profitLimitMin = Integer.parseInt(profitLimitMin.toString());
			}
		}
	}
	public Integer getProfitLimitMin() {
		return this.profitLimitMin ;
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
	 * 重置类型
	 */
	@Column(name="RESET_TYPE_")
	private String resetType;
	public void setResetType(String resetType) {
		this.resetType=resetType;
	}
	public void setResetType(Object resetType) {
		if (resetType != null) {
			this.resetType = resetType.toString();
		}
	}
	public String getResetType() {
		return this.resetType ;
	}

	/**
	 * 重置盈利上限
	 */
	@Column(name="RESET_PROFIT_MAX_")
	private Integer resetProfitMax;
	public void setResetProfitMax(Integer resetProfitMax) {
		this.resetProfitMax=resetProfitMax;
	}
	public void setResetProfitMax(Object resetProfitMax) {
		if (resetProfitMax != null) {
			if (resetProfitMax instanceof Integer) {
				this.resetProfitMax= (Integer) resetProfitMax;
			}else if (StringTool.isInteger(resetProfitMax.toString())) {
				this.resetProfitMax = Integer.parseInt(resetProfitMax.toString());
			}
		}
	}
	public Integer getResetProfitMax() {
		return this.resetProfitMax ;
	}

	/**
	 * 重置亏损下限
	 */
	@Column(name="RESET_LOSS_MIN_")
	private Integer resetLossMin;
	public void setResetLossMin(Integer resetLossMin) {
		this.resetLossMin=resetLossMin;
	}
	public void setResetLossMin(Object resetLossMin) {
		if (resetLossMin != null) {
			if (resetLossMin instanceof Integer) {
				this.resetLossMin= (Integer) resetLossMin;
			}else if (StringTool.isInteger(resetLossMin.toString())) {
				this.resetLossMin = Integer.parseInt(resetLossMin.toString());
			}
		}
	}
	public Integer getResetLossMin() {
		return this.resetLossMin ;
	}

	/**
	 * 模式切换状态
	 */
	@Column(name="MODE_CUTOVER_STATE_")
	private String modeCutoverState;
	public void setModeCutoverState(String modeCutoverState) {
		this.modeCutoverState=modeCutoverState;
	}
	public void setModeCutoverState(Object modeCutoverState) {
		if (modeCutoverState != null) {
			this.modeCutoverState = modeCutoverState.toString();
		}
	}
	public String getModeCutoverState() {
		return this.modeCutoverState ;
	}

	/**
	 * 炸停止
	 */
	@Column(name="BLAST_STOP_")
	private String blastStop;
	public void setBlastStop(String blastStop) {
		this.blastStop=blastStop;
	}
	public void setBlastStop(Object blastStop) {
		if (blastStop != null) {
			this.blastStop = blastStop.toString();
		}
	}
	public String getBlastStop() {
		return this.blastStop ;
	}

	/**
	 * 投注合并
	 */
	@Column(name="BET_MERGER_")
	private String betMerger;
	public void setBetMerger(String betMerger) {
		this.betMerger=betMerger;
	}
	public void setBetMerger(Object betMerger) {
		if (betMerger != null) {
			this.betMerger = betMerger.toString();
		}
	}
	public String getBetMerger() {
		return this.betMerger ;
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