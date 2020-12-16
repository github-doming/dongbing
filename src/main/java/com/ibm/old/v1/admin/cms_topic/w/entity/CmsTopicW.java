package com.ibm.old.v1.admin.cms_topic.w.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table cms_topic 
 * CMS_主题CMS_TOPIC的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "cms_topic")
public class CmsTopicW implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public void setIdx(Object idx) {
		if (idx != null) {
			if (idx instanceof Long) {
				this.idx= (Long) idx;
			}else if (StringTool.isInteger(idx.toString())) {
				this.idx = Long.parseLong(idx.toString());
			}
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * CMS_主题主键CMS_TOPIC_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CMS_TOPIC_ID_")
	private String cmsTopicId;
	public void setCmsTopicId(String cmsTopicId) {
		this.cmsTopicId=cmsTopicId;
	}
	public void setCmsTopicId(Object cmsTopicId) {
		if (cmsTopicId != null) {
			this.cmsTopicId = cmsTopicId.toString();
		}
	}
	public String getCmsTopicId() {
		return this.cmsTopicId ;
	}

	/**
	 * 类型
	 */
	@Column(name="CMS_TOPIC_TYPE_")
	private String cmsTopicType;
	public void setCmsTopicType(String cmsTopicType) {
		this.cmsTopicType=cmsTopicType;
	}
	public void setCmsTopicType(Object cmsTopicType) {
		if (cmsTopicType != null) {
			this.cmsTopicType = cmsTopicType.toString();
		}
	}
	public String getCmsTopicType() {
		return this.cmsTopicType ;
	}

	/**
	 * 标题TITLE_
	 */
	@Column(name="TITLE_")
	private String title;
	public void setTitle(String title) {
		this.title=title;
	}
	public void setTitle(Object title) {
		if (title != null) {
			this.title = title.toString();
		}
	}
	public String getTitle() {
		return this.title ;
	}

	/**
	 * 内容CONTENT_
	 */
	@Column(name="CONTENT_")
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public void setContent(Object content) {
		if (content != null) {
			this.content = content.toString();
		}
	}
	public String getContent() {
		return this.content ;
	}

	/**
	 * 查看地址URL_VIEW_
	 */
	@Column(name="URL_VIEW_")
	private String urlView;
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public void setUrlView(Object urlView) {
		if (urlView != null) {
			this.urlView = urlView.toString();
		}
	}
	public String getUrlView() {
		return this.urlView ;
	}

	/**
	 * 是否精华DIGEST_
	 */
	@Column(name="DIGEST_")
	private String digest;
	public void setDigest(String digest) {
		this.digest=digest;
	}
	public void setDigest(Object digest) {
		if (digest != null) {
			this.digest = digest.toString();
		}
	}
	public String getDigest() {
		return this.digest ;
	}

	/**
	 * 是否高亮HIGHLIGHT_
	 */
	@Column(name="HIGHLIGHT_")
	private String highlight;
	public void setHighlight(String highlight) {
		this.highlight=highlight;
	}
	public void setHighlight(Object highlight) {
		if (highlight != null) {
			this.highlight = highlight.toString();
		}
	}
	public String getHighlight() {
		return this.highlight ;
	}

	/**
	 * 是否回复REPLY_
	 */
	@Column(name="REPLY_")
	private String reply;
	public void setReply(String reply) {
		this.reply=reply;
	}
	public void setReply(Object reply) {
		if (reply != null) {
			this.reply = reply.toString();
		}
	}
	public String getReply() {
		return this.reply ;
	}

	/**
	 * 关键字KEYWORDS_
	 */
	@Column(name="KEYWORDS_")
	private String keywords;
	public void setKeywords(String keywords) {
		this.keywords=keywords;
	}
	public void setKeywords(Object keywords) {
		if (keywords != null) {
			this.keywords = keywords.toString();
		}
	}
	public String getKeywords() {
		return this.keywords ;
	}

	/**
	 * 发送用户ID SEND_USER_
	 */
	@Column(name="SEND_USER_")
	private String sendUser;
	public void setSendUser(String sendUser) {
		this.sendUser=sendUser;
	}
	public void setSendUser(Object sendUser) {
		if (sendUser != null) {
			this.sendUser = sendUser.toString();
		}
	}
	public String getSendUser() {
		return this.sendUser ;
	}

	/**
	 * 接收用户ID RECEIVE_USER_
	 */
	@Column(name="RECEIVE_USER_")
	private String receiveUser;
	public void setReceiveUser(String receiveUser) {
		this.receiveUser=receiveUser;
	}
	public void setReceiveUser(Object receiveUser) {
		if (receiveUser != null) {
			this.receiveUser = receiveUser.toString();
		}
	}
	public String getReceiveUser() {
		return this.receiveUser ;
	}

	/**
	 * 创建者CREATE_USER_
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}
	}
	public String getCreateUser() {
		return this.createUser ;
	}

	/**
	 * 创建者名称CREATE_USER_NAME_
	 */
	@Column(name="CREATE_USER_NAME_")
	private String createUserName;
	public void setCreateUserName(String createUserName) {
		this.createUserName=createUserName;
	}
	public void setCreateUserName(Object createUserName) {
		if (createUserName != null) {
			this.createUserName = createUserName.toString();
		}
	}
	public String getCreateUserName() {
		return this.createUserName ;
	}

	/**
	 * 创建时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME_")
	private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public void setCreateTime(Object createTime) throws ParseException {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间
	 */
	@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong= (Long) createTimeLong;
			}else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}

	/**
	 * 更新者UPDATE_USER_
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}

	/**
	 * 更新时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME_")
	private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTime(Object updateTime) throws ParseException {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间
	 */
	@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong= (Long) updateTimeLong;
			}else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name="STATE_")
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		}
	}
	public String getState() {
		return this.state ;
	}

	/**
	 * 描述
	 */
	@Column(name="DESC_")
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 * @return 表名
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}