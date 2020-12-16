package com.ibm.old.v1.cloud.ibm_exec_result.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_exec_result 
 * vo类
 * @author Robot
 */

public class IbmExecResultTVo implements Serializable {

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
	 * IBM_方案翻译工作作执行阶段主键
	 */
	private String ibmExecResultId;
	public void setIbmExecResultId(String ibmExecResultId) {
		this.ibmExecResultId=ibmExecResultId;
	}
	public String getIbmExecResultId() {
		return this.ibmExecResultId ;
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
	 * 方案主键
	 */
	private String planId;
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public String getPlanId() {
		return this.planId ;
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
	 * 执行结果
	 */
	private String execResult;
	public void setExecResult(String execResult) {
		this.execResult=execResult;
	}
	public String getExecResult() {
		return this.execResult ;
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