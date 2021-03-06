package com.ibm.follow.servlet.cloud.vr_member_profit.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_member_profit 
 * VR_会员总盈亏 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_member_profit")
public class VrMemberProfit implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR_会员总盈亏主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_MEMBER_PROFIT_ID_")
	private String vrMemberProfitId;
	public void setVrMemberProfitId(String vrMemberProfitId) {
		this.vrMemberProfitId=vrMemberProfitId;
	}
	public void setVrMemberProfitId(Object vrMemberProfitId) {
		if (vrMemberProfitId != null) {
			this.vrMemberProfitId = vrMemberProfitId.toString();
		}
	}
	public String getVrMemberProfitId() {
		return this.vrMemberProfitId ;
	}

	/**
	 * 虚拟会员主键
	 */
	@Column(name="VR_MEMBER_ID_")
	private String vrMemberId;
	public void setVrMemberId(String vrMemberId) {
		this.vrMemberId=vrMemberId;
	}
	public void setVrMemberId(Object vrMemberId) {
		if (vrMemberId != null) {
			this.vrMemberId = vrMemberId.toString();
		}
	}
	public String getVrMemberId() {
		return this.vrMemberId ;
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
		}
	}
	public Long getBetLen() {
		return this.betLen ;
	}

	/**
	 * 盈亏峰值
	 */
	@Column(name="PROFIT_PEAK_")
	private Long profitPeak;
	public void setProfitPeak(Long profitPeak) {
		this.profitPeak=profitPeak;
	}
	public void setProfitPeak(Object profitPeak) {
		if (profitPeak != null) {
			if (profitPeak instanceof Long) {
				this.profitPeak= (Long) profitPeak;
			}else if (StringTool.isInteger(profitPeak.toString())) {
				this.profitPeak = Long.parseLong(profitPeak.toString());
			}
		}
	}
	public Long getProfitPeak() {
		return this.profitPeak ;
	}

	/**
	 * 盈亏谷值
	 */
	@Column(name="PROFIT_VALLEY_")
	private Long profitValley;
	public void setProfitValley(Long profitValley) {
		this.profitValley=profitValley;
	}
	public void setProfitValley(Object profitValley) {
		if (profitValley != null) {
			if (profitValley instanceof Long) {
				this.profitValley= (Long) profitValley;
			}else if (StringTool.isInteger(profitValley.toString())) {
				this.profitValley = Long.parseLong(profitValley.toString());
			}
		}
	}
	public Long getProfitValley() {
		return this.profitValley ;
	}

	/**
	 * 盈利投注数
	 */
	@Column(name="PROFIT_BET_LEN_")
	private Integer profitBetLen;
	public void setProfitBetLen(Integer profitBetLen) {
		this.profitBetLen=profitBetLen;
	}
	public Integer getProfitBetLen() {
		return this.profitBetLen ;
	}

	/**
	 * 亏损投注数
	 */
	@Column(name="LOSS_BET_LEN_")
	private Integer lossBetLen;
	public void setLossBetLen(Integer lossBetLen) {
		this.lossBetLen=lossBetLen;
	}
	public Integer getLossBetLen() {
		return this.lossBetLen ;
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