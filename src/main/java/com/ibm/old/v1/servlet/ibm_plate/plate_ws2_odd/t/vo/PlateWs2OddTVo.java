package com.ibm.old.v1.servlet.ibm_plate.plate_ws2_odd.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table plate_ws2_odd 
 * vo类
 * @author Robot
 */

public class PlateWs2OddTVo implements Serializable {

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
	private String plateWs2OddId;
	public void setPlateWs2OddId(String plateWs2OddId) {
		this.plateWs2OddId=plateWs2OddId;
	}
	public String getPlateWs2OddId() {
		return this.plateWs2OddId ;
	}
	
	/**
	 * 玩法名称
	 */
	private String gameplayName;
	public void setGameplayName(String gameplayName) {
		this.gameplayName=gameplayName;
	}
	public String getGameplayName() {
		return this.gameplayName ;
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
	 * 创建者CREATE_USER_
	 */
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
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
	 * 更新者UPDATE_USER_
	 */
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
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