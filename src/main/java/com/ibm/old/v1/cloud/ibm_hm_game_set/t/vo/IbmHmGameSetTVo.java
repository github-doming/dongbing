package com.ibm.old.v1.cloud.ibm_hm_game_set.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_hm_game_set 
 * vo类
 * @author Robot
 */

public class IbmHmGameSetTVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 索引
	 */
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
	/**
	 * IBM_盘口会员游戏设置主键
	 */
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
	private String betAutoStopTime;
	public void setBetAutoStopTime(String betAutoStopTime) {
		this.betAutoStopTime=betAutoStopTime;
	}
	public String getBetAutoStopTime() {
		return this.betAutoStopTime ;
	}
	
	/**
	 * 自动开始投注
	 */
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
	private String betAutoStartTime;
	public void setBetAutoStartTime(String betAutoStartTime) {
		this.betAutoStartTime=betAutoStartTime;
	}
	public String getBetAutoStartTime() {
		return this.betAutoStartTime ;
	}
	
	/**
	 * 新增状态
	 */
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
	private String increaseStopTime;
	public void setIncreaseStopTime(String increaseStopTime) {
		this.increaseStopTime=increaseStopTime;
	}
	public String getIncreaseStopTime() {
		return this.increaseStopTime ;
	}
	
	/**
	 * 每期投注时刻
	 */
	private String betSecond;
	public void setBetSecond(String betSecond) {
		this.betSecond=betSecond;
	}
	public String getBetSecond() {
		return this.betSecond ;
	}
	
	/**
	 * 双面分投额度
	 */
	private String splitTwoSideAmount;
	public void setSplitTwoSideAmount(String splitTwoSideAmount) {
		this.splitTwoSideAmount=splitTwoSideAmount;
	}
	public String getSplitTwoSideAmount() {
		return this.splitTwoSideAmount ;
	}
	
	/**
	 * 号码分投额度
	 */
	private String splitNumberAmount;
	public void setSplitNumberAmount(String splitNumberAmount) {
		this.splitNumberAmount=splitNumberAmount;
	}
	public String getSplitNumberAmount() {
		return this.splitNumberAmount ;
	}
	
	/**
	 * 创建时间
	 */
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
	/**
	 * 创建时间
	 */
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
	/**
	 * 更新时间
	 */
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
	/**
	 * 更新时间
	 */
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
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
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	}