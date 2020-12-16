package com.ibm.old.v1.client.ibm_client_hm_game_set.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_client_hm_game_set 
 * vo类
 * @author Robot
 */

public class IbmClientHmGameSetTVo implements Serializable {

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
	 * IBM_客户端盘口会员游戏设置主键
	 */
	private String ibmClientHmGameSetId;
	public void setIbmClientHmGameSetId(String ibmClientHmGameSetId) {
		this.ibmClientHmGameSetId=ibmClientHmGameSetId;
	}
	public String getIbmClientHmGameSetId() {
		return this.ibmClientHmGameSetId ;
	}
	
	/**
	 * 客户端已存在盘口会员主键
	 */
	private String clientExistHmId;
	public void setClientExistHmId(String clientExistHmId) {
		this.clientExistHmId=clientExistHmId;
	}
	public String getClientExistHmId() {
		return this.clientExistHmId ;
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
	 * 游戏编码GAME_CODE_
	 */
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public String getGameCode() {
		return this.gameCode ;
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
	 * 投注限额
	 */
	private String betLimit;
	public void setBetLimit(String betLimit) {
		this.betLimit=betLimit;
	}
	public String getBetLimit() {
		return this.betLimit ;
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