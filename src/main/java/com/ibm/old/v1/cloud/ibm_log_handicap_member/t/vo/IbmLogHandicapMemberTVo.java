package com.ibm.old.v1.cloud.ibm_log_handicap_member.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_log_handicap_member 
 * vo类
 */

public class IbmLogHandicapMemberTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//IBM_盘口会员操作日志表主键IBM_LOG_HANDICAP_MEMBER_ID_
	private String ibmLogHandicapMemberId;
	public void setIbmLogHandicapMemberId(String ibmLogHandicapMemberId) {
		this.ibmLogHandicapMemberId=ibmLogHandicapMemberId;
	}
	public String getIbmLogHandicapMemberId() {
		return this.ibmLogHandicapMemberId ;
	}
	
//盘口会员主键HANDICAP_MEMBER_ID_
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}
	
//盘口主键HANDICAP_ID_
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
	
//用户主键APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//操作类型HANDLE_TYPE_
	private String handleType;
	public void setHandleType(String handleType) {
		this.handleType=handleType;
	}
	public String getHandleType() {
		return this.handleType ;
	}
	
//创建时间CREATE_TIME_
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间数字型CREATE_TIME_LONG_
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间UPDATE_TIME_
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间数字型UPDATE_TIME_LONG_
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}


}