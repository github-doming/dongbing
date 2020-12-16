package com.ibm.follow.servlet.cloud.ibm_hm_bet_item.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_bet_item 
 * IBM_盘口会员投注的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_bet_item")
public class IbmHmBetItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口会员投注主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HM_BET_ITEM_ID_")
	private String ibmHmBetItemId;
	public void setIbmHmBetItemId(String ibmHmBetItemId) {
		this.ibmHmBetItemId=ibmHmBetItemId;
	}
	public void setIbmHmBetItemId(Object ibmHmBetItemId) {
		if (ibmHmBetItemId != null) {
			this.ibmHmBetItemId = ibmHmBetItemId.toString();
		}else{
			this.ibmHmBetItemId = null;
		}
	}
	public String getIbmHmBetItemId() {
		return this.ibmHmBetItemId ;
	}

	/**
	 * 客户端代理跟随投注主键
	 */
	@Column(name="CLIENT_HA_FOLLOW_BET_ID_")
	private String clientHaFollowBetId;
	public void setClientHaFollowBetId(String clientHaFollowBetId) {
		this.clientHaFollowBetId=clientHaFollowBetId;
	}
	public void setClientHaFollowBetId(Object clientHaFollowBetId) {
		if (clientHaFollowBetId != null) {
			this.clientHaFollowBetId = clientHaFollowBetId.toString();
		}else{
			this.clientHaFollowBetId = null;
		}
	}
	public String getClientHaFollowBetId() {
		return this.clientHaFollowBetId ;
	}

	/**
	 * 盘口主键
	 */
	@Column(name = "HANDICAP_ID_") private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId = handicapId;
	}
	public void setHandicapId(Object handicapId) {
		if (handicapId != null) {
			this.handicapId = handicapId.toString();
		}
	}
	public String getHandicapId() {
		return this.handicapId;
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
	 * 游戏主键
	 */
	@Column(name="GAME_ID_")
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public void setGameId(Object gameId) {
		if (gameId != null) {
			this.gameId = gameId.toString();
		}else{
			this.gameId = null;
		}
	}
	public String getGameId() {
		return this.gameId ;
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
		}else{
			this.period = null;
		}
	}
	public String getPeriod() {
		return this.period ;
	}

	/**
	 * 投注模式
	 */
	@Column(name="BET_MODE_")
	private String betMode;
	public void setBetMode(String betMode) {
		this.betMode=betMode;
	}
	public void setBetMode(Object betMode) {
		if (betMode != null) {
			this.betMode = betMode.toString();
		}else{
			this.betMode = null;
		}
	}
	public String getBetMode() {
		return this.betMode ;
	}

	/**
	 * 投注类型
	 */
	@Column(name="BET_TYPE_")
	private Integer betType;
	public void setBetType(Integer betType) {
		this.betType=betType;
	}
	public Integer getBetType() {
		return this.betType ;
	}

	/**
	 * 跟随会员账号
	 */
	@Column(name="FOLLOW_MEMBER_ACCOUNT_")
	private String followMemberAccount;
	public void setFollowMemberAccount(String followMemberAccount) {
		this.followMemberAccount=followMemberAccount;
	}
	public void setFollowMemberAccount(Object followMemberAccount) {
		if (followMemberAccount != null) {
			this.followMemberAccount = followMemberAccount.toString();
		}else{
			this.followMemberAccount = null;
		}
	}
	public String getFollowMemberAccount() {
		return this.followMemberAccount ;
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
		}else{
			this.drawNumber = null;
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
		}else{
			this.betContent = null;
		}
	}
	public String getBetContent() {
		return this.betContent ;
	}

	/**
	 * 金额
	 */
	@Column(name="FUND_T_")
	private Long fundT;
	public void setFundT(Long fundT) {
		this.fundT=fundT;
	}
	public void setFundT(Object fundT) {
		if (fundT != null) {
			if (fundT instanceof Long) {
				this.fundT= (Long) fundT;
			}else if (StringTool.isInteger(fundT.toString())) {
				this.fundT = Long.parseLong(fundT.toString());
			}
		}else{
			this.fundT = null;
		}
	}
	public Long getFundT() {
		return this.fundT ;
	}

	/**
	 * 投注内容码
	 */
	@Column(name="BET_INFO_CODE_")
	private String betInfoCode;
	public void setBetInfoCode(String betInfoCode) {
		this.betInfoCode=betInfoCode;
	}
	public void setBetInfoCode(Object betInfoCode) {
		if (betInfoCode != null) {
			this.betInfoCode = betInfoCode.toString();
		}else{
			this.betInfoCode = null;
		}
	}
	public String getBetInfoCode() {
		return this.betInfoCode ;
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
		}else{
			this.profitT = null;
		}
	}
	public Long getProfitT() {
		return this.profitT ;
	}

	/**
	 * 赔率
	 */
	@Column(name="ODDS_")
	private String odds;
	public void setOdds(String odds) {
		this.odds=odds;
	}
	public void setOdds(Object odds) {
		if (odds != null) {
			this.odds = odds.toString();
		}else{
			this.odds = null;
		}
	}
	public String getOdds() {
		return this.odds ;
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
		}else{
			this.execState = null;
		}
	}
	public String getExecState() {
		return this.execState ;
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