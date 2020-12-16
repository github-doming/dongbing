package com.ibm.follow.servlet.cloud.ibm_hm_profit.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_profit 
 * IBM_盘口会员总盈亏IBM_HM_PROFIT的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_profit")
public class IbmHmProfit implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口会员方案盈亏信息主键IBM_HM_PLAN_PROFIT_INFO_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HM_PROFIT_ID_")
	private String ibmHmProfitId;
	public void setIbmHmProfitId(String ibmHmProfitId) {
		this.ibmHmProfitId=ibmHmProfitId;
	}
	public void setIbmHmProfitId(Object ibmHmProfitId) {
		if (ibmHmProfitId != null) {
			this.ibmHmProfitId = ibmHmProfitId.toString();
		}else{
			this.ibmHmProfitId = null;
		}
	}
	public String getIbmHmProfitId() {
		return this.ibmHmProfitId ;
	}


	/**
	 * 盘口会员主键HANDICAP_MEMBER_ID_
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
	 * 盘口盈亏HANDICAP_PROFIT_T_
	 */
	@Column(name="HANDICAP_PROFIT_T_")
	private Long handicapProfitT;
	public void setHandicapProfitT(Long handicapProfitT) {
		this.handicapProfitT=handicapProfitT;
	}
	public void setHandicapProfitT(Object handicapProfitT) {
		if (handicapProfitT != null) {
			if (handicapProfitT instanceof Long) {
				this.handicapProfitT= (Long) handicapProfitT;
			}else if (StringTool.isInteger(handicapProfitT.toString())) {
				this.handicapProfitT = Long.parseLong(handicapProfitT.toString());
			}
		}else{
			this.handicapProfitT = null;
		}
	}
	public Long getHandicapProfitT() {
		return this.handicapProfitT ;
	}

	/**
	 * 盈亏总额PROFIT_T_
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
	 * 投注总额BET_FUNDS_T_
	 */
	@Column(name="BET_FUNDS_T_")
	private Long betFundsT;
	public void setBetFundsT(Long betFundsT) {
		this.betFundsT=betFundsT;
	}
	public void setBetFundsT(Object betFundsT) {
		if (betFundsT != null) {
			if (betFundsT instanceof Long) {
				this.betFundsT= (Long) betFundsT;
			}else if (StringTool.isInteger(betFundsT.toString())) {
				this.betFundsT = Long.parseLong(betFundsT.toString());
			}
		}else{
			this.betFundsT = null;
		}
	}
	public Long getBetFundsT() {
		return this.betFundsT ;
	}

	/**
	 * 投注总数BET_LEN_
	 */
	@Column(name="BET_LEN_")
	private Long betLen;
	public void setBetLen(Long betLen) {
		this.betLen=betLen;
	}
	public void setBetLen(Object betLen) {
		if (betLen != null) {
			if (betLen instanceof Long) {
				this.betLen= (Long) betLen;
			}else if (StringTool.isInteger(betLen.toString())) {
				this.betLen = Long.parseLong(betLen.toString());
			}
		}else{
			this.betLen = null;
		}
	}
	public Long getBetLen() {
		return this.betLen ;
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
		}else{
			this.profitLimitMax = null;
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
		}else{
			this.profitLimitMin = null;
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
		}else{
			this.lossLimitMin = null;
		}
	}
	public Integer getLossLimitMin() {
		return this.lossLimitMin ;
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