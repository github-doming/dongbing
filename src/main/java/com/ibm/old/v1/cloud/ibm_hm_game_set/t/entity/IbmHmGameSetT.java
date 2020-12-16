package com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_game_set 
 * IBM_盘口会员游戏设置的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_game_set")
public class IbmHmGameSetT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}

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
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}

	/**
	 * 盘口游戏主键
	 */
	@Column(name="HANDICAP_GAME_ID_")
	private String handicapGameId;
	public void setHandicapGameId(String handicapGameId) {
		this.handicapGameId=handicapGameId;
	}
	public String getHandicapGameId() {
		return this.handicapGameId ;
	}

	/**
	 * 盘口主键
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}

	/**
	 * 用户主键
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public String getUserId() {
		return this.userId ;
	}

	/**
	 * 游戏主键
	 */
	@Column(name="GAME_ID_")
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
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
	public String getBetAutoStop() {
		return this.betAutoStop ;
	}

	/**
	 * 自动停止投注时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="BET_AUTO_STOP_TIME_")
	private Date betAutoStopTime;
	public void setBetAutoStopTime(Date betAutoStopTime) {
		this.betAutoStopTime=betAutoStopTime;
	}
	public void setBetAutoStopTime(Object betAutoStopTime) throws ParseException {
		if (betAutoStopTime != null) {
			if (betAutoStopTime instanceof Date) {
				this.betAutoStopTime = (Date) betAutoStopTime;
			} else if (StringTool.isInteger(betAutoStopTime.toString())) {
				this.betAutoStopTime = new Date(Long.parseLong(betAutoStopTime.toString()));
			} else {
				this.betAutoStopTime = DateTool.getTime(betAutoStopTime.toString());
			}
		}
	}
	public Date getBetAutoStopTime() {
		return this.betAutoStopTime ;
	}

	/**
	 * 自动开始投注
	 */
	@Column(name="BET_AUTO_START_")
	private String betAutoStart;
	public void setBetAutoStart(String betAutoStart) {
		this.betAutoStart=betAutoStart;
	}
	public String getBetAutoStart() {
		return this.betAutoStart ;
	}

	/**
	 * 自动开始投注时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="BET_AUTO_START_TIME_")
	private Date betAutoStartTime;
	public void setBetAutoStartTime(Date betAutoStartTime) {
		this.betAutoStartTime=betAutoStartTime;
	}
	public void setBetAutoStartTime(Object betAutoStartTime) throws ParseException {
		if (betAutoStartTime != null) {
			if (betAutoStartTime instanceof Date) {
				this.betAutoStartTime = (Date) betAutoStartTime;
			} else if (StringTool.isInteger(betAutoStartTime.toString())) {
				this.betAutoStartTime = new Date(Long.parseLong(betAutoStartTime.toString()));
			} else {
				this.betAutoStartTime = DateTool.getTime(betAutoStartTime.toString());
			}
		}
	}
	public Date getBetAutoStartTime() {
		return this.betAutoStartTime ;
	}

	/**
	 * 新增状态
	 */
	@Column(name="INCREASE_STATE_")
	private String increaseState;
	public void setIncreaseState(String increaseState) {
		this.increaseState=increaseState;
	}
	public String getIncreaseState() {
		return this.increaseState ;
	}

	/**
	 * 停止新增时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="INCREASE_STOP_TIME_")
	private Date increaseStopTime;
	public void setIncreaseStopTime(Date increaseStopTime) {
		this.increaseStopTime=increaseStopTime;
	}
	public void setIncreaseStopTime(Object increaseStopTime) throws ParseException {
		if (increaseStopTime != null) {
			if (increaseStopTime instanceof Date) {
				this.increaseStopTime = (Date) increaseStopTime;
			} else if (StringTool.isInteger(increaseStopTime.toString())) {
				this.increaseStopTime = new Date(Long.parseLong(increaseStopTime.toString()));
			} else {
				this.increaseStopTime = DateTool.getTime(increaseStopTime.toString());
			}
		}
	}
	public Date getIncreaseStopTime() {
		return this.increaseStopTime ;
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