package com.ibm.old.v1.cloud.ibm_cms_message_content.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_cms_message_content 
 * IBM_消息内容IBM_CMS_MESSAGE_CONTENT的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_cms_message_content")
public class IbmCmsMessageContentT implements Serializable {

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
	 * IBM_消息内容主键IBM_CMS_MESSAGE_CONTENT_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CMS_MESSAGE_CONTENT_ID_")
	private String ibmCmsMessageContentId;
	public void setIbmCmsMessageContentId(String ibmCmsMessageContentId) {
		this.ibmCmsMessageContentId=ibmCmsMessageContentId;
	}
	public void setIbmCmsMessageContentId(Object ibmCmsMessageContentId) {
		if (ibmCmsMessageContentId != null) {
			this.ibmCmsMessageContentId = ibmCmsMessageContentId.toString();
		}
	}
	public String getIbmCmsMessageContentId() {
		return this.ibmCmsMessageContentId ;
	}

	/**
	 * 发送用户SEND_USER_ID_
	 */
	@Column(name="SEND_USER_ID_")
	private String sendUserId;
	public void setSendUserId(String sendUserId) {
		this.sendUserId=sendUserId;
	}
	public void setSendUserId(Object sendUserId) {
		if (sendUserId != null) {
			this.sendUserId = sendUserId.toString();
		}
	}
	public String getSendUserId() {
		return this.sendUserId ;
	}

	/**
	 * 接收用户RECEIVE_USER_ID_
	 */
	@Column(name="RECEIVE_USER_ID_")
	private String receiveUserId;
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId=receiveUserId;
	}
	public void setReceiveUserId(Object receiveUserId) {
		if (receiveUserId != null) {
			this.receiveUserId = receiveUserId.toString();
		}
	}
	public String getReceiveUserId() {
		return this.receiveUserId ;
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
	 * 回复状态REPLY_STATE_
	 */
	@Column(name="REPLY_STATE_")
	private String replyState;
	public void setReplyState(String replyState) {
		this.replyState=replyState;
	}
	public void setReplyState(Object replyState) {
		if (replyState != null) {
			this.replyState = replyState.toString();
		}
	}
	public String getReplyState() {
		return this.replyState ;
	}

	/**
	 * 阅读状态READING_STATE_
	 */
	@Column(name="READING_STATE_")
	private String readingState;
	public void setReadingState(String readingState) {
		this.readingState=readingState;
	}
	public void setReadingState(Object readingState) {
		if (readingState != null) {
			this.readingState = readingState.toString();
		}
	}
	public String getReadingState() {
		return this.readingState ;
	}

	/**
	 * 关键字KEYWORD_
	 */
	@Column(name="KEYWORD_")
	private String keyword;
	public void setKeyword(String keyword) {
		this.keyword=keyword;
	}
	public void setKeyword(Object keyword) {
		if (keyword != null) {
			this.keyword = keyword.toString();
		}
	}
	public String getKeyword() {
		return this.keyword ;
	}

	/**
	 * 通知类型MESSAGE_TYPE_
	 */
	@Column(name="MESSAGE_TYPE_")
	private String messageType;
	public void setMessageType(String messageType) {
		this.messageType=messageType;
	}
	public void setMessageType(Object messageType) {
		if (messageType != null) {
			this.messageType = messageType.toString();
		}
	}
	public String getMessageType() {
		return this.messageType ;
	}

	/**
	 * 创建时间CREATE_TIME_
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
	 * 创建时间数字型CREATE_TIME_LONG_
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
	 * 更新时间UPDATE_TIME_
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
	 * 更新时间数字型UPDATE_TIME_LONG_
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