package all.gen.cms_post.t.entity;

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
 * The persistent class for the database table cms_post 
 * CMS_帖子CMS_POST的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "cms_post")
public class CmsPostT implements Serializable {

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
		
			
//CMS_帖子主键CMS_POST_ID_
@Column(name="CMS_POST_ID_")
	private String cmsPostId;
	
	public void setCmsPostId(String cmsPostId) {
		this.cmsPostId=cmsPostId;
	}
	public String getCmsPostId() {
		return this.cmsPostId ;
	}
			
			
//CMS_主题主键CMS_TOPIC_ID_
@Column(name="CMS_TOPIC_ID_")
	private String cmsTopicId;
	
	public void setCmsTopicId(String cmsTopicId) {
		this.cmsTopicId=cmsTopicId;
	}
	public String getCmsTopicId() {
		return this.cmsTopicId ;
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
			
			
//发送用户名称SEND_USER_NAME_
@Column(name="SEND_USER_NAME_")
	private String sendUserName;
	
	public void setSendUserName(String sendUserName) {
		this.sendUserName=sendUserName;
	}
	public String getSendUserName() {
		return this.sendUserName ;
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
			
			
//接收用户名称RECEIVE_USER_NAME_
@Column(name="RECEIVE_USER_NAME_")
	private String receiveUserName;
	
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName=receiveUserName;
	}
	public String getReceiveUserName() {
		return this.receiveUserName ;
	}
			
			
//类型CMS_POST_TYPE_
@Column(name="CMS_POST_TYPE_")
	private String cmsPostType;
	
	public void setCmsPostType(String cmsPostType) {
		this.cmsPostType=cmsPostType;
	}
	public String getCmsPostType() {
		return this.cmsPostType ;
	}
			
			
//标题CMS_POST_TITLE_
@Column(name="CMS_POST_TITLE_")
	private String cmsPostTitle;
	
	public void setCmsPostTitle(String cmsPostTitle) {
		this.cmsPostTitle=cmsPostTitle;
	}
	public String getCmsPostTitle() {
		return this.cmsPostTitle ;
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
			
			
//查看地址URL_VIEW_
@Column(name="URL_VIEW_")
	private String urlView;
	
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
	}
			
			
//是否精华DIGEST_
@Column(name="DIGEST_")
	private String digest;
	
	public void setDigest(String digest) {
		this.digest=digest;
	}
	public String getDigest() {
		return this.digest ;
	}
			
			
//是否高亮HIGHLIGHT_
@Column(name="HIGHLIGHT_")
	private String highlight;
	
	public void setHighlight(String highlight) {
		this.highlight=highlight;
	}
	public String getHighlight() {
		return this.highlight ;
	}
			
			
//是否回复REPLY_
@Column(name="REPLY_")
	private String reply;
	
	public void setReply(String reply) {
		this.reply=reply;
	}
	public String getReply() {
		return this.reply ;
	}
			
			
//创建者CREATE_USER_
@Column(name="CREATE_USER_")
	private String createUser;
	
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
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
			
			
//更新者UPDATE_USER_
@Column(name="UPDATE_USER_")
	private String updateUser;
	
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
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