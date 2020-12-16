package com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_game_set 
 * IBM_盘口会员游戏设置的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_game_set")
public class IbmHmGameSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口会员游戏设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HM_GAME_SET_ID_")
	private String ibmHmGameSetId;
	public void setIbmHmGameSetId(String ibmHmGameSetId) {
		this.ibmHmGameSetId=ibmHmGameSetId;
	}
	public void setIbmHmGameSetId(Object ibmHmGameSetId) {
		if (ibmHmGameSetId != null) {
			this.ibmHmGameSetId = ibmHmGameSetId.toString();
		}else{
			this.ibmHmGameSetId = null;
		}
	}
	public String getIbmHmGameSetId() {
		return this.ibmHmGameSetId ;
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
	 * 用户主键
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			this.userId = userId.toString();
		}else{
			this.userId = null;
		}
	}
	public String getUserId() {
		return this.userId ;
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
		}else{
			this.betState = null;
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
		}else{
			this.betMode = null;
		}
	}
	public String getBetMode() {
		return this.betMode ;
	}

	/**
	 * 自动停止投注
	 */
	@Column(name="BET_AUTO_STOP_")
	private String betAutoStop;
	public void setBetAutoStop(String betAutoStop) {
		this.betAutoStop=betAutoStop;
	}
	public void setBetAutoStop(Object betAutoStop) {
		if (betAutoStop != null) {
			this.betAutoStop = betAutoStop.toString();
		}else{
			this.betAutoStop = null;
		}
	}
	public String getBetAutoStop() {
		return this.betAutoStop ;
	}

	/**
	 * 自动停止投注时间
	 */
	@Column(name="BET_AUTO_STOP_TIME_LONG_")
	private Long betAutoStopTimeLong;
	public void setBetAutoStopTimeLong(Long betAutoStopTimeLong) {
		this.betAutoStopTimeLong=betAutoStopTimeLong;
	}
	public void setBetAutoStopTimeLong(Object betAutoStopTimeLong) {
		if (betAutoStopTimeLong != null) {
			if (betAutoStopTimeLong instanceof Long) {
				this.betAutoStopTimeLong= (Long) betAutoStopTimeLong;
			}else if (StringTool.isInteger(betAutoStopTimeLong.toString())) {
				this.betAutoStopTimeLong = Long.parseLong(betAutoStopTimeLong.toString());
			}
		}else{
			this.betAutoStopTimeLong = null;
		}
	}
	public Long getBetAutoStopTimeLong() {
		return this.betAutoStopTimeLong ;
	}

	/**
	 * 自动开始投注
	 */
	@Column(name="BET_AUTO_START_")
	private String betAutoStart;
	public void setBetAutoStart(String betAutoStart) {
		this.betAutoStart=betAutoStart;
	}
	public void setBetAutoStart(Object betAutoStart) {
		if (betAutoStart != null) {
			this.betAutoStart = betAutoStart.toString();
		}else{
			this.betAutoStart = null;
		}
	}
	public String getBetAutoStart() {
		return this.betAutoStart ;
	}

	/**
	 * 自动开始投注时间
	 */
	@Column(name="BET_AUTO_START_TIME_LONG_")
	private Long betAutoStartTimeLong;
	public void setBetAutoStartTimeLong(Long betAutoStartTimeLong) {
		this.betAutoStartTimeLong=betAutoStartTimeLong;
	}
	public void setBetAutoStartTimeLong(Object betAutoStartTimeLong) {
		if (betAutoStartTimeLong != null) {
			if (betAutoStartTimeLong instanceof Long) {
				this.betAutoStartTimeLong= (Long) betAutoStartTimeLong;
			}else if (StringTool.isInteger(betAutoStartTimeLong.toString())) {
				this.betAutoStartTimeLong = Long.parseLong(betAutoStartTimeLong.toString());
			}
		}else{
			this.betAutoStartTimeLong = null;
		}
	}
	public Long getBetAutoStartTimeLong() {
		return this.betAutoStartTimeLong ;
	}

	/**
	 * 每期投注时刻
	 */
	@Column(name="BET_SECOND_")
	private Integer betSecond;
	public void setBetSecond(Integer betSecond) {
		this.betSecond=betSecond;
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
		}else{
			this.splitTwoSideAmount = null;
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
		}else{
			this.splitNumberAmount = null;
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