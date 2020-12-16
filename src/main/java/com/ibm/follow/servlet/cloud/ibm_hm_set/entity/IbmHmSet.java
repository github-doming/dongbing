package com.ibm.follow.servlet.cloud.ibm_hm_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_set 
 * IBM_盘口会员设置的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_set")
public class IbmHmSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口会员设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HM_SET_ID_")
	private String ibmHmSetId;
	public void setIbmHmSetId(String ibmHmSetId) {
		this.ibmHmSetId=ibmHmSetId;
	}
	public void setIbmHmSetId(Object ibmHmSetId) {
		if (ibmHmSetId != null) {
			this.ibmHmSetId = ibmHmSetId.toString();
		}else{
			this.ibmHmSetId = null;
		}
	}
	public String getIbmHmSetId() {
		return this.ibmHmSetId ;
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
	 * 投注记录保存行数
	 */
	@Column(name="BET_RECORD_ROWS_")
	private Integer betRecordRows;
	public void setBetRecordRows(Integer betRecordRows) {
		this.betRecordRows=betRecordRows;
	}
	public Integer getBetRecordRows() {
		return this.betRecordRows ;
	}

	/**
	 * 投注比例
	 */
	@Column(name="BET_RATE_T_")
	private Integer betRateT;
	public void setBetRateT(Integer betRateT) {
		this.betRateT=betRateT;
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
		}else{
			this.betMerger = null;
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