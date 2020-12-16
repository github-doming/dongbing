package com.ibm.old.v1.cloud.ibm_profit_info.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_profit_info 
 * IBM_盘口会员盈亏信息的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_profit_info")
public class IbmProfitInfoT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//IBM_盘口会员方案组投注结果主键IBM_HM_PLAN_PROFIT_INFO_ID_
@Column(name="IBM_PROFIT_INFO_ID_")
	private String ibmProfitInfoId;
	
	public void setIbmProfitInfoId(String ibmProfitInfoId) {
		this.ibmProfitInfoId=ibmProfitInfoId;
	}
	public String getIbmProfitInfoId() {
		return this.ibmProfitInfoId ;
	}
			
			
//IBM_盘口会员方案盈亏主键
@Column(name="PROFIT_PLAN_ID_")
	private String profitPlanId;
	
	public void setProfitPlanId(String profitPlanId) {
		this.profitPlanId=profitPlanId;
	}
	public String getProfitPlanId() {
		return this.profitPlanId ;
	}
			
			
//执行投注项主键
@Column(name="EXEC_BET_ITEM_ID_")
	private String execBetItemId;
	
	public void setExecBetItemId(String execBetItemId) {
		this.execBetItemId=execBetItemId;
	}
	public void setExecBetItemId(Object execBetItemId) {
		if(execBetItemId != null){
			this.execBetItemId = execBetItemId.toString();
		}else{
			this.execBetItemId = null;
		}
	}
	public String getExecBetItemId() {
		return this.execBetItemId ;
	}
			
			
//盘口会员主键
@Column(name="HANDICAP_MEMBER_ID_")
	private String handicapMemberId;
	
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public void setHandicapMemberId(Object handicapMemberId) {
		if(handicapMemberId != null){
			this.handicapMemberId = handicapMemberId.toString();
		}else{
			this.handicapMemberId = null;
		}
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}
			
			
//期数
@Column(name="PERIOD_")
	private String period;
	
	public void setPeriod(String period) {
		this.period=period;
	}
	public void setPeriod(Object period) {
		if(period != null){
			this.period = period.toString();
		}else{
			this.period = null;
		}
	}
	public String getPeriod() {
		return this.period ;
	}
			
			
//盈亏额
@Column(name="PROFIT_T_")
	private Long profitT;
	
	public void setProfitT(Long profitT) {
		this.profitT=profitT;
	}
	public Long getProfitT() {
		return this.profitT ;
	}
			
			
//投注额
@Column(name="BET_FUNDS_T_")
	private Long betFundsT;
	
	public void setBetFundsT(Long betFundsT) {
		this.betFundsT=betFundsT;
	}
	public Long getBetFundsT() {
		return this.betFundsT ;
	}
			
			
//投注数
@Column(name="BET_LEN_")
	private Integer betLen;
	
	public void setBetLen(Integer betLen) {
		this.betLen=betLen;
	}
	public Integer getBetLen() {
		return this.betLen ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}