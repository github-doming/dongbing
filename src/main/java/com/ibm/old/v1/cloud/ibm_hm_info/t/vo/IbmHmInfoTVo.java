package com.ibm.old.v1.cloud.ibm_hm_info.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_hm_info 
 * vo类
 * @author Robot
 */

public class IbmHmInfoTVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * IBM_盘口会员信息主键IBM_HM_INFO_ID_
	 */
	private String ibmHmInfoId;
	public void setIbmHmInfoId(String ibmHmInfoId) {
		this.ibmHmInfoId=ibmHmInfoId;
	}
	public String getIbmHmInfoId() {
		return this.ibmHmInfoId ;
	}
	
	/**
	 * 盘口会员主键HANDICAP_MEMBER_ID_
	 */
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
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
	 * 信息类型INFO_TYPE_
	 */
	private String infoType;
	public void setInfoType(String infoType) {
		this.infoType=infoType;
	}
	public String getInfoType() {
		return this.infoType ;
	}
	
	/**
	 * 信息内容INFO_CONTENT_
	 */
	private String infoContent;
	public void setInfoContent(String infoContent) {
		this.infoContent=infoContent;
	}
	public String getInfoContent() {
		return this.infoContent ;
	}
	
	/**
	 * 信息状态INFO_STATE_
	 */
	private String infoState;
	public void setInfoState(String infoState) {
		this.infoState=infoState;
	}
	public String getInfoState() {
		return this.infoState ;
	}
	
	/**
	 * 创建时间CREATE_TIME_
	 */
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
	/**
	 * 创建时间数字型CREATE_TIME_LONG_
	 */
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
	/**
	 * 更新时间UPDATE_TIME_
	 */
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
	/**
	 * 更新时间数字型UPDATE_TIME_LONG_
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