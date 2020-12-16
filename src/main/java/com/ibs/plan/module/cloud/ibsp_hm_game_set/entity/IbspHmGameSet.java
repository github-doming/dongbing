package com.ibs.plan.module.cloud.ibsp_hm_game_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_hm_game_set 
 * IBSP_盘口会员游戏设置 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_hm_game_set")
public class IbspHmGameSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_盘口会员游戏设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_HM_GAME_SET_ID_")
	private String ibspHmGameSetId;
	public void setIbspHmGameSetId(String ibspHmGameSetId) {
		this.ibspHmGameSetId=ibspHmGameSetId;
	}
	public void setIbspHmGameSetId(Object ibspHmGameSetId) {
		if (ibspHmGameSetId != null) {
			this.ibspHmGameSetId = ibspHmGameSetId.toString();
		}
	}
	public String getIbspHmGameSetId() {
		return this.ibspHmGameSetId ;
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
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
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
		}
	}
	public String getGameId() {
		return this.gameId ;
	}

	/**
	 * 投注状态
	 */
	@Column(name="BET_STATE_")
	private String betState;
	public void setBetState(String betState) {
		this.betState=betState;
	}
	public void setBetState(Object betState) {
		if (betState != null) {
			this.betState = betState.toString();
		}
	}
	public String getBetState() {
		return this.betState ;
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
		}
	}
	public String getBetMode() {
		return this.betMode ;
	}

	/**
	 * 自动停止投注
	 */
	@Column(name="IS_AUTO_STOP_BET_")
	private String isAutoStopBet;
	public void setIsAutoStopBet(String isAutoStopBet) {
		this.isAutoStopBet=isAutoStopBet;
	}
	public void setIsAutoStopBet(Object isAutoStopBet) {
		if (isAutoStopBet != null) {
			this.isAutoStopBet = isAutoStopBet.toString();
		}
	}
	public String getIsAutoStopBet() {
		return this.isAutoStopBet ;
	}

	/**
	 * 自动停止投注时间
	 */
	@Column(name="AUTO_STOP_BET_TIME_LONG_")
	private Long autoStopBetTimeLong;
	public void setAutoStopBetTimeLong(Long autoStopBetTimeLong) {
		this.autoStopBetTimeLong=autoStopBetTimeLong;
	}
	public void setAutoStopBetTimeLong(Object autoStopBetTimeLong) {
		if (autoStopBetTimeLong != null) {
			if (autoStopBetTimeLong instanceof Long) {
				this.autoStopBetTimeLong= (Long) autoStopBetTimeLong;
			}else if (StringTool.isInteger(autoStopBetTimeLong.toString())) {
				this.autoStopBetTimeLong = Long.parseLong(autoStopBetTimeLong.toString());
			}
		}
	}
	public Long getAutoStopBetTimeLong() {
		return this.autoStopBetTimeLong ;
	}

	/**
	 * 自动开始投注
	 */
	@Column(name="IS_AUTO_START_BET_")
	private String isAutoStartBet;
	public void setIsAutoStartBet(String isAutoStartBet) {
		this.isAutoStartBet=isAutoStartBet;
	}
	public void setIsAutoStartBet(Object isAutoStartBet) {
		if (isAutoStartBet != null) {
			this.isAutoStartBet = isAutoStartBet.toString();
		}
	}
	public String getIsAutoStartBet() {
		return this.isAutoStartBet ;
	}

	/**
	 * 自动开始投注时间
	 */
	@Column(name="AUTO_START_BET_TIME_LONG_")
	private Long autoStartBetTimeLong;
	public void setAutoStartBetTimeLong(Long autoStartBetTimeLong) {
		this.autoStartBetTimeLong=autoStartBetTimeLong;
	}
	public void setAutoStartBetTimeLong(Object autoStartBetTimeLong) {
		if (autoStartBetTimeLong != null) {
			if (autoStartBetTimeLong instanceof Long) {
				this.autoStartBetTimeLong= (Long) autoStartBetTimeLong;
			}else if (StringTool.isInteger(autoStartBetTimeLong.toString())) {
				this.autoStartBetTimeLong = Long.parseLong(autoStartBetTimeLong.toString());
			}
		}
	}
	public Long getAutoStartBetTimeLong() {
		return this.autoStartBetTimeLong ;
	}

	/**
	 * 是否反投
	 */
	@Column(name="IS_INVERSE_")
	private String isInverse;
	public void setIsInverse(String isInverse) {
		this.isInverse=isInverse;
	}
	public void setIsInverse(Object isInverse) {
		if (isInverse != null) {
			this.isInverse = isInverse.toString();
		}
	}
	public String getIsInverse() {
		return this.isInverse ;
	}

	/**
	 * 新增状态
	 */
	@Column(name="INCREASE_STATE_")
	private String increaseState;
	public void setIncreaseState(String increaseState) {
		this.increaseState=increaseState;
	}
	public void setIncreaseState(Object increaseState) {
		if (increaseState != null) {
			this.increaseState = increaseState.toString();
		}
	}
	public String getIncreaseState() {
		return this.increaseState ;
	}

	/**
	 * 停止新增时间
	 */
	@Column(name="INCREASE_STOP_TIME_LONG_")
	private Long increaseStopTimeLong;
	public void setIncreaseStopTimeLong(Long increaseStopTimeLong) {
		this.increaseStopTimeLong=increaseStopTimeLong;
	}
	public void setIncreaseStopTimeLong(Object increaseStopTimeLong) {
		if (increaseStopTimeLong != null) {
			if (increaseStopTimeLong instanceof Long) {
				this.increaseStopTimeLong= (Long) increaseStopTimeLong;
			}else if (StringTool.isInteger(increaseStopTimeLong.toString())) {
				this.increaseStopTimeLong = Long.parseLong(increaseStopTimeLong.toString());
			}
		}
	}
	public Long getIncreaseStopTimeLong() {
		return this.increaseStopTimeLong ;
	}

	/**
	 * 每期投注时刻
	 */
	@Column(name="BET_SECOND_")
	private Integer betSecond;
	public void setBetSecond(Integer betSecond) {
		this.betSecond=betSecond;
	}
	public void setBetSecond(Object betSecond) {
		if (betSecond != null) {
			if (betSecond instanceof Integer) {
				this.betSecond= (Integer) betSecond;
			}else if (StringTool.isInteger(betSecond.toString())) {
				this.betSecond = Integer.parseInt(betSecond.toString());
			}
		}
	}
	public Integer getBetSecond() {
		return this.betSecond ;
	}

	/**
	 * 双面分投额度
	 */
	@Column(name="SPLIT_TWO_SIDE_AMOUNT_")
	private Integer splitTwoSideAmount;
	public void setSplitTwoSideAmount(Integer splitTwoSideAmount) {
		this.splitTwoSideAmount=splitTwoSideAmount;
	}
	public void setSplitTwoSideAmount(Object splitTwoSideAmount) {
		if (splitTwoSideAmount != null) {
			if (splitTwoSideAmount instanceof Integer) {
				this.splitTwoSideAmount= (Integer) splitTwoSideAmount;
			}else if (StringTool.isInteger(splitTwoSideAmount.toString())) {
				this.splitTwoSideAmount = Integer.parseInt(splitTwoSideAmount.toString());
			}
		}
	}
	public Integer getSplitTwoSideAmount() {
		return this.splitTwoSideAmount ;
	}

	/**
	 * 号码分投额度
	 */
	@Column(name="SPLIT_NUMBER_AMOUNT_")
	private Integer splitNumberAmount;
	public void setSplitNumberAmount(Integer splitNumberAmount) {
		this.splitNumberAmount=splitNumberAmount;
	}
	public void setSplitNumberAmount(Object splitNumberAmount) {
		if (splitNumberAmount != null) {
			if (splitNumberAmount instanceof Integer) {
				this.splitNumberAmount= (Integer) splitNumberAmount;
			}else if (StringTool.isInteger(splitNumberAmount.toString())) {
				this.splitNumberAmount = Integer.parseInt(splitNumberAmount.toString());
			}
		}
	}
	public Integer getSplitNumberAmount() {
		return this.splitNumberAmount ;
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