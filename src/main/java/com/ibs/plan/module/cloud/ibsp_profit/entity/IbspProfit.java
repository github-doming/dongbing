package com.ibs.plan.module.cloud.ibsp_profit.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_profit 
 * IBSP_盘口会员总盈亏 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_profit")
public class IbspProfit implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_盘口会员总盈亏主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_PROFIT_ID_")
	private String ibspProfitId;
	public void setIbspProfitId(String ibspProfitId) {
		this.ibspProfitId=ibspProfitId;
	}
	public void setIbspProfitId(Object ibspProfitId) {
		if (ibspProfitId != null) {
			this.ibspProfitId = ibspProfitId.toString();
		}
	}
	public String getIbspProfitId() {
		return this.ibspProfitId ;
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
	 * 盘口盈亏
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
		}
	}
	public Long getHandicapProfitT() {
		return this.handicapProfitT ;
	}

	/**
	 * 盈亏总额
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
	 * 投注总额
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
		}
	}
	public Long getBetFundsT() {
		return this.betFundsT ;
	}

	/**
	 * 投注总数
	 */
	@Column(name="BET_LEN_")
	private Integer betLen;
	public void setBetLen(Integer betLen) {
		this.betLen=betLen;
	}
	public void setBetLen(Object betLen) {
		if (betLen != null) {
			if (betLen instanceof Integer) {
				this.betLen= (Integer) betLen;
			}else if (StringTool.isInteger(betLen.toString())) {
				this.betLen = Integer.parseInt(betLen.toString());
			}
		}
	}
	public Integer getBetLen() {
		return this.betLen ;
	}

	/**
	 * 修正值
	 */
	@Column(name="CORRECTION_T_")
	private Integer correctionT;
	public void setCorrectionT(Integer correctionT) {
		this.correctionT=correctionT;
	}
	public void setCorrectionT(Object correctionT) {
		if (correctionT != null) {
			if (correctionT instanceof Integer) {
				this.correctionT= (Integer) correctionT;
			}else if (StringTool.isInteger(correctionT.toString())) {
				this.correctionT = Integer.parseInt(correctionT.toString());
			}
		}
	}
	public Integer getCorrectionT() {
		return this.correctionT ;
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
		}
	}
	public Long getLossLimitMinT() {
		return this.lossLimitMinT ;
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