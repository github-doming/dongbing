package com.ibm.old.v1.cloud.ibm_sys_bet_odds.w.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_sys_bet_odds 
 * vo类
 * @author Robot
 */

public class IbmSysBetOddsWVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * IDX_
	 */
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
	/**
	 * IBM_SYS_BET_ODDS_ID_
	 */
	private String ibmSysBetOddsId;
	public void setIbmSysBetOddsId(String ibmSysBetOddsId) {
		this.ibmSysBetOddsId=ibmSysBetOddsId;
	}
	public String getIbmSysBetOddsId() {
		return this.ibmSysBetOddsId ;
	}
	
	/**
	 * GAME_ID_
	 */
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public String getGameId() {
		return this.gameId ;
	}
	
	/**
	 * PLAY_TYPE_CODE_
	 */
	private String playTypeCode;
	public void setPlayTypeCode(String playTypeCode) {
		this.playTypeCode=playTypeCode;
	}
	public String getPlayTypeCode() {
		return this.playTypeCode ;
	}
	
	/**
	 * PLAY_TYPE_NAME_
	 */
	private String playTypeName;
	public void setPlayTypeName(String playTypeName) {
		this.playTypeName=playTypeName;
	}
	public String getPlayTypeName() {
		return this.playTypeName ;
	}
	
	/**
	 * ODDS_
	 */
	private String odds;
	public void setOdds(String odds) {
		this.odds=odds;
	}
	public String getOdds() {
		return this.odds ;
	}
	
	/**
	 * CREATE_TIME_
	 */
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
	/**
	 * CREATE_TIME_LONG_
	 */
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
	/**
	 * UPDATE_TIME_
	 */
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
	/**
	 * UPDATE_TIME_LONG_
	 */
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
	/**
	 * STATE_
	 */
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
	/**
	 * DESC_
	 */
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	}