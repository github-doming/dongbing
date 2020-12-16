package com.ibm.old.v1.cloud.ibm_rep_grab_cqssc.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_rep_grab_cqssc 
 * vo类
 * @author Robot
 */

public class IbmRepGrabCqsscTVo implements Serializable {

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
	 * 重庆时时彩CQSSC_抓取数据主键
	 */
	private String ibmRepGrabCqsscId;
	public void setIbmRepGrabCqsscId(String ibmRepGrabCqsscId) {
		this.ibmRepGrabCqsscId=ibmRepGrabCqsscId;
	}
	public String getIbmRepGrabCqsscId() {
		return this.ibmRepGrabCqsscId ;
	}
	
	/**
	 * 重庆时时彩CQSSC_结果数据
	 */
	private String repDrawCqsscId;
	public void setRepDrawCqsscId(String repDrawCqsscId) {
		this.repDrawCqsscId=repDrawCqsscId;
	}
	public String getRepDrawCqsscId() {
		return this.repDrawCqsscId ;
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
	 * 期数
	 */
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public String getPeriod() {
		return this.period ;
	}
	
	/**
	 * 开奖时间
	 */
	private String drawTime;
	public void setDrawTime(String drawTime) {
		this.drawTime=drawTime;
	}
	public String getDrawTime() {
		return this.drawTime ;
	}
	
	/**
	 * 开奖时间数字型
	 */
	private String drawTimeLong;
	public void setDrawTimeLong(String drawTimeLong) {
		this.drawTimeLong=drawTimeLong;
	}
	public String getDrawTimeLong() {
		return this.drawTimeLong ;
	}
	
	/**
	 * 开奖号码
	 */
	private String drawNumber;
	public void setDrawNumber(String drawNumber) {
		this.drawNumber=drawNumber;
	}
	public String getDrawNumber() {
		return this.drawNumber ;
	}
	
	/**
	 * 第1名号码
	 */
	private String p1Number;
	public void setP1Number(String p1Number) {
		this.p1Number=p1Number;
	}
	public String getP1Number() {
		return this.p1Number ;
	}
	
	/**
	 * 第2名号码
	 */
	private String p2Number;
	public void setP2Number(String p2Number) {
		this.p2Number=p2Number;
	}
	public String getP2Number() {
		return this.p2Number ;
	}
	
	/**
	 * 第3名号码
	 */
	private String p3Number;
	public void setP3Number(String p3Number) {
		this.p3Number=p3Number;
	}
	public String getP3Number() {
		return this.p3Number ;
	}
	
	/**
	 * 第4名号码
	 */
	private String p4Number;
	public void setP4Number(String p4Number) {
		this.p4Number=p4Number;
	}
	public String getP4Number() {
		return this.p4Number ;
	}
	
	/**
	 * 第5名号码
	 */
	private String p5Number;
	public void setP5Number(String p5Number) {
		this.p5Number=p5Number;
	}
	public String getP5Number() {
		return this.p5Number ;
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