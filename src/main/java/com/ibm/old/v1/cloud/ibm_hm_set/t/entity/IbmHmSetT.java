package com.ibm.old.v1.cloud.ibm_hm_set.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_set 
 * IBM_盘口会员设置的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_set")
public class IbmHmSetT implements Serializable {

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
	 * 用户主键
	 */
	@Column(name="APP_USER_ID_")
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public void setAppUserId(Object appUserId) {
		if (appUserId != null) {
			this.appUserId = appUserId.toString();
		}else{
			this.appUserId = null;
		}
	}
	public String getAppUserId() {
		return this.appUserId ;
	}

	/**
	 * 保存账户
	 */
	@Column(name="SAVE_ACCOUNT_")
	private String saveAccount;
	public void setSaveAccount(String saveAccount) {
		this.saveAccount=saveAccount;
	}
	public void setSaveAccount(Object saveAccount) {
		if (saveAccount != null) {
			this.saveAccount = saveAccount.toString();
		}else{
			this.saveAccount = null;
		}
	}
	public String getSaveAccount() {
		return this.saveAccount ;
	}

	/**
	 * 定时登陆
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="LANDED_TIME_")
	private Date landedTime;
	public void setLandedTime(Date landedTime) {
		this.landedTime=landedTime;
	}
	public void setLandedTime(Object landedTime) throws ParseException {
		if (landedTime != null) {
			if (landedTime instanceof Date) {
				this.landedTime= (Date) landedTime;
			}else if (StringTool.isInteger(landedTime.toString())) {
				this.landedTime = new Date(Long.parseLong(landedTime.toString()));
			}else {
				this.landedTime = DateTool.getTime(landedTime.toString());
			}
		}else{
			this.landedTime = null;
		}
	}
	public Date getLandedTime() {
		return this.landedTime ;
	}

	/**
	 * 投注记录保存行数
	 */
	@Column(name="BET_RECORD_ROWS_")
	private Integer betRecordRows;
	public void setBetRecordRows(Integer betRecordRows) {
		this.betRecordRows=betRecordRows;
	}
	public void setBetRecordRows(Object betRecordRows) {
		if (betRecordRows != null) {
			if (betRecordRows instanceof Integer) {
				this.betRecordRows= (Integer) betRecordRows;
			}else if (StringTool.isInteger(betRecordRows.toString())) {
				this.betRecordRows = Integer.parseInt(betRecordRows.toString());
			}
		}else{
			this.betRecordRows = null;
		}
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
	public void setBetRateT(Object betRateT) {
		if (betRateT != null) {
			if (betRateT instanceof Integer) {
				this.betRateT= (Integer) betRateT;
			}else if (StringTool.isInteger(betRateT.toString())) {
				this.betRateT = Integer.parseInt(betRateT.toString());
			}
		}else{
			this.betRateT = null;
		}
	}
	public Integer getBetRateT() {
		return this.betRateT ;
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
	 * 止盈下限
	 */
	@Column(name="PROFIT_LIMIT_MIN_T_")
	private Long profitLimitMinT;
	public void setProfitLimitMinT(Long profitLimitMinT) {
		this.profitLimitMinT=profitLimitMinT;
	}
	public void setProfitLimitMinT(Object profitLimitMinT) {
		if (profitLimitMinT != null) {
			if (profitLimitMinT instanceof Long) {
				this.profitLimitMinT= (Long) profitLimitMinT;
			}else if (StringTool.isInteger(profitLimitMinT.toString())) {
				this.profitLimitMinT = Long.parseLong(profitLimitMinT.toString());
			}
		}else{
			this.profitLimitMinT = null;
		}
	}
	public Long getProfitLimitMinT() {
		return this.profitLimitMinT ;
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
	 * 每天重置-1，自定义重置-2
	 */
	@Column(name="RESET_TYPE_")
	private String resetType;
	public void setResetType(String resetType) {
		this.resetType=resetType;
	}
	public void setResetType(Object resetType) {
		if (resetType != null) {
			this.resetType = resetType.toString();
		}else{
			this.resetType = null;
		}
	}
	public String getResetType() {
		return this.resetType ;
	}

	/**
	 * 重置盈利上限
	 */
	@Column(name="RESET_PROFIT_MAX_T_")
	private Long resetProfitMaxT;
	public void setResetProfitMaxT(Long resetProfitMaxT) {
		this.resetProfitMaxT=resetProfitMaxT;
	}
	public void setResetProfitMaxT(Object resetProfitMaxT) {
		if (resetProfitMaxT != null) {
			if (resetProfitMaxT instanceof Long) {
				this.resetProfitMaxT= (Long) resetProfitMaxT;
			}else if (StringTool.isInteger(resetProfitMaxT.toString())) {
				this.resetProfitMaxT = Long.parseLong(resetProfitMaxT.toString());
			}
		}else{
			this.resetProfitMaxT = null;
		}
	}
	public Long getResetProfitMaxT() {
		return this.resetProfitMaxT ;
	}

	/**
	 * 重置亏损下限
	 */
	@Column(name="RESET_LOSS_MIN_T_")
	private Long resetLossMinT;
	public void setResetLossMinT(Long resetLossMinT) {
		this.resetLossMinT=resetLossMinT;
	}
	public void setResetLossMinT(Object resetLossMinT) {
		if (resetLossMinT != null) {
			if (resetLossMinT instanceof Long) {
				this.resetLossMinT= (Long) resetLossMinT;
			}else if (StringTool.isInteger(resetLossMinT.toString())) {
				this.resetLossMinT = Long.parseLong(resetLossMinT.toString());
			}
		}else{
			this.resetLossMinT = null;
		}
	}
	public Long getResetLossMinT() {
		return this.resetLossMinT ;
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
		}else{
			this.blastStop = null;
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