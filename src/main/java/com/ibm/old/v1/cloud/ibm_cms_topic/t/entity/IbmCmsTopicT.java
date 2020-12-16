package com.ibm.old.v1.cloud.ibm_cms_topic.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_cms_topic 
 * IBM_主题状态的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_cms_topic")
public class IbmCmsTopicT implements Serializable {

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
	 * IBM_主题状态主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CMS_TOPIC_ID_")
	private String ibmCmsTopicId;
	public void setIbmCmsTopicId(String ibmCmsTopicId) {
		this.ibmCmsTopicId=ibmCmsTopicId;
	}
	public void setIbmCmsTopicId(Object ibmCmsTopicId) {
		if (ibmCmsTopicId != null) {
			this.ibmCmsTopicId = ibmCmsTopicId.toString();
		}
	}
	public String getIbmCmsTopicId() {
		return this.ibmCmsTopicId ;
	}

	/**
	 * CMS_主题主键
	 */
	@Column(name="TOPIC_ID_")
	private String topicId;
	public void setTopicId(String topicId) {
		this.topicId=topicId;
	}
	public void setTopicId(Object topicId) {
		if (topicId != null) {
			this.topicId = topicId.toString();
		}
	}
	public String getTopicId() {
		return this.topicId ;
	}

	/**
	 * 用户主键
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			this.userId = userId.toString();
		}
	}
	public String getUserId() {
		return this.userId ;
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
	 * 主题创建者名称
	 */
	@Column(name="TOPIC_CREATE_USER_NAME_")
	private String topicCreateUserName;
	public void setTopicCreateUserName(String topicCreateUserName) {
		this.topicCreateUserName=topicCreateUserName;
	}
	public void setTopicCreateUserName(Object topicCreateUserName) {
		if (topicCreateUserName != null) {
			this.topicCreateUserName = topicCreateUserName.toString();
		}
	}
	public String getTopicCreateUserName() {
		return this.topicCreateUserName ;
	}

	/**
	 * 主题创建时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="TOPIC_CREATE_TIME_")
	private Date topicCreateTime;
	public void setTopicCreateTime(Date topicCreateTime) {
		this.topicCreateTime=topicCreateTime;
	}
	public void setTopicCreateTime(Object topicCreateTime) throws ParseException {
		if (topicCreateTime != null) {
			if (topicCreateTime instanceof Date) {
				this.topicCreateTime= (Date) topicCreateTime;
			}else if (StringTool.isInteger(topicCreateTime.toString())) {
				this.topicCreateTime = new Date(Long.parseLong(topicCreateTime.toString()));
			}else {
				this.topicCreateTime = DateTool.getTime(topicCreateTime.toString());
			}
		}
	}
	public Date getTopicCreateTime() {
		return this.topicCreateTime ;
	}

	/**
	 * 主题创建时间数字型
	 */
	@Column(name="TOPIC_CREATE_TIME_LONG_")
	private Long topicCreateTimeLong;
	public void setTopicCreateTimeLong(Long topicCreateTimeLong) {
		this.topicCreateTimeLong=topicCreateTimeLong;
	}
	public void setTopicCreateTimeLong(Object topicCreateTimeLong) {
		if (topicCreateTimeLong != null) {
			if (topicCreateTimeLong instanceof Long) {
				this.topicCreateTimeLong= (Long) topicCreateTimeLong;
			}else if (StringTool.isInteger(topicCreateTimeLong.toString())) {
				this.topicCreateTimeLong = Long.parseLong(topicCreateTimeLong.toString());
			}
		}
	}
	public Long getTopicCreateTimeLong() {
		return this.topicCreateTimeLong ;
	}

	/**
	 * 阅读状态
	 */
	@Column(name="READ_STATE_")
	private Byte readState;
	public void setReadState(Byte readState) {
		this.readState=readState;
	}
	public Byte getReadState() {
		return this.readState ;
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