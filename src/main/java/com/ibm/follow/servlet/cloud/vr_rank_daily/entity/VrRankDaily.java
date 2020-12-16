package com.ibm.follow.servlet.cloud.vr_rank_daily.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_rank_daily 
 * VR_每日榜单 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_rank_daily")
public class VrRankDaily implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR_每日榜单主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_RANK_DAILY_ID_")
	private String vrRankDailyId;
	public void setVrRankDailyId(String vrRankDailyId) {
		this.vrRankDailyId=vrRankDailyId;
	}
	public void setVrRankDailyId(Object vrRankDailyId) {
		if (vrRankDailyId != null) {
			this.vrRankDailyId = vrRankDailyId.toString();
		}
	}
	public String getVrRankDailyId() {
		return this.vrRankDailyId ;
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
	 * 虚拟用户名称
	 */
	@Column(name="VR_USER_NAME_")
	private String vrUserName;
	public void setVrUserName(String vrUserName) {
		this.vrUserName=vrUserName;
	}
	public void setVrUserName(Object vrUserName) {
		if (vrUserName != null) {
			this.vrUserName = vrUserName.toString();
		}
	}
	public String getVrUserName() {
		return this.vrUserName ;
	}

	/**
	 * 虚拟会员账号
	 */
	@Column(name="VR_MEMBER_ACCOUNT_")
	private String vrMemberAccount;
	public void setVrMemberAccount(String vrMemberAccount) {
		this.vrMemberAccount=vrMemberAccount;
	}
	public void setVrMemberAccount(Object vrMemberAccount) {
		if (vrMemberAccount != null) {
			this.vrMemberAccount = vrMemberAccount.toString();
		}
	}
	public String getVrMemberAccount() {
		return this.vrMemberAccount ;
	}

	/**
	 * 盘口编码
	 */
	@Column(name="HANDICAP_CODE_")
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public void setHandicapCode(Object handicapCode) {
		if (handicapCode != null) {
			this.handicapCode = handicapCode.toString();
		}
	}
	public String getHandicapCode() {
		return this.handicapCode ;
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
	 * 投注数
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
	 * 胜率
	 */
	@Column(name="WIN_RATE_T_")
	private Long winRateT;
	public void setWinRateT(Long winRateT) {
		this.winRateT=winRateT;
	}
	public void setWinRateT(Object winRateT) {
		if (winRateT != null) {
			if (winRateT instanceof Long) {
				this.winRateT= (Long) winRateT;
			}else if (StringTool.isInteger(winRateT.toString())) {
				this.winRateT = Long.parseLong(winRateT.toString());
			}
		}
	}
	public Long getWinRateT() {
		return this.winRateT ;
	}

	/**
	 * 输赢金额
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
	 * 名次
	 */
	@Column(name="RANKING_")
	private Integer ranking;
	public void setRanking(Integer ranking) {
		this.ranking=ranking;
	}
	public void setRanking(Object ranking) {
		if (ranking != null) {
			if (ranking instanceof Integer) {
				this.ranking= (Integer) ranking;
			}else if (StringTool.isInteger(ranking.toString())) {
				this.ranking = Integer.parseInt(ranking.toString());
			}
		}
	}
	public Integer getRanking() {
		return this.ranking ;
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