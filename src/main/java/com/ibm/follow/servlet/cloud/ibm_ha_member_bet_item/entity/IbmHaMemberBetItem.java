package com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_ha_member_bet_item 
 * IBM_盘口代理会员投注信息的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_ha_member_bet_item")
public class IbmHaMemberBetItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口代理会员投注信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HA_MEMBER_BET_ITEM_ID_")
	private String ibmHaMemberBetItemId;
	public void setIbmHaMemberBetItemId(String ibmHaMemberBetItemId) {
		this.ibmHaMemberBetItemId=ibmHaMemberBetItemId;
	}
	public void setIbmHaMemberBetItemId(Object ibmHaMemberBetItemId) {
		if (ibmHaMemberBetItemId != null) {
			this.ibmHaMemberBetItemId = ibmHaMemberBetItemId.toString();
		}else{
			this.ibmHaMemberBetItemId = null;
		}
	}
	public String getIbmHaMemberBetItemId() {
		return this.ibmHaMemberBetItemId ;
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
	 * 盘口代理主键
	 */
	@Column(name="HANDICAP_AGENT_ID_")
	private String handicapAgentId;
	public void setHandicapAgentId(String handicapAgentId) {
		this.handicapAgentId=handicapAgentId;
	}
	public void setHandicapAgentId(Object handicapAgentId) {
		if (handicapAgentId != null) {
			this.handicapAgentId = handicapAgentId.toString();
		}else{
			this.handicapAgentId = null;
		}
	}
	public String getHandicapAgentId() {
		return this.handicapAgentId ;
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
	 * 投注内容长度
	 */
	@Column(name="BET_CONTENT_LEN_")
	private Integer betContentLen;
	public void setBetContentLen(int betContentLen) {
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
	 * 投注金额
	 */
	@Column(name="BET_FUND_T_")
	private Long betFundT;
	public void setBetFundT(Long betFundT) {
		this.betFundT=betFundT;
	}
	public void setBetFundT(Object betFundT) {
		if (betFundT != null) {
			if (betFundT instanceof Long) {
				this.betFundT= (Long) betFundT;
			}else if (StringTool.isInteger(betFundT.toString())) {
				this.betFundT = Long.parseLong(betFundT.toString());
			}
		}else{
			this.betFundT = null;
		}
	}
	public Long getBetFundT() {
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