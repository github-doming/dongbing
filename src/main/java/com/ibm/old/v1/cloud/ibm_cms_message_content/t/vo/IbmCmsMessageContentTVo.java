package com.ibm.old.v1.cloud.ibm_cms_message_content.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cms_message_content 
 * vo类
 * @author Robot
 */

public class IbmCmsMessageContentTVo implements Serializable {

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
	 * IBM_消息内容主键IBM_CMS_MESSAGE_CONTENT_ID_
	 */
	private String ibmCmsMessageContentId;
	public void setIbmCmsMessageContentId(String ibmCmsMessageContentId) {
		this.ibmCmsMessageContentId=ibmCmsMessageContentId;
	}
	public String getIbmCmsMessageContentId() {
		return this.ibmCmsMessageContentId ;
	}
	
	/**
	 * 发送用户SEND_USER_ID_
	 */
	private String sendUserId;
	public void setSendUserId(String sendUserId) {
		this.sendUserId=sendUserId;
	}
	public String getSendUserId() {
		return this.sendUserId ;
	}
	
	/**
	 * 接收用户RECEIVE_USER_ID_
	 */
	private String receiveUserId;
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId=receiveUserId;
	}
	public String getReceiveUserId() {
		return this.receiveUserId ;
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
	 * 内容CONTENT_
	 */
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
	
	/**
	 * 回复状态REPLY_STATE_
	 */
	private String replyState;
	public void setReplyState(String replyState) {
		this.replyState=replyState;
	}
	public String getReplyState() {
		return this.replyState ;
	}
	
	/**
	 * 阅读状态READING_STATE_
	 */
	private String readingState;
	public void setReadingState(String readingState) {
		this.readingState=readingState;
	}
	public String getReadingState() {
		return this.readingState ;
	}
	
	/**
	 * 关键字KEYWORD_
	 */
	private String keyword;
	public void setKeyword(String keyword) {
		this.keyword=keyword;
	}
	public String getKeyword() {
		return this.keyword ;
	}

	/**
	 * 通知类型MESSAGE_TYPE_
	 */
	private String messageType;
	public void setMessageType(String messageType) {
		this.messageType=messageType;
	}
	public String getMessageType() {
		return this.messageType ;
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