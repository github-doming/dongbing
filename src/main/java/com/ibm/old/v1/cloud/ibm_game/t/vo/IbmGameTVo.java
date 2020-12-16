package com.ibm.old.v1.cloud.ibm_game.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_game 
 * vo类
 * @author Robot
 */

public class IbmGameTVo implements Serializable {

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
	 * IBM_游戏主键IBM_GAME_ID_
	 */
	private String ibmGameId;
	public void setIbmGameId(String ibmGameId) {
		this.ibmGameId=ibmGameId;
	}
	public String getIbmGameId() {
		return this.ibmGameId ;
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
	 * 游戏图标
	 */
	private String icon;
	public void setIcon(String icon) {
		this.icon=icon;
	}
	public String getIcon() {
		return this.icon ;
	}
	
	/**
	 * 开奖数据表名
	 */
	private String repGrabTableName;
	public void setRepGrabTableName(String repGrabTableName) {
		this.repGrabTableName=repGrabTableName;
	}
	public String getRepGrabTableName() {
		return this.repGrabTableName ;
	}
	
	/**
	 * 开奖结果表名
	 */
	private String repDrawTableName;
	public void setRepDrawTableName(String repDrawTableName) {
		this.repDrawTableName=repDrawTableName;
	}
	public String getRepDrawTableName() {
		return this.repDrawTableName ;
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