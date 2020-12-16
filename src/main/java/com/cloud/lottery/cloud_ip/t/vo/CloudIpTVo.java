package com.cloud.lottery.cloud_ip.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table cloud_ip 
 * vo类
 */

public class CloudIpTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//IBM_客户机IP地址主键
	private String cloudIpId;
	public void setCloudIpId(String cloudIpId) {
		this.cloudIpId=cloudIpId;
	}
	public String getCloudIpId() {
		return this.cloudIpId ;
	}
	
//IP
	private String ip;
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
	
//PORT_
	private String port;
	public void setPort(String port) {
		this.port=port;
	}
	public String getPort() {
		return this.port ;
	}
	
//开始时间
	private String startTime;
	public void setStartTime(String startTime) {
		this.startTime=startTime;
	}
	public String getStartTime() {
		return this.startTime ;
	}
	
//开始时间数字型
	private String startTimeLong;
	public void setStartTimeLong(String startTimeLong) {
		this.startTimeLong=startTimeLong;
	}
	public String getStartTimeLong() {
		return this.startTimeLong ;
	}
	
//结束时间
	private String endTime;
	public void setEndTime(String endTime) {
		this.endTime=endTime;
	}
	public String getEndTime() {
		return this.endTime ;
	}
	
//结束时间数字型
	private String endTimeLong;
	public void setEndTimeLong(String endTimeLong) {
		this.endTimeLong=endTimeLong;
	}
	public String getEndTimeLong() {
		return this.endTimeLong ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
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