package com.ibm.follow.servlet.client.ibmc_ha_follow_bet.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibmc_ha_follow_bet 
 * IBMC客户端_代理跟随投注的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibmc_ha_follow_bet")
public class IbmcHaFollowBet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC客户端_代理跟随投注主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBMC_HA_FOLLOW_BET_ID_")
	private String ibmcHaFollowBetId;
	public void setIbmcHaFollowBetId(String ibmcHaFollowBetId) {
		this.ibmcHaFollowBetId=ibmcHaFollowBetId;
	}
	public void setIbmcHaFollowBetId(Object ibmcHaFollowBetId) {
		if (ibmcHaFollowBetId != null) {
			this.ibmcHaFollowBetId = ibmcHaFollowBetId.toString();
		}else{
			this.ibmcHaFollowBetId = null;
		}
	}
	public String getIbmcHaFollowBetId() {
		return this.ibmcHaFollowBetId ;
	}

	/**
	 * 已存在盘口代理主键
	 */
	@Column(name="EXIST_HA_ID_")
	private String existHaId;
	public void setExistHaId(String existHaId) {
		this.existHaId=existHaId;
	}
	public void setExistHaId(Object existHaId) {
		if (existHaId != null) {
			this.existHaId = existHaId.toString();
		}else{
			this.existHaId = null;
		}
	}
	public String getExistHaId() {
		return this.existHaId ;
	}


	/**
	 * 会员账号MEMBER_ACCOUNT_
	 */
	@Column(name="MEMBER_ACCOUNT_")
	private String memberAccount;
	public void setMemberAccount(String memberAccount) {
		this.memberAccount=memberAccount;
	}
	public void setMemberAccount(Object memberAccount) {
		if (memberAccount != null) {
			this.memberAccount = memberAccount.toString();
		}else{
			this.memberAccount = null;
		}
	}
	public String getMemberAccount() {
		return this.memberAccount ;
	}

	/**
	 * 投注时间
	 */
	@Column(name="BET_TIME_LONG_")
	private Long betTimeLong;
	public void setBetTimeLong(Long betTimeLong) {
		this.betTimeLong=betTimeLong;
	}
	public void setBetTimeLong(Object betTimeLong) {
		if (betTimeLong != null) {
			if (betTimeLong instanceof Long) {
				this.betTimeLong= (Long) betTimeLong;
			}else if (StringTool.isInteger(betTimeLong.toString())) {
				this.betTimeLong = Long.parseLong(betTimeLong.toString());
			}
		}else{
			this.betTimeLong = null;
		}
	}
	public Long getBetTimeLong() {
		return this.betTimeLong ;
	}

	/**
	 * 游戏编码GAME_CODE_
	 */
	@Column(name="GAME_CODE_")
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public void setGameCode(Object gameCode) {
		if (gameCode != null) {
			this.gameCode = gameCode.toString();
		}else{
			this.gameCode = null;
		}
	}
	public String getGameCode() {
		return this.gameCode ;
	}

	/**
	 * 期数PERIOD_
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
	 * 投注内容BET_INFO_
	 */
	@Column(name="BET_INFO_")
	private String betInfo;
	public void setBetInfo(String betInfo) {
		this.betInfo=betInfo;
	}
	public void setBetInfo(Object betInfo) {
		if (betInfo != null) {
			this.betInfo = betInfo.toString();
		}else{
			this.betInfo = null;
		}
	}
	public String getBetInfo() {
		return this.betInfo ;
	}

	/**
	 * 投注金额
	 */
	@Column(name="BET_FUND_T_")
	private Integer betFundT;
	public void setBetFundT(Integer betFundT) {
		this.betFundT=betFundT;
	}
	public void setBetFundT(Object betFundT) {
		if (betFundT != null) {
			if (betFundT instanceof Integer) {
				this.betFundT= (Integer) betFundT;
			}else if (StringTool.isInteger(betFundT.toString())) {
				this.betFundT = Integer.parseInt(betFundT.toString());
			}
		}else{
			this.betFundT = null;
		}
	}
	public Integer getBetFundT() {
		return this.betFundT ;
	}

	/**
	 * 跟投内容
	 */
	@Column(name="FOLLOW_CONTENT_")
	private String followContent;
	public void setFollowContent(String followContent) {
		this.followContent=followContent;
	}
	public void setFollowContent(Object followContent) {
		if (followContent != null) {
			this.followContent = followContent.toString();
		}else{
			this.followContent = null;
		}
	}
	public String getFollowContent() {
		return this.followContent ;
	}

	/**
	 * 跟投金额
	 */
	@Column(name="FOLLOW_FUND_T_")
	private Long followFundT;
	public void setFollowFundT(Long followFundT) {
		this.followFundT=followFundT;
	}
	public void setFollowFundT(Object followFundT) {
		if (followFundT != null) {
			if (followFundT instanceof Long) {
				this.followFundT= (Long) followFundT;
			}else if (StringTool.isInteger(followFundT.toString())) {
				this.followFundT = Long.parseLong(followFundT.toString());
			}
		}else{
			this.followFundT = null;
		}
	}
	public Long getFollowFundT() {
		return this.followFundT ;
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
		}else{
			this.createTimeLong = null;
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
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