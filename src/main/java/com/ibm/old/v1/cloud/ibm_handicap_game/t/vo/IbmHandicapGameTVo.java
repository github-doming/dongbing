package com.ibm.old.v1.cloud.ibm_handicap_game.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_handicap_game 
 * vo类
 * @author Robot
 */

public class IbmHandicapGameTVo implements Serializable {

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
	 * IBM_盘口游戏主键IBM_HANDICAP_GAME_ID_
	 */
	private String ibmHandicapGameId;
	public void setIbmHandicapGameId(String ibmHandicapGameId) {
		this.ibmHandicapGameId=ibmHandicapGameId;
	}
	public String getIbmHandicapGameId() {
		return this.ibmHandicapGameId ;
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
	 * 盘口名称
	 */
	private String handicapName;
	public void setHandicapName(String handicapName) {
		this.handicapName=handicapName;
	}
	public String getHandicapName() {
		return this.handicapName ;
	}
	
	/**
	 * 盘口编码
	 */
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public String getHandicapCode() {
		return this.handicapCode ;
	}
	
	/**
	 * 游戏名称
	 */
	private String gameName;
	public void setGameName(String gameName) {
		this.gameName=gameName;
	}
	public String getGameName() {
		return this.gameName ;
	}
	
	/**
	 * 投注执行表名SUB_IEBI_TABLE_NAME_
	 */
	private String subIeblTableName;
	public void setSubIeblTableName(String subIeblTableName) {
		this.subIeblTableName=subIeblTableName;
	}
	public String getSubIeblTableName() {
		return this.subIeblTableName ;
	}
	/**
	 * 游戏编码
	 */
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public String getGameCode() {
		return this.gameCode ;
	}
	
	/**
	 * 盘口游戏图标
	 */
	private String icon;
	public void setIcon(String icon) {
		this.icon=icon;
	}
	public String getIcon() {
		return this.icon ;
	}
	
	/**
	 * 次序
	 */
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
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