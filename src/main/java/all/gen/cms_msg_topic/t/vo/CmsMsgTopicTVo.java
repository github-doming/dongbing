package all.gen.cms_msg_topic.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table cms_msg_topic 
 * vo类
 */

public class CmsMsgTopicTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//CMS_消息主题主键 CMS_MSG_TOPIC_ID_
	private String cmsMsgTopicId;
	public void setCmsMsgTopicId(String cmsMsgTopicId) {
		this.cmsMsgTopicId=cmsMsgTopicId;
	}
	public String getCmsMsgTopicId() {
		return this.cmsMsgTopicId ;
	}
	
//发送用户ID SEND_USER_
	private String sendUser;
	public void setSendUser(String sendUser) {
		this.sendUser=sendUser;
	}
	public String getSendUser() {
		return this.sendUser ;
	}
	
//接收用户ID RECEIVE_USER_
	private String receiveUser;
	public void setReceiveUser(String receiveUser) {
		this.receiveUser=receiveUser;
	}
	public String getReceiveUser() {
		return this.receiveUser ;
	}
	
//发送用户名称SEND_USER_NAME_
	private String sendUserName;
	public void setSendUserName(String sendUserName) {
		this.sendUserName=sendUserName;
	}
	public String getSendUserName() {
		return this.sendUserName ;
	}
	
//发送用户昵称SEND_USER_NICKNAME_
	private String sendUserNickname;
	public void setSendUserNickname(String sendUserNickname) {
		this.sendUserNickname=sendUserNickname;
	}
	public String getSendUserNickname() {
		return this.sendUserNickname ;
	}
	
//接收用户名称RECEIVE_USER_NAME_
	private String receiveUserName;
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName=receiveUserName;
	}
	public String getReceiveUserName() {
		return this.receiveUserName ;
	}
	
//接收用户昵称RECEIVE_USER_NICKNAME_
	private String receiveUserNickname;
	public void setReceiveUserNickname(String receiveUserNickname) {
		this.receiveUserNickname=receiveUserNickname;
	}
	public String getReceiveUserNickname() {
		return this.receiveUserNickname ;
	}
	
//主题类型MSG_TOPIC_TYPE_
	private String msgTopicType;
	public void setMsgTopicType(String msgTopicType) {
		this.msgTopicType=msgTopicType;
	}
	public String getMsgTopicType() {
		return this.msgTopicType ;
	}
	
//标题MSG_TOPIC_TITLE_
	private String msgTopicTitle;
	public void setMsgTopicTitle(String msgTopicTitle) {
		this.msgTopicTitle=msgTopicTitle;
	}
	public String getMsgTopicTitle() {
		return this.msgTopicTitle ;
	}
	
//内容CONTENT_
	private String cmsContent;
	public void setCmsContent(String cmsContent) {
		this.cmsContent=cmsContent;
	}
	public String getCmsContent() {
		return this.cmsContent ;
	}
	
//查看地址URL_VIEW_
	private String urlView;
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
	}
	
//是否回复REPLY_
	private String reply;
	public void setReply(String reply) {
		this.reply=reply;
	}
	public String getReply() {
		return this.reply ;
	}
	
//关键字KEYWORDS_
	private String cmsKeywords;
	public void setCmsKeywords(String cmsKeywords) {
		this.cmsKeywords=cmsKeywords;
	}
	public String getCmsKeywords() {
		return this.cmsKeywords ;
	}
	
//创建者CREATE_USER_
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
	
//创建者名称CREATE_USER_NAME_
	private String createUserName;
	public void setCreateUserName(String createUserName) {
		this.createUserName=createUserName;
	}
	public String getCreateUserName() {
		return this.createUserName ;
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
	
//更新者UPDATE_USER_
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
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