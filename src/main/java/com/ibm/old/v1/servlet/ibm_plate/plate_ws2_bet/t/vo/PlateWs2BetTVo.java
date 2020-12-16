package com.ibm.old.v1.servlet.ibm_plate.plate_ws2_bet.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table plate_ws2_bet 
 * vo类
 * @author Robot
 */

public class PlateWs2BetTVo implements Serializable {

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
	 * WS2挡板赔率主键
	 */
	private String plateWs2BetId;
	public void setPlateWs2BetId(String plateWs2BetId) {
		this.plateWs2BetId=plateWs2BetId;
	}
	public String getPlateWs2BetId() {
		return this.plateWs2BetId ;
	}
	
	/**
	 * 玩法CODE
	 */
	private String gameplayCode;
	public void setGameplayCode(String gameplayCode) {
		this.gameplayCode=gameplayCode;
	}
	public String getGameplayCode() {
		return this.gameplayCode ;
	}
	
	/**
	 * 游戏CODE
	 */
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public String getGameCode() {
		return this.gameCode ;
	}
	
	/**
	 * 赔率
	 */
	private String oddsT;
	public void setOddsT(String oddsT) {
		this.oddsT=oddsT;
	}
	public String getOddsT() {
		return this.oddsT ;
	}
	
	/**
	 * 金额
	 */
	private String amoutT;
	public void setAmoutT(String amoutT) {
		this.amoutT=amoutT;
	}
	public String getAmoutT() {
		return this.amoutT ;
	}
	
	/**
	 * 玩法分类
	 */
	private String gameplayType;
	public void setGameplayType(String gameplayType) {
		this.gameplayType=gameplayType;
	}
	public String getGameplayType() {
		return this.gameplayType ;
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