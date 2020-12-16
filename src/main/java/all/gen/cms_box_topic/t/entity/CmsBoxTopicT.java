package all.gen.cms_box_topic.t.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table cms_box_topic 
 * CMS个人信箱_主题CMS_BOX_TOPIC的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "cms_box_topic")
public class CmsBoxTopicT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//CMS个人信箱_主题主键CMS_BOX_TOPIC_ID_
@Column(name="CMS_BOX_TOPIC_ID_")
	private String cmsBoxTopicId;
	
	public void setCmsBoxTopicId(String cmsBoxTopicId) {
		this.cmsBoxTopicId=cmsBoxTopicId;
	}
	public String getCmsBoxTopicId() {
		return this.cmsBoxTopicId ;
	}
			
			
//发送用户ID SEND_USER_
@Column(name="SEND_USER_")
	private String sendUser;
	
	public void setSendUser(String sendUser) {
		this.sendUser=sendUser;
	}
	public String getSendUser() {
		return this.sendUser ;
	}
			
			
//接收用户ID RECEIVE_USER_
@Column(name="RECEIVE_USER_")
	private String receiveUser;
	
	public void setReceiveUser(String receiveUser) {
		this.receiveUser=receiveUser;
	}
	public String getReceiveUser() {
		return this.receiveUser ;
	}
			
			
//发送用户名称SEND_USER_NAME_
@Column(name="SEND_USER_NAME_")
	private String sendUserName;
	
	public void setSendUserName(String sendUserName) {
		this.sendUserName=sendUserName;
	}
	public String getSendUserName() {
		return this.sendUserName ;
	}
			
			
//发送用户昵称SEND_USER_NICKNAME_
@Column(name="SEND_USER_NICKNAME_")
	private String sendUserNickname;
	
	public void setSendUserNickname(String sendUserNickname) {
		this.sendUserNickname=sendUserNickname;
	}
	public String getSendUserNickname() {
		return this.sendUserNickname ;
	}
			
			
//接收用户名称RECEIVE_USER_NAME_
@Column(name="RECEIVE_USER_NAME_")
	private String receiveUserName;
	
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName=receiveUserName;
	}
	public String getReceiveUserName() {
		return this.receiveUserName ;
	}
			
			
//接收用户昵称RECEIVE_USER_NICKNAME_
@Column(name="RECEIVE_USER_NICKNAME_")
	private String receiveUserNickname;
	
	public void setReceiveUserNickname(String receiveUserNickname) {
		this.receiveUserNickname=receiveUserNickname;
	}
	public String getReceiveUserNickname() {
		return this.receiveUserNickname ;
	}
			
			
//CMS个人发件箱_主题草稿主键CMS_SEND_BOX_DRAFT_ID_
@Column(name="CMS_SEND_BOX_DRAFT_ID_")
	private String cmsSendBoxDraftId;
	
	public void setCmsSendBoxDraftId(String cmsSendBoxDraftId) {
		this.cmsSendBoxDraftId=cmsSendBoxDraftId;
	}
	public String getCmsSendBoxDraftId() {
		return this.cmsSendBoxDraftId ;
	}
			
			
//CMS_消息主题主键 CMS_MSG_TOPIC_ID_
@Column(name="CMS_MSG_TOPIC_ID_")
	private String cmsMsgTopicId;
	
	public void setCmsMsgTopicId(String cmsMsgTopicId) {
		this.cmsMsgTopicId=cmsMsgTopicId;
	}
	public String getCmsMsgTopicId() {
		return this.cmsMsgTopicId ;
	}
			
			
//用户主键APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//相当于表名
@Column(name="TABLE_NAME_")
	private String tableName;
	
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
			
			
//相当于表的ID
@Column(name="TABLE_ID_")
	private String tableId;
	
	public void setTableId(String tableId) {
		this.tableId=tableId;
	}
	public String getTableId() {
		return this.tableId ;
	}
			
			
//发送渠道(邮件，短信，微信)(用逗号分割)CHANNEL_
@Column(name="CHANNEL_")
	private String channel;
	
	public void setChannel(String channel) {
		this.channel=channel;
	}
	public String getChannel() {
		return this.channel ;
	}
			
			
//主题类型TOPIC_TYPE_
@Column(name="TOPIC_TYPE_")
	private String topicType;
	
	public void setTopicType(String topicType) {
		this.topicType=topicType;
	}
	public String getTopicType() {
		return this.topicType ;
	}
			
			
//主题标题TOPIC_TITLE_
@Column(name="TOPIC_TITLE_")
	private String topicTitle;
	
	public void setTopicTitle(String topicTitle) {
		this.topicTitle=topicTitle;
	}
	public String getTopicTitle() {
		return this.topicTitle ;
	}
			
			
//内容CONTENT_
@Column(name="CMS_CONTENT_")
	private String cmsContent;
	
	public void setCmsContent(String cmsContent) {
		this.cmsContent=cmsContent;
	}
	public String getCmsContent() {
		return this.cmsContent ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}