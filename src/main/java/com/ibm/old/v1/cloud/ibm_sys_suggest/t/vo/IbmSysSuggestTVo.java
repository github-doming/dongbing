package com.ibm.old.v1.cloud.ibm_sys_suggest.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_sys_suggest 
 * vo类
 * @author Robot
 */

public class IbmSysSuggestTVo implements Serializable {

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
	 * IBM__用户反馈主键IBM__USER_SUGGEST_ID_
	 */
	private String ibmSysSuggestId;
	public void setIbmSysSuggestId(String ibmSysSuggestId) {
		this.ibmSysSuggestId=ibmSysSuggestId;
	}
	public String getIbmSysSuggestId() {
		return this.ibmSysSuggestId ;
	}
	
	/**
	 * 用户反馈内容
	 */
	private String suggestContent;
	public void setSuggestContent(String suggestContent) {
		this.suggestContent=suggestContent;
	}
	public String getSuggestContent() {
		return this.suggestContent ;
	}
	
	/**
	 * 处理结果内容
	 */
	private String resultContent;
	public void setResultContent(String resultContent) {
		this.resultContent=resultContent;
	}
	public String getResultContent() {
		return this.resultContent ;
	}
	
	/**
	 * 创建者
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
	 * 更新者
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
	 * 阅读状态
	 */
	private String readState;
	public void setReadState(String readState) {
		this.readState=readState;
	}
	public String getReadState() {
		return this.readState ;
	}
	
	/**
	 * 处理状态
	 */
	private String handleState;
	public void setHandleState(String handleState) {
		this.handleState=handleState;
	}
	public String getHandleState() {
		return this.handleState ;
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