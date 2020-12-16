package com.ibm.old.v1.cloud.ibm_cms_topic.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cms_topic 
 * vo类
 * @author Robot
 */

public class IbmCmsTopicTVo implements Serializable {

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
	 * IBM_主题状态主键
	 */
	private String ibmCmsTopicId;
	public void setIbmCmsTopicId(String ibmCmsTopicId) {
		this.ibmCmsTopicId=ibmCmsTopicId;
	}
	public String getIbmCmsTopicId() {
		return this.ibmCmsTopicId ;
	}
	
	/**
	 * CMS_主题主键
	 */
	private String topicId;
	public void setTopicId(String topicId) {
		this.topicId=topicId;
	}
	public String getTopicId() {
		return this.topicId ;
	}
	
	/**
	 * 用户主键
	 */
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public String getUserId() {
		return this.userId ;
	}
	
	/**
	 * 标题TITLE_
	 */
	private String title;
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
	
	/**
	 * 主题创建者名称
	 */
	private String topicCreateUserName;
	public void setTopicCreateUserName(String topicCreateUserName) {
		this.topicCreateUserName=topicCreateUserName;
	}
	public String getTopicCreateUserName() {
		return this.topicCreateUserName ;
	}
	
	/**
	 * 主题创建时间
	 */
	private String topicCreateTime;
	public void setTopicCreateTime(String topicCreateTime) {
		this.topicCreateTime=topicCreateTime;
	}
	public String getTopicCreateTime() {
		return this.topicCreateTime ;
	}
	
	/**
	 * 主题创建时间数字型
	 */
	private String topicCreateTimeLong;
	public void setTopicCreateTimeLong(String topicCreateTimeLong) {
		this.topicCreateTimeLong=topicCreateTimeLong;
	}
	public String getTopicCreateTimeLong() {
		return this.topicCreateTimeLong ;
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