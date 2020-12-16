package all.gen.cms_box_topic.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table cms_box_topic 
 * vo类
 */

public class CmsBoxTopicTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//CMS个人信箱_主题主键CMS_BOX_TOPIC_ID_
	private String cmsBoxTopicId;
	public void setCmsBoxTopicId(String cmsBoxTopicId) {
		this.cmsBoxTopicId=cmsBoxTopicId;
	}
	public String getCmsBoxTopicId() {
		return this.cmsBoxTopicId ;
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
	
//CMS个人发件箱_主题草稿主键CMS_SEND_BOX_DRAFT_ID_
	private String cmsSendBoxDraftId;
	public void setCmsSendBoxDraftId(String cmsSendBoxDraftId) {
		this.cmsSendBoxDraftId=cmsSendBoxDraftId;
	}
	public String getCmsSendBoxDraftId() {
		return this.cmsSendBoxDraftId ;
	}
	
//CMS_消息主题主键 CMS_MSG_TOPIC_ID_
	private String cmsMsgTopicId;
	public void setCmsMsgTopicId(String cmsMsgTopicId) {
		this.cmsMsgTopicId=cmsMsgTopicId;
	}
	public String getCmsMsgTopicId() {
		return this.cmsMsgTopicId ;
	}
	
//用户主键APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//相当于表名
	private String tableName;
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
	
//相当于表的ID
	private String tableId;
	public void setTableId(String tableId) {
		this.tableId=tableId;
	}
	public String getTableId() {
		return this.tableId ;
	}
	
//发送渠道(邮件，短信，微信)(用逗号分割)CHANNEL_
	private String channel;
	public void setChannel(String channel) {
		this.channel=channel;
	}
	public String getChannel() {
		return this.channel ;
	}
	
//主题类型TOPIC_TYPE_
	private String topicType;
	public void setTopicType(String topicType) {
		this.topicType=topicType;
	}
	public String getTopicType() {
		return this.topicType ;
	}
	
//主题标题TOPIC_TITLE_
	private String topicTitle;
	public void setTopicTitle(String topicTitle) {
		this.topicTitle=topicTitle;
	}
	public String getTopicTitle() {
		return this.topicTitle ;
	}
	
//内容CONTENT_
	private String cmsContent;
	public void setCmsContent(String cmsContent) {
		this.cmsContent=cmsContent;
	}
	public String getCmsContent() {
		return this.cmsContent ;
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